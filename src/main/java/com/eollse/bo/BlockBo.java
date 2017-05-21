package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Block;
import com.eollse.po.Grid;
import com.eollse.po.Grid_staff;

public interface BlockBo {

	public Map<String, Object> getAllBlockByCourtId(List<Integer> courtIds,
			Integer pageSize, Integer pageCurrent);

	public Integer saveBlock(Block block, Integer floorCount);

	public List<Block> getBlockById(Integer blockId);

	public Integer editBlockById(Block block);

	public Integer updateAuditBlock(List<Integer> blockIds);

	public Integer getAllBlockCount(List<Integer> areaIds);

	public List<Grid_staff> getGridStaffName(Integer gridId);

	public List<Grid> getGridName(List<Integer> areaIds);

	public List<Block> getOneBlockById(Block block);

	public List<Grid> getGridById(Integer gridId);

	public List<Grid_staff> getGridStaffById(Integer gridStaffId);

	public List<Block> getBlockName(Integer courtIds);

	public List<Block> getBlockByCourtIds(List<Integer> courtIds);

	public List<Block> getBlockByGridStaffId(Integer gridStaffId);

	public Integer editBlockGridStaff(Integer gridStaffIds, Integer newId);

	public List<Integer> getUnitIdByBlockId(Integer blockId);


	public List<Block> getBlockCountById(Integer courtId);

	public Integer editBlockGrid(Integer gridId, Integer newGBId);

	public List<Block> getBlockByGridId(Integer gridId);

	public List<Integer> getAllBlockId(List<Integer> courtIds);


}
