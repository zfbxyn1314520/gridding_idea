package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Block;
import com.eollse.po.Grid;
import com.eollse.po.GridStaffApp;
import com.eollse.po.Grid_post;
import com.eollse.po.Grid_staff;

public interface GridStaffDao {

	public List<Grid> getGridMenuById(List<Integer> areaIds);

	public List<Grid> getGridNameById(Integer areaId);

	public Integer getGridCount(List<Integer> areaIds);

	public List<Grid_staff> getAllGridStaffByGridId(@Param("gridId")Integer gridIds, @Param("x")Integer x,
			@Param("y")Integer y);

	public Grid_post getGridPostById(Integer gridPostId);

	public List<Grid_post> getGridPostName();

	public Integer saveGridStaff(Grid_staff gridStaff);

	public Integer getGridStaffCount(Integer gridId);

	public List<Grid_staff> getGridStaffById(Integer gridStaffId);

	public Integer editGridStaffById(Grid_staff gridStaff);

	public List<Block> getBlockByGridId(Integer gridId);

	public List<String> getGridStaffNameById(@Param("gridStaffId")Integer gridStaffId);

	public Integer deleteGridStaff(Integer gridStaffId);

	public Integer updateAuditGridStaff(List<Integer> gridStaffIds);

	public List<Grid_staff> getOneGridStaffById(Grid_staff gridStaff);

	public Grid getGridById(Integer gridId);

	public Integer getGridIdByGridStaffId(@Param("gridStaffId")Integer gridStaffId);

	public List<Grid_staff> getAllGridStaff(Integer gridId);

	public List<Grid_staff> getGridStaffCountById(Integer gridId);

	public Integer editGridStaffGrid(@Param("oldId")Integer gridId, @Param("newId")Integer newSGId);

	public List<Grid_staff> getAllGridStaffByAreaId(@Param("list")List<Integer> gridIds,
			@Param("x")Integer x, @Param("y")Integer y);

	public Integer getGridCountByGridId(@Param("gridId")Integer gridIds);

	public List<Grid> getAllGridByAreaId(@Param("list")List<Integer> areaIds);

	public void alterAppUserStatusOfStaff(@Param("appUser")Integer appUser, @Param("gridStaffId")Integer gridStaffId);

	public List<Integer> getAllGridStaffIdByAreaId(@Param("list")List<Integer> gridIds);

	public List<GridStaffApp> getAllGridStaffById(
			@Param("list")List<Integer> gridIds,
			@Param("field")String field,
			@Param("x")Integer x,
			@Param("y")Integer y
		);

	public List<GridStaffApp> getAllGridStaffOfGrid(@Param("gridStaffId")Integer gridStaffId);

	public Integer getAllGridStaffByIdCount(@Param("list")List<Integer> gridIds, @Param("field")String field);


	public List<Integer> getAllGridStaffByAreaIds(@Param("list")List<Integer> areaIds);
}
