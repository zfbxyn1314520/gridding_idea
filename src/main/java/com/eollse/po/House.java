package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class House implements Serializable {
	private Integer houseId;
	private Integer statusId;
	private Integer typeId;
	private Integer unitId;
	private String houseNum;
	private String houseArea;
	private String houseSite;
	private String editHouseName;
	private Date editHouseDate;
	private Integer houseAudit;
	private House_status houseStatus;
	private House_type houseType;
	private String houseHolder;
	private Unit unit;

	public House() {
		super();
		this.houseStatus = new House_status();
		this.houseType = new House_type();
		this.unit = new Unit();
	}

	public House(Integer houseId, Integer statusId, Integer typeId,
			Integer unitId, String houseNum, String houseArea,
			String houseSite, String editHouseName, Date editHouseDate,
			Integer houseAudit, House_status houseStatus, House_type houseType,
			String houseHolder, Unit unit) {
		super();
		this.houseId = houseId;
		this.statusId = statusId;
		this.typeId = typeId;
		this.unitId = unitId;
		this.houseNum = houseNum;
		this.houseArea = houseArea;
		this.houseSite = houseSite;
		this.editHouseName = editHouseName;
		this.editHouseDate = editHouseDate;
		this.houseAudit = houseAudit;
		this.houseStatus = houseStatus;
		this.houseType = houseType;
		this.houseHolder = houseHolder;
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "House [houseId=" + houseId + ", statusId=" + statusId
				+ ", typeId=" + typeId + ", unitId=" + unitId + ", houseNum="
				+ houseNum + ", houseArea=" + houseArea + ", houseSite="
				+ houseSite + ", editHouseName=" + editHouseName
				+ ", editHouseDate=" + editHouseDate + ", houseAudit="
				+ houseAudit + ", houseStatus=" + houseStatus + ", houseType="
				+ houseType + ", houseHolder=" + houseHolder + ", unit=" + unit
				+ "]";
	}

	public String getHouseHolder() {
		return houseHolder;
	}

	public void setHouseHolder(String houseHolder) {
		this.houseHolder = houseHolder;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public House_status getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(House_status houseStatus) {
		this.houseStatus = houseStatus;
	}

	public House_type getHouseType() {
		return houseType;
	}

	public void setHouseType(House_type houseType) {
		this.houseType = houseType;
	}

	public String getEditHouseName() {
		return editHouseName;
	}

	public void setEditHouseName(String editHouseName) {
		this.editHouseName = editHouseName;
	}

	public Date getEditHouseDate() {
		return editHouseDate;
	}

	public void setEditHouseDate(Date editHouseDate) {
		this.editHouseDate = editHouseDate;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getHouseSite() {
		return houseSite;
	}

	public void setHouseSite(String houseSite) {
		this.houseSite = houseSite;
	}

	public Integer getHouseAudit() {
		return houseAudit;
	}

	public void setHouseAudit(Integer houseAudit) {
		this.houseAudit = houseAudit;
	}

}
