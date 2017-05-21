package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.CourtBo;
import com.eollse.dao.BlockDao;
import com.eollse.dao.CourtDao;
import com.eollse.po.Court;

@Service
public class CourtBoImpl implements CourtBo{
	@Autowired
	private CourtDao courtDao;
	@Autowired
	private BlockDao blockDao;

	@Override
	public Map<String, Object> getAllCourtByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent) {
		// 计算起始数值
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Court> courts = this.courtDao.getAllCourtByAreaId(areaIds,x, y);			
		for(Court court:courts){
			court.setBlockCount(this.blockDao.getBlockCountById(court.getCourtId()).size());
		}
		int totalRow = this.courtDao.getAllCourtCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", courts);
		return map;
	}

	@Override
	public Integer saveCourt(Court court) {
		// TODO Auto-generated method stub
		return this.courtDao.saveCourt(court);
	}

	@Override
	public List<Court> getCourtById(Integer courtId) {
		// TODO Auto-generated method stub
		List<Court> courts=this.courtDao.getCourtById(courtId);
		for(Court court:courts){
			court.setBlockCount(this.blockDao.getBlockCountById(court.getCourtId()).size());
		}
		return courts;
	}

	@Override
	public Integer editCourtById(Court court) {
		// TODO Auto-generated method stub
		return this.courtDao.editCourtById(court);
	}

	@Override
	public Integer deleteCourt(List<Integer> courtIds) {
		// TODO Auto-generated method stub
		return this.courtDao.deleteCourt(courtIds);
	}

	@Override
	public Integer updateAuditCourt(List<Integer> courtIds) {
		// TODO Auto-generated method stub
		return this.courtDao.updateAuditCourt(courtIds);
	}

	@Override
	public List<Court> getOneCourtById(Court court) {
		// TODO Auto-generated method stub
		List<Court> courts=this.courtDao.getOneCourtById(court);
		courts.get(0).setBlockCount(this.blockDao.getBlockCountById(court.getCourtId()).size());
		return courts;
	}

	@Override
	public List<Court> getAllCourtsByAreaId(List<Integer> areaIds) {
		List<Court> courts = this.courtDao.getAllCourtsByAreaId(areaIds);			
		return courts;
	}

	@Override
	public Integer getAllCourtCount(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.courtDao.getAllCourtCount(areaIds);
	}

	@Override
	public List<Integer> getAllCourtId(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.courtDao.getAllCourtId(areaIds);
	}
	
	
}
