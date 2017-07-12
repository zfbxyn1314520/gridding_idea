package com.eollse.action;

import com.eollse.bo.GridStaffBo;
import com.eollse.bo.LeavesBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eollse on 2017/7/12.
 */
@Controller
@RequestMapping("/leaves")
public class LeavesAction extends CommonAction {

    @Autowired
    private LeavesBo leavesBo;
    @Autowired
    private GridStaffBo gridStaffBo;


    /**
     * 获取一个权限区域下所有请假申请
     * @param session
     * @param pageSize 页面大小
     * @param pageCurrent 页面当前页
     * @return
     */
    @RequestMapping("/getAllLeavesByAreaIds")
    @ResponseBody
    public String getAllLeavesByAreaIds(HttpSession session, Integer pageSize, Integer pageCurrent) {
        List<Integer> areaIds = (List<Integer>) session.getAttribute("areaIds");
        Map<String, Object> map = new HashMap<String, Object>();
        if (areaIds.size() > 0) {
            map = this.leavesBo.getAllLeavesByGsIds(areaIds, pageSize, pageCurrent);
        } else {
            map.put("totalRow", 0);
            map.put("pageCurrent", 1);
            map.put("list", areaIds);
        }
        return createPageJSONString(map);
    }

}
