package com.eollse.dao;

import java.util.List;

import com.eollse.po.Leaves;
import com.eollse.po.Leaves_type;

public interface LeavesDao {

	public List<Leaves_type> getAllLeavesType();

	public Integer addOneLeavesInfo(Leaves leaves);

}
