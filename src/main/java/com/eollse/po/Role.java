package com.eollse.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Role implements Serializable {
	private Integer roleId;
	private String roleName;
	private String roleMemo;
	private String editRoleName;
	private Date editRoleDate;
	private Integer role_enable;
	private Integer roleLevel;
	private List<Permission> permissions;
	private List<Menu> menus;
	private Integer userCount;

	public Role() {
		super();
		this.permissions = new ArrayList<Permission>();
		this.menus = new ArrayList<Menu>();
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName
				+ ", roleMemo=" + roleMemo + ", editRoleName=" + editRoleName
				+ ", editRoleDate=" + editRoleDate + ", role_enable="
				+ role_enable + ", roleLevel=" + roleLevel + ", permissions="
				+ permissions + ", menus=" + menus + ", userCount=" + userCount
				+ "]";
	}

	public Role(Integer roleId, String roleName, String roleMemo,
			String editRoleName, Date editRoleDate, Integer role_enable,
			Integer roleLevel, List<Permission> permissions, List<Menu> menus,
			Integer userCount) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleMemo = roleMemo;
		this.editRoleName = editRoleName;
		this.editRoleDate = editRoleDate;
		this.role_enable = role_enable;
		this.roleLevel = roleLevel;
		this.permissions = permissions;
		this.menus = menus;
		this.userCount = userCount;
	}

	public Role(String roleName, String roleMemo, String editRoleName,
			Date editRoleDate, Integer role_enable, Integer roleLevel) {
		super();
		this.roleName = roleName;
		this.roleMemo = roleMemo;
		this.editRoleName = editRoleName;
		this.editRoleDate = editRoleDate;
		this.role_enable = role_enable;
		this.roleLevel = roleLevel;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleMemo() {
		return roleMemo;
	}

	public void setRoleMemo(String roleMemo) {
		this.roleMemo = roleMemo;
	}

	public String getEditRoleName() {
		return editRoleName;
	}

	public void setEditRoleName(String editRoleName) {
		this.editRoleName = editRoleName;
	}

	public Date getEditRoleDate() {
		return editRoleDate;
	}

	public void setEditRoleDate(Date editRoleDate) {
		this.editRoleDate = editRoleDate;
	}

	public Integer getRole_enable() {
		return role_enable;
	}

	public void setRole_enable(Integer role_enable) {
		this.role_enable = role_enable;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

}
