package com.eollse.po;

import java.io.Serializable;

public class Info_type implements Serializable {

	private Integer infoTypeId;
	private String infoTypeName;

	public Info_type() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Info_type(Integer infoTypeId, String infoTypeName) {
		super();
		this.infoTypeId = infoTypeId;
		this.infoTypeName = infoTypeName;
	}

	@Override
	public String toString() {
		return "Info_type [infoTypeId=" + infoTypeId + ", infoTypeName="
				+ infoTypeName + "]";
	}

	public Integer getInfoTypeId() {
		return infoTypeId;
	}

	public void setInfoTypeId(Integer infoTypeId) {
		this.infoTypeId = infoTypeId;
	}

	public String getInfoTypeName() {
		return infoTypeName;
	}

	public void setInfoTypeName(String infoTypeName) {
		this.infoTypeName = infoTypeName;
	}

}
