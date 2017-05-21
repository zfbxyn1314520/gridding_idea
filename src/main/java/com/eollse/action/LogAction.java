package com.eollse.action;

/**
 * author 刘春晓
 * content 系统日志控制器
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AreaBo;
import com.eollse.bo.LogBo;
import com.eollse.bo.UserBo;


@Controller
@RequestMapping("/log")
public class LogAction extends CommonAction{
	
	@Autowired
	private LogBo logBo;
	@Autowired
	private UserBo userBo;
	@Autowired
	private AreaBo areaBo;
	
	
	/**
	 * 获取用户下所有系统日志
	 * @param session 用户登录session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAllLogByUserId")
	@ResponseBody
	public String getAllLogByUserId(HttpSession session,Integer pageSize, Integer pageCurrent){
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		List<Integer> userIds=this.userBo.getUserIdByAreaId(areaIds);
		Map<String, Object> map=this.logBo.getAllLogByUserId(userIds,pageSize,pageCurrent);
		String content=this.createPageJSONString(map);
	    return content;
	}
	
	/**
	 * 删除系统日志
	 * @param deLid 系统日志Id
	 * @return string “1”==>表示删除成功 “0”==>表示删除失败
	 */
	@RequestMapping(value="/deleteLog")
	@ResponseBody
	public String deleteLog(String deLid){
		String[] str=deLid.split(",");
		List<Integer> logIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			logIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.logBo.deleteLog(logIds);
		return result>0 ? "1" : "0";
	}
}
