package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Court implements Serializable {
	private Integer courtId;
	private Integer areaId;
	private String courtName;
	private String propertyName;
	private String courtSite;
	private String courtArea;
	private String courtPic;
	private Integer blockCount;
	private Integer courtAudit;
	private Area area;
	private String editCourtName;
	private Date editCourtDate;

	public Court() {
		super();
		this.area = new Area();
	}

	public Court(Integer courtId, Integer areaId, String courtName,
			String propertyName, String courtSite, String courtArea,
			String courtPic, Integer blockCount, Integer courtAudit, Area area,
			String editCourtName, Date editCourtDate) {
		super();
		this.courtId = courtId;
		this.areaId = areaId;
		this.courtName = courtName;
		this.propertyName = propertyName;
		this.courtSite = courtSite;
		this.courtArea = courtArea;
		this.courtPic = courtPic;
		this.blockCount = blockCount;
		this.courtAudit = courtAudit;
		this.area = area;
		this.editCourtName = editCourtName;
		this.editCourtDate = editCourtDate;
	}

	@Override
	public String toString() {
		return "Court [courtId=" + courtId + ", areaId=" + areaId
				+ ", courtName=" + courtName + ", propertyName=" + propertyName
				+ ", courtSite=" + courtSite + ", courtArea=" + courtArea
				+ ", courtPic=" + courtPic + ", blockCount=" + blockCount
				+ ", courtAudit=" + courtAudit + ", area=" + area
				+ ", editCourtName=" + editCourtName + ", editCourtDate="
				+ editCourtDate + "]";
	}

	public String getEditCourtName() {
		return editCourtName;
	}

	public void setEditCourtName(String editCourtName) {
		this.editCourtName = editCourtName;
	}

	public Date getEditCourtDate() {
		return editCourtDate;
	}

	public void setEditCourtDate(Date editCourtDate) {
		this.editCourtDate = editCourtDate;
	}

	public Integer getCourtId() {
		return courtId;
	}

	public void setCourtId(Integer courtId) {
		this.courtId = courtId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getCourtSite() {
		return courtSite;
	}

	public void setCourtSite(String courtSite) {
		this.courtSite = courtSite;
	}

	public String getCourtArea() {
		return courtArea;
	}

	public void setCourtArea(String courtArea) {
		this.courtArea = courtArea;
	}

	public Integer getBlockCount() {
		return blockCount;
	}

	public void setBlockCount(Integer blockCount) {
		this.blockCount = blockCount;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Integer getCourtAudit() {
		return courtAudit;
	}

	public void setCourtAudit(Integer courtAudit) {
		this.courtAudit = courtAudit;
	}

	public String getCourtPic() {
		return courtPic;
	}

	public void setCourtPic(String courtPic) {
		this.courtPic = courtPic;
	}

}
