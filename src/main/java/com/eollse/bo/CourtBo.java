package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Court;

public interface CourtBo {

	public Map<String, Object> getAllCourtByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent);

	public Integer saveCourt(Court court);

	public List<Court> getCourtById(Integer courtId);

	public Integer editCourtById(Court court);

	public Integer deleteCourt(List<Integer> courtIds);

	public Integer updateAuditCourt(List<Integer> courtIds);

	public List<Court> getOneCourtById(Court court);

	public List<Court> getAllCourtsByAreaId(List<Integer> areaIds);

	public Integer getAllCourtCount(List<Integer> areaIds);

	public List<Integer> getAllCourtId(List<Integer> areaIds);


}
