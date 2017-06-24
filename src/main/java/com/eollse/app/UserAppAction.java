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
     * @param userName 用户名
     * @param password 密码
     * @param session  验证码session
     * @return {\"statusCode\":200,\"message\":\"验证码已发送至你的手机，请注意查收！\",\"captcha\":\""+captcha+"\"}
     */
    @RequestMapping(value = "/getPhoneCaptcha", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getPhoneCaptcha(User u, HttpSession session, HttpServletRequest request) {
        User user = this.userBo.validateUser(u);
        String captcha = null;
        if (user != null) {
            String oldCaptcha = (String) session.getAttribute("captcha");
            String logIP = getIpAddr(request);
            if (oldCaptcha != null) {
                Integer count = this.logBo.getRequestCount(user.getUserId(), logIP);
                if (count > 2) {
                    return "{\"statusCode\":200,\"message\":\"你的操作过于频繁，请5分钟之后重新获取！\"}";
                }
            }

            Random random = new Random();
            HttpClientUtil httpClient = new HttpClientUtil();
            Map<String, String> map = new HashMap<String, String>();
            captcha = String.valueOf(random.nextInt(999999) % (900000) + 100000);

//			调试http请求
//            map.put("type", "shentong");
//            map.put("postid", "3327110080673");
//            String rows = httpClient.post("http://www.kuaidi100.com/query", "gb2312", map);
//            System.out.println("rows:" + rows);
            session.setAttribute("captcha", captcha);
            session.setMaxInactiveInterval(300);
            System.out.println("newCode=" + (String) session.getAttribute("captcha"));
            MDC.put("userId", user.getUserId());
            MDC.put("logIP", getIpAddr(request));
            this.logger.info("获取验证码");
            return "{\"statusCode\":200,\"message\":\"验证码已发送至你的手机，请注意查收！\",\"captcha\":\"" + captcha + "\"}";

//			发送手机验证码
//			map.put("CorpID","CQLKY00729");  
//	        map.put("Pwd","zxkj@666");
//	        map.put("Mobile",user.getMobileTel());  
//	        map.put("Content","您的验证码为"+captcha+"，有效时间5分钟。");
//	        String status=httpClient.post("http://yzm.mb345.com/ws/BatchSend2.aspx","gb2312",map);
//			Integer code = Integer.parseInt(status);
//			if(code > 0){
//				session.setAttribute("captcha", captcha);
//	        	session.setMaxInactiveInterval(300);
//	        	MDC.put("userId", user.getUserId());  
//            	MDC.put("logIP", logIP);
//	        	this.logger.info("获取验证码");
//				return "{\"statusCode\":200,\"message\":\"验证码已发送至你的手机，请注意查收！\",\"captcha\":\""+captcha+"\"}";
//			}else{
//				return "{\"statusCode\":201,\"message\":\""+this.getCaptchaStatus(code)+"\",\"captcha\":\"000000\"}";
//			}
        } else {
            return "{\"statusCode\":300,\"message\":\"用户名或密码错误，请重新输入！\",\"captcha\":\"000000\"}";
        }
    }

    /**
     * App验证用户登陆
     *
     * @param userName&password 用户名&密码
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/login", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getOneUserById(String code, User u, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String captcha = (String) session.getAttribute("captcha");
        if (code.equals(captcha)) {
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
                session.setMaxInactiveInterval(1800);
                MDC.put("userId", user.getUserId());
                MDC.put("logIP", loginIP);
                logger.info("用户登录成功（App）");
                return "{\"statusCode\":200,\"message\":\"登陆成功！\",\"roleLevel\":" + user.getRole().getRoleLevel() + ",\"areaName\":\"" + user.getArea().getAreaName() + "\"}";
            } else {
                return "{\"statusCode\":300,\"message\":\"用户名或密码错误！\"}";
            }
        } else {
            return "{\"statusCode\":300,\"message\":\"验证码错误！\"}";
        }
    }

    /**
     * 保存App用户区域session
     *
     * @param session 用户session的areaId
     * @return
     */
    @RequestMapping("/saveUserAreaSession")
    @ResponseBody
    public void saveUserAreaSession(HttpServletRequest request, HttpSession session) {
        User s_user = (User) session.getAttribute("user");
        if (this.getAreaIds().size() > 0) {
            this.getAreaIds().clear();
        }
        List<Area> area = this.areaBo.getAreaByAreaId(s_user.getAreaId());
        this.getAllAreaIdById(area.get(0).getAreaCode());
        session.setAttribute("areaIds", this.getAreaIds());
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
