package com.eollse.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Role;
import com.eollse.po.Role_per;
import com.eollse.po.User;

public interface UserDao {

	public User validateUser(User u);

	public List<Role_per> getUserPerMenu(@Param("user")User user, @Param("menuId")Integer menuId);

	public List<User> getAllUserByAreaId(@Param("list")List<Integer> areaIds, @Param("x")Integer x,@Param("y")Integer y);

	public int getAllUsersCount(List<Integer> areaIds);

	public Integer addNewUser(User user);

	public List<User> getOneUserInfo(User user);

	public Integer alterUserInfo(User user);

	public Integer deleteUserById(@Param("userId")Integer userId);

	public List<User> userCountByRoleId(@Param("role")Role role, @Param("list")List<Integer> areaIds);

	public Integer alterUserByRoleId(@Param("list")List<Integer> delRoleIds, @Param("roleId")Integer roleId);

	public Integer alterAuditStatus(User user);

	public void updateUserLoginInfo(@Param("user_login_ip")String user_login_ip, 
				@Param("user_last_login")Date user_last_login, @Param("userId")Integer userId);

	public void alterUserStatusOfRole(List<User> users);

	public Integer getLastUserId();

	public Integer addUserStaffInfo(@Param("userId")Integer userId, @Param("gridStaffId")Integer gridStaffId);

	public void deleteUserByUserId(@Param("userId")Integer userId);

	public Integer getUserIdOfAppUser(@Param("gridStaffId")Integer gridStaffId);

	public User getAppUserInfo(@Param("userId")Integer userId);

	public Integer deleteUserStaffById(@Param("userId")Integer userId);
	
	public Integer getStaffIdByUserId(@Param("userId")Integer userId);

	public List<Integer> getUserIdByAreaId(List<Integer> areaIds);


}
