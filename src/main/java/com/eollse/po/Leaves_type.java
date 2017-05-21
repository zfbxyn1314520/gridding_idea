package com.eollse.po;

import java.io.Serializable;

public class Leaves_type implements Serializable {
	
	private Integer leaves_type_id;
	private String leaves_type_name;
	
	public Leaves_type() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Leaves_type(Integer leaves_type_id, String leaves_type_name) {
		super();
		this.leaves_type_id = leaves_type_id;
		this.leaves_type_name = leaves_type_name;
	}
	@Override
	public String toString() {
		return "Leaves_type [leaves_type_id=" + leaves_type_id
				+ ", leaves_type_name=" + leaves_type_name + "]";
	}
	public Integer getLeaves_type_id() {
		return leaves_type_id;
	}
	public void setLeaves_type_id(Integer leaves_type_id) {
		this.leaves_type_id = leaves_type_id;
	}
	public String getLeaves_type_name() {
		return leaves_type_name;
	}
	public void setLeaves_type_name(String leaves_type_name) {
		this.leaves_type_name = leaves_type_name;
	}
	
	

}
