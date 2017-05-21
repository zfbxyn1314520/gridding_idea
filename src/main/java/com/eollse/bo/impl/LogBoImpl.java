package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.LogBo;
import com.eollse.dao.LogDao;
import com.eollse.po.Log;

@Service
public class LogBoImpl implements LogBo {
	
	@Autowired
	private LogDao logDao;

	@Override
	public Map<String, Object> getAllLogByUserId(List<Integer> userIds,
			Integer pageSize, Integer pageCurrent) {
		// TODO Auto-generated method stub
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Log> logs=this.logDao.getAllLogByUserId(userIds,x,y);
		int totalRow = this.logDao.getAllLogsCount(userIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", logs);
		return map;
	}

	@Override
	public Integer deleteLog(List<Integer> logIds) {
		// TODO Auto-generated method stub
		return this.logDao.deleteLog(logIds);
	}

	@Override
	public Integer deleteLogByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return this.logDao.deleteLogByUserId(userId);
	}

	@Override
	public Integer getRequestCount(Integer userId, String logIP) {
		// TODO Auto-generated method stub
		return this.logDao.getRequestCount(userId, logIP);
	}

}
