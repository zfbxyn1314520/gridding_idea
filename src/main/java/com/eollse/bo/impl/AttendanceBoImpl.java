package com.eollse.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.AttendanceBo;
import com.eollse.dao.AttendanceDao;
import com.eollse.po.Attendance;

@Service
public class AttendanceBoImpl implements AttendanceBo{
	
	@Autowired
	private AttendanceDao attendanceDao;

	@Override
	public Integer addStaffClockInInfo(Attendance attendance) {
		// TODO Auto-generated method stub
		return this.attendanceDao.addStaffClockInInfo(attendance);
	}

	@Override
	public List<Attendance> validateSignIn(Integer gridStaffId, String recordDate) {
		// TODO Auto-generated method stub
		return this.attendanceDao.validateSignIn(gridStaffId,recordDate);
	}

}
