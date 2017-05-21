package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

public class Company implements Serializable {
	private Integer companyId;
	private Integer tradeId;
	private Integer scaleId;
	private Integer areaId;
	private Integer natureId;
	private String companyName;
	private String companyMan;
	private String companyIntro;
	private String companyTel;
	private String companySite;
	private String creditCode;
	private String registerOffice;
	private String companyPic;
	private Integer companyAudit;
	private Company_nature companyNature;
	private Company_scale companyScale;
	private Company_trade companyTrade;
	private Area area;
	private String editComName;
	private Date editComDate;

	public Company() {
		super();
		this.companyNature = new Company_nature();
		this.companyScale = new Company_scale();
		this.companyTrade = new Company_trade();
		this.area = new Area();
	}

	public Company(Integer companyId, Integer tradeId, Integer scaleId,
			Integer areaId, Integer natureId, String companyName,
			String companyMan, String companyIntro, String companyTel,
			String companySite, String creditCode, String registerOffice,
			String companyPic, Integer companyAudit,
			Company_nature companyNature, Company_scale companyScale,
			Company_trade companyTrade, Area area, String editComName,
			Date editComDate) {
		super();
		this.companyId = companyId;
		this.tradeId = tradeId;
		this.scaleId = scaleId;
		this.areaId = areaId;
		this.natureId = natureId;
		this.companyName = companyName;
		this.companyMan = companyMan;
		this.companyIntro = companyIntro;
		this.companyTel = companyTel;
		this.companySite = companySite;
		this.creditCode = creditCode;
		this.registerOffice = registerOffice;
		this.companyPic = companyPic;
		this.companyAudit = companyAudit;
		this.companyNature = companyNature;
		this.companyScale = companyScale;
		this.companyTrade = companyTrade;
		this.area = area;
		this.editComName = editComName;
		this.editComDate = editComDate;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", tradeId=" + tradeId
				+ ", scaleId=" + scaleId + ", areaId=" + areaId + ", natureId="
				+ natureId + ", companyName=" + companyName + ", companyMan="
				+ companyMan + ", companyIntro=" + companyIntro
				+ ", companyTel=" + companyTel + ", companySite=" + companySite
				+ ", creditCode=" + creditCode + ", registerOffice="
				+ registerOffice + ", companyPic=" + companyPic
				+ ", companyAudit=" + companyAudit + ", companyNature="
				+ companyNature + ", companyScale=" + companyScale
				+ ", companyTrade=" + companyTrade + ", area=" + area
				+ ", editComName=" + editComName + ", editComDate="
				+ editComDate + "]";
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getEditComName() {
		return editComName;
	}

	public void setEditComName(String editComName) {
		this.editComName = editComName;
	}

	public Date getEditComDate() {
		return editComDate;
	}

	public void setEditComDate(Date editComDate) {
		this.editComDate = editComDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}

	public Integer getScaleId() {
		return scaleId;
	}

	public void setScaleId(Integer scaleId) {
		this.scaleId = scaleId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getNatureId() {
		return natureId;
	}

	public void setNatureId(Integer natureId) {
		this.natureId = natureId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyMan() {
		return companyMan;
	}

	public void setCompanyMan(String companyMan) {
		this.companyMan = companyMan;
	}

	public String getCompanyIntro() {
		return companyIntro;
	}

	public void setCompanyIntro(String companyIntro) {
		this.companyIntro = companyIntro;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanySite() {
		return companySite;
	}

	public void setCompanySite(String companySite) {
		this.companySite = companySite;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getRegisterOffice() {
		return registerOffice;
	}

	public void setRegisterOffice(String registerOffice) {
		this.registerOffice = registerOffice;
	}

	public Integer getCompanyAudit() {
		return companyAudit;
	}

	public void setCompanyAudit(Integer companyAudit) {
		this.companyAudit = companyAudit;
	}

	public Company_nature getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(Company_nature companyNature) {
		this.companyNature = companyNature;
	}

	public Company_scale getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(Company_scale companyScale) {
		this.companyScale = companyScale;
	}

	public Company_trade getCompanyTrade() {
		return companyTrade;
	}

	public void setCompanyTrade(Company_trade companyTrade) {
		this.companyTrade = companyTrade;
	}

	public String getCompanyPic() {
		return companyPic;
	}

	public void setCompanyPic(String companyPic) {
		this.companyPic = companyPic;
	}

}
