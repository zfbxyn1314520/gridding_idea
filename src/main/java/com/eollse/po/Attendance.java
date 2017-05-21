package com.eollse.po;

import java.io.Serializable;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Attendance implements Serializable {

	private Integer attendance_id;
	private Integer gridStaffId;
	private String recordDate;
	@DateTimeFormat( pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date start_time;
	private String start_site;
	private String start_memo;
	@DateTimeFormat( pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date end_time;
	private String end_site;
	private String end_memo;
	private String attendance_pic;

	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Attendance [attendance_id=" + attendance_id + ", gridStaffId="
				+ gridStaffId + ", recordDate=" + recordDate + ", start_time="
				+ start_time + ", start_site=" + start_site + ", start_memo="
				+ start_memo + ", end_time=" + end_time + ", end_site="
				+ end_site + ", end_memo=" + end_memo + ", attendance_pic="
				+ attendance_pic + "]";
	}


	public Attendance(Integer attendance_id, Integer gridStaffId,
			String recordDate, Date start_time, String start_site,
			String start_memo, Date end_time, String end_site, String end_memo,
			String attendance_pic) {
		super();
		this.attendance_id = attendance_id;
		this.gridStaffId = gridStaffId;
		this.recordDate = recordDate;
		this.start_time = start_time;
		this.start_site = start_site;
		this.start_memo = start_memo;
		this.end_time = end_time;
		this.end_site = end_site;
		this.end_memo = end_memo;
		this.attendance_pic = attendance_pic;
	}



	public Integer getAttendance_id() {
		return attendance_id;
	}

	public void setAttendance_id(Integer attendance_id) {
		this.attendance_id = attendance_id;
	}

	public Integer getGridStaffId() {
		return gridStaffId;
	}

	public void setGridStaffId(Integer gridStaffId) {
		this.gridStaffId = gridStaffId;
	}


	public String getRecordDate() {
		return recordDate;
	}


	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}


	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public String getStart_site() {
		return start_site;
	}

	public void setStart_site(String start_site) {
		this.start_site = start_site;
	}

	public String getStart_memo() {
		return start_memo;
	}

	public void setStart_memo(String start_memo) {
		this.start_memo = start_memo;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getEnd_site() {
		return end_site;
	}

	public void setEnd_site(String end_site) {
		this.end_site = end_site;
	}

	public String getEnd_memo() {
		return end_memo;
	}

	public void setEnd_memo(String end_memo) {
		this.end_memo = end_memo;
	}

	public String getAttendance_pic() {
		return attendance_pic;
	}

	public void setAttendance_pic(String attendance_pic) {
		this.attendance_pic = attendance_pic;
	}

}
