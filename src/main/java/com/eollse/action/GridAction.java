package com.eollse.action;

/**
 * author 刘春晓
 * content 网格控制器
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
import com.eollse.bo.GridBo;
import com.eollse.bo.GridStaffBo;
import com.eollse.po.Area;
import com.eollse.po.Grid;
import com.eollse.po.User;

@Controller
@RequestMapping(value="/grid")
public class GridAction extends CommonAction {
	@Autowired
	private GridBo gridBo;
	@Autowired
	private AreaBo areaBo;
	@Autowired
	private GridStaffBo gridStaffBo;
	
	/**
	 * 获取所有网格
	 * @param session 用户登录session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAllGridByAreaId")
	@ResponseBody
	public String getAllGridByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
		    Map<String, Object> map=this.gridBo.getAllGridByAreaId(areaIds,pageSize,pageCurrent);
		    String content=this.createPageJSONString(map);
		    return content;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取区域名称
	 * @param session
	 * @return 区域
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
	 * 添加保存方法
	 * @param grid 网格实体
	 * @param session
	 * @param request 请求
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveGrid")
	@ResponseBody
	public String saveGrid(Grid grid,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			grid.setEditGridName(user.getUserName());
			grid.setEditGridDate(new Date());
			Integer result=this.gridBo.saveGrid(grid);
			if(result>0){
				logger.info("成功添加网格"+ grid.getGridName() + "！");
			}
			return result>0 ? "1" : "0";
		}else{
			return "index.html";
		}
	}
	
	/**
	 * 获取修改所需信息
	 * @param upGrid 网格Id
	 * @return 网格实体
	 */
	@RequestMapping(value="/getGridById")
	@ResponseBody
	public Grid getGridById(String upGrid){
		Integer gridId=Integer.parseInt(upGrid);
		List<Grid> grid=this.gridBo.getGridById(gridId);
		if(grid.size()>0){
			Integer areaId=grid.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			grid.get(0).setArea(area.get(0));
			return grid.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 修改
	 * @param grid 网格实体
	 * @param request
	 * @param session
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editGridById")
	@ResponseBody
	public String editGridById(Grid grid,HttpServletRequest request,HttpSession session){
		User user=(User)session.getAttribute("user");
		grid.setEditGridName(user.getUserName());
		grid.setEditGridDate(new Date());
		Integer result=this.gridBo.editGridById(grid);
		if (result > 0) {
			logger.info("成功修改网格"	+ grid.getGridName() + "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 审核
	 * @param audGrid
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/updateAuditGrid")
	@ResponseBody
	public String updateAuditGrid(String audGrid){
		String[] str=audGrid.split(",");
		List<Integer> gridIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			gridIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.gridBo.updateAuditGrid(gridIds);
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 查看
	 * @param grid 网格实体
	 * @return 网格实体
	 */
	@RequestMapping(value="/getOneGridById")
	@ResponseBody
	public Grid getOneGridById(Grid grid){
		List<Grid> grids=this.gridBo.getOneGridById(grid);
		if(grids.size()>0){
			Integer areaId=grids.get(0).getArea().getAreaId();
			List<Area> area=this.areaBo.getAreaByAreaId(areaId);
			grids.get(0).setArea(area.get(0));
			grids.get(0).setGridStaffCount(this.gridStaffBo.getGridStaffCountById(grid.getGridId()).size());
			return grids.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取所有网格
	 * @param session 
	 * @return 网格类集合
	 */
	@RequestMapping(value="/getAllGrid")
	@ResponseBody
	public List<Grid> getAllGrid(HttpSession session){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
		    List<Grid> grids=this.gridBo.getAllGrid(areaIds);
		    return grids;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据网格Id获取区域
	 * @param gridId
	 * @return 区域实体
	 */
	@RequestMapping(value="/getAreaByGridId")
	@ResponseBody
	public Area getAreaByGridId(Integer gridId){
		Area area=this.gridBo.getAreaByGridId(gridId);
		return area;
	}
	
}
