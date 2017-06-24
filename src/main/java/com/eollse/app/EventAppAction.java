package com.eollse.app;

/**
 * author 李宁财
 * content App事件控制器
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.eollse.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eollse.action.CommonAction;
import com.eollse.bo.EventBo;
import com.eollse.bo.GridBo;
import com.eollse.bo.GridStaffBo;
import com.eollse.bo.UserBo;
import com.eollse.po.Event;
import com.eollse.po.EventLogApp;
import com.eollse.po.Event_level;
import com.eollse.po.Event_source_type;
import com.eollse.po.Event_type;
import com.eollse.po.User;

@Controller
@RequestMapping("/app/event")
public class EventAppAction extends CommonAction {

    @Autowired
    private EventBo eventBo;
    @Autowired
    private UserBo userBo;
    @Autowired
    private GridBo gridBo;
    @Autowired
    private GridStaffBo gridStaffBo;


    /**
     * 获取app用户所有相关事件/根据名字或者电话号码筛选事件
     *
     * @param session
     * @param field       搜索值
     * @param pageSize    分页大小
     * @param pageCurrent 当前页码
     * @return
     */
    @RequestMapping(value = "/getAllEventInfoById", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getOneStaffBlogById(Integer pageSize, Integer pageCurrent, String field, String eventStatus, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Integer> areaIds = (List<Integer>) session.getAttribute("areaIds");
        List<Integer> gridStaffIds = new ArrayList<Integer>();
        Map<String, Object> events = new HashMap<String, Object>();
        if (user.getRole().getRoleLevel() < 6) {
//			areaIds=null;
            List<Integer> gridIds = this.gridBo.getAllGridIdsByAreaId(areaIds);
            if (gridIds.size() > 0) {
                gridStaffIds = this.gridStaffBo.getAllGridStaffIdByAreaId(gridIds);
            }
        } else {
            Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
            if (user.getRole().getRoleName().equals("社区网格员")) {
                gridStaffIds.add(gridStaffId);
            } else if (user.getRole().getRoleName().equals("社区网格长")) {
                List<Integer> gridIds = new ArrayList<Integer>();
                gridIds.add(this.gridStaffBo.getGridIdByGridStaffId(gridStaffId));
                gridStaffIds = this.gridStaffBo.getAllGridStaffIdByAreaId(gridIds);
            }
        }
        Integer type = 0;
        if (!type.equals("") && type != null) {
            type = Integer.parseInt(eventStatus);
        }
        events = this.eventBo.getAllEventInfoById(gridStaffIds, field, type, pageSize, pageCurrent);
        System.out.println(events);
        return "{\"statusCode\":200,\"data\":" + this.createPageJSONString(events) + "}";
    }

    /**
     * 获取某个事件的跟踪记录
     *
     * @param eventId 事件Id/String类型
     * @return
     */
    @RequestMapping(value = "/getOneEventLogInfoById", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getOneEventLogInfoById(String eventId) {
        List<EventLogApp> lists = this.eventBo.getOneEventLogInfoById(Integer.parseInt(eventId));
        JsonConfig jf = new JsonConfig();
        DateJsonValueProcessor djvp = new DateJsonValueProcessor();
        djvp.setFormate("yyyy-MM-dd HH:mm:ss");
        jf.registerJsonValueProcessor(java.util.Date.class, djvp);
        if (lists.size() > 0) {
            return "{\"statusCode\":200,\"data\":" + JSONArray.fromObject(lists, jf) + "}";
        } else {
            return "{\"statusCode\":300,\"message\":\"暂无处理相关信息！\"}";
        }
    }

    /**
     * 添加事件方法
     *
     * @param eventLevelId 事件等级id
     * @param sourceTypeId 事件来源id
     * @param eventTypeId  事件类型id
     * @param eventTitle   事件标题
     * @param eventContent 事件内容
     * @param isImportant  重点督办 1 ==>是，0 ==>否
     * @param eventPic     事件相关图片
     * @return
     */
    @RequestMapping(value = "/addNewEventInfo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addNewEventInfo(Event event, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.getRole().getRoleLevel() < 6) {
            return "{\"statusCode\":300,\"message\":\"你的账户暂无此权限！\"}";
        } else {
            Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
            event.setAreaId(user.getAreaId());
            event.setGridId(this.gridStaffBo.getGridIdByGridStaffId(gridStaffId));
            event.setGridStaffId(gridStaffId);
            event.setEditEventDate(new Date());
            Integer result = this.eventBo.saveEvent(event);
            if (result > 0) {
                logger.info("成功添加事件" + event.getEventTitle() + "！");
                return "{\"statusCode\":200,\"message\":\"添加事件成功！\"}";
            } else {
                logger.error("添加事件" + event.getEventTitle() + "失败！");
                return "{\"statusCode\":300,\"message\":\"添加事件失败！\"}";
            }
        }
    }

    /**
     * 获取所有事件等级
     *
     * @return 事件等级集合
     */
    @RequestMapping(value = "/getAllEventLevel", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAllEventLevel() {
        List<Event_level> lists = this.eventBo.getAllEventLevel();
        if (lists.size() > 0) {
            return "{\"statusCode\":200,\"data\":" + JSONArray.fromObject(lists) + "}";
        } else {
            return "{\"statusCode\":300,\"data\":null}";
        }
    }

    /**
     * 获取所有事件来源
     *
     * @return 事件来源集合
     */
    @RequestMapping(value = "/getAllEventSourceType", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAllType() {
        List<Event_source_type> lists = this.eventBo.getAllType();
        if (lists.size() > 0) {
            return "{\"statusCode\":200,\"data\":" + JSONArray.fromObject(lists) + "}";
        } else {
            return "{\"statusCode\":300,\"data\":null}";
        }
    }

    /**
     * 获取所有事件类型
     *
     * @return
     */
    @RequestMapping(value = "/getAllEventType", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getAllEventType() {
        List<Event_type> lists = this.eventBo.getAllEventType();
        if (lists.size() > 0) {
            return "{\"statusCode\":200,\"data\":" + JSONArray.fromObject(lists) + "}";
        } else {
            return "{\"statusCode\":300,\"data\":null}";
        }
    }

    /**
     * 上传App事件图片信息
     *
     * @param request
     * @param mainName 用户上传图片文件夹名称 not null
     * @param picName  如果是上传多张图片应该传图片名称用户生成文件夹
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadEventImg", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadClockInImg(@RequestParam("mFile") MultipartFile[] files, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.getRole().getRoleLevel() < 6) {
            return "{\"statusCode\":300,\"message\":\"你的账户暂无此权限！\"}";
        } else {
            return this.mutilFileUpload(files, "app/event/", user.getUserName() + "/");
        }
    }


}
