package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Leaves;
import com.eollse.po.Leaves_type;

public interface LeavesBo {

	public List<Leaves_type> getAllLeavesType();

	public Integer addOneLeavesInfo(Leaves leaves);

	public Map<String,Object> getAllLeavesByGsIds(List<Integer> areaIds, Integer pageSize, Integer pageCurrent);
}
