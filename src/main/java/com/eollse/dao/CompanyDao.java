package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Company;
import com.eollse.po.Company_nature;
import com.eollse.po.Company_scale;
import com.eollse.po.Company_trade;

public interface CompanyDao {

	public List<Company> getAllCompanyByAreaId(@Param("list")List<Integer> areaIds, @Param("x")Integer x,
			@Param("y")Integer y);

	public int getAllCompanyCount(List<Integer> areaIds);

	public Integer saveCompany(Company company);

	public List<Company> getCompanyById(Integer companyId);

	public Integer editCompanyById(Company company);

	public Integer deleteCompany(List<Integer> companyIds);

	public Integer updateAuditCompany(List<Integer> companyIds);

	public List<Company> getOneRoadById(Company company);

	public Company_nature getCompanyNature(@Param("natureId")Integer natureId);

	public Company_scale getCompanyScale(@Param("scaleId")Integer scaleId);

	public Company_trade getCompanyTrade(@Param("tradeId")Integer tradeId);

	public List<Company_nature> getAllCompanyNature();

	public List<Company_scale> getAllCompanyScale();

	public List<Company_trade> getAllCompanyTrade();

	public List<String> getCompanyName(List<Integer> companyIds);

}
