package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Grid_staff implements Serializable {
	private Integer gridStaffId;
	private Integer gridId;
	private Integer gridPostId;
	private String gridStaffName;
	private Character gridStaffSex;
	private Integer gridStaffAge;
	private String gridStaffCard;
	private String gridStaffTel;
	private String gridStaffIcon;
	private String gridStaffScope;
	private String editStaffName;
	private Date editStaffDate;
	private Integer gridStaffAudit;
	private Integer appUser;
	private Grid grid;
	private Grid_post gridPost;

	public Grid_staff() {
		super();
		this.grid = new Grid();
		this.gridPost = new Grid_post();
	}


	@Override
	public String toString() {
		return "Grid_staff [gridStaffId=" + gridStaffId + ", gridId=" + gridId
				+ ", gridPostId=" + gridPostId + ", gridStaffName="
				+ gridStaffName + ", gridStaffSex=" + gridStaffSex
				+ ", gridStaffAge=" + gridStaffAge + ", gridStaffCard="
				+ gridStaffCard + ", gridStaffTel=" + gridStaffTel
				+ ", gridStaffIcon=" + gridStaffIcon + ", gridStaffScope="
				+ gridStaffScope + ", editStaffName=" + editStaffName
				+ ", editStaffDate=" + editStaffDate + ", gridStaffAudit="
				+ gridStaffAudit + ", appUser=" + appUser + ", grid=" + grid
				+ ", gridPost=" + gridPost + "]";
	}


	public Grid_staff(Integer gridStaffId, Integer gridId, Integer gridPostId,
			String gridStaffName, Character gridStaffSex, Integer gridStaffAge,
			String gridStaffCard, String gridStaffTel, String gridStaffIcon,
			String gridStaffScope, String editStaffName, Date editStaffDate,
			Integer gridStaffAudit, Integer appUser, Grid grid,
			Grid_post gridPost) {
		super();
		this.gridStaffId = gridStaffId;
		this.gridId = gridId;
		this.gridPostId = gridPostId;
		this.gridStaffName = gridStaffName;
		this.gridStaffSex = gridStaffSex;
		this.gridStaffAge = gridStaffAge;
		this.gridStaffCard = gridStaffCard;
		this.gridStaffTel = gridStaffTel;
		this.gridStaffIcon = gridStaffIcon;
		this.gridStaffScope = gridStaffScope;
		this.editStaffName = editStaffName;
		this.editStaffDate = editStaffDate;
		this.gridStaffAudit = gridStaffAudit;
		this.appUser = appUser;
		this.grid = grid;
		this.gridPost = gridPost;
	}


	public Integer getAppUser() {
		return appUser;
	}


	public void setAppUser(Integer appUser) {
		this.appUser = appUser;
	}


	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Grid_post getGridPost() {
		return gridPost;
	}

	public void setGridPost(Grid_post gridPost) {
		this.gridPost = gridPost;
	}

	public Integer getGridStaffId() {
		return gridStaffId;
	}

	public void setGridStaffId(Integer gridStaffId) {
		this.gridStaffId = gridStaffId;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public Integer getGridPostId() {
		return gridPostId;
	}

	public void setGridPostId(Integer gridPostId) {
		this.gridPostId = gridPostId;
	}

	public String getGridStaffName() {
		return gridStaffName;
	}

	public void setGridStaffName(String gridStaffName) {
		this.gridStaffName = gridStaffName;
	}

	public Character getGridStaffSex() {
		return gridStaffSex;
	}

	public void setGridStaffSex(Character gridStaffSex) {
		this.gridStaffSex = gridStaffSex;
	}

	public Integer getGridStaffAge() {
		return gridStaffAge;
	}

	public void setGridStaffAge(Integer gridStaffAge) {
		this.gridStaffAge = gridStaffAge;
	}

	public String getGridStaffCard() {
		return gridStaffCard;
	}

	public void setGridStaffCard(String gridStaffCard) {
		this.gridStaffCard = gridStaffCard;
	}

	public String getGridStaffTel() {
		return gridStaffTel;
	}

	public void setGridStaffTel(String gridStaffTel) {
		this.gridStaffTel = gridStaffTel;
	}

	public String getGridStaffIcon() {
		return gridStaffIcon;
	}

	public void setGridStaffIcon(String gridStaffIcon) {
		this.gridStaffIcon = gridStaffIcon;
	}

	public String getGridStaffScope() {
		return gridStaffScope;
	}

	public void setGridStaffScope(String gridStaffScope) {
		this.gridStaffScope = gridStaffScope;
	}

	public Integer getGridStaffAudit() {
		return gridStaffAudit;
	}

	public void setGridStaffAudit(Integer gridStaffAudit) {
		this.gridStaffAudit = gridStaffAudit;
	}

	public String getEditStaffName() {
		return editStaffName;
	}

	public void setEditStaffName(String editStaffName) {
		this.editStaffName = editStaffName;
	}

	public Date getEditStaffDate() {
		return editStaffDate;
	}

	public void setEditStaffDate(Date editStaffDate) {
		this.editStaffDate = editStaffDate;
	}

}
