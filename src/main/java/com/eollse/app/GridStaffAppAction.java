package com.eollse.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.action.CommonAction;
import com.eollse.bo.GridBo;
import com.eollse.bo.GridStaffBo;
import com.eollse.bo.UserBo;
import com.eollse.po.User;

/**
 * author 李宁财
 * content App网格员控制器
 */

@Controller
@RequestMapping("/app/staff")
public class GridStaffAppAction extends CommonAction{
	
	@Autowired
	private GridBo gridBo;
	@Autowired
	private GridStaffBo gridStaffBo;
	@Autowired
	private UserBo userBo;
	
	/**
	 * @param session
	 * @param field 搜索值
	 * @param pageSize 页面大小
	 * @param pageCurrent 当前页
	 * @return
	 */
	@RequestMapping(value="/getAllGridStaffById", produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public String getAllGridStaffById(HttpSession session,String field,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		List<Integer> gridIds = new ArrayList<Integer>();
		Map<String,Object> gridStaffs = new HashMap<String,Object>();
		if(user.getRole().getRoleLevel()<6){
			gridIds=this.gridBo.getAllGridIdsByAreaId(areaIds);
		}else{
			Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
			gridIds.add(this.gridStaffBo.getGridIdByGridStaffId(gridStaffId));
		}
		if(!field.equals("")){
			pageCurrent = 1;
		}
		gridStaffs = this.gridStaffBo.getAllGridStaffById(gridIds,field,pageSize,pageCurrent);
		return "{\"statusCode\":200,\"data\":"+this.createPageJSONString(gridStaffs)+"}";
	}

}
