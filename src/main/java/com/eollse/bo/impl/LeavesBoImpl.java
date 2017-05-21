package com.eollse.bo.impl;

import java.util.List;

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

}
