package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Icon;
import com.eollse.po.Menu;
import com.eollse.po.Menu_per;

public interface MenuDao {

	public List<Menu> getAllMenu();

	public List<Menu> getMenuPerById(@Param("menu")Menu menu, @Param("roleId")Integer roleId);

	public List<Menu> getParentMenuById(@Param("parentMenuId")Integer parentMenuId);

	public List<Menu> getAllMainMenu();

	public Integer addNewMenu(Menu menu);

	public Integer getLastMenuId();

	public Integer addMenuPer(List<Menu_per> lists);

	public List<Menu> getOneMenuInfo(Menu menu);

	public Integer alterMenuInfo(Menu menu);

	public Integer deletePersByMenuId(Integer menuId);

	public Integer deleteMenuByIds(List<Integer> delMenuIds);

	public Integer deleteMenuPerByMenuIds(List<Integer> delMenuIds);

	public List<Icon> getAllIcon();



}
