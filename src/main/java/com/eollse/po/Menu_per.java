package com.eollse.po;

import java.io.Serializable;

public class Menu_per implements Serializable {

	private Integer menuId;
	private Integer perId;

	public Menu_per() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Menu_per(Integer menuId, Integer perId) {
		super();
		this.menuId = menuId;
		this.perId = perId;
	}

	@Override
	public String toString() {
		return "Menu_per [menuId=" + menuId + ", perId=" + perId + "]";
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getPerId() {
		return perId;
	}

	public void setPerId(Integer perId) {
		this.perId = perId;
	}

}
