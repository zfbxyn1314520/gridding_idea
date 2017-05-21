package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Road;
import com.eollse.po.Road_grade;

public interface RoadBo {

	public Map<String, Object> getAllRoadByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent);

	public Integer saveRoad(Road road);

	public Integer updateAuditRoad(List<Integer> roadIds);

	public Integer deleteRoad(List<Integer> roadIds);

	public List<Road> getOneRoadById(Road road);

	public Integer editRoadById(Road road);

	public List<Road> getRoadById(Integer roadId);

	public List<Road_grade> getAllRoadGrade();

	public Integer getAllRoadsCount(List<Integer> areaIds);

	public List<String> getRoadNameById(List<Integer> roadIds);


}
