package com.eollse.bo.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.UserBo;
import com.eollse.dao.UserDao;
import com.eollse.po.Role;
import com.eollse.po.Role_per;
import com.eollse.po.User;

@Service
public class UserBoImpl implements UserBo {

	@Autowired
	private UserDao userDao;

	@Override
	public User validateUser(User u) {
		// TODO Auto-generated method stub
		return this.userDao.validateUser(u);
	}

	@Override
	public List<Role_per> getUserPerMenu(User user, Integer menuId) {
		// TODO Auto-generated method stub
		return this.userDao.getUserPerMenu(user,menuId);
	}

	@Override
	public Map<String, Object> getAllUserByAreaId(List<Integer> areaIds,Integer pageSize, Integer pageCurrent) {
		// 计算起始数值
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> users = this.userDao.getAllUserByAreaId(areaIds,x, y);
		int totalRow = this.userDao.getAllUsersCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", users);
		return map;
	}

	@Override
	public List<User> validateUsername(User user) {
		// TODO Auto-generated method stub
		return this.userDao.getOneUserInfo(user);
	}

	@Override
	public Integer getAllUsersCount(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.userDao.getAllUsersCount(areaIds);
	}

	@Override
	public Integer addNewUser(User user) {
		Integer result = this.userDao.addNewUser(user);
		return result > 0 ? 1 : 0;
	}

	@Override
	public List<User> getOneUserInfo(User user) {
		// TODO Auto-generated method stub
		return this.userDao.getOneUserInfo(user);
	}

	@Override
	public Integer alterUserInfo(User user) {
		// TODO Auto-generated method stub
		return this.userDao.alterUserInfo(user);
	}

	@Override
	public Integer deleteUserById(Integer userId) {
		// TODO Auto-generated method stub
		return this.userDao.deleteUserById(userId);
	}

	@Override
	public Integer alterUserByRoleId(List<Integer> delRoleIds,Integer roleId) {
		// TODO Auto-generated method stub
		return this.userDao.alterUserByRoleId(delRoleIds,roleId);
	}

	@Override
	public Integer alterAuditStatus(User user) {
		// TODO Auto-generated method stub
		return this.userDao.alterAuditStatus(user);
	}

	@Override
	public List<User> userCountByRoleId(Role role, List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.userDao.userCountByRoleId(role, areaIds);
	}

	@Override
	public void updateUserLoginInfo(String user_login_ip, Date user_last_login,Integer userId) {
		// TODO Auto-generated method stub
		this.userDao.updateUserLoginInfo(user_login_ip, user_last_login, userId);
	}

	@Override
	public void alterUserStatusOfRole(List<User> users) {
		// TODO Auto-generated method stub
		this.userDao.alterUserStatusOfRole(users);
	}

	@Override
	public Integer getLastUserId() {
		// TODO Auto-generated method stub
		return this.userDao.getLastUserId();
	}

	@Override
	public Integer addUserStaffInfo(Integer userId, Integer gridStaffId) {
		// TODO Auto-generated method stub
		return this.userDao.addUserStaffInfo(userId, gridStaffId);
	}

	@Override
	public void deleteUserByUserId(Integer userId) {
		// TODO Auto-generated method stub
		this.userDao.deleteUserByUserId(userId);
	}

	@Override
	public Integer getUserIdOfAppUser(Integer gridStaffId) {
		// TODO Auto-generated method stub
		return this.userDao.getUserIdOfAppUser(gridStaffId);
	}

	@Override
	public User getAppUserInfo(Integer userId) {
		// TODO Auto-generated method stub
		return this.userDao.getAppUserInfo(userId);
	}

	@Override
	public Integer deleteUserStaffById(Integer userId) {
		// TODO Auto-generated method stub
		return this.userDao.deleteUserStaffById(userId);
	}

	@Override
	public List<Integer> getUserIdByAreaId(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.userDao.getUserIdByAreaId(areaIds);
	}
	
	@Override
	public Integer getStaffIdByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return this.userDao.getStaffIdByUserId(userId);
	}
	

}
