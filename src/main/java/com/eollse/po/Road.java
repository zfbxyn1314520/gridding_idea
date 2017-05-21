package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Road implements Serializable {
	private Integer roadId;
	private Integer areaId;
	private Integer roadGradeId;
	private String roadName;
	private String roadLength;
	private String roadWidth;
	private String laneNum;
	private Integer singleLane;
	private String roadPic;
	private String roadAudit;
	private String editRoadName;
	private Date editRoadDate;
	private Area area;
	private Road_grade roadGrade;

	public Road() {
		super();
		this.area = new Area();
		this.roadGrade = new Road_grade();
	}

	public Road(Integer roadId, Integer areaId, Integer roadGradeId,
			String roadName, String roadLength, String roadWidth,
			String laneNum, Integer singleLane, String roadPic,
			String roadAudit, String editRoadName, Date editRoadDate,
			Area area, Road_grade roadGrade) {
		super();
		this.roadId = roadId;
		this.areaId = areaId;
		this.roadGradeId = roadGradeId;
		this.roadName = roadName;
		this.roadLength = roadLength;
		this.roadWidth = roadWidth;
		this.laneNum = laneNum;
		this.singleLane = singleLane;
		this.roadPic = roadPic;
		this.roadAudit = roadAudit;
		this.editRoadName = editRoadName;
		this.editRoadDate = editRoadDate;
		this.area = area;
		this.roadGrade = roadGrade;
	}

	@Override
	public String toString() {
		return "Road [roadId=" + roadId + ", areaId=" + areaId
				+ ", roadGradeId=" + roadGradeId + ", roadName=" + roadName
				+ ", roadLength=" + roadLength + ", roadWidth=" + roadWidth
				+ ", laneNum=" + laneNum + ", singleLane=" + singleLane
				+ ", roadPic=" + roadPic + ", roadAudit=" + roadAudit
				+ ", editRoadName=" + editRoadName + ", editRoadDate="
				+ editRoadDate + ", area=" + area + ", roadGrade=" + roadGrade
				+ "]";
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Road_grade getRoadGrade() {
		return roadGrade;
	}

	public void setRoadGrade(Road_grade roadGrade) {
		this.roadGrade = roadGrade;
	}

	public String getEditRoadName() {
		return editRoadName;
	}

	public void setEditRoadName(String editRoadName) {
		this.editRoadName = editRoadName;
	}

	public Date getEditRoadDate() {
		return editRoadDate;
	}

	public void setEditRoadDate(Date editRoadDate) {
		this.editRoadDate = editRoadDate;
	}

	public Integer getRoadId() {
		return roadId;
	}

	public void setRoadId(Integer roadId) {
		this.roadId = roadId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getRoadGradeId() {
		return roadGradeId;
	}

	public void setRoadGradeId(Integer roadGradeId) {
		this.roadGradeId = roadGradeId;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getRoadLength() {
		return roadLength;
	}

	public void setRoadLength(String roadLength) {
		this.roadLength = roadLength;
	}

	public String getRoadWidth() {
		return roadWidth;
	}

	public void setRoadWidth(String roadWidth) {
		this.roadWidth = roadWidth;
	}

	public String getLaneNum() {
		return laneNum;
	}

	public void setLaneNum(String laneNum) {
		this.laneNum = laneNum;
	}

	public Integer getSingleLane() {
		return singleLane;
	}

	public void setSingleLane(Integer singleLane) {
		this.singleLane = singleLane;
	}

	public String getRoadPic() {
		return roadPic;
	}

	public void setRoadPic(String roadPic) {
		this.roadPic = roadPic;
	}

	public String getRoadAudit() {
		return roadAudit;
	}

	public void setRoadAudit(String roadAudit) {
		this.roadAudit = roadAudit;
	}

}
