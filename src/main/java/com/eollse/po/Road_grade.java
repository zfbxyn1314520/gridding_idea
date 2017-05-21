package com.eollse.po;

import java.io.Serializable;

public class Road_grade implements Serializable {
	private Integer roadGradeId;
	private String roadGradeName;

	public Road_grade() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Road_grade(Integer roadGradeId, String roadGradeName) {
		super();
		this.roadGradeId = roadGradeId;
		this.roadGradeName = roadGradeName;
	}

	@Override
	public String toString() {
		return "Road_grade [roadGradeId=" + roadGradeId + ", roadGradeName="
				+ roadGradeName + "]";
	}

	public Integer getRoadGradeId() {
		return roadGradeId;
	}

	public void setRoadGradeId(Integer roadGradeId) {
		this.roadGradeId = roadGradeId;
	}

	public String getRoadGradeName() {
		return roadGradeName;
	}

	public void setRoadGradeName(String roadGradeName) {
		this.roadGradeName = roadGradeName;
	}

}
