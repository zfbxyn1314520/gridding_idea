package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eollse.po.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.LeavesBo;
import com.eollse.dao.LeavesDao;
import com.eollse.po.Leaves;
import com.eollse.po.Leaves_type;

@Service
public class LeavesBoImpl implements LeavesBo {

    @Autowired
    private LeavesDao leavesDao;

    @Override
    public List<Leaves_type> getAllLeavesType() {
        // TODO Auto-generated method stub
        return this.leavesDao.getAllLeavesType();
    }

    @Override
    public Integer addOneLeavesInfo(Leaves leaves) {
        // TODO Auto-generated method stub
        return this.leavesDao.addOneLeavesInfo(leaves);
    }

    @Override
    public Map<String, Object> getAllLeavesByGsIds(List<Integer> areaIds, Integer pageSize, Integer pageCurrent) {
        // 计算起始数值
        Integer x = (pageCurrent - 1) * pageSize;
        Integer y = pageCurrent * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Leaves> attendances = this.leavesDao.getAllLeavesByGsIds(areaIds, x, y);
        Integer totalRow = this.leavesDao.getAllLeavesCount(areaIds);
        map.put("totalRow", totalRow);
        map.put("pageCurrent", pageCurrent);
        map.put("list", attendances);
        return map;
    }

}
