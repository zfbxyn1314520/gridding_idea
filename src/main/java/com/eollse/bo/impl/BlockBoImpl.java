package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.BlockBo;
import com.eollse.dao.BlockDao;
import com.eollse.dao.HouseDao;
import com.eollse.po.Block;
import com.eollse.po.Grid;
import com.eollse.po.Grid_staff;
import com.eollse.po.Unit;

@Service
public class BlockBoImpl implements BlockBo {
	@Autowired
	private BlockDao blockDao;
	@Autowired
	private HouseDao houseDao;
	private List<Integer> unitIds;

	@Override
	public Map<String, Object> getAllBlockByCourtId(List<Integer> courtIds,
			Integer pageSize, Integer pageCurrent) {
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Block> blocks = this.blockDao.getAllBlockByCourtId(courtIds,x,y);	
		for(Block block:blocks){
			unitIds=this.blockDao.getUnitIdByBlockId(block.getBlockId());
			if(unitIds.size()>0){
				block.setHouseCount(this.houseDao.getHouseCountById(unitIds).size());
			}
			if(this.blockDao.getUnitIdByBlockId(block.getBlockId()).size()>0){
				block.setUnitFloorCount(this.blockDao.getUnitFloorCountById(block.getBlockId()).get(0));
			}
		}
		int totalRow = this.blockDao.getAllBlockCount(courtIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", blocks);
		return map;
	}

	@Override
	public Integer saveBlock(Block block,Integer floorCount) {
		// TODO Auto-generated method stub
		Integer result=this.blockDao.saveBlock(block);
		Integer blockId=this.blockDao.getMaxBlockId();
		String courtName=this.houseDao.getCourtNameById(block.getCourtId());
		Integer unitCount=block.getUnitCount();
		String unitName="";
		Unit unit=new Unit();
		if(result>0){
			for(int i=1;i<=unitCount;i++){
				unitName=courtName+""+block.getBlockName()+""+i+"单元";
				unit.setBlockId(blockId);
				unit.setUnitName(unitName);
				unit.setFloorCount(floorCount);
				this.blockDao.saveUnit(unit);
			}
		}
		
		return result;
	}

	@Override
	public List<Block> getBlockById(Integer blockId) {
		// TODO Auto-generated method stub
		List<Block> blocks=this.blockDao.getBlockById(blockId);
		for(Block block:blocks){
			block.setUnitFloorCount(this.blockDao.getUnitFloorCountById(block.getBlockId()).get(0));
		}
		return blocks;
	}

	@Override
	public Integer editBlockById(Block block) {
		// TODO Auto-generated method stub
		return this.blockDao.editBlockById(block);
	}

	@Override
	public Integer updateAuditBlock(List<Integer> blockIds) {
		// TODO Auto-generated method stub
		return this.blockDao.updateAuditBlock(blockIds);
	}

	@Override
	public Integer getAllBlockCount(List<Integer> courtIds) {
		// TODO Auto-generated method stub
		return this.blockDao.getAllBlockCount(courtIds);
	}

	@Override
	public List<Grid_staff> getGridStaffName(Integer gridId) {
		// TODO Auto-generated method stub
		return this.blockDao.getGridStaffName(gridId);
	}

	@Override
	public List<Grid> getGridName(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.blockDao.getGridName(areaIds);
	}

	@Override
	public List<Block> getOneBlockById(Block block) {
		// TODO Auto-generated method stub
		return this.blockDao.getOneBlockById(block);
	}

	@Override
	public List<Grid> getGridById(Integer gridId) {
		// TODO Auto-generated method stub
		return this.blockDao.getGridById(gridId);
	}

	@Override
	public List<Grid_staff> getGridStaffById(Integer gridStaffId) {
		// TODO Auto-generated method stub
		return this.blockDao.getGridStaffById(gridStaffId);
	}

	@Override
	public List<Block> getBlockName(Integer courtIds) {
		// TODO Auto-generated method stub
		return this.blockDao.getBlockName(courtIds);
	}

	@Override
	public List<Block> getBlockByCourtIds(List<Integer> courtIds) {
		// TODO Auto-generated method stub
		return this.blockDao.getBlockByCourtIds(courtIds);
	}

	@Override
	public List<Block> getBlockByGridStaffId(Integer gridStaffId) {
		// TODO Auto-generated method stub
		return this.blockDao.getBlockByGridStaffId(gridStaffId);
	}


	@Override
	public Integer editBlockGridStaff(Integer gridStaffIds, Integer newId) {
		// TODO Auto-generated method stub
		return this.blockDao.editBlockGridStaff(gridStaffIds,newId);
	}

	@Override
	public List<Integer> getUnitIdByBlockId(Integer blockId) {
		// TODO Auto-generated method stub
		return this.blockDao.getUnitIdByBlockId(blockId);
	}


	@Override
	public List<Block> getBlockCountById(Integer courtId) {
		// TODO Auto-generated method stub
		return this.blockDao.getBlockCountById(courtId);
	}

	@Override
	public Integer editBlockGrid(Integer gridId, Integer newGBId) {
		// TODO Auto-generated method stub
		return this.blockDao.editBlockGrid(gridId,newGBId);
	}

	@Override
	public List<Block> getBlockByGridId(Integer gridId) {
		// TODO Auto-generated method stub
		return this.blockDao.getBlockByGridId(gridId);
	}

	@Override
	public List<Integer> getAllBlockId(List<Integer> courtIds) {
		// TODO Auto-generated method stub
		return this.blockDao.getAllBlockId(courtIds);
	}

}
