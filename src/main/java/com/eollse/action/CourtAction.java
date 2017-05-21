package com.eollse.action;

/**
 * author 刘春晓
 * content 小区控制器
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
import com.eollse.bo.CourtBo;
import com.eollse.po.Area;
import com.eollse.po.Court;
import com.eollse.po.User;

@Controller
@RequestMapping(value="court")
public class CourtAction extends CommonAction {
	@Autowired
	private CourtBo courtBo;
	@Autowired
	private AreaBo areaBo;
	
	/**
	 * 获取用户区域下所有小区
	 * @param session 用户登录session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return 
	 */
	@RequestMapping(value="/getAllCourtByAreaId")
	@ResponseBody
	public String getAllCourtByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user"); 
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String, Object> map=this.courtBo.getAllCourtByAreaId(areaIds,pageSize,pageCurrent);
			String content=this.createPageJSONString(map);
			return content;		
		}else{
			return null;
		}
	}
	
	/**
	 * 获取用户所属区域
	 * @param session 
	 * @return 区域实体
	 */
	@RequestMapping(value="/getAreaName")
	@ResponseBody
	public Area getAreaName(HttpSession session){
		User user=(User)session.getAttribute("user");
		Integer areaId=user.getAreaId();
		List<Area> area=this.areaBo.getAreaByAreaId(areaId);
		if(area.size()>0){
			return area.get(0);
		}else{
			return null; 
		}
	}
	
	/**
	 * 新增小区保存方法
	 * @param court 小区实体
	 * @param session 
	 * @param request 请求
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveCourt")
	@ResponseBody
	public String saveCourt(Court court,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			court.setEditCourtName(user.getUserName());
			court.setEditCourtDate(new Date());
			Integer result=this.courtBo.saveCourt(court);
			if(result>0){
				logger.info("成功添加小区"+ court.getCourtName() + "！");
			}
			return result>0 ? "1" : "0";
		}else{
			return "index.html";
		}
	}
	
	/**
	 * 修改所需数据
	 * @param upCid 小区Id
	 * @return 小区实体
	 */
	@RequestMapping(value="/getCourtById")
	@ResponseBody
	public Court getCourtById(String upCid){
		Integer courtId=Integer.parseInt(upCid);
		List<Court> court=this.courtBo.getCourtById(courtId);
		if(court.size()>0){
			Integer areaId=court.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			court.get(0).setArea(area.get(0));
			return court.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 修改方法
	 * @param court 小区实体
	 * @param session
	 * @param request
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editCourtById")
	@ResponseBody
	public String editCourtById(Court court,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		court.setEditCourtName(user.getUserName());
		court.setEditCourtDate(new Date());
		Integer result=this.courtBo.editCourtById(court);
		if (result > 0) {
			logger.info("成功修改小区"	+ court.getCourtName() + "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 删除方法
	 * @param delCid
	 * @return
	 */
	@RequestMapping(value="/deleteCourt")
	@ResponseBody
	public String deleteCourt(String delCid){
		String[] str=delCid.split(",");
		List<Integer> courtIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			courtIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.courtBo.deleteCourt(courtIds);
		return result>0 ? "1" : "0";
		
	}
	
	/**
	 * 审核
	 * @param audCid 小区Id组成字符串
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/updateAuditCourt")
	@ResponseBody
	public String updateAuditCourt(String audCid){
		String[] str=audCid.split(",");
		List<Integer> courtIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			courtIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.courtBo.updateAuditCourt(courtIds);
		return result>0 ? "1" : "0";
	}
	
	@RequestMapping(value="/uploadCourtPic")
	@ResponseBody
	public String uploadCourtPic(HttpServletRequest request, String mainName,
			String picName) {
		String path = this.fileUpload(request, mainName, picName);
		return path != null ? "{\"statusCode\":200,\"message\":\"" + path+ "\"}" : null;
	}
	
	/**
	 * 查看方法
	 * @param court 小区实体
	 * @return 小区实体
	 */
	@RequestMapping(value="/getOneCourtById")
	@ResponseBody
	public Court getOneCourtById(Court court){
		List<Court> courts=this.courtBo.getOneCourtById(court);
		if(courts.size()>0){
			Integer areaId=courts.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			courts.get(0).setArea(area.get(0));
			return courts.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取所有用户区域下小区
	 * @param areaId 区域Id
	 * @return 小区集合
	 */
	@RequestMapping(value="/getAllCourtsByAreaId")
	@ResponseBody
	public List<Court> getAllCourtsByAreaId(Integer areaId){
		if(this.getAreaIds().size()>0){
			this.getAreaIds().clear();
		}
		List<Area> area = this.areaBo.getAreaByAreaId(areaId);
		this.getAllAreaIdById(area.get(0).getAreaCode());
		return this.courtBo.getAllCourtsByAreaId(this.getAreaIds());
	}
}
