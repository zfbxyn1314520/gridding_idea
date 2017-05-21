package com.eollse.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.RolePerBo;
import com.eollse.dao.RolePerDao;
import com.eollse.po.Role;
import com.eollse.po.Role_per;

@Service
public class RolePerBoImpl implements RolePerBo {
	
	@Autowired
	private RolePerDao rolePerDao;

	@Override
	public Integer deletePerByRoleId(List<Integer> delRoleIds) {
		// TODO Auto-generated method stub
		return this.rolePerDao.deletePerByRoleId(delRoleIds);
	}

	@Override
	public Integer deletePerByMenuId(List<Integer> delMenuIds) {
		// TODO Auto-generated method stub
		return this.rolePerDao.deletePerByMenuId(delMenuIds);
	}

	@Override
	public List<Role_per> getRolePerInfo(Role role) {
		// TODO Auto-generated method stub
		return this.rolePerDao.getRolePerInfo(role);
	}

	@Override
	public Integer deleteRolePer(Role role) {
		// TODO Auto-generated method stub
		return this.rolePerDao.deleteRolePer(role);
	}

	@Override
	public Integer saveRolePer(List<Role_per> rolePers) {
		// TODO Auto-generated method stub
		return this.rolePerDao.saveRolePer(rolePers);
	}
	
}
