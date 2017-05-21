package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.CompanyBo;
import com.eollse.dao.CompanyDao;
import com.eollse.po.Company;
import com.eollse.po.Company_nature;
import com.eollse.po.Company_scale;
import com.eollse.po.Company_trade;

@Service
public class CompanyBoImpl implements CompanyBo {
	@Autowired
	private CompanyDao companyDao;

	@Override
	public Map<String, Object> getAllCompanyByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent) {
		// TODO Auto-generated method stub
		Integer x= (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Company> companys=this.companyDao.getAllCompanyByAreaId(areaIds,x,y);
		int totalRow = this.companyDao.getAllCompanyCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", companys);
		return map;
	}

	@Override
	public Integer saveCompany(Company company) {
		// TODO Auto-generated method stub
		return this.companyDao.saveCompany(company);
	}

	@Override
	public List<Company> getCompanyById(Integer companyId) {
		// TODO Auto-generated method stub
		return this.companyDao.getCompanyById(companyId);
	}

	@Override
	public Integer editCompanyById(Company company) {
		// TODO Auto-generated method stub
		return this.companyDao.editCompanyById(company);
	}

	@Override
	public Integer deleteCompany(List<Integer> companyIds) {
		// TODO Auto-generated method stub
		return this.companyDao.deleteCompany(companyIds);
	}

	@Override
	public Integer updateAuditCompany(List<Integer> companyIds) {
		// TODO Auto-generated method stub
		return this.companyDao.updateAuditCompany(companyIds);
	}

	@Override
	public List<Company> getOneRoadById(Company company) {
		// TODO Auto-generated method stub
		List<Company> companys=this.companyDao.getOneRoadById(company);
		Company_nature companyNature=this.companyDao.getCompanyNature(companys.get(0).getNatureId());
		Company_scale companyScale=this.companyDao.getCompanyScale(companys.get(0).getScaleId());
		Company_trade companyTrade=this.companyDao.getCompanyTrade(companys.get(0).getTradeId());
		companys.get(0).setCompanyNature(companyNature);
		companys.get(0).setCompanyScale(companyScale);
		companys.get(0).setCompanyTrade(companyTrade);
		return companys;
	}

	@Override
	public List<Company_nature> getAllCompanyNature() {
		// TODO Auto-generated method stub
		return this.companyDao.getAllCompanyNature();
	}

	@Override
	public List<Company_scale> getAllCompanyScale() {
		// TODO Auto-generated method stub
		return this.companyDao.getAllCompanyScale();
	}

	@Override
	public List<Company_trade> getAllCompanyTrade() {
		// TODO Auto-generated method stub
		return this.companyDao.getAllCompanyTrade();
	}

	@Override
	public Integer getAllCompanyCount(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.companyDao.getAllCompanyCount(areaIds);
	}

	@Override
	public List<String> getCompanyName(List<Integer> companyIds) {
		// TODO Auto-generated method stub
		return this.companyDao.getCompanyName(companyIds);
	}

}
