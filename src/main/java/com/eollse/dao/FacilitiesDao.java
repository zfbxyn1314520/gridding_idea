package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Facilities;
import com.eollse.po.Facilities_type;

public interface FacilitiesDao {

	public List<Facilities> getAllfacilitiesByAreaId(@Param("list")List<Integer> areaIds, @Param("x")Integer x,
			@Param("y")Integer y);

	public int getAllfacilitiesCount(List<Integer> areaIds);

	public Facilities_type getFacilitiesTypeById(@Param("facilitiesTypeId")Integer facilitiesTypeId);

	public Integer saveFacilitiesType(String facilitiesTypeName);

	public List<Facilities_type> findFacilitiesTypeIdByName(String facilitiesTypeName);

	public Integer saveFacilities(Facilities facilities);

	public Integer deleteFacilities(List<Integer> facilitiesIds);

	public List<Integer> getfacilitiesTypeId(List<Integer> facilitiesIds);

	public Integer deleteFacilitiesType(List<Integer> facilitiesTypeIds);

	public Integer updateAuditFacilities(List<Integer> facilitiesIds);

	public List<Facilities> getFacilitiesById(Integer facilitiesId);

	public Integer editfacilitiesById(Facilities facilities);

	public List<Facilities> getOneFacilitiesById(Facilities facilities);

	public List<Facilities_type> getAllFacilitiesType();

	public List<String> getFacilitiesNameById(List<Integer> facilitiesIds);

}
