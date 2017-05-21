package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.AccountBo;
import com.eollse.dao.AccountDao;
import com.eollse.dao.HouseDao;
import com.eollse.dao.PopulationDao;
import com.eollse.po.Account;
import com.eollse.po.Population;

@Service
public class AccountBoImpl implements AccountBo {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private PopulationDao popDao;
	@Autowired
	private HouseDao houseDao;
	
	@Override
	public Map<String, Object> getAllAccountByHouseId(List<Integer> houseIds,
			Integer pageSize, Integer pageCurrent) {
		// TODO Auto-generated method stub
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent * pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Account> accounts=this.accountDao.getAllAccountByHouseId(houseIds,x,y);
		for(Account account : accounts){
			if(this.popDao.getMemberCountById(account.getAccountId())>0)
			account.setMemberCount(this.popDao.getMemberCountById(account.getAccountId()));
		}
		int totalRow = this.accountDao.getAllAccountCount(houseIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", accounts);
		return map;
	}

	@Override
	public List<Account> getAccountById(Integer accountId) {
		// TODO Auto-generated method stub
		List<Account> account=this.accountDao.getAccountById(accountId);
		Integer memberCount=this.popDao.getMemberCountById(account.get(0).getAccountId());
		Integer blockId=this.houseDao.getBlockIdByUnitId(account.get(0).getHouse().getUnit().getUnitId()).getBlockId();
		Integer courtId=this.houseDao.getCourtIdByBlockId(blockId).getCourtId();
		account.get(0).setMemberCount(memberCount);
		account.get(0).getHouse().getUnit().getBlock().setBlockId(blockId);
		account.get(0).getHouse().getUnit().getBlock().getCourt()
				.setCourtId(courtId);
		System.out.println(account);
		return account;
	}

	@Override
	public Integer updateAuditAccount(List<Integer> accountIds) {
		// TODO Auto-generated method stub
		return this.accountDao.updateAuditAccount(accountIds);
	}

	@Override
	public Account getAccountByAccountId(Integer accountId) {
		// TODO Auto-generated method stub
		List<Account> account=this.accountDao.getAccountByAccountId(accountId);
		account.get(0).setMemberCount(this.popDao.getMemberCountById(accountId));
		return account.get(0);
	}

	@Override
	public Map<String, Object> getAccountPopByAccountId(Integer accountId,Integer pageSize, Integer pageCurrent) {
		// 计算起始数值
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent * pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Population> pops = this.accountDao.getAccountPopByAccountId(accountId, x, y);
		Integer totalRow = this.accountDao.getAccountPopCount(accountId);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", pops);
		return map;
	}
}
