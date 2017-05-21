package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Facilities;
import com.eollse.po.Facilities_type;

public interface FacilitiesBo {

	public Map<String, Object> getAllfacilitiesByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent);


	public Integer saveFacilitiesType(String facilitiesTypeName);


	public List<Facilities_type> findFacilitiesTypeIdByName(String facilitiesTypeName);


	public Integer saveFacilities(Facilities facilities);


	public Integer deleteFacilities(List<Integer> facilitiesIds);


	public List<Integer> getfacilitiesTypeId(List<Integer> facilitiesIds);


	public Integer deleteFacilitiesType(List<Integer> facilitiesTypeIds);


	public Integer updateAuditFacilities(List<Integer> facilitiesIds);


	public List<Facilities> getFacilitiesById(Integer facilitiesId);


	public Facilities_type getFacilitiesTypeById(Integer facilitiesTypeId);


	public Integer editfacilitiesById(Facilities facilities);


	public List<Facilities> getOneFacilitiesById(Facilities facilities);


	public List<Facilities_type> getAllFacilitiesType();


	public Integer getAllfacilitiesCount(List<Integer> areaIds);


	public List<String> getFacilitiesNameById(List<Integer> facilitiesIds);



}
