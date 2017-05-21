package com.eollse.action;

/**
 * author 李宁财
 * content 人口控制器
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.PopulationBo;
import com.eollse.po.Culture_level;
import com.eollse.po.Flow;
import com.eollse.po.Flow_type;
import com.eollse.po.Holder_relation;
import com.eollse.po.Marriage_status;
import com.eollse.po.Politics;
import com.eollse.po.Pop_attr;
import com.eollse.po.Pop_nation;
import com.eollse.po.Popattr;
import com.eollse.po.Population;
import com.eollse.po.User;

@Controller
@RequestMapping("/pop")
public class PopulationAction extends CommonAction {
	
	@Autowired
	private PopulationBo popBo;
	
	@RequestMapping("/getAllPopByAreaId")
	@ResponseBody
	public String getAllPopByAreaId(HttpSession session, Integer pageSize,Integer pageCurrent) {
		User user = (User) session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if (user != null) {
			Map<String, Object> map = this.popBo.getAllPopByAreaId(areaIds, pageSize, pageCurrent);
			String content = this.createPageJSONString(map);
			return content;
		} else {
			return null;
		}
	}
	
	@RequestMapping("/addOnePopInfo")
	@ResponseBody
	public String addOnePopInfo(Population pop,Flow flow,HttpSession session,HttpServletRequest request) {
		User s_user = (User) session.getAttribute("user");
		List<Pop_attr> pop_attrs=new ArrayList<Pop_attr>();
		List<Popattr> attrs=pop.getPopAttrs();
		pop.setEditPopName(s_user.getUserName());
		pop.setEditPopDate(new Date());
		Integer result = this.popBo.addOnePopInfo(pop);
		if(result>0){
			Integer popId = this.popBo.getNewPopPopId();
			Integer flowResult=0;
			Integer attrResult=0;
			if(pop.getIsFlow()==1){
				flow.setPopId(popId);
				try{
					flowResult=this.popBo.addOnePopFlowInfo(flow);
				}catch(Exception e){
					if(flowResult==0){
						this.popBo.deletePopInfoForError(popId);
						return "0";
					}
				}
			}
			if(attrs.size()>0){
				Iterator<Popattr> lists = attrs.iterator(); 
				while(lists.hasNext()){ 
					Popattr pop_attr = lists.next();
					if(pop_attr.getPopAttrId()==null){
						lists.remove();
					}
				}
				Integer rows=attrs.size();
				for(int i=0;i<rows;i++){
					pop_attrs.add(new Pop_attr(popId,attrs.get(i).getPopAttrId()));
				}
				try{
					attrResult=this.popBo.addOnePopAttrInfo(pop_attrs);	
				}catch(Exception e){
					if(attrResult==0){
						this.popBo.deleteFlowInfoForError(popId);
						this.popBo.deletePopInfoForError(popId);
						return "0";
					}
				}
			}
		}
		return result > 0 ? "1" : "0";
	}
	
	@RequestMapping(value="/editOnePopInfoById", produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public String editOnePopInfoById(Population pop,Flow flow,HttpSession session,HttpServletRequest request) {
		User s_user = (User) session.getAttribute("user");
		List<Pop_attr> pop_attrs=new ArrayList<Pop_attr>();
		String message="";
		List<Popattr> attrs=pop.getPopAttrs();
		pop.setEditPopName(s_user.getUserName());
		pop.setEditPopDate(new Date());
		Integer result = this.popBo.editOnePopInfoById(pop);
		if(result>0){
			Integer flowResult=0;
			Integer attrResult=0;
			if(pop.getIsFlow()==1){
				flow.setPopId(pop.getPopId());
				try{
					flowResult=this.popBo.editOnePopFlowInfo(flow);
				}catch(Exception e){
					if(flowResult==0){
						message+="修改失败："+pop.getPopName()+"的流动信息";
					}
				}
			}else{
				this.popBo.deleteFlowInfoForError(pop.getPopId());
			}
			if(attrs.size()>0){
				Iterator<Popattr> lists = attrs.iterator(); 
				while(lists.hasNext()){ 
					Popattr pop_attr = lists.next();
					if(pop_attr.getPopAttrId()==null){
						lists.remove();
					}
				}
				Integer rows=attrs.size();
				for(int i=0;i<rows;i++){
					pop_attrs.add(new Pop_attr(pop.getPopId(),attrs.get(i).getPopAttrId()));
				}
				try{
					this.popBo.deleteOnePopAttrInfo(pop.getPopId());
					attrResult=this.popBo.addOnePopAttrInfo(pop_attrs);	
				}catch(Exception e){
					if(attrResult==0){
						message+="、人口属性信息！";
					}
				}
			}
		}
		return result > 0 ? "{\"statusCode\":200,\"message\":\"" + message+ "\"}" : "0";
	}
	
	@RequestMapping("/getOnePopInfo")
	@ResponseBody
	public Flow getOnePopInfo(Integer popId) {
		List<Flow> flows = new ArrayList<Flow>();
		flows = this.popBo.getOnePopFlowInfo(popId);
		if(flows.size()==0){
			flows.add(new Flow());
		}
		Population pop = this.popBo.getOnePopInfo(popId).get(0);
		List<Popattr> popattrs = this.popBo.getOnePopAttrsInfo(popId);
		if(popattrs.size()>0){
			pop.setPopAttrs(popattrs);
		}
		flows.get(0).setPopulation(pop);
		return flows.get(0);
	}
	
	@RequestMapping("/deletePopInfoByIds")
	@ResponseBody
	public String deletePopInfoByIds(Integer popId) {
		this.popBo.deleteOnePopAttrInfo(popId);
		this.popBo.deleteFlowInfoForError(popId);
		Integer result = this.popBo.deletePopInfoForError(popId);
		return result > 0 ? "1" : "0";
	}
	
	@RequestMapping("/alterOnePopAuditStatusInfo")
	@ResponseBody
	public String alterOnePopAuditStatusInfo(@RequestParam(value = "popIds[]") Integer[] popIds){
		List<Integer> delPopIds = new ArrayList<Integer>();
		for (int i = 0; i < popIds.length; i++) {
			delPopIds.add(i, popIds[i]);
		}
		Integer result=this.popBo.alterOnePopAuditStatusInfo(delPopIds);
		return result>0 ? "1" : "0";
	}
	
	@RequestMapping("/getOnePopDetailInfoResult")
	@ResponseBody
	public Flow getOnePopDetailInfoResult(Integer popId) {
		List<Flow> flows = new ArrayList<Flow>();
		flows = this.popBo.getOnePopFlowInfo(popId);
		if(flows.size()==0){
			flows.add(new Flow());
		}
		Population pop = this.popBo.getOnePopDetailInfo(popId).get(0);
		List<Popattr> popattrs = this.popBo.getOnePopAttrsInfo(popId);
		if(popattrs.size()>0){
			pop.setPopAttrs(popattrs);
		}
		flows.get(0).setPopulation(pop);
		return flows.get(0);
	}
	
	@RequestMapping("/getAllHolderRelation")
	@ResponseBody
	public List<Holder_relation> getAllHolderRelation() {
		return this.popBo.getAllHolderRelation();
	}
	
	
	@RequestMapping("/getAllMarriageStatus")
	@ResponseBody
	public List<Marriage_status> getAllMarriageStatus() {
		return this.popBo.getAllMarriageStatus();
	}
	
	@RequestMapping("/getAllPopNation")
	@ResponseBody
	public List<Pop_nation> getAllPopNation() {
		return this.popBo.getAllPopNation();
	}
	
	@RequestMapping("/getAllPolitics")
	@ResponseBody
	public List<Politics> getAllPolitics() {
		return this.popBo.getAllPolitics();
	}
	
	@RequestMapping("/getAllCultureLevel")
	@ResponseBody
	public List<Culture_level> getAllCultureLevel() {
		return this.popBo.getAllCultureLevel();
	}
	
	@RequestMapping("/getAllFlowType")
	@ResponseBody
	public List<Flow_type> getAllFlowType() {
		
		return this.popBo.getAllFlowType();
	}
	
	@RequestMapping("/getAllPopAttr")
	@ResponseBody
	public List<Popattr> getAllPopAttr() {
		
		return this.popBo.getAllPopAttr();
	}
	
	
}
