package com.eollse.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Population implements Serializable {
	private Integer popId;
	private Integer marriageId;
	private Integer relationId;
	private Integer areaId;
	private Integer levelId;
	private Integer nationId;
	private Integer accountId;
	private Integer politicsId;
	private Integer gridId;
	private Integer gridStaffId;
	private String popName;
	private String popSex;
	@DateTimeFormat( pattern = "yyyy年MM月dd日" )
	private Date popBirthday;
	private Integer popAge;
	private String popNative;
	private String popCode;
	private String popJob;
	private String popTel;
	private String popIcon;
	private String popNativeSite;
	private String popNowSite;
	private String popCompany;
	private String popJobSite;
	private String popCar;
	private String popHobby;
	private Integer popInsured;
	private Integer householder;
	private Integer isFlow;
	private String editPopName;
	private Date editPopDate;
	private Integer popAudit;
	private List<Popattr> popAttrs;
	private Culture_level cultureLevel;
	private Politics politics;
	private Marriage_status marriageStatus;
	private Pop_nation popNation;
	private Holder_relation holderRelation;
	private Account account;
	private Area area;
	private Grid grid;
	private Grid_staff gridStaff;

	public Population() {
		super();
		this.popAttrs = new ArrayList<Popattr>();
		this.cultureLevel = new Culture_level();
		this.politics = new Politics();
		this.marriageStatus = new Marriage_status();
		this.popNation = new Pop_nation();
		this.holderRelation = new Holder_relation();
		this.account = new Account();
		this.area = new Area();
		this.grid = new Grid();
		this.gridStaff = new Grid_staff();

	}

	@Override
	public String toString() {
		return "Population [popId=" + popId + ", marriageId=" + marriageId
				+ ", relationId=" + relationId + ", areaId=" + areaId
				+ ", levelId=" + levelId + ", nationId=" + nationId
				+ ", accountId=" + accountId + ", politicsId=" + politicsId
				+ ", gridId=" + gridId + ", gridStaffId=" + gridStaffId
				+ ", popName=" + popName + ", popSex=" + popSex
				+ ", popBirthday=" + popBirthday + ", popAge=" + popAge
				+ ", popNative=" + popNative + ", popCode=" + popCode
				+ ", popJob=" + popJob + ", popTel=" + popTel + ", popIcon="
				+ popIcon + ", popNativeSite=" + popNativeSite
				+ ", popNowSite=" + popNowSite + ", popCompany=" + popCompany
				+ ", popJobSite=" + popJobSite + ", popCar=" + popCar
				+ ", popHobby=" + popHobby + ", popInsured=" + popInsured
				+ ", householder=" + householder + ", isFlow=" + isFlow
				+ ", editPopName=" + editPopName + ", editPopDate="
				+ editPopDate + ", popAudit=" + popAudit + ", popAttrs="
				+ popAttrs + ", cultureLevel=" + cultureLevel + ", politics="
				+ politics + ", marriageStatus=" + marriageStatus
				+ ", popNation=" + popNation + ", holderRelation="
				+ holderRelation + ", account=" + account + ", area=" + area
				+ ", grid=" + grid + ", gridStaff=" + gridStaff + "]";
	}

	public Population(Integer popId, Integer marriageId, Integer relationId,
			Integer areaId, Integer levelId, Integer nationId,
			Integer accountId, Integer politicsId, Integer gridId,
			Integer gridStaffId, String popName, String popSex,
			Date popBirthday, Integer popAge, String popNative, String popCode,
			String popJob, String popTel, String popIcon, String popNativeSite,
			String popNowSite, String popCompany, String popJobSite,
			String popCar, String popHobby, Integer popInsured,
			Integer householder, Integer isFlow, String editPopName,
			Date editPopDate, Integer popAudit, List<Popattr> popAttrs,
			Culture_level cultureLevel, Politics politics,
			Marriage_status marriageStatus, Pop_nation popNation,
			Holder_relation holderRelation, Account account, Area area,
			Grid grid, Grid_staff gridStaff) {
		super();
		this.popId = popId;
		this.marriageId = marriageId;
		this.relationId = relationId;
		this.areaId = areaId;
		this.levelId = levelId;
		this.nationId = nationId;
		this.accountId = accountId;
		this.politicsId = politicsId;
		this.gridId = gridId;
		this.gridStaffId = gridStaffId;
		this.popName = popName;
		this.popSex = popSex;
		this.popBirthday = popBirthday;
		this.popAge = popAge;
		this.popNative = popNative;
		this.popCode = popCode;
		this.popJob = popJob;
		this.popTel = popTel;
		this.popIcon = popIcon;
		this.popNativeSite = popNativeSite;
		this.popNowSite = popNowSite;
		this.popCompany = popCompany;
		this.popJobSite = popJobSite;
		this.popCar = popCar;
		this.popHobby = popHobby;
		this.popInsured = popInsured;
		this.householder = householder;
		this.isFlow = isFlow;
		this.editPopName = editPopName;
		this.editPopDate = editPopDate;
		this.popAudit = popAudit;
		this.popAttrs = popAttrs;
		this.cultureLevel = cultureLevel;
		this.politics = politics;
		this.marriageStatus = marriageStatus;
		this.popNation = popNation;
		this.holderRelation = holderRelation;
		this.account = account;
		this.area = area;
		this.grid = grid;
		this.gridStaff = gridStaff;
	}

	public List<Popattr> getPopAttrs() {
		return popAttrs;
	}

	public void setPopAttrs(List<Popattr> popAttrs) {
		this.popAttrs = popAttrs;
	}

	public Culture_level getCultureLevel() {
		return cultureLevel;
	}

	public void setCultureLevel(Culture_level cultureLevel) {
		this.cultureLevel = cultureLevel;
	}

	public Politics getPolitics() {
		return politics;
	}

	public void setPolitics(Politics politics) {
		this.politics = politics;
	}

	public Marriage_status getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(Marriage_status marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public Pop_nation getPopNation() {
		return popNation;
	}

	public void setPopNation(Pop_nation popNation) {
		this.popNation = popNation;
	}

	public Holder_relation getHolderRelation() {
		return holderRelation;
	}

	public void setHolderRelation(Holder_relation holderRelation) {
		this.holderRelation = holderRelation;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Grid_staff getGridStaff() {
		return gridStaff;
	}

	public void setGridStaff(Grid_staff gridStaff) {
		this.gridStaff = gridStaff;
	}

	public String getEditPopName() {
		return editPopName;
	}

	public void setEditPopName(String editPopName) {
		this.editPopName = editPopName;
	}

	public Date getEditPopDate() {
		return editPopDate;
	}

	public void setEditPopDate(Date editPopDate) {
		this.editPopDate = editPopDate;
	}

	public Integer getPopId() {
		return popId;
	}

	public void setPopId(Integer popId) {
		this.popId = popId;
	}

	public Integer getMarriageId() {
		return marriageId;
	}

	public void setMarriageId(Integer marriageId) {
		this.marriageId = marriageId;
	}

	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getNationId() {
		return nationId;
	}

	public void setNationId(Integer nationId) {
		this.nationId = nationId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getPoliticsId() {
		return politicsId;
	}

	public void setPoliticsId(Integer politicsId) {
		this.politicsId = politicsId;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public Integer getGridStaffId() {
		return gridStaffId;
	}

	public void setGridStaffId(Integer gridStaffId) {
		this.gridStaffId = gridStaffId;
	}

	public String getPopName() {
		return popName;
	}

	public void setPopName(String popName) {
		this.popName = popName;
	}

	public String getPopSex() {
		return popSex;
	}

	public void setPopSex(String popSex) {
		this.popSex = popSex;
	}

	public Date getPopBirthday() {
		return popBirthday;
	}

	public void setPopBirthday(Date popBirthday) {
		this.popBirthday = popBirthday;
	}

	public Integer getPopAge() {
		return popAge;
	}

	public void setPopAge(Integer popAge) {
		this.popAge = popAge;
	}

	public String getPopNative() {
		return popNative;
	}

	public void setPopNative(String popNative) {
		this.popNative = popNative;
	}

	public String getPopCode() {
		return popCode;
	}

	public void setPopCode(String popCode) {
		this.popCode = popCode;
	}

	public String getPopJob() {
		return popJob;
	}

	public void setPopJob(String popJob) {
		this.popJob = popJob;
	}

	public String getPopTel() {
		return popTel;
	}

	public void setPopTel(String popTel) {
		this.popTel = popTel;
	}

	public String getPopIcon() {
		return popIcon;
	}

	public void setPopIcon(String popIcon) {
		this.popIcon = popIcon;
	}

	public String getPopNativeSite() {
		return popNativeSite;
	}

	public void setPopNativeSite(String popNativeSite) {
		this.popNativeSite = popNativeSite;
	}

	public String getPopNowSite() {
		return popNowSite;
	}

	public void setPopNowSite(String popNowSite) {
		this.popNowSite = popNowSite;
	}

	public String getPopCompany() {
		return popCompany;
	}

	public void setPopCompany(String popCompany) {
		this.popCompany = popCompany;
	}

	public String getPopJobSite() {
		return popJobSite;
	}

	public void setPopJobSite(String popJobSite) {
		this.popJobSite = popJobSite;
	}

	public String getPopCar() {
		return popCar;
	}

	public void setPopCar(String popCar) {
		this.popCar = popCar;
	}

	public String getPopHobby() {
		return popHobby;
	}

	public void setPopHobby(String popHobby) {
		this.popHobby = popHobby;
	}

	public Integer getPopInsured() {
		return popInsured;
	}

	public void setPopInsured(Integer popInsured) {
		this.popInsured = popInsured;
	}

	public Integer getHouseholder() {
		return householder;
	}

	public void setHouseholder(Integer householder) {
		this.householder = householder;
	}

	public Integer getIsFlow() {
		return isFlow;
	}

	public void setIsFlow(Integer isFlow) {
		this.isFlow = isFlow;
	}

	public Integer getPopAudit() {
		return popAudit;
	}

	public void setPopAudit(Integer popAudit) {
		this.popAudit = popAudit;
	}
	

}
