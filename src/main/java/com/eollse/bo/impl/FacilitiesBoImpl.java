package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.FacilitiesBo;
import com.eollse.dao.FacilitiesDao;
import com.eollse.po.Facilities;
import com.eollse.po.Facilities_type;

@Service
public class FacilitiesBoImpl implements FacilitiesBo {
	@Autowired
	private FacilitiesDao facilitiesDao;

	@Override
	public Map<String, Object> getAllfacilitiesByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent) {
		Integer x=(pageCurrent-1)*pageSize;
		Integer y=pageCurrent*pageSize;
		Map<String,Object> map=new HashMap<String,Object>();
		List<Facilities> facilitiess=this.facilitiesDao.getAllfacilitiesByAreaId(areaIds,x,y);
		for(Facilities facilities : facilitiess){
			Facilities_type facilitiesType=this.facilitiesDao.getFacilitiesTypeById(facilities.getFacilitiesTypeId());
			facilities.setFacilitiesType(facilitiesType);
		}
		int totalRow=this.facilitiesDao.getAllfacilitiesCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", facilitiess);
		return map;
	}

	@Override
	public Integer saveFacilitiesType(String facilitiesTypeName) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.saveFacilitiesType(facilitiesTypeName);
	}

	@Override
	public List<Facilities_type> findFacilitiesTypeIdByName(String facilitiesTypeName) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.findFacilitiesTypeIdByName(facilitiesTypeName);
	}

	@Override
	public Integer saveFacilities(Facilities facilities) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.saveFacilities(facilities);
	}

	@Override
	public Integer deleteFacilities(List<Integer> facilitiesIds) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.deleteFacilities(facilitiesIds);
	}

	@Override
	public List<Integer> getfacilitiesTypeId(List<Integer> facilitiesIds) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.getfacilitiesTypeId(facilitiesIds);
	}

	@Override
	public Integer deleteFacilitiesType(List<Integer> facilitiesTypeIds) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.deleteFacilitiesType(facilitiesTypeIds);
	}

	@Override
	public Integer updateAuditFacilities(List<Integer> facilitiesIds) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.updateAuditFacilities(facilitiesIds);
	}

	@Override
	public List<Facilities> getFacilitiesById(Integer facilitiesId) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.getFacilitiesById(facilitiesId);
	}

	@Override
	public Facilities_type getFacilitiesTypeById(Integer facilitiesTypeId) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.getFacilitiesTypeById(facilitiesTypeId);
	}

	@Override
	public Integer editfacilitiesById(Facilities facilities) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.editfacilitiesById(facilities);
	}

	@Override
	public List<Facilities> getOneFacilitiesById(Facilities facilities) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.getOneFacilitiesById(facilities);
	}

	@Override
	public List<Facilities_type> getAllFacilitiesType() {
		// TODO Auto-generated method stub
		return this.facilitiesDao.getAllFacilitiesType();
	}

	@Override
	public Integer getAllfacilitiesCount(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.getAllfacilitiesCount(areaIds);
	}

	@Override
	public List<String> getFacilitiesNameById(List<Integer> facilitiesIds) {
		// TODO Auto-generated method stub
		return this.facilitiesDao.getFacilitiesNameById(facilitiesIds);
	}
	
}
