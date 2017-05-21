package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Block implements Serializable {
	private Integer blockId;
	private Integer courtId;
	private Integer gridStaffId;
	private Integer gridId;
	private String blockName;
	private Integer unitCount;
	private Integer blockAudit;
	private Court court;
	private Grid_staff gridStaff;
	private Grid grid;
	private String editBlockName;
	private Date editBlockDate;
	private String blockAdmintor;
	private String blockAdmintorTel;
	private Integer houseCount;
	private Integer unitFloorCount;

	public Block() {
		super();
		this.court = new Court();
		this.gridStaff = new Grid_staff();
		this.grid = new Grid();
	}

	public Block(Integer blockId, Integer courtId, Integer gridStaffId,
			Integer gridId, String blockName, Integer unitCount,
			Integer blockAudit, Court court, Grid_staff gridStaff, Grid grid,
			String editBlockName, Date editBlockDate, String blockAdmintor,
			String blockAdmintorTel, Integer houseCount, Integer unitFloorCount) {
		super();
		this.blockId = blockId;
		this.courtId = courtId;
		this.gridStaffId = gridStaffId;
		this.gridId = gridId;
		this.blockName = blockName;
		this.unitCount = unitCount;
		this.blockAudit = blockAudit;
		this.court = court;
		this.gridStaff = gridStaff;
		this.grid = grid;
		this.editBlockName = editBlockName;
		this.editBlockDate = editBlockDate;
		this.blockAdmintor = blockAdmintor;
		this.blockAdmintorTel = blockAdmintorTel;
		this.houseCount = houseCount;
		this.unitFloorCount = unitFloorCount;
	}

	@Override
	public String toString() {
		return "Block [blockId=" + blockId + ", courtId=" + courtId
				+ ", gridStaffId=" + gridStaffId + ", gridId=" + gridId
				+ ", blockName=" + blockName + ", unitCount=" + unitCount
				+ ", blockAudit=" + blockAudit + ", court=" + court
				+ ", gridStaff=" + gridStaff + ", grid=" + grid
				+ ", editBlockName=" + editBlockName + ", editBlockDate="
				+ editBlockDate + ", blockAdmintor=" + blockAdmintor
				+ ", blockAdmintorTel=" + blockAdmintorTel + ", houseCount="
				+ houseCount + ", unitFloorCount=" + unitFloorCount + "]";
	}

	public Integer getUnitFloorCount() {
		return unitFloorCount;
	}

	public void setUnitFloorCount(Integer unitFloorCount) {
		this.unitFloorCount = unitFloorCount;
	}

	public Integer getHouseCount() {
		return houseCount;
	}

	public void setHouseCount(Integer houseCount) {
		this.houseCount = houseCount;
	}

	public String getBlockAdmintor() {
		return blockAdmintor;
	}

	public void setBlockAdmintor(String blockAdmintor) {
		this.blockAdmintor = blockAdmintor;
	}

	public String getBlockAdmintorTel() {
		return blockAdmintorTel;
	}

	public void setBlockAdmintorTel(String blockAdmintorTel) {
		this.blockAdmintorTel = blockAdmintorTel;
	}

	public String getEditBlockName() {
		return editBlockName;
	}

	public void setEditBlockName(String editBlockName) {
		this.editBlockName = editBlockName;
	}

	public Date getEditBlockDate() {
		return editBlockDate;
	}

	public void setEditBlockDate(Date editBlockDate) {
		this.editBlockDate = editBlockDate;
	}

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public Integer getCourtId() {
		return courtId;
	}

	public void setCourtId(Integer courtId) {
		this.courtId = courtId;
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

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(Integer unitCount) {
		this.unitCount = unitCount;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
	}

	public Grid_staff getGridStaff() {
		return gridStaff;
	}

	public void setGridStaff(Grid_staff gridStaff) {
		this.gridStaff = gridStaff;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Integer getBlockAudit() {
		return blockAudit;
	}

	public void setBlockAudit(Integer blockAudit) {
		this.blockAudit = blockAudit;
	}

}
