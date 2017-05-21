package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Court;

public interface CourtDao {

	public List<Court> getAllCourtByAreaId(@Param("list")List<Integer> areaIds, @Param("x")Integer x, @Param("y")Integer y);

	public int getAllCourtCount(List<Integer> areaIds);

	public Integer saveCourt(Court court);

	public List<Court> getCourtById(Integer courtId);

	public Integer editCourtById(Court court);

	public Integer deleteCourt(@Param("list")List<Integer> courtIds);

	public Integer updateAuditCourt(List<Integer> courtIds);

	public List<Court> getOneCourtById(Court court);

	public List<Court> getAllCourtsByAreaId(List<Integer> areaIds);

	public List<Integer> getAllCourtId(@Param("list")List<Integer> areaIds);
	
}
