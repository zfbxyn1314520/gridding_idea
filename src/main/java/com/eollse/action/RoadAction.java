package com.eollse.action;
/**
 * author 刘春晓
 * content 道路控制器
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AreaBo;
import com.eollse.bo.RoadBo;
import com.eollse.po.Area;
import com.eollse.po.Road;
import com.eollse.po.Road_grade;
import com.eollse.po.User;

@Controller
@RequestMapping("/road")
public class RoadAction extends CommonAction {
	@Autowired
	private RoadBo roadBo;
	@Autowired
	private AreaBo areaBo;
	
	/**
	 * 获取用户区域下所有道路信息
	 * @param session 用户登录session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAllRoadByAreaId")
	@ResponseBody
	public String getAllRoadByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user"); 
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String, Object> map=this.roadBo.getAllRoadByAreaId(areaIds,pageSize,pageCurrent);
			String content=this.createPageJSONString(map);
			return content;
		}
		return null;
	}
	
	/**
	 * 获取用户区域
	 * @param session
	 * @return 区域实体
	 */
	@RequestMapping(value="/getAreaName")
	@ResponseBody
	public Area getAreaName(HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user!=null){
			Integer areaId=user.getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			if(area.size()>0){
				return area.get(0);
			}else{
				return null; 
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 添加道路保存方法
	 * @param road 道路实体
	 * @param session 
	 * @param request 请求
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveRoad")
	@ResponseBody
	public String saveRoad(Road road,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			road.setEditRoadName(user.getUserName());
			road.setEditRoadDate(new Date());
			Integer result=this.roadBo.saveRoad(road);
			if (result > 0) {
				logger.info("成功添加道路"+ road.getRoadName() + "！");
			}
			return result>0 ? "1" : "0";
		}else{
			return "index.html";
		}
	}
	
	/**
	 * 审核方法
	 * @param audRid 道路Id组成字符串
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/updateAuditRoad")
	@ResponseBody
	public String updateAuditRoad(String audRid){
		String[] str=audRid.split(",");
		List<Integer> roadIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			roadIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.roadBo.updateAuditRoad(roadIds);
		return result>0 ? "1" : "0";
				
	}
	
	/**
	 * 删除方法
	 * @param deRid 道路Id组成字符串
	 * @param session 
	 * @param request
	 * @return string “1”==>表示删除成功 “0”==>表示删除失败
	 */
	@RequestMapping(value="/deleteRoad")
	@ResponseBody
	public String deleteRoad(String deRid,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		String[] str=deRid.split(",");
		List<Integer> roadIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			roadIds.add(i,Integer.parseInt(str[i]));
		}
		List<String> roadNames=this.roadBo.getRoadNameById(roadIds);
		String str1="";
		for(String str2 : roadNames){
			str1+=str2+",";
		}
		Integer result=this.roadBo.deleteRoad(roadIds);
		if (result > 0) {
			logger.info("成功删除道路"+ str1 + "！");
		}
		return result>0 ? "1" : "0";
		
	}
	
	/**
	 * 修改弹窗所需数据
	 * @param upRid 道路Id
	 * @return 道路实体
	 */
	@RequestMapping(value="/getRoadById")
	@ResponseBody
	public Road getRoadById(String upRid){
		Integer roadId=Integer.parseInt(upRid);
		List<Road> road=this.roadBo.getRoadById(roadId);
		if(road.size()>0){
			Integer areaId=road.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			road.get(0).setArea(area.get(0));
			return road.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 修改道路
	 * @param road 道路实体
	 * @param session 
	 * @param request
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editRoadById")
	@ResponseBody
	public String editRoadById(Road road,HttpSession session, HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		road.setEditRoadName(user.getUserName());
		road.setEditRoadDate(new Date());
		Integer result=this.roadBo.editRoadById(road);
		if (result > 0) {
			logger.info("成功修改道路"	+ road.getRoadName() + "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 查看方法
	 * @param road 道路实体
	 * @return 道路实体
	 */
	@RequestMapping(value="/getOneRoadById")
	@ResponseBody
	public Road getOneRoadById(Road road){
		List<Road> roads=this.roadBo.getOneRoadById(road);
		if(roads.size()>0){
			Integer areaId=roads.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			roads.get(0).setArea(area.get(0));
			return roads.get(0);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/uploadRoadPic")
	@ResponseBody
	public String uploadRoadPic(HttpServletRequest request, String mainName,
			String picName) {
		String path = this.fileUpload(request, mainName, picName);
		return path != null ? "{\"statusCode\":200,\"message\":\"" + path+ "\"}" : null;
	}
	
	/**
	 * 获取所有道路等级
	 * @return 道路等级集合
	 */
	@RequestMapping(value="/getAllRoadGrade")
	@ResponseBody
	public List<Road_grade> getAllRoadGrade(){
		List<Road_grade> roadGrades=this.roadBo.getAllRoadGrade();
		return roadGrades;
	}
}
