package com.eollse.po;

import java.io.Serializable;

public class House_status implements Serializable {
	private Integer statusId;
	private String statusName;

	public House_status() {
		super();
		// TODO Auto-generated constructor stub
	}

	public House_status(Integer statusId, String statusName) {
		super();
		this.statusId = statusId;
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "House_status [statusId=" + statusId + ", statusName="
				+ statusName + "]";
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
