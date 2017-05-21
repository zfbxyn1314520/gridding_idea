package com.eollse.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.InfoBo;
import com.eollse.dao.InfoDao;
import com.eollse.po.Info;
import com.eollse.po.Info_type;

@Service
public class InfoBoImpl implements InfoBo {
	
	@Autowired
	private InfoDao infoDao;

	@Override
	public List<Info_type> getAllInfoType() {
		// TODO Auto-generated method stub
		return this.infoDao.getAllInfoType();
	}

	@Override
	public Integer addNewInfo(Info info) {
		// TODO Auto-generated method stub
		return this.infoDao.addNewInfo(info);
	}

	@Override
	public List<Info_type> getInfoTypeById(Integer infoTypeId) {
		// TODO Auto-generated method stub
		return this.infoDao.getInfoTypeById(infoTypeId);
	}

	@Override
	public List<Info> getAllInfoByAreaId(Integer areaId) {
		// TODO Auto-generated method stub
		return this.infoDao.getAllInfoByAreaId(areaId);
	}

	@Override
	public Integer alterInfo(Info info) {
		// TODO Auto-generated method stub
		return this.infoDao.alterInfo(info);
	}

	@Override
	public Info getOneAreaIntrInfo(Info info) {
		// TODO Auto-generated method stub
		return this.infoDao.getOneAreaIntrInfo(info);
	}

}
