package com.eollse.po;

import java.io.Serializable;

public class Event_source_type implements Serializable {
	private Integer sourceTypeId;
	private String sourceTypeName;

	public Event_source_type() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event_source_type(Integer sourceTypeId, String sourceTypeName) {
		super();
		this.sourceTypeId = sourceTypeId;
		this.sourceTypeName = sourceTypeName;
	}

	@Override
	public String toString() {
		return "Event_source_type [sourceTypeId=" + sourceTypeId
				+ ", sourceTypeName=" + sourceTypeName + "]";
	}

	public Integer getSourceTypeId() {
		return sourceTypeId;
	}

	public void setSourceTypeId(Integer sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	public String getSourceTypeName() {
		return sourceTypeName;
	}

	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}

}
