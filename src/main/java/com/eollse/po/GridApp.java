package com.eollse.po;

import java.io.Serializable;

public class GridApp implements Serializable {

	private Integer gridId;
	private String gridName;

	public GridApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GridApp(Integer gridId, String gridName) {
		super();
		this.gridId = gridId;
		this.gridName = gridName;
	}

	@Override
	public String toString() {
		return "GridApp [gridId=" + gridId + ", gridName=" + gridName + "]";
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

}
