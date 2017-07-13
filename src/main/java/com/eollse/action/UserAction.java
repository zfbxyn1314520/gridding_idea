package com.eollse.action;

/**
 * author 李宁财
 * content 用户控制器
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.eollse.util.AreaTreeUtil;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AreaBo;
import com.eollse.bo.GridStaffBo;
import com.eollse.bo.LogBo;
import com.eollse.bo.MenuBo;
import com.eollse.bo.RoleBo;
import com.eollse.bo.UserBo;
import com.eollse.po.Area;
import com.eollse.po.Menu;
import com.eollse.po.Role;
import com.eollse.po.Role_per;
import com.eollse.po.User;
import com.eollse.util.HttpClientUtil;

@Controller
@RequestMapping("/user")
public class UserAction extends CommonAction {

    @Autowired
    private UserBo userBo;
    @Autowired
    private MenuBo menuBo;
    @Autowired
    private RoleBo roleBo;
    @Autowired
    private AreaBo areaBo;
    @Autowired
    private LogBo logBo;
    @Autowired
    private GridStaffBo gridStaffBo;


    /**
     * 用户登录
     *
     * @param request
     * @param u       ==> userName 用户名
     * @param u       ==> password 密码
     * @param code    验证码
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public String getOneUserById(String code, User u, HttpServletRequest request, HttpSession session) {
        String captcha = (String) session.getAttribute("captcha");
        if (code.equals(captcha)) {
            User user = this.userBo.validateUser(u);
            if (user != null) {
                String loginIP = getIpAddr(request);
                user.setUser_last_login(new Date());
                user.setUser_login_ip(loginIP);
                this.userBo.updateUserLoginInfo(getIpAddr(request), new Date(), user.getUserId());
                Role role = this.roleBo.getRoleById(user.getRoleId());
                if (role != null) {
                    user.setRole(role);
                }
                session.setAttribute("user", user);
                this.saveUserAreaSession(session);
                System.out.println(session.getAttribute("user"));
                session.setMaxInactiveInterval(60);
                MDC.put("userId", user.getUserId());
                MDC.put("logIP", loginIP);
                this.logger.info("用户登录成功（PC）");
                return "{\"statusCode\":200,\"message\":\"用户登录成功！\"}";
            } else {
                return "{\"statusCode\":300,\"message\":\"用户名或密码错误，请重新输入！\"}";
            }
        } else {
            return "{\"statusCode\":300,\"message\":\"验证码错误！\"}";
        }
    }

    /**
     * 获取手机验证码
     *
     * @param u       用户实体类
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPhoneCaptcha")
    @ResponseBody
    public String getPhoneCaptcha(User u, HttpSession session, HttpServletRequest request) {
        User user = this.userBo.validateUser(u);
        String captcha = null;
        if (user != null) {

            Integer roleLevel = this.roleBo.getRoleById(user.getRoleId()).getRoleLevel();
            if (roleLevel > 5) {
                return "{\"statusCode\":201,\"message\":\"你的账号暂无此权限登录本系统！\"}";
            }

            String oldCaptcha = (String) session.getAttribute("captcha");
            String logIP = getIpAddr(request);

            if (oldCaptcha != null) {
                Integer count = this.logBo.getRequestCount(user.getUserId(), logIP);
                if (count > 2) {
                    return "{\"statusCode\":500,\"message\":\"你的操作次数过于频繁，请稍后再试！\"}";
                }
            }

            Random random = new Random();
            HttpClientUtil httpClient = new HttpClientUtil();
            Map<String, String> map = new HashMap<String, String>();
            captcha = String.valueOf(random.nextInt(999999) % (900000) + 100000);
            System.out.println("captcha=========" + captcha);

//			代码调试
			map.put("type","shentong");
			map.put("postid","3327110080673");
			String rows=httpClient.post("http://www.kuaidi100.com/query","gb2312",map);
			System.out.println("rows:"+rows);
            session.setAttribute("captcha", captcha);
            session.setMaxInactiveInterval(300);
            MDC.put("userId", user.getUserId());
            MDC.put("logIP", logIP);
            this.logger.info("获取验证码");
            return "{\"statusCode\":200,\"message\":\"验证码已发送至你的手机，请注意查收！\"}";

//			发送手机验证码
//			map.put("CorpID","CQLKY00729");
//	        map.put("Pwd","zxkj@666");
//	        map.put("Mobile",user.getMobileTel());
//	        System.out.println(user.getMobileTel());
//			map.put("Content","您的手机验证码为："+captcha+"，有效时间为3分钟。请勿向任何单位及个人泄露。如非本人操作，请忽略本消息。");
//	        String status=httpClient.post("http://yzm.mb345.com/ws/BatchSend2.aspx","gb2312",map);
//			Integer code = Integer.parseInt(status);
//			if(code > 0){
//				session.setAttribute("captcha", captcha);
//	        	session.setMaxInactiveInterval(300);
//	        	MDC.put("userId", user.getUserId());
//            	MDC.put("logIP", logIP);
//	        	this.logger.info("获取验证码");
//				return "{\"statusCode\":200,\"message\":\"验证码已发送至你的手机，请注意查收！\"}";
//			}else{
//				return "{\"statusCode\":201,\"message\":\""+this.getCaptchaStatus(code)+"\"}";
//			}
        } else {
            return "{\"statusCode\":300,\"message\":\"用户名或密码错误，请重新输入！\"}";
        }
    }


    /**
     * 用户退出登录
     *
     * @param request
     */
    @RequestMapping(value = "/loginOut")
    @ResponseBody
    private String loginOut(HttpServletRequest request, HttpSession session) {
        //存储日志信息到数据库
        logger.info("退出系统！");
        // 清除session
        Enumeration<String> em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }
        request.getSession().invalidate();
        //项目域名地址
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
        return tempContextUrl;
    }

    /**
     * 获取用户权限菜单
     */
    @RequestMapping("/getUserPerMenu")
    @ResponseBody
    public List<Menu> getUserPerMenu(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Menu> menus = this.menuBo.getAllMenu();
            Integer menuId = 0;
            Iterator<Menu> lists = menus.iterator();
            while (lists.hasNext()) {
                Menu menu = lists.next();
                if (menu.getParentMenuId() != null) {
                    menuId = menu.getMenuId();
                    List<Role_per> rolePer = this.userBo.getUserPerMenu(user, menuId);
                    if (rolePer.size() == 0) {
                        lists.remove();
                    }
                    menuId = 0;
                }
            }
            //删除下没有二级菜单的一级菜单
            Iterator<Menu> list = menus.iterator();
            while (list.hasNext()) {
                Menu menu = list.next();
                if (menu.getParentMenuId() == null) {
                    Integer row = 0;
                    if (menu.getMenuName().equals("我的主页")) {
                        continue;
                    }
                    for (int i = 0; i < menus.size(); i++) {
                        if (menus.get(i).getParentMenuId() == menu.getMenuId()) {
                            row++;
                        }
                    }
                    if (row == 0) {
                        list.remove();
                    } else {
                        row = 0;
                    }
                }
            }
            return menus;
        } else {
            return null;
        }
    }


    /**
     * 保存用户区域session
     *
     * @param session 用户session的areaId
     * @return
     */
    @RequestMapping("/saveUserAreaSession")
    @ResponseBody
    public void saveUserAreaSession(HttpSession session) {
        AreaTreeUtil areaTreeUtil;
        List<Area> areas;
        User s_user = (User) session.getAttribute("user");
        List<Integer> s_areaId = (List<Integer>) session.getAttribute("areaIds");
        if (s_areaId == null || s_areaId.isEmpty() || s_areaId.size() == 0) {
            areaTreeUtil = new AreaTreeUtil();
            areas = this.areaBo.getAllAreaByLevel(s_user.getAreaId());
            areaTreeUtil.treeMenuList(areas, s_user.getArea().getAreaCode());
            areaTreeUtil.getAreaIds().add(s_user.getAreaId());
            session.setAttribute("areaIds", areaTreeUtil.getAreaIds());
        }
    }


    /**
     * 获取用户所在区域下的所有用户
     *
     * @param session
     * @param pageSize    分页大小
     * @param pageCurrent 当前页页码
     * @return
     */
    @RequestMapping("/getAllUserByAreaId")
    @ResponseBody
    public String getAllUserByAreaId(HttpSession session, Integer pageSize, Integer pageCurrent) {
        User user = (User) session.getAttribute("user");
        List<Integer> areaIds = (List<Integer>) session.getAttribute("areaIds");
        if (user != null) {
            Map<String, Object> map = this.userBo.getAllUserByAreaId(areaIds, pageSize, pageCurrent);
            String content = this.createPageJSONString(map);
            return content;
        } else {
            return null;
        }
    }

    /**
     * 编辑用户信息时验证用户名是否重复
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/validateUsername", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String validateUsername(User user) {
        List<User> u = this.userBo.validateUsername(user);
        return u.size() > 0 ? "{\"error\":\"用户名已被占用！\"}" : "{\"ok\": \"\"}";
    }

    /**
     * 根据用户区域Id获取所有区域生成下拉树ztree
     *
     * @param session
     * @return 区域集合
     */
    @RequestMapping("/getAreaMenuById")
    @ResponseBody
    public List<Area> getAreaMenuById(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Integer> areaIds = (List<Integer>) session.getAttribute("areaIds");
        if (user != null) {
            List<Area> areas = this.areaBo.getAreaMenuById(areaIds);
            return areas;
        } else {
            return null;
        }

    }

    /**
     * 添加用户信息
     *
     * @param user    用户实体类
     * @param request
     * @param session 用户登陆session
     * @return
     */
    @RequestMapping("/addNewUser")
    @ResponseBody
    public String addNewUser(User user, HttpServletRequest request, HttpSession session) {
        User s_user = (User) session.getAttribute("user");
        user.setEditUserName(s_user.getUserName());
        user.setEditUserDate(new Date());
        Integer result = this.userBo.addNewUser(user);
        if (result > 0) {
            logger.info("成功添加用户" + user.getUserName() + "！");
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 添加App用户信息
     *
     * @param user    用户实体类
     * @param request
     * @param session 用户登陆session
     * @return
     */
    @RequestMapping(value = "/addNewAppUser", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String addNewAppUser(User user, Integer gridStaffId, HttpServletRequest request, HttpSession session) {
        User s_user = (User) session.getAttribute("user");
        user.setEditUserName(s_user.getUserName());
        user.setEditUserDate(new Date());
        Integer result = this.userBo.addNewUser(user);
        if (result > 0) {
            Integer userId = this.userBo.getLastUserId();
            Integer row = this.userBo.addUserStaffInfo(userId, gridStaffId);
            if (row > 0) {
                this.gridStaffBo.alterAppUserStatusOfStaff(1, gridStaffId);
                logger.info("成功添加用户" + user.getUserName() + "！");
                return "{\"statusCode\":200,\"message\":\"成功添加App用户" + user.getUserName() + "！\"}";
            } else {
                this.userBo.deleteUserByUserId(userId);
                logger.error("添加App用户" + user.getUserName() + "失败！");
                return "{\"statusCode\":300,\"message\":\"添加App用户失败！\"}";
            }
        } else {
            logger.error("添加App用户" + user.getUserName() + "失败！");
            return "{\"statusCode\":300,\"message\":\"添加App用户失败！\"}";
        }
    }


    /**
     * 获取App用户详细信息
     *
     * @param gridStaffId 网管员Id
     * @return
     */
    @RequestMapping("/getAppUserInfo")
    @ResponseBody
    public User getAppUserInfo(Integer gridStaffId) {
        Integer userId = this.userBo.getUserIdOfAppUser(gridStaffId);
        return this.userBo.getAppUserInfo(userId);
    }

    /**
     * 获取一个用户的详细信息
     *
     * @param user 用户实体类
     * @return user 用户实体类
     */
    @RequestMapping("/getOneUserInfo")
    @ResponseBody
    public User getOneUserInfo(User user) {
        List<User> users = this.userBo.getOneUserInfo(user);
        List<Area> areas = this.areaBo.getAreaByAreaId(users.get(0).getAreaId());
        Role role = this.roleBo.getRoleById(users.get(0).getRoleId());
        Integer level = areas.get(0).getAreaLevel();
        String areaStr = areas.get(0).getAreaName();
        List<String> areaName = new ArrayList<String>();
        Long areaCode = areas.get(0).getAreaParentCode();
        if (level > 1) {
            for (int i = 0; i < level - 1; i++) {
                Area area = this.areaBo.getAreaByAreaCode(areaCode);
                if (area != null) {
                    areaCode = area.getAreaParentCode();
                    areaName.add(i, area.getAreaName());
                }
            }
            for (int i = 0; i < areaName.size(); i++) {
                if (areaName.get(i).equals("市辖区")
                        || areaName.get(i).equals("县")) {
                    continue;
                } else {
                    areaStr = areaName.get(i) + "," + areaStr;
                }
            }
            areas.get(0).setAreaName(areaStr);
        }
        users.get(0).setArea(areas.get(0));
        users.get(0).setRole(role);
        return users.get(0);
    }

    /**
     * 修改用户基本信息
     *
     * @param user    用户实体类
     * @param session
     * @return string “1”==>表示修改成功 “0”==>表示修改失败
     */
    @RequestMapping("/alterUserInfo")
    @ResponseBody
    public String alterUserInfo(User user, HttpSession session) {
        User s_user = (User) session.getAttribute("user");
        user.setEditUserName(s_user.getUserName());
        user.setEditUserDate(new Date());
        Integer result = this.userBo.alterUserInfo(user);
        if (result > 0) {
            logger.info("成功修改用户" + user.getUserName() + "的基本信息！");
        }
        return result > 0 ? "1" : "0";
    }


    /**
     * 修改App用户基本信息
     *
     * @param user    用户实体类
     * @param session
     * @return string “1”==>表示修改成功 “0”==>表示修改失败
     */
    @RequestMapping(value = "/alterAppUserInfo", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String alterAppUserInfo(User user, HttpSession session) {
        User s_user = (User) session.getAttribute("user");
        user.setEditUserName(s_user.getUserName());
        user.setEditUserDate(new Date());
        Integer result = this.userBo.alterUserInfo(user);
        if (result > 0) {
            logger.info("成功修改App用户" + user.getUserName() + "的基本信息！");
            return "{\"statusCode\":200,\"message\":\"成功修改App用户" + user.getUserName() + "的基本信息!\"}";
        } else {
            logger.error("修改App用户" + user.getUserName() + "的基本信息失败！");
            return "{\"statusCode\":300,\"message\":\"修改App用户" + user.getUserName() + "的基本信息失败！\"}";
        }
    }


    /**
     * 修改账号启用状态
     *
     * @param user 用户实体类
     */
    @RequestMapping("/alterAuditStatus")
    @ResponseBody
    public void alterAuditStatus(User user) {
        this.userBo.alterAuditStatus(user);
    }

    /**
     * 根据id删除用户信息
     *
     * @param user    ==> userId ==> 用户id
     * @param isStaff 是否是网格员用户
     * @return
     */
    @RequestMapping(value = "/deleteUserByIds", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String deleteUserByIds(User user, boolean isStaff) {
        if (isStaff) {
            Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
            this.gridStaffBo.alterAppUserStatusOfStaff(0, gridStaffId);
            this.userBo.deleteUserStaffById(user.getUserId());
        }
        try {
            this.logBo.deleteLogByUserId(user.getUserId());
        } catch (Exception e) {
            logger.error("删除用户" + user.getUserName() + "的日志信息失败！");
            e.printStackTrace();
        }
        Integer result = this.userBo.deleteUserById(user.getUserId());
        if (result > 0) {
            logger.info("成功删除用户" + user.getUserName() + "的信息!");
            return "{\"statusCode\":200,\"message\":\"成功删除用户" + user.getUserName() + "的信息!\"}";
        } else {
            logger.error("删除用户" + user.getUserName() + "的信息失败！");
            return "{\"statusCode\":300,\"message\":\"删除用户" + user.getUserName() + "的信息失败！\"}";
        }
    }


    /**
     * 上传用户图片信息
     *
     * @param request
     * @param mainName 用户上传图片文件夹名称 not null
     * @param picName  如何是上传多张图片应该传图片名称用户生成文件夹
     * @return
     */
    @RequestMapping(value = "/uploadHeadIcon", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadHeadIcon(HttpServletRequest request, String mainName, String picName) {
        String path = this.fileUpload(request, mainName, picName);
        return path != null ? "{\"statusCode\":200,\"message\":\"上传成功！\",\"filename\":\"" + path + "\"}" : null;
    }

    /**
     * 获取某个角色下的所有用户
     *
     * @param session
     * @param role    角色id
     * @return
     */
    @RequestMapping("/getUsersByRoleId")
    @ResponseBody
    public List<User> getUsersByRoleId(HttpSession session, Role role) {
        User user = (User) session.getAttribute("user");
        List<Integer> areaIds = (List<Integer>) session.getAttribute("areaIds");
        if (user != null) {
            List<User> users = this.userBo.userCountByRoleId(role, areaIds);
            return users.size() > 0 ? users : null;
        } else {
            return null;
        }
    }


}
