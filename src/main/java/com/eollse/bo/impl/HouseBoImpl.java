package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.HouseBo;
import com.eollse.dao.HouseDao;
import com.eollse.dao.PopulationDao;
import com.eollse.po.Account;
import com.eollse.po.Block;
import com.eollse.po.House;
import com.eollse.po.House_status;
import com.eollse.po.House_type;
import com.eollse.po.Unit;

@Service
public class HouseBoImpl implements HouseBo {
	@Autowired
	private HouseDao houseDao;
	@Autowired
	private PopulationDao popDao;

	public Map<String, Object> getAllHouseByUnitId(List<Integer> unitIds,
			Integer pageSize, Integer pageCurrent) {
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent * pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<House> houses = this.houseDao.getAllHouseByUnitId(unitIds, x, y);
		for(House house:houses){
			house.setHouseHolder((this.houseDao.getAccountByHouseId(house.getHouseId())).getAccountHolder());
		}
		int totalRow = this.houseDao.getAllHouseCount(unitIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", houses);
		return map;
	}

	@Override
	public List<Unit> getAllUnitByBlockId(List<Integer> blockIds) {
		// TODO Auto-generated method stub
		return this.houseDao.getAllUnitByBlockId(blockIds);
	}

	@Override
	public List<Unit> getUnitName(Integer blockIds) {
		// TODO Auto-generated method stub
		return this.houseDao.getUnitName(blockIds);
	}

	@Override
	public List<House_status> getStatusName() {
		// TODO Auto-generated method stub
		return this.houseDao.getStatusName();
	}

	@Override
	public List<House_type> getTypeName() {
		// TODO Auto-generated method stub
		return this.houseDao.getTypeName();
	}

	@Override
	public Integer saveHouse(House house) {
		// TODO Auto-generated method stub
		return this.houseDao.saveHouse(house);
	}

	@Override
	public Integer getAllHouseCount(List<Integer> unitIds) {
		// TODO Auto-generated method stub
		return this.houseDao.getAllHouseCount(unitIds);
	}

	@Override
	public List<Account> getHouseById(Integer houseId) {
		// TODO Auto-generated method stub
		List<Account> account=this.houseDao.getHouseById(houseId);
		Integer memberCount=this.popDao.getMemberCountById(account.get(0).getAccountId());
		Integer blockId=this.houseDao.getBlockIdByUnitId(account.get(0).getHouse().getUnit().getUnitId()).getBlockId();
		Integer courtId=this.houseDao.getCourtIdByBlockId(blockId).getCourtId();
		account.get(0).setMemberCount(memberCount);
		account.get(0).getHouse().getUnit().getBlock().setBlockId(blockId);
		account.get(0).getHouse().getUnit().getBlock().getCourt().setCourtId(courtId);
		return account;
	}

	@Override
	public Integer editHouseById(House house) {
		// TODO Auto-generated method stub
		return this.houseDao.editHouseById(house);
	}

	@Override
	public Account getOneHouseInfoById(House house) {
		// TODO Auto-generated method stub
		Account account=this.houseDao.getAccountByHouseId(house.getHouseId());
		List<House> houses = this.houseDao.getOneHouseById(house);
		Block block=this.houseDao.getBlockIdByUnitId(houses.get(0).getUnitId());
		block.setCourt(this.houseDao.getCourtIdByBlockId(block.getBlockId()));
		if(account==null){
			account=new Account();
		}
		houses.get(0).getUnit().setBlock(block);
		account.setMemberCount(this.popDao.getMemberCountById(account.getAccountId()));
		account.setHouse(houses.get(0));
		return account;
	}

	@Override
	public Integer updateAuditHouse(List<Integer> houseIds) {
		// TODO Auto-generated method stub
		return this.houseDao.updateAuditHouse(houseIds);
	}

	@Override
	public List<House> getHouseCountById(List<Integer> unitIds) {
		// TODO Auto-generated method stub
		List<House> houses = this.houseDao.getHouseCountById(unitIds);
		for (House house : houses) {
			String unitName = this.houseDao.getUnitNameById(house.getUnit()
					.getUnitId());
			house.getUnit().setUnitName(unitName);
		}
		return houses;
	}

	@Override
	public Integer saveAccount(Account account) {
		// TODO Auto-generated method stub
		return this.houseDao.saveAccount(account);
	}

	@Override
	public Integer getMaxHouseId() {
		// TODO Auto-generated method stub
		return this.houseDao.getMaxHouseId();
	}

	@Override
	public Integer editAccountById(Account account) {
		// TODO Auto-generated method stub
		return this.houseDao.editAccountById(account);
	}

	@Override
	public List<Account> getAllAccountByUnitId(Integer unitId) {
		// TODO Auto-generated method stub
		return this.houseDao.getAllAccountByUnitId(unitId);
	}
	

}
