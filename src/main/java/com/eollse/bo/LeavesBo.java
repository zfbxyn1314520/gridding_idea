package com.eollse.bo;

import java.util.List;

import com.eollse.po.Leaves;
import com.eollse.po.Leaves_type;

public interface LeavesBo {

	public List<Leaves_type> getAllLeavesType();

	public Integer addOneLeavesInfo(Leaves leaves);

}
