package com.eollse.app;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;

import com.eollse.util.AreaTreeUtil;
import com.eollse.util.SMSSendUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.action.CommonAction;
import com.eollse.bo.AreaBo;
import com.eollse.bo.GridStaffBo;
import com.eollse.bo.LogBo;
import com.eollse.bo.RoleBo;
import com.eollse.bo.UserBo;
import com.eollse.po.Area;
import com.eollse.po.Role;
import com.eollse.po.User;
import com.eollse.util.HttpClientUtil;

@Controller
@RequestMapping("/app/user")
public class UserAppAction extends CommonAction {

    @Autowired
    private UserBo userBo;
    @Autowired
    private RoleBo roleBo;
    @Autowired
    private GridStaffBo gridStaffBo;
    @Autowired
    private AreaBo areaBo;
    @Autowired
    private LogBo logBo;

    /**
     * 获取手机验证码
     *
     * @param u       ==> userName 用户名
     * @param u       ==> password 密码
     * @param session 验证码session
     * @return {\"statusCode\":200,\"message\":\"验证码已发送至你的手机，请注意查收！\",\"captcha\":\""+captcha+"\"}
     */
    @RequestMapping(value = "/getPhoneCaptcha", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getPhoneCaptcha(User u, HttpSession session, HttpServletRequest request) {
        User user = this.userBo.validateUser(u);
        SMSSendUtil smsSendUtil = new SMSSendUtil();
        String mobileTel = user.getMobileTel();
        String captcha = null;
        if (user != null) {
            String oldCaptcha = (String) session.getAttribute("captcha");
            String logIP = getIpAddr(request);
            if (oldCaptcha != null && !oldCaptcha.equals("")) {
                Integer count = this.logBo.getRequestCount(user.getUserId(), logIP);
                if (count > 2) {
                    return "{\"statusCode\":500,\"message\":\"你的操作次数过于频繁，请5分钟后再试！\"}";
                }
            }
            if (!mobileTel.equals("") && mobileTel != null) {
                String msg = smsSendUtil.sendPhoneCode(mobileTel);
                JSONObject jsonObject = JSONObject.fromObject(msg);
                if (jsonObject.getString("status").equals("200")) {
                    session.setAttribute("captcha", jsonObject.getString("captcha"));
                    session.setMaxInactiveInterval(300);
                    MDC.put("userId", user.getUserId());
                    MDC.put("logIP", logIP);
                    this.logger.info("获取验证码");
                    return "{\"statusCode\":200,\"message\":\"" + jsonObject.getString("msg") + "\",\"captcha\":\"" + captcha + "\"}";
                } else {
                    return "{\"statusCode\":201,\"message\":\"" + jsonObject.getString("msg") + "\",\"captcha\":\"000000\"}";
                }
            } else {
                return "{\"statusCode\":202,\"message\":\"你的账号暂未绑定手机号码，请联系管理员！\"}";
            }
        } else {
            return "{\"statusCode\":300,\"message\":\"用户名或密码错误，请重新输入！\",\"captcha\":\"000000\"}";
        }
    }

    /**
     * 登录
     *
     * @param code     验证码
     * @param u        ==> userName&password 用户名&密码
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getOneUserById(String code, User u, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String captcha = (String) session.getAttribute("captcha");
        if (code == null)
            code = "";
//        if (code.equals(captcha)) {
        User user = this.userBo.validateUser(u);
        if (user != null) {
            String loginIP = getIpAddr(request);
            user.setUser_last_login(new Date());
            user.setUser_login_ip(loginIP);
            this.userBo.updateUserLoginInfo(loginIP, new Date(), user.getUserId());
            Role role = this.roleBo.getRoleById(user.getRoleId());
            if (role != null) {
                user.setRole(role);
            }
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(72 * 60 * 60);
//                this.saveUserAreaSession(session);
            MDC.put("userId", user.getUserId());
            MDC.put("logIP", loginIP);
            logger.info("用户登录成功（App）");
            return "{\"statusCode\":200,\"message\":\"登陆成功！\",\"roleLevel\":" + user.getRole().getRoleLevel() + ",\"areaName\":\"" + user.getArea().getAreaName() + "\"}";
        } else {
            return "{\"statusCode\":300,\"message\":\"用户名或密码错误！\"}";
        }
//        } else {
//            return "{\"statusCode\":300,\"message\":\"验证码错误！\"}";
//        }
    }

    /**
     * 保存App用户区域session
     *
     * @param session 用户session的areaId
     * @return
     */
    @RequestMapping("/saveUserAreaSession")
    @ResponseBody
    public void saveUserAreaSession(HttpSession session) {
        User s_user = (User) session.getAttribute("user");
        List<Area> areas = this.areaBo.getAllAreaByLevel(s_user.getAreaId());
        AreaTreeUtil areaTreeUtil = new AreaTreeUtil();
        areaTreeUtil.treeMenuList(areas, s_user.getArea().getAreaCode());
        areaTreeUtil.getAreaIds().add(s_user.getAreaId());
        session.setAttribute("areaIds", areaTreeUtil.getAreaIds());
    }


    /**
     * App用户退出登录
     *
     * @param request
     */
    @RequestMapping(value = "/loginOut", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    private String loginOut(HttpServletRequest request, HttpSession session) {
        //存储日志信息到数据库
        logger.info("退出系统成功！");
        // 清除session
        Enumeration<String> em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        request.getSession().invalidate();
        return "{\"statusCode\":200,\"message\":\"退出系统成功！\"}";
    }

}
