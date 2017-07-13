package com.eollse.dao;

import java.util.List;
import java.util.Map;

import com.eollse.po.Leaves;
import com.eollse.po.Leaves_type;
import org.apache.ibatis.annotations.Param;

public interface LeavesDao {

    public List<Leaves_type> getAllLeavesType();

    public Integer addOneLeavesInfo(Leaves leaves);

    public List<Leaves> getAllLeavesByGsIds(@Param("list") List<Integer> areaIds, @Param("x") Integer x, @Param("y") Integer y);

    public Integer getAllLeavesCount(@Param("list") List<Integer> areaIds);

    public Integer deleteLeavesLogByIds(String[] delIds);
}
