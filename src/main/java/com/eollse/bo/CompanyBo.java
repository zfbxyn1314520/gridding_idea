package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Company;
import com.eollse.po.Company_nature;
import com.eollse.po.Company_scale;
import com.eollse.po.Company_trade;



public interface CompanyBo {

	public Map<String, Object> getAllCompanyByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent);

	public Integer saveCompany(Company company);

	public List<Company> getCompanyById(Integer companyId);

	public Integer editCompanyById(Company company);

	public Integer deleteCompany(List<Integer> companyIds);

	public Integer updateAuditCompany(List<Integer> companyIds);

	public List<Company> getOneRoadById(Company company);

	public List<Company_nature> getAllCompanyNature();

	public List<Company_scale> getAllCompanyScale();

	public List<Company_trade> getAllCompanyTrade();

	public Integer getAllCompanyCount(List<Integer> areaIds);

	public List<String> getCompanyName(List<Integer> companyIds);

}
