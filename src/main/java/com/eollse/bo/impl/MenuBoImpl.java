package com.eollse.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.MenuBo;
import com.eollse.dao.MenuDao;
import com.eollse.po.Icon;
import com.eollse.po.Menu;
import com.eollse.po.Menu_per;

@Service
public class MenuBoImpl implements MenuBo {
	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Menu> getAllMenu() {
		// TODO Auto-generated method stub
		return this.menuDao.getAllMenu();
	}

	@Override
	public List<Menu> getMenuPerById(Menu menu,Integer roleId) {
		// TODO Auto-generated method stub
		return this.menuDao.getMenuPerById(menu,roleId);
	}

	@Override
	public List<Menu> getParentMenuById(Integer parentMenuId) {
		// TODO Auto-generated method stub
		return this.menuDao.getParentMenuById(parentMenuId);
	}

	@Override
	public List<Menu> getAllMainMenu() {
		// TODO Auto-generated method stub
		return this.menuDao.getAllMainMenu();
	}

	@Override
	public Integer addNewMenu(Menu menu) {
		// TODO Auto-generated method stub
		return this.menuDao.addNewMenu(menu);
	}

	@Override
	public Integer getLastMenuId() {
		// TODO Auto-generated method stub
		return this.menuDao.getLastMenuId();
	}

	@Override
	public Integer addMenuPer(List<Menu_per> lists) {
		// TODO Auto-generated method stub
		return this.menuDao.addMenuPer(lists);
	}

	@Override
	public List<Menu> getOneMenuInfo(Menu menu) {
		// TODO Auto-generated method stub
		return this.menuDao.getOneMenuInfo(menu);
	}

	@Override
	public Integer alterMenuInfo(Menu menu) {
		// TODO Auto-generated method stub
		return this.menuDao.alterMenuInfo(menu);
	}

	@Override
	public Integer deletePersByMenuId(Integer menuId) {
		// TODO Auto-generated method stub
		return this.menuDao.deletePersByMenuId(menuId);
	}

	@Override
	public Integer deleteMenuByIds(List<Integer> delMenuIds) {
		// TODO Auto-generated method stub
		return this.menuDao.deleteMenuByIds(delMenuIds);
	}

	@Override
	public Integer deleteMenuPerByMenuIds(List<Integer> delMenuIds) {
		// TODO Auto-generated method stub
		return this.menuDao.deleteMenuPerByMenuIds(delMenuIds);
	}

	@Override
	public List<Icon> getAllIcon() {
		// TODO Auto-generated method stub
		return this.menuDao.getAllIcon();
	}


}
