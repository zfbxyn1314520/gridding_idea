package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Area;
import com.eollse.po.Grid;

public interface GridBo {

	public Map<String, Object> getAllGridByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent);

	public Integer getAllGridCount(List<Integer> areaIds);

	public Integer saveGrid(Grid grid);

	public List<Grid> getGridById(Integer gridId);

	public Integer editGridById(Grid grid);

	public Integer updateAuditGrid(List<Integer> gridIds);

	public List<Grid> getOneGridById(Grid grid);

	public List<Grid> getAllGrid(List<Integer> areaIds);

	public Integer deleteGrid(Integer gridId);

	public Area getAreaByGridId(Integer gridId);

	public List<Integer> getAllGridIdsByAreaId(List<Integer> areaIds);


}
