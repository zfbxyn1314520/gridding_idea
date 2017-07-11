package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.AttendanceBo;
import com.eollse.dao.AttendanceDao;
import com.eollse.po.Attendance;

@Service
public class AttendanceBoImpl implements AttendanceBo {

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
        return this.attendanceDao.validateSignIn(gridStaffId, recordDate);
    }

    @Override
    public Map<String, Object> getAllAttendanceLogByIds(List<Integer> gridStaffIds, Integer pageSize, Integer pageCurrent) {
        // 计算起始数值
        Integer x = (pageCurrent - 1) * pageSize;
        Integer y = pageCurrent * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Attendance> attendances = this.attendanceDao.getAllAttendanceLogByIds(gridStaffIds, x, y);
        Integer totalRow = this.attendanceDao.getAllAttendanceLogCount(gridStaffIds);
        map.put("totalRow", totalRow);
        map.put("pageCurrent", pageCurrent);
        map.put("list", attendances);
        return map;
    }

    @Override
    public Integer deleteWorkByIds(String[] delId) {
        return this.attendanceDao.deleteWorkByIds(delId);
    }

}
