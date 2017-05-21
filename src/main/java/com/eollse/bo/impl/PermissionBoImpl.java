package com.eollse.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.PermissionBo;
import com.eollse.dao.PermissionDao;
import com.eollse.po.Menu;
import com.eollse.po.Permission;

@Service
public class PermissionBoImpl implements PermissionBo {
	@Autowired
	private PermissionDao perDao;

	@Override
	public List<Permission> getMenuPer(Menu menu) {
		// TODO Auto-generated method stub
		return this.perDao.getMenuPer(menu);
	}

	@Override
	public List<Permission> getAllPermission() {
		// TODO Auto-generated method stub
		return this.perDao.getAllPermission();
	}


}
