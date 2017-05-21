package com.eollse.action;

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
import com.eollse.bo.GridStaffBo;
import com.eollse.po.Area;
import com.eollse.po.Block;
import com.eollse.po.Grid;
import com.eollse.po.Grid_post;
import com.eollse.po.Grid_staff;
import com.eollse.po.User;


@Controller
@RequestMapping("/gridStaff")
public class GridStaffAction extends CommonAction {
	@Autowired
	private GridStaffBo gridStaffBo;
	@Autowired
	private AreaBo areaBo;
	
	@RequestMapping(value="/getAllGridStaffByAreaId")
	@ResponseBody
	public String getAllGridStaffByAreaId(Integer areaId,Integer gridId,Integer pageSize, Integer pageCurrent){
		if(areaId==null && gridId!=null){
			Map<String,Object> map1=this.gridStaffBo.getAllGridStaffByGridId(gridId,pageSize,pageCurrent);
			if(map1!=null){
				String content1=this.createPageJSONString(map1);
				return content1;
			}else{
				return null;
			}
		}else{
			if(this.getAreaIds().size()>0){
				this.getAreaIds().clear();
			}
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			this.getAllAreaIdById(area.get(0).getAreaCode());
			Map<String,Object> map2=this.gridStaffBo.getAllGridStaffByAreaId(this.getAreaIds(), pageSize, pageCurrent);
			if(map2!=null){
				return this.createPageJSONString(map2);
			}else{
				return null;
			}
		}
	}
	
	
	@RequestMapping(value="/getAreaMenuById")
	@ResponseBody
	public List<Area> getAreaMenuById(HttpSession session){
		User user=(User)session.getAttribute("user");
		List<Area> lists=new ArrayList<Area>();
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");		
		if (user != null) {
			List<Area> areas = this.areaBo.getAreaMenuById(areaIds);
			for(Area area1:areas){
				if(area1.getAreaLevel()==5){
					Integer areaId=area1.getAreaId();
					List<Grid> grids=this.gridStaffBo.getGridNameById(areaId);
					for(Grid grid : grids){
						Area area2=new Area();
						area2.setAreaId(grid.getGridId());
						area2.setAreaName(grid.getGridName());
						area2.setAreaParentCode(area1.getAreaCode());
						lists.add(area2);
					}
				}
			}
			areas.addAll(lists);
			for(int i=0;i<areas.size();i++){
				if(areas.get(i).getAreaParentCode()==0){
					areas.remove(i);
				}
			}
			return areas;
		}else{
			return null;
		}
	}

	
	//保存方法
	@RequestMapping(value="/saveGridStaff")
	@ResponseBody
	public String saveGridStaff(HttpSession session,Grid_staff gridStaff,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		if(user!=null){
			gridStaff.setEditStaffName(user.getUserName());
			gridStaff.setEditStaffDate(new Date());
			Integer gridId=gridStaff.getGridId();
			Integer result=this.gridStaffBo.saveGridStaff(gridStaff);
			if(result>0){
				logger.info( "成功添加网格员"+ gridStaff.getGridStaffName() + "！");
			}
			return result>0 ? "1" : "0";
		}else{
			return "0";
		}
	}
	
	//获取选中行信息
	@RequestMapping(value="/getGridStaffById")
	@ResponseBody
	public Grid_staff getGridStaffById(String upGSid){
		Integer gridStaffId=Integer.parseInt(upGSid);
		List<Grid_staff> gridStaff=this.gridStaffBo.getGridStaffById(gridStaffId);
		return gridStaff.get(0);
	}
	
