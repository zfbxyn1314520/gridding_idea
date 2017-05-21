package com.eollse.po;

import java.io.Serializable;

public class Politics implements Serializable {
	private Integer politicsId;
	private String politicsName;

	public Politics() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Politics(Integer politicsId, String politicsName) {
		super();
		this.politicsId = politicsId;
		this.politicsName = politicsName;
	}

	@Override
	public String toString() {
		return "Politics [politicsId=" + politicsId + ", politicsName="
				+ politicsName + "]";
	}

	public Integer getPoliticsId() {
		return politicsId;
	}

	public void setPoliticsId(Integer politicsId) {
		this.politicsId = politicsId;
	}

	public String getPoliticsName() {
		return politicsName;
	}

	public void setPoliticsName(String politicsName) {
		this.politicsName = politicsName;
	}

}
