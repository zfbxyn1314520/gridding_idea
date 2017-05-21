package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Store implements Serializable {
	private Integer storeId;
	private Integer areaId;
	private String storeName;
	private String storeScope;
	private String storeTel;
	private Integer storeArea;
	private String storeSite;
	private String linlkMan;
	private String storePic;
	private Integer storeAudit;
	private String editStoreName;
	private Date editStoreDate;
	private Area area;

	public Store() {
		super();
		this.area = new Area();
	}

	public Store(Integer storeId, Integer areaId, String storeName,
			String storeScope, String storeTel, Integer storeArea,
			String storeSite, String linlkMan, String storePic,
			Integer storeAudit, String editStoreName, Date editStoreDate,
			Area area) {
		super();
		this.storeId = storeId;
		this.areaId = areaId;
		this.storeName = storeName;
		this.storeScope = storeScope;
		this.storeTel = storeTel;
		this.storeArea = storeArea;
		this.storeSite = storeSite;
		this.linlkMan = linlkMan;
		this.storePic = storePic;
		this.storeAudit = storeAudit;
		this.editStoreName = editStoreName;
		this.editStoreDate = editStoreDate;
		this.area = area;
	}

	@Override
	public String toString() {
		return "Store [storeId=" + storeId + ", areaId=" + areaId
				+ ", storeName=" + storeName + ", storeScope=" + storeScope
				+ ", storeTel=" + storeTel + ", storeArea=" + storeArea
				+ ", storeSite=" + storeSite + ", linlkMan=" + linlkMan
				+ ", storePic=" + storePic + ", storeAudit=" + storeAudit
				+ ", editStoreName=" + editStoreName + ", editStoreDate="
				+ editStoreDate + ", area=" + area + "]";
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getEditStoreName() {
		return editStoreName;
	}

	public void setEditStoreName(String editStoreName) {
		this.editStoreName = editStoreName;
	}

	public Date getEditStoreDate() {
		return editStoreDate;
	}

	public void setEditStoreDate(Date editStoreDate) {
		this.editStoreDate = editStoreDate;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreScope() {
		return storeScope;
	}

	public void setStoreScope(String storeScope) {
		this.storeScope = storeScope;
	}

	public String getStoreTel() {
		return storeTel;
	}

	public void setStoreTel(String storeTel) {
		this.storeTel = storeTel;
	}

	public Integer getStoreArea() {
		return storeArea;
	}

	public void setStoreArea(Integer storeArea) {
		this.storeArea = storeArea;
	}

	public String getStoreSite() {
		return storeSite;
	}

	public void setStoreSite(String storeSite) {
		this.storeSite = storeSite;
	}

	public String getLinlkMan() {
		return linlkMan;
	}

	public void setLinlkMan(String linlkMan) {
		this.linlkMan = linlkMan;
	}

	public String getStorePic() {
		return storePic;
	}

	public void setStorePic(String storePic) {
		this.storePic = storePic;
	}

	public Integer getStoreAudit() {
		return storeAudit;
	}

	public void setStoreAudit(Integer storeAudit) {
		this.storeAudit = storeAudit;
	}

}
