package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Account;
import com.eollse.po.Population;

public interface AccountDao {

	public List<Account> getAllAccountByHouseId(@Param("list")List<Integer> houseIds, @Param("x")Integer x,
			@Param("y")Integer y);

	public int getAllAccountCount(@Param("list")List<Integer> houseIds);

	public List<Account> getAccountById(Integer accountId);

	public Integer updateAuditAccount(@Param("list")List<Integer> accountIds);

	public List<Account> getAccountByAccountId(Integer accountId);

	public List<Population> getAccountPopByAccountId(@Param("accountId")Integer accountId,
			@Param("x")Integer x, @Param("y")Integer y);

	public Integer getAccountPopCount(Integer accountId);

}
