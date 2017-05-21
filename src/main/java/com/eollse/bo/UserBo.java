package com.eollse.bo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eollse.po.Role;
import com.eollse.po.Role_per;
import com.eollse.po.User;

public interface UserBo {

	public User validateUser(User u);

	public List<Role_per> getUserPerMenu(User user, Integer menuId);

	public Map<String, Object> getAllUserByAreaId(List<Integer> areaIds,Integer pageSize, Integer pageCurrent);

	public List<User> validateUsername(User user);

	public Integer getAllUsersCount(List<Integer> areaIds);

	public Integer addNewUser(User user);

	public List<User> getOneUserInfo(User user);

	public Integer alterUserInfo(User user);

	public Integer deleteUserById(Integer userId);

	public List<User> userCountByRoleId(Role role, List<Integer> areaIds);

	public Integer alterUserByRoleId(List<Integer> delRoleIds, Integer roleId);

	public Integer alterAuditStatus(User user);

	public void updateUserLoginInfo(String user_login_ip, Date user_last_login, Integer userId);

	public void alterUserStatusOfRole(List<User> users);

	public Integer getLastUserId();

	public Integer addUserStaffInfo(Integer userId, Integer gridStaffId);

	public void deleteUserByUserId(Integer userId);

	public Integer getUserIdOfAppUser(Integer gridStaffId);

	public User getAppUserInfo(Integer userId);

	public Integer deleteUserStaffById(Integer userId);
	
	public Integer getStaffIdByUserId(Integer userId);

	public List<Integer> getUserIdByAreaId(List<Integer> areaIds);

}
