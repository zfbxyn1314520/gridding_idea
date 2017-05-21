package com.eollse.action;

/**
 * author 刘春晓
 * content 公共设施控制器
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
import com.eollse.bo.FacilitiesBo;
import com.eollse.po.Area;
import com.eollse.po.Facilities;
import com.eollse.po.Facilities_type;
import com.eollse.po.User;

@Controller
@RequestMapping(value="/facilities")
public class FacilitiesAction extends CommonAction {
	@Autowired
	private FacilitiesBo facilitiesBo;
	@Autowired
	private AreaBo areaBo;
	
	
	/**
	 * 获取用户区域下所有公共设施
	 * @param session 用户登录session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAllfacilitiesByAreaId")
	@ResponseBody
	public String getAllfacilitiesByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String,Object> map=this.facilitiesBo.getAllfacilitiesByAreaId(areaIds,pageSize,pageCurrent);
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
	 * 添加弹窗保存方法
	 * @param facilities 公共设施实体
	 * @param session
	 * @param request
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveFacilities")
	@ResponseBody
	public String saveFcilities(Facilities facilities,HttpSession session,HttpServletRequest request){
		User user=(User) session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			facilities.setEditFacName(user.getUserName());
			facilities.setEditFacDate(new Date());
			Integer result=this.facilitiesBo.saveFacilities(facilities);
			if (result > 0) {
				logger.info("成功添加公共设施"+ facilities.getFacilitiesName() + "！");
			}
			return result>0 ? "1" : "0";
		}else{
			return "0";
		}
	}
	
	/**
	 * 删除
	 * @param deFid 公共设施Id组成的字符串
	 * @param session
	 * @param request
	 * @return string “1”==>表示删除成功 “0”==>表示删除失败
	 */
	@RequestMapping(value="deleteFacilities")
	@ResponseBody
	public String deleteFacilities(String deFid,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		String[] str=deFid.split(",");
		List<Integer>  facilitiesIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			facilitiesIds.add(i,Integer.parseInt(str[i]));
		}
		List<String> facilitiesNames=this.facilitiesBo.getFacilitiesNameById(facilitiesIds);
		String str1="";
		for(String str2:facilitiesNames){
			str1+=str2+",";
		}
//		List<Integer> facilitiesTypeIds=this.facilitiesBo.getfacilitiesTypeId(facilitiesIds);
		Integer result=this.facilitiesBo.deleteFacilities(facilitiesIds);
		if (result > 0) {
			logger.info("成功删除公共设施"+ str1 + "！");
		}
//		this.facilitiesBo.deleteFacilitiesType(facilitiesTypeIds);
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 审核 
	 * @param audFid 公共设施Id字符串
	 * @return
	 */
	@RequestMapping(value="/updateAuditFacilities")
	@ResponseBody
	public String updateAuditFacilities(String audFid){
		String[] str=audFid.split(",");
		List<Integer> facilitiesIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			facilitiesIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.facilitiesBo.updateAuditFacilities(facilitiesIds);
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 获取修改所需数据
	 * @param upFid 公共设施Id
	 * @return
	 */
	@RequestMapping(value="/getFacilitiesById")
	@ResponseBody
	public Facilities getFacilitiesById(String upFid){
		Integer facilitiesId=Integer.parseInt(upFid);
		List<Facilities> facilities=this.facilitiesBo.getFacilitiesById(facilitiesId);
		if(facilities.size()>0){
			Integer areaId=facilities.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			facilities.get(0).setArea(area.get(0));
			Integer facilitiesTypeId=facilities.get(0).getFacilitiesTypeId();
			Facilities_type facilitiesType=this.facilitiesBo.getFacilitiesTypeById(facilitiesTypeId);
			facilities.get(0).setFacilitiesType(facilitiesType);
			return facilities.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 修改公共设施
	 * @param facilities 公共设施实体
	 * @param session
	 * @param request
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editfacilitiesById")
	@ResponseBody
	public String editfacilitiesById(Facilities facilities,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		facilities.setEditFacName(user.getUserName());
		facilities.setEditFacDate(new Date());
		Integer result=this.facilitiesBo.editfacilitiesById(facilities);
		if (result > 0) {
			logger.info("成功修改公共设施"+ facilities.getFacilitiesName() + "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 查看
	 * @param facilities 公共设施实体
	 * @return 公共设施实体
	 */
	@RequestMapping(value="/getOneFacilitiesById")
	@ResponseBody
	public Facilities getOneFacilitiesById(Facilities facilities){
		List<Facilities> facilitiess=this.facilitiesBo.getOneFacilitiesById(facilities);
		if(facilitiess.size()>0){
			Integer areaId=facilitiess.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			facilitiess.get(0).setArea(area.get(0));
			Facilities_type facilitiesType=this.facilitiesBo.getFacilitiesTypeById(facilitiess.get(0).getFacilitiesTypeId());
			facilitiess.get(0).setFacilitiesType(facilitiesType);
			return facilitiess.get(0);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/uploadFacilitiesPic")
	@ResponseBody
	public String uploadFacilitiesPic(HttpServletRequest request, String mainName,
			String picName) {
		String path = this.fileUpload(request, mainName, picName);
		return path != null ? "{\"statusCode\":200,\"message\":\"" + path+ "\"}" : null;
	}
	
	/**
	 * 获取公共设施类别
	 * @return 公共设施类别集合
	 */
	@RequestMapping(value="/getAllFacilitiesType")
	@ResponseBody
	public List<Facilities_type> getAllFacilitiesType(){
		List<Facilities_type> facilitiesTypes=this.facilitiesBo.getAllFacilitiesType();
		return facilitiesTypes;
	}
}

