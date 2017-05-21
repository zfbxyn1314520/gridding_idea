package com.eollse.app;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.action.CommonAction;
import com.eollse.bo.GridStaffBo;
import com.eollse.bo.LeavesBo;
import com.eollse.bo.UserBo;
import com.eollse.po.GridStaffApp;
import com.eollse.po.Leaves;
import com.eollse.po.Leaves_type;
import com.eollse.po.User;

/**
 * author 李宁财
 * content App请假控制器
 */

@Controller
@RequestMapping("/app/leaves")
public class LeavesAppAction extends CommonAction{
	
	@Autowired
	private LeavesBo leavesBo;
	@Autowired
	private UserBo userBo;
	@Autowired
	private GridStaffBo gridStaffBo;
	
	/**
	 * 获取所有请假类型
	 * @return
	 */
	@RequestMapping(value="/getAllLeavesType", produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public String getAllLeavesType(){
		List<Leaves_type> lists = this.leavesBo.getAllLeavesType();
		if(lists.size() > 0){
			return "{\"statusCode\":200,\"data\":"+JSONArray.fromObject(lists)+"}";
		}else{
			return "{\"statusCode\":300,\"data\":null}";
		}
	}
	
	/**
	 * 获取请假审核人
	 * @return
	 */
	@RequestMapping(value="/getLeavesAuditor", produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public String getLeavesAuditor(HttpSession session){
		User user = (User) session.getAttribute("user");
		if(user.getRole().getRoleLevel()<6){
			return "{\"statusCode\":300,\"message\":\"你的账户暂无此权限！\"}";
		}else{
			Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
			List<GridStaffApp> lists= this.gridStaffBo.getAllGridStaffOfGrid(gridStaffId);
			if(lists.size() > 0){
				return "{\"statusCode\":200,\"data\":"+JSONArray.fromObject(lists)+"}";
			}else{
				return "{\"statusCode\":300,\"data\":null}";
			}
		}
	}
	
	/**
	 * 提交请假申请
	 * @param leaves_type_id 请假类型id
	 * @param leaves_auditor 审核人
	 * @param leaves_begin_time 请假开始时间
	 * @param leaves_end_time 请假结束时间
	 * @param leaves_reason 请假事由
	 * @return
	 */
	@RequestMapping(value="/addOneLeavesInfo", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String  addOneLeavesInfo(Leaves leaves, HttpSession session){
		User user = (User) session.getAttribute("user");
		if(user.getRole().getRoleLevel()<6){
			return "{\"statusCode\":300,\"message\":\"你的账户暂无此权限！\"}";
		}else{
			Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
			leaves.setLeaves_area_id(user.getAreaId());
			leaves.setLeaves_grid_id(this.gridStaffBo.getGridIdByGridStaffId(gridStaffId));
			leaves.setLeaves_gridStaff_id(gridStaffId);
			Integer result=this.leavesBo.addOneLeavesInfo(leaves);
			if(result>0){
				logger.info("请假申请已成功提交！");
				return "{\"statusCode\":200,\"message\":\"请假申请已成功提交！\"}";
			}else{
				logger.error("请假申请提交失败！");
				return "{\"statusCode\":300,\"message\":\"请假申请提交失败！\"}";
			}
		}
	}

}
