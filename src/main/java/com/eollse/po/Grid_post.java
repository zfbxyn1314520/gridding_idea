package com.eollse.po;

import java.io.Serializable;

public class Grid_post implements Serializable {
	private Integer gridPostId;
	private String gridPostName;

	public Grid_post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Grid_post(Integer gridPostId, String gridPostName) {
		super();
		this.gridPostId = gridPostId;
		this.gridPostName = gridPostName;
	}

	@Override
	public String toString() {
		return "Grid_post [gridPostId=" + gridPostId + ", gridPostName="
				+ gridPostName + "]";
	}

	public Integer getGridPostId() {
		return gridPostId;
	}

	public void setGridPostId(Integer gridPostId) {
		this.gridPostId = gridPostId;
	}

	public String getGridPostName() {
		return gridPostName;
	}

	public void setGridPostName(String gridPostName) {
		this.gridPostName = gridPostName;
	}

}
