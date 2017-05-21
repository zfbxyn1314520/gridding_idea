package com.eollse.po;

import java.io.Serializable;

public class Culture_level implements Serializable {
	private Integer levelId;
	private String levelName;
	
	public Culture_level() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Culture_level(Integer levelId, String levelName) {
		super();
		this.levelId = levelId;
		this.levelName = levelName;
	}
	@Override
	public String toString() {
		return "Culture_level [levelId=" + levelId + ", levelName=" + levelName
				+ "]";
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
}
