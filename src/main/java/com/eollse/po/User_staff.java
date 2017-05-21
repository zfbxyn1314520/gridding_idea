package com.eollse.po;

import java.io.Serializable;

public class User_staff implements Serializable {
	
	private Integer userId;
	private Integer gridStaffId;
	
	public User_staff() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User_staff(Integer userId, Integer gridStaffId) {
		super();
		this.userId = userId;
		this.gridStaffId = gridStaffId;
	}
	@Override
	public String toString() {
		return "User_staff [userId=" + userId + ", gridStaffId=" + gridStaffId
				+ "]";
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGridStaffId() {
		return gridStaffId;
	}
	public void setGridStaffId(Integer gridStaffId) {
		this.gridStaffId = gridStaffId;
	}
	

}
