package com.eollse.po;

import java.io.Serializable;

public class Company_nature implements Serializable {
	private Integer natureId;
	private String natureName;
	public Company_nature() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Company_nature(Integer natureId, String natureName) {
		super();
		this.natureId = natureId;
		this.natureName = natureName;
	}
	@Override
	public String toString() {
		return "Company_nature [natureId=" + natureId + ", natureName="
				+ natureName + "]";
	}
	public Integer getNatureId() {
		return natureId;
	}
	public void setNatureId(Integer natureId) {
		this.natureId = natureId;
	}
	public String getNatureName() {
		return natureName;
	}
	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	
}
