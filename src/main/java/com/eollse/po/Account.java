package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
	private Integer accountId;
	private Integer houseId;
	private String accountHolder;
	private String accountSite;
	private Integer memberCount;
	private Integer accountAudit;
	private String editAccountName;
	private Date editAccountDate;
	private House house;
	private String holderTel;
	private String holderCard;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
		this.house = new House();
	}

	public Account(Integer accountId, Integer houseId, String accountHolder,
			String accountSite, Integer memberCount, Integer accountAudit,
			String editAccountName, Date editAccountDate, House house,
			String holderTel, String holderCard) {
		super();
		this.accountId = accountId;
		this.houseId = houseId;
		this.accountHolder = accountHolder;
		this.accountSite = accountSite;
		this.memberCount = memberCount;
		this.accountAudit = accountAudit;
		this.editAccountName = editAccountName;
		this.editAccountDate = editAccountDate;
		this.house = house;
		this.holderTel = holderTel;
		this.holderCard = holderCard;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", houseId=" + houseId
				+ ", accountHolder=" + accountHolder + ", accountSite="
				+ accountSite + ", memberCount=" + memberCount
				+ ", accountAudit=" + accountAudit + ", editAccountName="
				+ editAccountName + ", editAccountDate=" + editAccountDate
				+ ", house=" + house + ", holderTel=" + holderTel
				+ ", holderCard=" + holderCard + "]";
	}

	public String getHolderTel() {
		return holderTel;
	}

	public void setHolderTel(String holderTel) {
		this.holderTel = holderTel;
	}

	public String getHolderCard() {
		return holderCard;
	}

	public void setHolderCard(String holderCard) {
		this.holderCard = holderCard;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public String getEditAccountName() {
		return editAccountName;
	}

	public void setEditAccountName(String editAccountName) {
		this.editAccountName = editAccountName;
	}

	public Date getEditAccountDate() {
		return editAccountDate;
	}

	public void setEditAccountDate(Date editAccountDate) {
		this.editAccountDate = editAccountDate;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getAccountSite() {
		return accountSite;
	}

	public void setAccountSite(String accountSite) {
		this.accountSite = accountSite;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public Integer getAccountAudit() {
		return accountAudit;
	}

	public void setAccountAudit(Integer accountAudit) {
		this.accountAudit = accountAudit;
	}

}
