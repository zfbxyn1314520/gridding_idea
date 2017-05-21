package com.eollse.bo.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.RoleBo;
import com.eollse.dao.RoleDao;
import com.eollse.po.Role;
import com.eollse.po.User;

@Service
public class RoleBoImpl implements RoleBo {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public Role getRoleById(Integer roleId) {
		// TODO Auto-generated method stub
		return this.roleDao.getRoleById(roleId);
	}

	@Override
	public List<Role> getAllRoles(Integer roleLevel) {
		// TODO Auto-generated method stub
		return this.roleDao.getAllRoles(roleLevel);
	}

	@Override
	public Map<String, Object> getAllRoleByAreaId(User user,List<Integer> areaIds,Integer pageSize, Integer pageCurrent) {
		// 计算起始数值
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		Integer roleLevel=user.getRole().getRoleLevel();
		List<Role> roles = this.roleDao.getAllRoleByAreaId(roleLevel,x, y);
		Integer num=roles.size();
		for(int i=0;i<num;i++){
			roles.get(i).setUserCount(this.roleDao.getUserCountByRoleId(roles.get(i).getRoleId(),areaIds));
		}
		if(roleLevel!=0){
			for(int i=0;i<num;i++){
				if(roles.get(i).getRoleName().equals("其他")){
					roles.remove(i);
				}
			}
		}
		Integer totalRow = this.roleDao.getAllRolesCount(roleLevel);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", roles);
		return map;
	}

	@Override
	public Integer addNewRole(Role role) {
		// TODO Auto-generated method stub
		return this.roleDao.addNewRole(role);
	}

	@Override
	public Integer getAllRolesCount(Integer roleLevel) {
		// TODO Auto-generated method stub
		return this.roleDao.getAllRolesCount(roleLevel);
	}

	@Override
	public Integer alterRoleInfo(Role role) {
		// TODO Auto-generated method stub
		return this.roleDao.alterRoleInfo(role);
	}

	@Override
	public Integer deleteRoleByIds(List<Integer> delRoleIds) {
		// TODO Auto-generated method stub
		return this.roleDao.deleteRoleByIds(delRoleIds);
	}

	@Override
	public List<Role> selectRoleName(String roleName) {
		// TODO Auto-generated method stub
		return this.roleDao.selectRoleName(roleName);
	}

	@Override
	public void alterRoleUseStatus(Role role) {
		// TODO Auto-generated method stub
		this.roleDao.alterRoleUseStatus(role);
	}

}
