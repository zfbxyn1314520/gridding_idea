package com.eollse.po;

import java.io.Serializable;

public class Company_trade implements Serializable {
	private Integer tradeId;
	private Integer tradePerId;
	private String tradeName;
	public Company_trade() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Company_trade(Integer tradeId, Integer tradePerId, String tradeName) {
		super();
		this.tradeId = tradeId;
		this.tradePerId = tradePerId;
		this.tradeName = tradeName;
	}
	@Override
	public String toString() {
		return "Company_trade [tradeId=" + tradeId + ", tradePerId="
				+ tradePerId + ", tradeName=" + tradeName + "]";
	}
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	public Integer getTradePerId() {
		return tradePerId;
	}
	public void setTradePerId(Integer tradePerId) {
		this.tradePerId = tradePerId;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
}
