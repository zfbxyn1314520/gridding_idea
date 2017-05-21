package com.eollse.po;

import java.io.Serializable;

public class Icon implements Serializable {

	private Integer iconId;
	private String iconName;
	private String iconType;

	public Icon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Icon(Integer iconId, String iconName, String iconType) {
		super();
		this.iconId = iconId;
		this.iconName = iconName;
		this.iconType = iconType;
	}

	@Override
	public String toString() {
		return "Icon [iconId=" + iconId + ", iconName=" + iconName
				+ ", iconType=" + iconType + "]";
	}

	public Integer getIconId() {
		return iconId;
	}

	public void setIconId(Integer iconId) {
		this.iconId = iconId;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

}
