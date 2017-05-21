package com.eollse.po;

import java.io.Serializable;

public class Flow_type implements Serializable {
	private Integer flowTypeId;
	private String flowTypeName;

	public Flow_type() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Flow_type(Integer flowTypeId, String flowTypeName) {
		super();
		this.flowTypeId = flowTypeId;
		this.flowTypeName = flowTypeName;
	}

	@Override
	public String toString() {
		return "Flow_type [flowTypeId=" + flowTypeId + ", flowTypeName="
				+ flowTypeName + "]";
	}

	public Integer getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(Integer flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public String getFlowTypeName() {
		return flowTypeName;
	}

	public void setFlowTypeName(String flowTypeName) {
		this.flowTypeName = flowTypeName;
	}

}
