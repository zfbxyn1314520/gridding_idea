package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Log;

public interface LogDao {

	public List<Log> getAllLogByUserId(@Param("list")List<Integer> userIds, @Param("x")Integer x, @Param("y")Integer y);

	public int getAllLogsCount(@Param("list")List<Integer> userIds);

	public Integer deleteLog(@Param("list")List<Integer> logIds);

	public Integer deleteLogByUserId(@Param("userId")Integer userId);

	public Integer getRequestCount(@Param("userId")Integer userId, @Param("logIP")String logIP);


}
