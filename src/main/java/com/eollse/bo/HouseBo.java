package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Account;
import com.eollse.po.House;
import com.eollse.po.House_status;
import com.eollse.po.House_type;
import com.eollse.po.Unit;

public interface HouseBo {

	public Map<String, Object> getAllHouseByUnitId(List<Integer> unitIds,
			Integer pageSize, Integer pageCurrent);

	public List<Unit> getAllUnitByBlockId(List<Integer> blockIds);

	public List<Unit> getUnitName(Integer blockIds);

	public List<House_status> getStatusName();

	public List<House_type> getTypeName();

	public Integer saveHouse(House house);

	public Integer getAllHouseCount(List<Integer> unitIds);

	public List<Account> getHouseById(Integer houseId);

	public Integer editHouseById(House house);

	public Account getOneHouseInfoById(House house);

	public Integer updateAuditHouse(List<Integer> houseIds);

	public List<House> getHouseCountById(List<Integer> unitIds);

	public Integer saveAccount(Account account);

	public Integer getMaxHouseId();

	public Integer editAccountById(Account account);

	public List<Account> getAllAccountByUnitId(Integer unitId);

}
