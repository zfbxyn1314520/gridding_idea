package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Area;

public interface AreaBo {

	public List<Area> getAreaByAreaId(Integer areaId);
	
	public List<Area> getSubAreaByAreaId(Long areaCode);

	public Area getAreaByAreaCode(Long areaCode);

	public List<Area> getAreaMenuById(List<Integer> areaIds);

	public List<Area> getAreaIdByAreaName(String string);

	public Map<String, Object> getAllAreaByAreaId(List<Integer> areaIds,Integer pageSize, Integer pageCurrent);

	public List<Area> getAllAreaMenu();

    public List<Area> getAllAreaByLevel(Integer areaId);
}
