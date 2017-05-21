package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class GridStaffApp implements Serializable {

	private Integer gridStaffId;
	private String gridStaffName;
	private Character gridStaffSex;
	private String gridStaffTel;
	private String gridStaffScope;

	public GridStaffApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "GridStaffApp [gridStaffId=" + gridStaffId + ", gridStaffName="
				+ gridStaffName + ", gridStaffSex=" + gridStaffSex
				+ ", gridStaffTel=" + gridStaffTel + ", gridStaffScope="
				+ gridStaffScope + "]";
	}

	public GridStaffApp(Integer gridStaffId, String gridStaffName,
			Character gridStaffSex, String gridStaffTel, String gridStaffScope) {
		super();
		this.gridStaffId = gridStaffId;
		this.gridStaffName = gridStaffName;
		this.gridStaffSex = gridStaffSex;
		this.gridStaffTel = gridStaffTel;
		this.gridStaffScope = gridStaffScope;
	}

	public Character getGridStaffSex() {
		return gridStaffSex;
	}

	public void setGridStaffSex(Character gridStaffSex) {
		this.gridStaffSex = gridStaffSex;
	}

	public String getGridStaffTel() {
		return gridStaffTel;
	}

	public void setGridStaffTel(String gridStaffTel) {
		this.gridStaffTel = gridStaffTel;
	}

	public String getGridStaffScope() {
		return gridStaffScope;
	}

	public void setGridStaffScope(String gridStaffScope) {
		this.gridStaffScope = gridStaffScope;
	}

	public Integer getGridStaffId() {
		return gridStaffId;
	}

	public void setGridStaffId(Integer gridStaffId) {
		this.gridStaffId = gridStaffId;
	}

	public String getGridStaffName() {
		return gridStaffName;
	}

	public void setGridStaffName(String gridStaffName) {
		this.gridStaffName = gridStaffName;
	}

}
