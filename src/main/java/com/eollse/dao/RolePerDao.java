package com.eollse.dao;

import java.util.List;


import com.eollse.po.Role;
import com.eollse.po.Role_per;

public interface RolePerDao {

	public Integer deletePerByRoleId(List<Integer> delRoleIds);

	public Integer deletePerByMenuId(List<Integer> delMenuIds);

	public List<Role_per> getRolePerInfo(Role role);

	public Integer deleteRolePer(Role role);

	public Integer saveRolePer(List<Role_per> rolePers);
	
}
