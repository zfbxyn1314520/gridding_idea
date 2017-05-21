package com.eollse.action;

/**
 * author 李宁财
 * content 信息管理控制器
 */

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eollse.bo.AreaBo;
import com.eollse.bo.InfoBo;
import com.eollse.po.Info;
import com.eollse.po.Info_type;
import com.eollse.po.User;

@Controller
@RequestMapping("/info")
public class InfoAction extends CommonAction {
	
	@Autowired
	private InfoBo infoBo;
	@Autowired
	private AreaBo areaBo;
	
	@RequestMapping(value="/uploadFile", produces = {"text/x-json;charset=utf-8"})
	@ResponseBody
	public String uploadHeadIcon(@RequestParam("infoPic")MultipartFile file,HttpServletRequest request, 
			String mainName,String picName) throws IOException, ServletException {
		// 判断提交来的文件是否为空
        if (!file.isEmpty()) {
        	// 文件类型限制
        	boolean result=true;
        	String[] allowedType = { "image/bmp", "image/gif", "image/jpeg", "image/png" };
        	for(int i=0;i<allowedType.length;i++){
        		if(file.getContentType().equals(allowedType[i])){
        			result=false;
        		}
        	}
        	if(result){
        		return "error|上传文件类型错误，请重新选择上传！";
        	}
        	// 文件大小限制
        	if (file.getSize() > 1 * 1024 * 1024) {
            	return "error|上传文件大小超过1M，请重新选择上传！";
            }
        }
    	return this.fileUpload(request, mainName, picName);
	}
	
	
	@RequestMapping("/getAllInfoType")
	@ResponseBody
	public List<Info_type> getAllInfoType(){
		
		return this.infoBo.getAllInfoType();
	}
	
	
	@RequestMapping(value="/addNewInfo", produces = {"text/plain;charset=utf-8"})
	@ResponseBody
	public String addNewInfo(Info info,HttpServletRequest request, HttpSession session){
		Integer result = this.infoBo.addNewInfo(info);
		if (result > 0) {
			String areaName=this.areaBo.getAreaByAreaId(info.getAreaId()).get(0).getAreaName();
			String infoTypeName=this.infoBo.getInfoTypeById(info.getInfoTypeId()).get(0).getInfoTypeName();
			logger.info("成功添加"+ areaName +" >> "+ infoTypeName + "的信息！");
			return areaName +" >> "+ infoTypeName;
		}else{
			return "0";
		}
	}
	
	@RequestMapping(value="/alterInfo", produces = {"text/plain;charset=utf-8"})
	@ResponseBody
	public String alterInfo(Info info,HttpServletRequest request, HttpSession session){
		Integer result = this.infoBo.alterInfo(info);
		if (result > 0) {
			String areaName=this.areaBo.getAreaByAreaId(info.getAreaId()).get(0).getAreaName();
			String infoTypeName=this.infoBo.getInfoTypeById(info.getInfoTypeId()).get(0).getInfoTypeName();
			logger.info("成功修改"+ areaName +" >> "+ infoTypeName + "的信息！");
			return areaName +" >> "+ infoTypeName;
		}else{
			return "0";
		}
	}
	
	@RequestMapping("/getAllInfoByAreaId")
	@ResponseBody
	public List<Info> getAllInfoByAreaId(Integer areaId){
		return this.infoBo.getAllInfoByAreaId(areaId);
	}
	
	@RequestMapping("/getOneAreaIntrInfo")
	@ResponseBody
	public Info getOneAreaIntrInfo(Info info, HttpSession session){
		User user=(User)session.getAttribute("user");
		info.setAreaId(user.getAreaId());
		return this.infoBo.getOneAreaIntrInfo(info);
	}
	

}
