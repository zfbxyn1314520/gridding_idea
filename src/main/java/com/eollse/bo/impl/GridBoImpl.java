package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.GridBo;
import com.eollse.dao.GridStaffDao;
import com.eollse.dao.GridDao;
import com.eollse.dao.LogDao;
import com.eollse.po.Area;
import com.eollse.po.Grid;

@Service
public class GridBoImpl implements GridBo {
	@Autowired
	private GridDao gridDao;
	@Autowired
	private LogDao logDao;
	@Autowired
	private GridStaffDao gridStaffDao;
	
	@Override
	public Map<String, Object> getAllGridByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent) {
		// TODO Auto-generated method stub
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Grid> grids=this.gridDao.getAllGridByAreaId(areaIds,x,y);
		for(Grid grid:grids){
			grid.setGridStaffCount(this.gridStaffDao.getGridStaffCountById(grid.getGridId()).size());
		}
		int totalRow = this.gridDao.getAllGridCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", grids);
		return map;
	}

	@Override
	public Integer getAllGridCount(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.gridDao.getAllGridCount(areaIds);
	}

	@Override
	public Integer saveGrid(Grid grid) {
		// TODO Auto-generated method stub
		return this.gridDao.saveGrid(grid);
	}

	@Override
	public List<Grid> getGridById(Integer gridId) {
		// TODO Auto-generated method stub
		return this.gridDao.getGridById(gridId);
	}

	@Override
	public Integer editGridById(Grid grid) {
		// TODO Auto-generated method stub
		return this.gridDao.editGridById(grid);
	}

	@Override
	public Integer updateAuditGrid(List<Integer> gridIds) {
		// TODO Auto-generated method stub
		return this.gridDao.updateAuditGrid(gridIds);
	}

	@Override
	public List<Grid> getOneGridById(Grid grid) {
		// TODO Auto-generated method stub
		return this.gridDao.getOneGridById(grid);
	}

	@Override
	public List<Grid> getAllGrid(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.gridDao.getAllGrid(areaIds);
	}

	@Override
	public Integer deleteGrid(Integer gridId) {
		// TODO Auto-generated method stub
		return this.gridDao.deleteGrid(gridId);
	}

	@Override
	public Area getAreaByGridId(Integer gridId) {
		// TODO Auto-generated method stub
		return this.gridDao.getAreaByGridId(gridId);
	}

	@Override
	public List<Integer> getAllGridIdsByAreaId(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.gridDao.getAllGridIdsByAreaId(areaIds);
	}

	
}
