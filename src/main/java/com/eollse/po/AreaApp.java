package com.eollse.po;

import java.io.Serializable;

public class AreaApp implements Serializable {

	private Integer areaId;
	private String areaName;

	public AreaApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AreaApp(Integer areaId, String areaName) {
		super();
		this.areaId = areaId;
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return "AreaApp [areaId=" + areaId + ", areaName=" + areaName + "]";
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
