package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Account;

public interface AccountBo {

	public Map<String, Object> getAllAccountByHouseId(List<Integer> houseIds,
			Integer pageSize, Integer pageCurrent);

	public List<Account> getAccountById(Integer accountId);

	public Integer updateAuditAccount(List<Integer> accountIds);

	public Account getAccountByAccountId(Integer accountId);

	public Map<String, Object> getAccountPopByAccountId(Integer accountId,
			Integer pageSize, Integer pageCurrent);

}
