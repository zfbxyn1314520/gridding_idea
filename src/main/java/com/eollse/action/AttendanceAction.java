package com.eollse.action;

import com.eollse.bo.AttendanceBo;
import com.eollse.bo.GridStaffBo;
import com.eollse.bo.UserBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by eollse on 2017/7/11.
 */
@Controller
@RequestMapping("/work")
public class AttendanceAction extends CommonAction {

    @Autowired
    private AttendanceBo attendanceBo;
    @Autowired
    private UserBo userBo;
    @Autowired
    private GridStaffBo gridStaffBo;

    @RequestMapping("/getAllAttendanceLogByAreaId")
    @ResponseBody
    public String getAllAttendanceLogByAreaId(HttpSession session, Integer pageSize, Integer pageCurrent) {
        List<Integer> areaIds = (List<Integer>) session.getAttribute("areaIds");
        List<Integer> gridStaffIds = this.gridStaffBo.getAllGridStaffByAreaIds(areaIds);
        Map<String, Object> map = this.attendanceBo.getAllAttendanceLogByIds(gridStaffIds, pageSize, pageCurrent);
        return this.createPageJSONString(map);
    }

    @RequestMapping("/deleteWorkByIds")
    @ResponseBody
    public String deleteWorkByIds(String delId) {
        String[] delIds = delId.split(",");
        Integer result = this.attendanceBo.deleteWorkByIds(delIds);
        if (result > 0) {
            logger.info("成功删除考勤记录:" + delId.substring(0, delId.length() - 1));
            return "1";
        } else {
            logger.info("删除考勤记录:" + delId.substring(0, delId.length() - 1) + "失败");
            return "0";
        }

    }


}
