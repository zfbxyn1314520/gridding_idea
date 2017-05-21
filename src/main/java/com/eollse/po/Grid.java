package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Grid implements Serializable {
	private Integer gridId;
	private Integer areaId;
	private String gridName;
	private String gridScope;
	private String editGridName;
	private Date editGridDate;
	private Integer gridAudit;
	private String gridAdmitor;
	private Area area;
	private Integer gridStaffCount;

	public Grid() {
		super();
		this.area = new Area();
	}

	@Override
	public String toString() {
		return "Grid [gridId=" + gridId + ", areaId=" + areaId + ", gridName="
				+ gridName + ", gridScope=" + gridScope + ", editGridName="
				+ editGridName + ", editGridDate=" + editGridDate
				+ ", gridAudit=" + gridAudit + ", gridAdmitor=" + gridAdmitor
				+ ", area=" + area + ", gridStaffCount=" + gridStaffCount + "]";
	}

	public Grid(Integer gridId, Integer areaId, String gridName,
			String gridScope, String editGridName, Date editGridDate,
			Integer gridAudit, String gridAdmitor, Area area,
			Integer gridStaffCount) {
		super();
		this.gridId = gridId;
		this.areaId = areaId;
		this.gridName = gridName;
		this.gridScope = gridScope;
		this.editGridName = editGridName;
		this.editGridDate = editGridDate;
		this.gridAudit = gridAudit;
		this.gridAdmitor = gridAdmitor;
		this.area = area;
		this.gridStaffCount = gridStaffCount;
	}

	public Integer getGridStaffCount() {
		return gridStaffCount;
	}

	public void setGridStaffCount(Integer gridStaffCount) {
		this.gridStaffCount = gridStaffCount;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getEditGridName() {
		return editGridName;
	}

	public void setEditGridName(String editGridName) {
		this.editGridName = editGridName;
	}

	public Date getEditGridDate() {
		return editGridDate;
	}

	public void setEditGridDate(Date editGridDate) {
		this.editGridDate = editGridDate;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getGridScope() {
		return gridScope;
	}

	public void setGridScope(String gridScope) {
		this.gridScope = gridScope;
	}

	public Integer getGridAudit() {
		return gridAudit;
	}

	public void setGridAudit(Integer gridAudit) {
		this.gridAudit = gridAudit;
	}

	public String getGridAdmitor() {
		return gridAdmitor;
	}

	public void setGridAdmitor(String gridAdmitor) {
		this.gridAdmitor = gridAdmitor;
	}

}
