package com.eollse.dao;

import java.util.Date;
import java.util.List;

import com.eollse.po.User;
import org.apache.ibatis.annotations.Param;

import com.eollse.po.Attendance;

public interface AttendanceDao {

    public Integer addStaffClockInInfo(Attendance attendance);

    public List<Attendance> validateSignIn(@Param("gridStaffId") Integer gridStaffId, @Param("recordDate") String recordDate);

    public List<Attendance> getAllAttendanceLogByIds(@Param("list") List<Integer> gridStaffIds, @Param("x") Integer x, @Param("y") Integer y);

    public Integer getAllAttendanceLogCount(@Param("list") List<Integer> gridStaffIds);

    public Integer deleteWorkByIds(String[] delId);
}
