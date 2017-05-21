package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Block;
import com.eollse.po.Grid;
import com.eollse.po.Grid_staff;
import com.eollse.po.Unit;

public interface BlockDao {

	public List<Block> getAllBlockByCourtId(@Param("list")List<Integer> courtIds, @Param("x")Integer x,
			@Param("y")Integer y);

	public int getAllBlockCount(@Param("list")List<Integer> courtIds);

	public Integer saveBlock(Block block);

	public List<Block> getBlockById(Integer blockId);

	public Integer editBlockById(Block block);

	public Integer updateAuditBlock(List<Integer> blockIds);

	public List<Grid_staff> getGridStaffName(Integer gridId);

	public List<Grid> getGridName(List<Integer> areaIds);

	public List<Block> getOneBlockById(Block block);

	public List<Grid> getGridById(Integer gridId);

	public List<Grid_staff> getGridStaffById(Integer gridStaffId);

	public List<Block> getBlockName(Integer courtIds);

	public List<Block> getBlockByCourtIds(List<Integer> courtIds);

	public List<Block> getBlockByGridStaffId(Integer gridStaffId);

	public Integer editBlockGridStaff(@Param("oldGridStaffId")Integer gridStaffIds, @Param("newGridStaffId")Integer newId);

	public List<Integer> getUnitIdByBlockId(Integer blockId);

	public List<Integer> getFloorIdByUnitId(@Param("list")List<Integer> unitIds);

	public List<Block> getBlockCountById(Integer courtId);

	public Integer editBlockGrid(@Param("oldId")Integer gridId, @Param("newId")Integer newGBId);

	public List<Block> getBlockByGridId(Integer gridId);

	public Integer getMaxBlockId();

	public void saveUnit(Unit unit);

	public List<Integer> getUnitFloorCountById(Integer blockId);

	public List<Integer> getAllBlockId(@Param("list")List<Integer> courtIds);


}
