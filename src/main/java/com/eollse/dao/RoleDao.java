package com.eollse.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Role;

public interface RoleDao {

	public Role getRoleById(@Param("roleId")Integer roleId);

	public List<Role> getAllRoles(@Param("roleLevel")Integer roleLevel);

	public List<Role> getAllRoleByAreaId(@Param("roleLevel")Integer roleLevel, @Param("x")Integer x, @Param("y")Integer y);

	public Integer getAllRolesCount(Integer roleLevel);

	public Integer addNewRole(Role role);

	public Integer alterRoleInfo(Role role);

	public Integer deleteRoleByIds(List<Integer> delRoleIds);

	public List<Role> selectRoleName(@Param("roleName")String roleName);

	public Integer getUserCountByRoleId(@Param("roleId")Integer roleId, @Param("list")List<Integer> areaIds);

	public void alterRoleUseStatus(Role role);

}
