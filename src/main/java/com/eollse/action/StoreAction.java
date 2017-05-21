package com.eollse.action;
/**
 * author 刘春晓
 * content 商铺控制器
 */

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AreaBo;
import com.eollse.bo.StoreBo;
import com.eollse.po.Store;
import com.eollse.po.Area;
import com.eollse.po.User;


@Controller
@RequestMapping("/store")
public class StoreAction extends CommonAction {
	@Autowired
	private StoreBo storeBo;
	@Autowired
	private AreaBo areaBo;

	/**
	 * 获取用户区域下所有商铺
	 * @param session 用户登录session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAllStoreByAreaId")
	@ResponseBody
	public String getAllStoreByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){	
		User user=(User)session.getAttribute("user"); 
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String, Object> map=this.storeBo.getAllStoreByAreaId(areaIds,pageSize,pageCurrent);
			String content=this.createPageJSONString(map);
			return content;		
		}else{
			return null;
		}
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
		Integer areaId=user.getAreaId();
		List<Area> area=this.areaBo.getAreaByAreaId(areaId);
		if(area.size()>0){
			return area.get(0);
		}else{
			return null; 
		}
	}
	
	
	/**
	 * 添加商铺保存方法
	 * @param store 商铺实体
	 * @param session 
	 * @param request 请求
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveStore")
	@ResponseBody
	public String saveStore(Store store,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		store.setEditStoreName(user.getUserName());
		store.setEditStoreDate(new Date());
		Integer result=this.storeBo.saveStore(store);
		if (result > 0) {
			logger.info( "成功添加商铺" + store.getStoreName() + "！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 删除
	 * @param delSid 商铺Id组成字符串
	 * @param session
	 * @param request
	 * @return string “1”==>表示删除成功 “0”==>表示删除失败
	 */
	@RequestMapping(value="/deleteStore")
	@ResponseBody
	public String deleteStore(String delSid,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		String[] str=delSid.split(",");
		List<Integer> storeIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			storeIds.add(i,Integer.parseInt(str[i]));
		}
		List<String> storeNames=this.storeBo.getStoreNameById(storeIds);
		String str1="";
		for(String str2:storeNames){
			str1+=str2+",";
		}
		Integer result=this.storeBo.deleteStoreByStoreId(storeIds);
		if (result > 0) {
			logger.info("成功删除商铺"	+ str1 + "！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 审核方法
	 * @param audSid 商铺Id组成字符串
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/updateAuditStore")
	@ResponseBody
	public String updateAuditStore(String audSid){
		String[] str=audSid.split(",");
		List<Integer> storeIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			storeIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.storeBo.updateAuditStore(storeIds);
		return result>0 ? "1" : "0";
				
	}
	
	/**
	 * 获取修改弹窗所需数据
	 * @param upSid 商铺Id
	 * @return 商铺实体
	 */
	@RequestMapping(value="/getStoreById")
	@ResponseBody
	public Store getStoreById(String upSid){
		Integer storeId=Integer.parseInt(upSid);
		List<Store> store=this.storeBo.getStoreById(storeId);
		if(store.size()>0){
			Integer areaId=store.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			store.get(0).setArea(area.get(0));
			return store.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 修改方法
	 * @param store 商铺实体
	 * @param request 
	 * @param session
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editStoreById")
	@ResponseBody
	public String editStoreById(Store store,HttpServletRequest request,HttpSession session){
		User user=(User)session.getAttribute("user");
		store.setEditStoreName(user.getUserName());
		store.setEditStoreDate(new Date());
		Integer result=this.storeBo.editStoreById(store);
		if (result > 0) {
			logger.info("成功修改商铺"+ store.getStoreName() + "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 查看方法
	 * @param store 商铺实体
	 * @return 商铺实体
	 */
	@RequestMapping(value="/getOneStoreById")
	@ResponseBody
	public Store getOneStoreById(Store store){
		List<Store> stores=this.storeBo.getOneStoreById(store);
		if(stores.size()>0){
			Integer areaId=stores.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			stores.get(0).setArea(area.get(0));
			return stores.get(0);
		}else{
			return null;
		}
	}
	
	@RequestMapping(value="/uploadStorePic")
	@ResponseBody
	public String uploadStorePic(HttpServletRequest request, String mainName,
			String picName) {
		String path = this.fileUpload(request, mainName, picName);
		return path != null ? "{\"statusCode\":200,\"message\":\"" + path+ "\"}" : null;
	}
}
