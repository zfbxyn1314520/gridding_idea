package com.eollse.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {

	private Integer menuId;
	private Integer parentMenuId;
	private String menuName;
	private String menuUri;
	private String menuIcon;
	private List<Permission> permissions;

	public Menu() {
		super();
		this.permissions = new ArrayList<Permission>();
	}

	public Menu(Integer menuId, Integer parentMenuId, String menuName,
			String menuUri, String menuIcon, List<Permission> permissions) {
		super();
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
		this.menuName = menuName;
		this.menuUri = menuUri;
		this.menuIcon = menuIcon;
		this.permissions = permissions;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUri() {
		return menuUri;
	}

	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", parentMenuId=" + parentMenuId
				+ ", menuName=" + menuName + ", menuUri=" + menuUri
				+ ", menuIcon=" + menuIcon + ", permissions=" + permissions
				+ "]";
	}

}
