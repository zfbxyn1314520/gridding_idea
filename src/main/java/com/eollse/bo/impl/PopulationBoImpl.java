package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.PopulationBo;
import com.eollse.dao.PopulationDao;
import com.eollse.po.Culture_level;
import com.eollse.po.Flow;
import com.eollse.po.Flow_type;
import com.eollse.po.Holder_relation;
import com.eollse.po.Marriage_status;
import com.eollse.po.Politics;
import com.eollse.po.Pop_attr;
import com.eollse.po.Pop_nation;
import com.eollse.po.Popattr;
import com.eollse.po.Population;

@Service
public class PopulationBoImpl implements PopulationBo {
	@Autowired
	private PopulationDao popDao;

	@Override
	public Integer editPopGridStaff(Integer gridStaffIds, Integer newPgId) {
		// TODO Auto-generated method stub
		return this.popDao.editPopGridStaff(gridStaffIds, newPgId);
	}

	@Override
	public Integer editPopGrid(Integer gridId, Integer newPGId) {
		// TODO Auto-generated method stub
		return this.popDao.editPopGrid(gridId, newPGId);
	}

	@Override
	public List<Population> getPopByGridId(Integer gridId) {
		// TODO Auto-generated method stub
		return this.popDao.getPopByGridId(gridId);
	}

	@Override
	public List<Population> getPopByGridStaffId(Integer gridStaffIds) {
		// TODO Auto-generated method stub
		return this.popDao.getPopByGridStaffId(gridStaffIds);
	}

	@Override
	public Map<String, Object> getAllPopByAreaId(List<Integer> areaIds,
			Integer pageSize, Integer pageCurrent) {
		// 计算起始数值
		Integer x = (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent * pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Population> pops = this.popDao.getAllPopByAreaId(areaIds, x, y);
		Integer totalRow = this.popDao.getAllPopCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", pops);
		return map;
	}

	@Override
	public List<Holder_relation> getAllHolderRelation() {
		// TODO Auto-generated method stub
		return this.popDao.getAllHolderRelation();
	}

	@Override
	public List<Marriage_status> getAllMarriageStatus() {
		// TODO Auto-generated method stub
		return this.popDao.getAllMarriageStatus();
	}

	@Override
	public List<Pop_nation> getAllPopNation() {
		// TODO Auto-generated method stub
		return this.popDao.getAllPopNation();
	}

	@Override
	public List<Politics> getAllPolitics() {
		// TODO Auto-generated method stub
		return this.popDao.getAllPolitics();
	}

	@Override
	public List<Culture_level> getAllCultureLevel() {
		// TODO Auto-generated method stub
		return this.popDao.getAllCultureLevel();
	}

	@Override
	public List<Flow_type> getAllFlowType() {
		// TODO Auto-generated method stub
		return this.popDao.getAllFlowType();
	}

	@Override
	public List<Popattr> getAllPopAttr() {
		// TODO Auto-generated method stub
		return this.popDao.getAllPopAttr();
	}

	@Override
	public Integer addOnePopInfo(Population pop) {
		// TODO Auto-generated method stub
		return this.popDao.addOnePopInfo(pop);
	}

	@Override
	public Integer getNewPopPopId() {
		// TODO Auto-generated method stub
		return this.popDao.getNewPopPopId();
	}

	@Override
	public Integer addOnePopFlowInfo(Flow flow) {
		// TODO Auto-generated method stub
		return this.popDao.addOnePopFlowInfo(flow);
	}

	@Override
	public Integer addOnePopAttrInfo(List<Pop_attr> pop_attrs) {
		// TODO Auto-generated method stub
		return this.popDao.addOnePopAttrInfo(pop_attrs);
	}

	@Override
	public Integer deletePopInfoForError(Integer popId) {
		// TODO Auto-generated method stub
		return this.popDao.deletePopInfoForError(popId);
	}

	@Override
	public Integer deleteFlowInfoForError(Integer popId) {
		// TODO Auto-generated method stub
		return this.popDao.deleteFlowInfoForError(popId);
	}

	@Override
	public List<Population> getOnePopInfo(Integer popId) {
		// TODO Auto-generated method stub
		return this.popDao.getOnePopInfo(popId);
	}

	@Override
	public List<Flow> getOnePopFlowInfo(Integer popId) {
		// TODO Auto-generated method stub
		return this.popDao.getOnePopFlowInfo(popId);
	}

	@Override
	public List<Popattr> getOnePopAttrsInfo(Integer popId) {
		// TODO Auto-generated method stub
		return this.popDao.getOnePopAttrsInfo(popId);
	}

	@Override
	public Integer editOnePopInfoById(Population pop) {
		// TODO Auto-generated method stub
		return this.popDao.editOnePopInfoById(pop);
	}

	@Override
	public Integer editOnePopFlowInfo(Flow flow) {
		// TODO Auto-generated method stub
		return this.popDao.editOnePopFlowInfo(flow);
	}

	@Override
	public Integer deleteOnePopAttrInfo(Integer popId) {
		// TODO Auto-generated method stub
		return this.popDao.deleteOnePopAttrInfo(popId);
	}

	@Override
	public Integer alterOnePopAuditStatusInfo(List<Integer> delPopIds) {
		// TODO Auto-generated method stub
		return this.popDao.alterOnePopAuditStatusInfo(delPopIds);
	}

	@Override
	public List<Population> getOnePopDetailInfo(Integer popId) {
		// TODO Auto-generated method stub
		return this.popDao.getOnePopDetailInfo(popId);
	}
	
	
	

}
