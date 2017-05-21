package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Account;
import com.eollse.po.Block;
import com.eollse.po.Court;
import com.eollse.po.House;
import com.eollse.po.House_status;
import com.eollse.po.House_type;
import com.eollse.po.Unit;

public interface HouseDao {

	public List<House> getAllHouseByUnitId(@Param("list") List<Integer> unitIds, @Param("x") Integer x,@Param("y") Integer y);

	public int getAllHouseCount(List<Integer> unitIds);

	public List<Unit> getAllUnitByBlockId(List<Integer> blockIds);

	public List<Unit> getUnitName(Integer blockIds);

	public List<House_status> getStatusName();

	public List<House_type> getTypeName();

	public Integer saveHouse(House house);

	public List<Account> getHouseById(Integer houseId);

	public Block getBlockIdByUnitId(@Param("unitId")Integer unitId);

	public Court getCourtIdByBlockId(@Param("blockId")Integer blockId);

	public Integer editHouseById(House house);

	public List<House> getOneHouseById(House house);

	public String getUnitNameById(Integer unitId);

	public String getBlockNameById(Integer blockId);

	public String getCourtNameById(Integer courtId);

	public String getStatusNameById(Integer statusId);

	public String getTypeNameById(Integer typeId);

	public Integer updateAuditHouse(List<Integer> houseIds);

	public List<House> getHouseCountById(@Param("list") List<Integer> unitIds);

	public Account getAccountName(Integer houseId);

	public Integer saveAccount(Account account);

	public Integer getMaxHouseId();

	public Integer editAccountById(Account account);

	public List<Account> getAllAccountByUnitId(@Param("unitId") Integer unitId);

	public Account getAccountByHouseId(Integer houseId);

}
