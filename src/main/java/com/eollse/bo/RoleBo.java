package com.eollse.bo;


import java.util.List;
import java.util.Map;

import com.eollse.po.Role;
import com.eollse.po.User;

public interface RoleBo {

	public Role getRoleById(Integer roleId);

	public List<Role> getAllRoles(Integer roleLevel);

	public Map<String, Object> getAllRoleByAreaId(User user,List<Integer> areaIds, Integer pageSize, Integer pageCurrent);

	public Integer addNewRole(Role role);

	public Integer getAllRolesCount(Integer roleLevel);

	public Integer alterRoleInfo(Role role);

	public Integer deleteRoleByIds(List<Integer> delRoleIds);

	public List<Role> selectRoleName(String roleName);

	public void alterRoleUseStatus(Role role);

}
