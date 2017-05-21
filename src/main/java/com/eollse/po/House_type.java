package com.eollse.po;

import java.io.Serializable;

public class House_type implements Serializable {
	private Integer typeId;
	private String typeName;

	public House_type() {
		super();
		// TODO Auto-generated constructor stub
	}

	public House_type(Integer typeId, String typeName) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return "House_type [typeId=" + typeId + ", typeName=" + typeName + "]";
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
