package com.eollse.action;

/**
 * author 刘春晓
 * content 企业控制器
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
import com.eollse.bo.CompanyBo;
import com.eollse.po.Area;
import com.eollse.po.Company;
import com.eollse.po.Company_nature;
import com.eollse.po.Company_scale;
import com.eollse.po.Company_trade;
import com.eollse.po.User;

@Controller
@RequestMapping(value="/company")
public class CompanyAction extends CommonAction{
	@Autowired
	private CompanyBo companyBo;
	@Autowired
	private AreaBo areaBo;
	
	/**
	 * 获取所有企业
	 * @param session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAllCompanyByAreaId")
	@ResponseBody
	public String getAllCompanyByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user"); 
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String,Object> map=this.companyBo.getAllCompanyByAreaId(areaIds,pageSize,pageCurrent);
			String content=this.createPageJSONString(map);
			return content;		
		}else{
			return null;
		}
	}
	
	/**
	 * 获取当前用户所属区域
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
	 * 添加企业保存方法
	 * @param company 企业实体
	 * @param session 用户登录session	
	 * @param request 请求
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveCompany")
	@ResponseBody
	public String saveCompany(Company company,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		if(user!=null){
			company.setEditComName(user.getUserName());
			company.setEditComDate(new Date());
			Integer result=this.companyBo.saveCompany(company);
			if(result>0){
				logger.info("成功添加企业"+ company.getCompanyName() + "！");
			}
			return result>0 ? "1" : "0" ;
		}else{
			return "index.html";
		}
	}
	
	/**
	 * 获取修改弹窗所需数据
	 * @param upCid 企业Id
	 * @return 企业实体
	 */
	@RequestMapping(value="/getCompanyById")
	@ResponseBody
	public Company getCompanyById(String upCid){
		Integer companyId=Integer.parseInt(upCid);
		List<Company> company=this.companyBo.getCompanyById(companyId);
		if(company.size()>0){
			Integer areaId=company.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			company.get(0).setArea(area.get(0));
			return company.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 修改企业
	 * @param company 企业实体
	 * @param session
	 * @param request
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editCompanyById")
	@ResponseBody
	public String editCompanyById(Company company,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		company.setEditComName(user.getUserName());
		company.setEditComDate(new Date());
		Integer result=this.companyBo.editCompanyById(company);
		if(result>0){
			logger.info("成功修改企业"+ company.getCompanyName() + "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 删除企业
	 * @param deCid 企业Id的字符串
	 * @param session
	 * @param request
	 * @return string “1”==>表示删除成功 “0”==>表示删除败
	 */
	@RequestMapping(value="/deleteCompany")
	@ResponseBody
	public String deleteCompany(String deCid,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		String[] str=deCid.split(",");
		List<Integer> companyIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			companyIds.add(i,Integer.parseInt(str[i]));
		}
		List<String> companyName=this.companyBo.getCompanyName(companyIds);
		Integer result=this.companyBo.deleteCompany(companyIds);
		String str1="";
		for(String str2:companyName){
			str1+=str2+",";
		}
		if (result > 0) {
			logger.info("成功删除企业"+str1+ "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 审核方法
	 * @param audCid 企业Id
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/updateAuditCompany")
	@ResponseBody
	public String updateAuditCompany(String audCid){
		String[] str=audCid.split(",");
		List<Integer> companyIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			companyIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.companyBo.updateAuditCompany(companyIds);
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 查看方法
	 * @param company 企业实体
	 * @return 企业实体
	 */
	@RequestMapping(value="/getOneCompanyById")
	@ResponseBody
	public Company getOneRoadById(Company company){
		List<Company> companys=this.companyBo.getOneRoadById(company);
		if(companys.size()>0){
			Integer areaId=companys.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			companys.get(0).setArea(area.get(0));
			return companys.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取企业性质
	 * @return 企业性质集合
	 */
	@RequestMapping(value="/getAllCompanyNature")
	@ResponseBody
	public List<Company_nature> getAllCompanyNature(){
		List<Company_nature> companyNatures=this.companyBo.getAllCompanyNature();
		return companyNatures;
	}
	
	/**
	 * 获取企业规模
	 * @return 企业规模集合
	 */
	@RequestMapping(value="/getAllCompanyScale")
	@ResponseBody
	public List<Company_scale> getAllCompanyScale(){
		List<Company_scale> companyScales=this.companyBo.getAllCompanyScale();
		return companyScales;
	}
	
	/**
	 * 获取企业行业
	 * @return 企业行业集合
	 */
	@RequestMapping(value="/getAllCompanyTrade")
	@ResponseBody
	public List<Company_trade> getAllCompanyTrade(){
		List<Company_trade> companyTrades=this.companyBo.getAllCompanyTrade();
		return companyTrades;
	}
	
	/**
	 * 上传企业图片
	 * @param request
	 * @param mainName 用户上传图片文件夹名称 not null
	 * @param picName 如何是上传多张图片应该传图片名称用户生成文件夹
	 * @return
	 */
	@RequestMapping(value="/uploadCompanyPic")
	@ResponseBody
	public String uploadCompanyPic(HttpServletRequest request, String mainName,
			String picName) {
		String path = this.fileUpload(request, mainName, picName);
		return path != null ? "{\"statusCode\":200,\"message\":\"" + path+ "\"}" : null;
	}
}
