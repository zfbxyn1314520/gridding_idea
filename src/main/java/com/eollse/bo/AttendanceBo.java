package com.eollse.bo;

import java.util.Date;
import java.util.List;

import com.eollse.po.Attendance;

public interface AttendanceBo {

	public Integer addStaffClockInInfo(Attendance attendance);

	public List<Attendance> validateSignIn(Integer gridStaffId, String nowTime);

}
