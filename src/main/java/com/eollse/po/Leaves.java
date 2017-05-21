package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Leaves implements Serializable {
	private Integer leaves_id;
	private Integer leaves_area_id;
	private Integer leaves_grid_id;
	private Integer leaves_gridStaff_id;
	private Integer leaves_type_id;
	private String leaves_auditor;
	@DateTimeFormat( pattern = "yyyy-MM-dd hh:mm" )
	private Date leaves_begin_time;
	@DateTimeFormat( pattern = "yyyy-MM-dd hh:mm" )
	private Date leaves_end_time;
	private String leaves_reason;
	private Leaves_type leavesType;

	public Leaves() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Leaves(Integer leaves_id, Integer leaves_area_id,
			Integer leaves_grid_id, Integer leaves_gridStaff_id,
			Integer leaves_type_id, String leaves_auditor,
			Date leaves_begin_time, Date leaves_end_time, String leaves_reason,
			Leaves_type leavesType) {
		super();
		this.leaves_id = leaves_id;
		this.leaves_area_id = leaves_area_id;
		this.leaves_grid_id = leaves_grid_id;
		this.leaves_gridStaff_id = leaves_gridStaff_id;
		this.leaves_type_id = leaves_type_id;
		this.leaves_auditor = leaves_auditor;
		this.leaves_begin_time = leaves_begin_time;
		this.leaves_end_time = leaves_end_time;
		this.leaves_reason = leaves_reason;
		this.leavesType = leavesType;
	}

	@Override
	public String toString() {
		return "Leaves [leaves_id=" + leaves_id + ", leaves_area_id="
				+ leaves_area_id + ", leaves_grid_id=" + leaves_grid_id
				+ ", leaves_gridStaff_id=" + leaves_gridStaff_id
				+ ", leaves_type_id=" + leaves_type_id + ", leaves_auditor="
				+ leaves_auditor + ", leaves_begin_time=" + leaves_begin_time
				+ ", leaves_end_time=" + leaves_end_time + ", leaves_reason="
				+ leaves_reason + ", leavesType=" + leavesType + "]";
	}

	public Integer getLeaves_id() {
		return leaves_id;
	}

	public void setLeaves_id(Integer leaves_id) {
		this.leaves_id = leaves_id;
	}

	public Integer getLeaves_area_id() {
		return leaves_area_id;
	}

	public void setLeaves_area_id(Integer leaves_area_id) {
		this.leaves_area_id = leaves_area_id;
	}

	public Integer getLeaves_grid_id() {
		return leaves_grid_id;
	}

	public void setLeaves_grid_id(Integer leaves_grid_id) {
		this.leaves_grid_id = leaves_grid_id;
	}

	public Integer getLeaves_gridStaff_id() {
		return leaves_gridStaff_id;
	}

	public void setLeaves_gridStaff_id(Integer leaves_gridStaff_id) {
		this.leaves_gridStaff_id = leaves_gridStaff_id;
	}

	public Integer getLeaves_type_id() {
		return leaves_type_id;
	}

	public void setLeaves_type_id(Integer leaves_type_id) {
		this.leaves_type_id = leaves_type_id;
	}

	public String getLeaves_auditor() {
		return leaves_auditor;
	}

	public void setLeaves_auditor(String leaves_auditor) {
		this.leaves_auditor = leaves_auditor;
	}

	public Date getLeaves_begin_time() {
		return leaves_begin_time;
	}

	public void setLeaves_begin_time(Date leaves_begin_time) {
		this.leaves_begin_time = leaves_begin_time;
	}

	public Date getLeaves_end_time() {
		return leaves_end_time;
	}

	public void setLeaves_end_time(Date leaves_end_time) {
		this.leaves_end_time = leaves_end_time;
	}

	public String getLeaves_reason() {
		return leaves_reason;
	}

	public void setLeaves_reason(String leaves_reason) {
		this.leaves_reason = leaves_reason;
	}

	public Leaves_type getLeavesType() {
		return leavesType;
	}

	public void setLeavesType(Leaves_type leavesType) {
		this.leavesType = leavesType;
	}

}
