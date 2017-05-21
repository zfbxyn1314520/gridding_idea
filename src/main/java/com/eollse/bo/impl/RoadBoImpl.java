package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.RoadBo;
import com.eollse.dao.RoadDao;
import com.eollse.po.Road;
import com.eollse.po.Road_grade;

@Service
public class RoadBoImpl implements RoadBo {
	@Autowired
	private RoadDao roadDao;

	@Override
	public Map<String, Object> getAllRoadByAreaId(List<Integer> areaIds,Integer pageSize, Integer pageCurrent) {
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Road> roads= this.roadDao.getAllRoadByAreaId(areaIds,x, y);
		for(Road road:roads){
			Road_grade roadGrade=this.roadDao.getRoadGradeById(road.getRoadGradeId());
			road.setRoadGrade(roadGrade);
		}
		
		int totalRow = this.roadDao.getAllRoadsCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", roads);
		return map;
	}

	@Override
	public Integer saveRoad(Road road) {
		// TODO Auto-generated method stub
		return this.roadDao.saveRoad(road);
	}

	@Override
	public Integer updateAuditRoad(List<Integer> roadIds) {
		// TODO Auto-generated method stub
		return this.roadDao.updateAuditRoad(roadIds);
	}

	@Override
	public Integer deleteRoad(List<Integer> roadIds) {
		// TODO Auto-generated method stub
		return this.roadDao.deleteRoad(roadIds);
	}

	@Override
	public List<Road> getOneRoadById(Road road) {
		// TODO Auto-generated method stub
		List<Road> roads=this.roadDao.getOneRoadById(road);
		Road_grade roadGrade=this.roadDao.getRoadGradeById(roads.get(0).getRoadGradeId());
		roads.get(0).setRoadGrade(roadGrade);
		return roads;
	}

	@Override
	public Integer editRoadById(Road road) {
		// TODO Auto-generated method stub
		return this.roadDao.editRoadById(road);
	}

	@Override
	public List<Road> getRoadById(Integer roadId) {
		// TODO Auto-generated method stub
		return this.roadDao.getRoadById(roadId);
	}

	@Override
	public List<Road_grade> getAllRoadGrade() {
		// TODO Auto-generated method stub
		return this.roadDao.getAllRoadGrade();
	}

	@Override
	public Integer getAllRoadsCount(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.roadDao.getAllRoadsCount(areaIds);
	}

	@Override
	public List<String> getRoadNameById(List<Integer> roadIds) {
		// TODO Auto-generated method stub
		return this.roadDao.getRoadNameById(roadIds);
	}

	
}
