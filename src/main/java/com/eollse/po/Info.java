package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Info implements Serializable {

	private Integer infoId;
	private Integer infoTypeId;
	private Integer areaId;
	private String infoTitle;
	private String infoContent;
	private String infoEditor;
	@DateTimeFormat( pattern = "yyyy年MM月dd日 HH:mm:ss" )
	private Date infoEditDate;
	private Area area;

	public Info() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Info(Integer infoId, Integer infoTypeId, Integer areaId,
			String infoTitle, String infoContent, String infoEditor,
			Date infoEditDate, Area area) {
		super();
		this.infoId = infoId;
		this.infoTypeId = infoTypeId;
		this.areaId = areaId;
		this.infoTitle = infoTitle;
		this.infoContent = infoContent;
		this.infoEditor = infoEditor;
		this.infoEditDate = infoEditDate;
		this.area = area;
	}


	public Info(Area area) {
		super();
		this.area = area;
	}


	@Override
	public String toString() {
		return "Info [infoId=" + infoId + ", infoTypeId=" + infoTypeId
				+ ", areaId=" + areaId + ", infoTitle=" + infoTitle
				+ ", infoContent=" + infoContent + ", infoEditor=" + infoEditor
				+ ", infoEditDate=" + infoEditDate + ", area=" + area + "]";
	}


	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getInfoTypeId() {
		return infoTypeId;
	}

	public void setInfoTypeId(Integer infoTypeId) {
		this.infoTypeId = infoTypeId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public String getInfoEditor() {
		return infoEditor;
	}

	public void setInfoEditor(String infoEditor) {
		this.infoEditor = infoEditor;
	}

	public Date getInfoEditDate() {
		return infoEditDate;
	}

	public void setInfoEditDate(Date infoEditDate) {
		this.infoEditDate = infoEditDate;
	}


	public Area getArea() {
		return area;
	}


	public void setArea(Area area) {
		this.area = area;
	}
	

}
