package com.eollse.action;

/**
 * author 刘春晓
 * content 楼栋控制器
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
import com.eollse.bo.BlockBo;
import com.eollse.bo.CourtBo;
import com.eollse.bo.GridStaffBo;
import com.eollse.bo.GridBo;
import com.eollse.bo.PopulationBo;
import com.eollse.po.Block;
import com.eollse.po.Court;
import com.eollse.po.Grid;
import com.eollse.po.Grid_staff;
import com.eollse.po.Population;
import com.eollse.po.User;


@Controller
@RequestMapping(value="/block")
public class BlockAction extends CommonAction {
	@Autowired
	private BlockBo blockBo;
	@Autowired
	private CourtBo courtBo;
	@Autowired
	private AreaBo areaBo;
	@Autowired
	private GridStaffBo gridStaffBo;
	@Autowired
	private PopulationBo popBo;
	@Autowired
	private GridBo gridBo;
	
	/**
	 * 查询所有楼栋信息
	 * @param session 用户登录session
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return 
	 */
	@RequestMapping(value="/getAllBlockByCourtId")
	@ResponseBody
	public String getAllBlockByCourtId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
		    List<Integer> courtIds=this.courtBo.getAllCourtId(areaIds);
		    Map<String,Object> map1=this.blockBo.getAllBlockByCourtId(courtIds,pageSize,pageCurrent);
		    return this.createPageJSONString(map1);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取用户区域下所有小区
	 * @param session 用户登录session
	 * @return 小区类型集合
	 */
	@RequestMapping(value="/getCourtName")
	@ResponseBody
	public List<Court> getCourtName(HttpSession session){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			List<Court> court=this.courtBo.getAllCourtsByAreaId(areaIds);
			return court;
		}else{
			return null;
		}
	}
	
	/**
	 * 添加楼栋保存方法
	 * @param block 楼栋实体类
	 * @param floorCount 楼层数
	 * @param session 用户登录session
	 * @param request 请求
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveBlock")
	@ResponseBody
	public String saveBlock(Block block,Integer floorCount,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			block.setEditBlockName(user.getUserName());
			block.setEditBlockDate(new Date());
			List<Court> courts=this.courtBo.getAllCourtsByAreaId(areaIds);
			List<Integer> courtIds=new ArrayList<Integer>();
			for(Court court : courts) {
				courtIds.add(court.getCourtId());
			}
			Integer result=this.blockBo.saveBlock(block,floorCount);
			if(result>0){
//				Log log = new Log(0, user.getUserId(), user.getUserName(),
//						getIpAddr(request), new Date(), "成功添加楼栋"
//								+ block.getBlockName() + "！");
//				this.logBo.keepOneLog(log);
				logger.info("成功添加楼栋"+ block.getBlockName() +"！");
			}
			return result>0 ? "1" : "0";
		}else{
			return "index.html";
		}
	}
	
	/**
	 * 修改弹窗所需数据
	 * @param upBid 楼栋Id
	 * @return
	 */
	@RequestMapping(value="/getBlockById")
	@ResponseBody
	public Block getBlockById(String upBid){
		Integer blockId=Integer.parseInt(upBid);
		List<Block> block=this.blockBo.getBlockById(blockId);
		if(block.size()>0){
			Integer courtId=block.get(0).getCourt().getCourtId();
			List<Court> court=this.courtBo.getCourtById(courtId);
			block.get(0).setCourt(court.get(0));
			return block.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 修改楼栋
	 * @param block 楼栋实体
	 * @param request 
	 * @param session
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editBlockById")
	@ResponseBody
	public String editBlockById(Block block,HttpServletRequest request,HttpSession session){
		User user=(User)session.getAttribute("user");
		block.setEditBlockName(user.getUserName());
		block.setEditBlockDate(new Date());
		Integer result=this.blockBo.editBlockById(block);
		if(result>0){
			logger.info( "成功修改楼栋"+ block.getBlockName()  +"！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 审核楼栋
	 * @param audBid 楼栋Id组成的字符串
	 * @return
	 */
	@RequestMapping(value="/updateAuditBlock")
	@ResponseBody
	public String updateAuditBlock(String audBid){
		String[] str=audBid.split(",");
		List<Integer> blockIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			blockIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.blockBo.updateAuditBlock(blockIds);
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 获取网格员下拉框所需数据
	 * @param session
	 * @param gridId 网格Id
	 * @return 网格员类型集合
	 */
	@RequestMapping(value="/getGridStaffName")
	@ResponseBody
	public List<Grid_staff> getGridStaffName(HttpSession session,String gridId){
			Integer gridIds=Integer.parseInt(gridId);
			List<Grid_staff> gridStaff=this.blockBo.getGridStaffName(gridIds);
			return gridStaff;
	}
	
	/**
	 * 获取所有网格
	 * @param session
	 * @return 网格类型集合
	 */
	@RequestMapping(value="/getGridName")
	@ResponseBody
	public List<Grid> getGridName(HttpSession session){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			List<Grid> grid=this.blockBo.getGridName(areaIds);
			return grid;
		}else{
			return null;
		}
	}
	
	/**
	 * 查看方法
	 * @param block 楼栋实体
	 * @return 楼栋实体
	 */
	@RequestMapping(value="/getOneBlockById")
	@ResponseBody
	public Block getOneBlockById(Block block){
		List<Block> blocks=this.blockBo.getOneBlockById(block);
		if(blocks.size()>0){
			Integer courtId=blocks.get(0).getCourt().getCourtId();
			Integer gridId=blocks.get(0).getGridId();
			Integer gridStaffId=blocks.get(0).getGridStaffId();
			List<Court> court=this.courtBo.getCourtById(courtId);
			List<Grid> grid=this.blockBo.getGridById(gridId);
			List<Grid_staff> gridStaff=this.blockBo.getGridStaffById(gridStaffId);
			blocks.get(0).setGrid(grid.get(0));
			blocks.get(0).setGridStaff(gridStaff.get(0));
			blocks.get(0).setCourt(court.get(0));
			return blocks.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据网格员Id获取楼栋
	 * @param gridStaffId 网格员Id
	 * @return 楼栋类型集合
	 */
	@RequestMapping(value="/getBlockByGridStaffId")
	@ResponseBody
	public List<Block> getBlockByGridStaffId(String gridStaffId){
		Integer gridStaffIds=Integer.parseInt(gridStaffId);
		List<Block> blocks=this.blockBo.getBlockByGridStaffId(gridStaffIds);
		return blocks;
	}
	
	/**
	 * 重新配置楼栋网格员
	 * @param session
	 * @param request 
	 * @param oldId 旧的网格员Id
	 * @param newBgId 新的楼栋网格员Id
	 * @param newPgId 新的人口网格员Id
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editBlockGridStaff",produces="text/html;charset=utf-8")
	@ResponseBody
	public String editBlockGridStaff(HttpSession session,HttpServletRequest request,String oldId,Integer newBgId,Integer newPgId){
		User user=(User)session.getAttribute("user");
		Integer gridStaffIds=Integer.parseInt(oldId);
		List<Block> blocks=this.blockBo.getBlockByGridStaffId(gridStaffIds);
		List<Population> pop=this.popBo.getPopByGridStaffId(gridStaffIds);
		List<Grid_staff> gridStaff=this.gridStaffBo.getGridStaffById(gridStaffIds);
		logger.info( "成功删除网格员"+ gridStaff.get(0).getGridStaffName() + "！");
		if(blocks.size()==0){
			if(pop.size()==0){
				return this.gridStaffBo.deleteGridStaff(gridStaffIds)>0 ? "无下属楼栋、人口信息！删除成功！" : "删除失败！";
			}else{
				return this.popBo.editPopGridStaff(gridStaffIds,newPgId)>0 && this.gridStaffBo.deleteGridStaff(gridStaffIds)>0 ? "无下属楼栋信息！人口配置成功！删除成功！" : "删除失败！";
			}
		}else{
			if(pop.size()==0){
				return this.blockBo.editBlockGridStaff(gridStaffIds,newBgId)>0 && this.gridStaffBo.deleteGridStaff(gridStaffIds)>0 ? "无下属人口信息！楼栋配置成功！删除成功！" : "删除失败！";
			}else{
				return this.blockBo.editBlockGridStaff(gridStaffIds,newBgId)>0 && this.popBo.editPopGridStaff(gridStaffIds,newPgId)>0 && this.gridStaffBo.deleteGridStaff(gridStaffIds)>0 ? "楼栋、人口配置成功！删除成功！" : "删除失败！";
			}
		}
	}
	
	
	/**
	 * 获取小区下的所有楼栋
	 * @param courtId 小区Id
	 * @return
	 */
	@RequestMapping(value="/getBlockCountById")
	@ResponseBody
	public String getBlockCountById(Integer courtId){
		List<Block> blocks=this.blockBo.getBlockCountById(courtId);
		return "{\"statusCode\":200,\"message\":" + blocks.size() + "}";
	}
	
	/**
	 * 获取小区下的所有楼栋
	 * @param courtId 小区Id
	 * @return 楼栋类型集合
	 */
	@RequestMapping(value="/getBlockByCourtId")
	@ResponseBody
	public List<Block> getBlockByCourtId(Integer courtId){
		List<Block> blocks=this.blockBo.getBlockCountById(courtId);
		return blocks.size()>0 ? blocks : null;
	}
	
	/**
	 * 重新配置楼栋、人口、网格员网格
	 * @param session
	 * @param request
	 * @param oldId 旧的网格Id
	 * @param newGBId 新的楼栋网格Id
	 * @param newSGId 新的网格员网格Id
	 * @param newPGId 新的人口网格Id
	 * @return
	 */
	@RequestMapping(value="/editBlockGrid",produces="text/html;charset=utf-8")
	@ResponseBody
	public String editBlockGrid(HttpSession session,HttpServletRequest request,String oldId,Integer newGBId,Integer newSGId,Integer newPGId){
		User user=(User)session.getAttribute("user");
		Integer gridId=Integer.parseInt(oldId);
		List<Block> blocks=this.blockBo.getBlockByGridId(gridId);
		List<Grid_staff> gridStaffs=this.gridStaffBo.getGridStaffCountById(gridId); 
		List<Population> pop=this.popBo.getPopByGridId(gridId);
		List<Grid> grids=this.gridBo.getGridById(gridId);
		logger.info("成功删除网格"+ grids.get(0).getGridName() + "！");
		if(blocks.size()==0){
			if(gridStaffs.size()==0){
				if(pop.size()==0){
					return this.gridBo.deleteGrid(gridId)>0 ? "无下属楼栋、网格员、人口信息！删除成功！" : "删除失败！";
				}else{
					return this.popBo.editPopGrid(gridId,newPGId)>0 && this.gridBo.deleteGrid(gridId)>0 ? "无下属楼栋、网格员！人口重新配置成功！删除成功！" : "删除失败！";
				}
			}else{
				if(pop.size()==0){
					return this.gridStaffBo.editGridStaffGrid(gridId,newSGId)>0 && this.gridBo.deleteGrid(gridId)>0 ? "无下属楼栋、人口！网格员配置成功！删除成功！" : "删除失败！";
				}else{
					return this.gridStaffBo.editGridStaffGrid(gridId,newSGId)>0 && this.popBo.editPopGrid(gridId,newPGId)>0 && this.gridBo.deleteGrid(gridId)>0 ? "无下属楼栋！人口、网格员配置成功！删除成功！" : "删除失败！";
				}
			}
		}else{
			if(gridStaffs.size()==0){
				if(pop.size()==0){
					return this.blockBo.editBlockGrid(gridId,newGBId)>0 && this.gridBo.deleteGrid(gridId)>0 ? "无下属网格员、人口信息！楼栋配置成功！删除成功！" : "删除失败！";
				}else{
					return this.blockBo.editBlockGrid(gridId,newGBId)>0 && this.popBo.editPopGrid(gridId,newPGId)>0 && this.gridBo.deleteGrid(gridId)>0 ? "无下属网格员！人口、楼栋配置成功！删除成功！" : "删除失败！";
				}
			}else{
				if(pop.size()==0){
					return this.blockBo.editBlockGrid(gridId,newGBId)>0 && this.gridStaffBo.editGridStaffGrid(gridId,newSGId)>0 && this.gridBo.deleteGrid(gridId)>0 ? "无下属人口！网格员、楼栋配置成功！删除成功！" : "删除失败！";
				}else{
					return this.blockBo.editBlockGrid(gridId,newGBId)>0 && this.gridStaffBo.editGridStaffGrid(gridId,newSGId)>0 && this.popBo.editPopGrid(gridId,newPGId)>0 && this.gridBo.deleteGrid(gridId)>0 ? "人口、网格员、楼栋配置成功！删除成功！" : "删除失败！";
				}
			}
		}
	}
}
