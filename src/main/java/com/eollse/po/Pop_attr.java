package com.eollse.po;

import java.io.Serializable;

public class Pop_attr implements Serializable {
	private Integer popId;
	private Integer popAttrId;

	public Pop_attr() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pop_attr(Integer popId, Integer popAttrId) {
		super();
		this.popId = popId;
		this.popAttrId = popAttrId;
	}

	@Override
	public String toString() {
		return "Pop_attr [popId=" + popId + ", popAttrId=" + popAttrId + "]";
	}

	public Integer getPopId() {
		return popId;
	}

	public void setPopId(Integer popId) {
		this.popId = popId;
	}

	public Integer getPopAttrId() {
		return popAttrId;
	}

	public void setPopAttrId(Integer popAttrId) {
		this.popAttrId = popAttrId;
	}

}
