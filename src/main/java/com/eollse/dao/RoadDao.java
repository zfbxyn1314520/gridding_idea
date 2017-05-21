package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Road;
import com.eollse.po.Road_grade;

public interface RoadDao {

	public List<Road> getAllRoadByAreaId(@Param("list")List<Integer> areaId, @Param("x")Integer x, @Param("y")Integer y);

	public int getAllRoadsCount(List<Integer> areaIds);

	public Road_grade getRoadGradeById(@Param("roadGradeId")Integer roadGradeId);

	public Integer saveRoad(Road road);

	public Integer updateAuditRoad(List<Integer> roadIds);

	public Integer deleteRoad(List<Integer> roadIds);

	public List<Road> getOneRoadById(Road road);

	public Integer editRoadById(Road road);

	public List<Road> getRoadById(Integer roadId);

	public Road_grade findroadGradeByRoadGradeId(Integer roadGradeId);

	public List<Road_grade> getAllRoadGrade();

	public List<String> getRoadNameById(List<Integer> roadIds);
	
}
