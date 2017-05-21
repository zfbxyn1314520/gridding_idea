package com.eollse.po;

import java.io.Serializable;

public class Company_scale implements Serializable {
	private Integer scaleId;
	private String scaleName;
	public Company_scale() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Company_scale(Integer scaleId, String scaleName) {
		super();
		this.scaleId = scaleId;
		this.scaleName = scaleName;
	}
	@Override
	public String toString() {
		return "Company_scale [scaleId=" + scaleId + ", scaleName=" + scaleName
				+ "]";
	}
	public Integer getScaleId() {
		return scaleId;
	}
	public void setScaleId(Integer scaleId) {
		this.scaleId = scaleId;
	}
	public String getScaleName() {
		return scaleName;
	}
	public void setScaleName(String scaleName) {
		this.scaleName = scaleName;
	}
	
}
