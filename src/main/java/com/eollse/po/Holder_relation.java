package com.eollse.po;

import java.io.Serializable;

public class Holder_relation implements Serializable {
	private Integer relationId;
	private String relationName;

	public Holder_relation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Holder_relation(Integer relationId, String relationName) {
		super();
		this.relationId = relationId;
		this.relationName = relationName;
	}

	@Override
	public String toString() {
		return "Holder_relation [relationId=" + relationId + ", relationName="
				+ relationName + "]";
	}

	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

}
