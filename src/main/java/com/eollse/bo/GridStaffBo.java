package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Block;
import com.eollse.po.Grid;
import com.eollse.po.GridStaffApp;
import com.eollse.po.Grid_post;
import com.eollse.po.Grid_staff;

public interface GridStaffBo {

	public List<Grid> getGridMenuById(List<Integer> areaIds);

	public List<Grid> getGridNameById(Integer areaId);

	public Integer getGridCount(List<Integer> areaIds);

	public Map<String, Object> getAllGridStaffByGridId(Integer gridIds, Integer pageSize, Integer pageCurrent);

	public List<Grid_post> getGridPostName();

	public Integer saveGridStaff(Grid_staff gridStaff);

	public Integer getGridStaffCount(Integer gridId);

	public List<Grid_staff> getGridStaffById(Integer gridStaffId);

	public Integer editGridStaffById(Grid_staff gridStaff);

	public List<Block> getBlockByGridId(Integer gridId);

	public List<String> getGridStaffNameById(Integer gridStaffId);

	public Integer deleteGridStaff(Integer gridStaffId);

	public Integer updateAuditGridStaff(List<Integer> gridStaffIds);

	public List<Grid_staff> getOneGridStaffById(Grid_staff gridStaff);

	public Grid_post getGridPostById(Integer gridPostId);

	public Grid getGridById(Integer gridId);

	public Integer getGridIdByGridStaffId(Integer gridStaffId);

	public List<Grid_staff> getAllGridStaff(Integer gridId);

	public List<Grid_staff> getGridStaffCountById(Integer gridId);

	public Integer editGridStaffGrid(Integer gridId, Integer newSGId);

	public Map<String, Object> getAllGridStaffByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent);

	public List<Grid> getAllGridByAreaId(List<Integer> areaIds);

	public void alterAppUserStatusOfStaff(Integer appUser, Integer gridStaffId);

	public List<Integer> getAllGridStaffIdByAreaId(List<Integer> gridIds);

	public List<GridStaffApp> getAllGridStaffOfGrid(Integer gridStaffId);

	public Map<String, Object> getAllGridStaffById(
		List<Integer> gridIds,
		String field, 
		Integer pageSize, 
		Integer pageCurrent
	);


    public List<Integer> getAllGridStaffByAreaIds(List<Integer> areaIds);
}