	//修改
	@RequestMapping(value="/editGridStaffById")
	@ResponseBody
	public String editGridStaffById(HttpSession session,Grid_staff gridStaff,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		gridStaff.setEditStaffName(user.getUserName());
		gridStaff.setEditStaffDate(new Date());
		Integer result=this.gridStaffBo.editGridStaffById(gridStaff);
		if(result>0){
			logger.info("成功修改网格员"+ gridStaff.getGridStaffName() + "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
//	//删除
//	@RequestMapping(value="/deleteGridStaff")
//	@ResponseBody
//	public String deleteGridStaff(String deGsid,HttpSession session,HttpServletRequest request){
//		User user=(User)session.getAttribute("user");
//		Integer gridStaffIds=Integer.parseInt(deGsid);
//		List<String> gridStaffNames=this.gridStaffBo.getGridStaffNameById(gridStaffIds);
//		String str1="";
//		for(String str2:gridStaffNames){
//			str1+=str2+",";
//		}
//		Integer result=this.gridStaffBo.deleteGridStaff(gridStaffIds);
//		if (result > 0) {
//			Log log = new Log(0, user.getUserId(), user.getUserName(),
//					getIpAddr(request), new Date(), "成功删除网格员"
//							+ str1 + "！");
//			this.logBo.keepOneLog(log);
//		}
//		return result>0 ? "1" : "0";
//	}
	
	//审核
	@RequestMapping(value="/updateAuditGridStaff")
	@ResponseBody
	public String updateAuditGridStaff(String audGsid){
		String[] str=audGsid.split(",");
		List<Integer> gridStaffIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			gridStaffIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.gridStaffBo.updateAuditGridStaff(gridStaffIds);
		return result>0 ? "1" : "0";
	}
	
	//查看
	@RequestMapping(value="/getOneGridStaffById")
	@ResponseBody
	public Grid_staff getOneGridStaffById(Grid_staff gridStaff){
		List<Grid_staff> gridStaffs=this.gridStaffBo.getOneGridStaffById(gridStaff);
		if(gridStaffs.size()>0){
			Grid_post gridPost=this.gridStaffBo.getGridPostById(gridStaffs.get(0).getGridPost().getGridPostId());
			Grid grid=this.gridStaffBo.getGridById(gridStaffs.get(0).getGridId());
			gridStaffs.get(0).setGrid(grid);
			gridStaffs.get(0).setGridPost(gridPost);
			return gridStaffs.get(0);
		}else{
			return null;
		}
	}
	
	//获取所有网格员职务
	@RequestMapping(value="/getGridPostName")
	@ResponseBody
	public List<Grid_post> getGridPostName(){
		List<Grid_post> gridPost=this.gridStaffBo.getGridPostName();
		return gridPost;
	}
	
	//获取该网格下所有楼栋
	@RequestMapping(value="getBlockByGridId")
	@ResponseBody
	public List<Block> getBlockByGridId(Integer gridId){
		List<Block> block=this.gridStaffBo.getBlockByGridId(gridId);
		return block;
	}
	
	//获取选中行的gridId
	@RequestMapping(value="/getGridIdByGridStaffId")
	@ResponseBody
	public Integer getGridIdByGridStaffId(Integer gridStaffId){
		Integer gridId=this.gridStaffBo.getGridIdByGridStaffId(gridStaffId);
		return gridId;
	}
	
	//获取删除后所有的网格员
	@RequestMapping(value="/getAllGridStaff")
	@ResponseBody
	public List<Grid_staff> getAllGridStaff(Integer gridId){
		List<Grid_staff> gridStaff=this.gridStaffBo.getAllGridStaff(gridId);
		return gridStaff;
	}
	
	@RequestMapping(value="/getGridStaffCountById")
	@ResponseBody
	public String getGridStaffCountById(Integer gridId){
		List<Grid_staff> gridStaffs=this.gridStaffBo.getGridStaffCountById(gridId);
		return "{\"statusCode\":200,\"message\":" + gridStaffs.size() + "}";
	}
	
	@RequestMapping(value="/getGridStaffByGridId")
	@ResponseBody
	public List<Grid_staff> getGridStaffByGridId(Integer gridId){
		List<Grid_staff> gridStaffs=this.gridStaffBo.getGridStaffCountById(gridId);
		return gridStaffs.size()>0 ? gridStaffs : null;
	}
	
	//获取所选区域下所有网格
	@RequestMapping(value="/getAllGridByAreaId")
	@ResponseBody
	public List<Grid> getAllGridByAreaId(Integer areaId){
		if(this.getAreaIds().size()>0){
			this.getAreaIds().clear();
		}
		List<Area> area=this.areaBo.getAreaByAreaId(areaId);
		this.getAllAreaIdById(area.get(0).getAreaCode());
		return this.gridStaffBo.getAllGridByAreaId(this.getAreaIds());
	}
	
}
