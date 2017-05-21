package com.eollse.bo;

import java.util.List;
import java.util.Map;


public interface LogBo {

	public Map<String, Object> getAllLogByUserId(List<Integer> userIds,Integer pageSize, Integer pageCurrent);

	public Integer deleteLog(List<Integer> logIds);

	public Integer deleteLogByUserId(Integer userId);

	public Integer getRequestCount(Integer userId, String logIP);

}
