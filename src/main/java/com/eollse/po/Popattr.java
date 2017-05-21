package com.eollse.po;

import java.io.Serializable;

public class Popattr implements Serializable {
	private Integer popAttrId;
	private String popAttrName;

	public Popattr() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Popattr(Integer popAttrId, String popAttrName) {
		super();
		this.popAttrId = popAttrId;
		this.popAttrName = popAttrName;
	}

	@Override
	public String toString() {
		return "Popattr [popAttrId=" + popAttrId + ", popAttrName="
				+ popAttrName + "]";
	}

	public Integer getPopAttrId() {
		return popAttrId;
	}

	public void setPopAttrId(Integer popAttrId) {
		this.popAttrId = popAttrId;
	}

	public String getPopAttrName() {
		return popAttrName;
	}

	public void setPopAttrName(String popAttrName) {
		this.popAttrName = popAttrName;
	}

}
