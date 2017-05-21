package com.eollse.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Attendance;

public interface AttendanceDao {

	public Integer addStaffClockInInfo(Attendance attendance);

	public List<Attendance> validateSignIn(@Param("gridStaffId")Integer gridStaffId, @Param("recordDate")String recordDate);

}
