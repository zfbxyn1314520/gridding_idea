package com.eollse.po;

import java.io.Serializable;

public class Facilities_type implements Serializable {
	private Integer facilitiesTypeId;
	private Integer facilitiesTypePerId;
	private String facilitiesTypeName;
	public Facilities_type() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Facilities_type(Integer facilitiesTypeId,
			Integer facilitiesTypePerId, String facilitiesTypeName) {
		super();
		this.facilitiesTypeId = facilitiesTypeId;
		this.facilitiesTypePerId = facilitiesTypePerId;
		this.facilitiesTypeName = facilitiesTypeName;
	}
	@Override
	public String toString() {
		return "Facilities_type [facilitiesTypeId=" + facilitiesTypeId
				+ ", facilitiesTypePerId=" + facilitiesTypePerId
				+ ", facilitiesTypeName=" + facilitiesTypeName + "]";
	}
	public Integer getFacilitiesTypeId() {
		return facilitiesTypeId;
	}
	public void setFacilitiesTypeId(Integer facilitiesTypeId) {
		this.facilitiesTypeId = facilitiesTypeId;
	}
	public Integer getFacilitiesTypePerId() {
		return facilitiesTypePerId;
	}
	public void setFacilitiesTypePerId(Integer facilitiesTypePerId) {
		this.facilitiesTypePerId = facilitiesTypePerId;
	}
	public String getFacilitiesTypeName() {
		return facilitiesTypeName;
	}
	public void setFacilitiesTypeName(String facilitiesTypeName) {
		this.facilitiesTypeName = facilitiesTypeName;
	}
	
}
