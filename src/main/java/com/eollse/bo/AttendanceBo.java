package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Attendance;

public interface AttendanceBo {

	public Integer addStaffClockInInfo(Attendance attendance);

	public List<Attendance> validateSignIn(Integer gridStaffId, String nowTime);

	public Map<String, Object> getAllAttendanceLogByIds(List<Integer> gridStaffIds, Integer pageSize, Integer pageCurrent);

	public Integer deleteWorkByIds(String[] delId);
}
