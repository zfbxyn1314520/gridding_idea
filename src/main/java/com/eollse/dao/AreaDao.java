package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Area;
import com.eollse.po.Store;

public interface AreaDao {

	public List<Area> getAreaByAreaId(@Param("areaId")Integer areaId);
	
	public List<Area> getSubAreaByAreaId(@Param("areaCode")Long areaCode);

	public Area getAreaByAreaCode(@Param("areaCode")Long areaCode);

	public List<Area> getAreaMenuById(List<Integer> areaIds);

	public List<Area> getAreaIdByAreaName(@Param("areaName")String areaName);

	public List<Store> getAllAreaByAreaId(@Param("list")List<Integer> areaIds, @Param("x")Integer x,@Param("y")Integer y);

	public Integer getAllAreasCount(List<Integer> areaIds);

	public List<Area> getAllAreaMenu();

	public List<Area> getAllAreaByLevel(@Param("areaId")Integer areaId);

	public Integer addNewArea(Area area);
}
