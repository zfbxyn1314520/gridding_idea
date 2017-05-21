package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

public interface PopulationDao {

	public Integer editPopGridStaff(@Param("oldGridStaffId")Integer gridStaffIds, @Param("newGridStaffId")Integer newPgId);

	public Integer editPopGrid(@Param("oldId")Integer gridId, @Param("newId")Integer newPGId);

	public List<Population> getPopByGridId(Integer gridId);

	public List<Population> getPopByGridStaffId(@Param("gridStaffId")Integer gridStaffIds);

	public List<Population> getAllPopByAreaId(@Param("list")List<Integer> areaIds, @Param("x")Integer x,@Param("y")Integer y);

	public Integer getAllPopCount(@Param("list")List<Integer> areaIds);

	public Integer getMemberCountById(Integer accountId);

	public List<Holder_relation> getAllHolderRelation();

	public List<Marriage_status> getAllMarriageStatus();

	public List<Pop_nation> getAllPopNation();

	public List<Politics> getAllPolitics();

	public List<Culture_level> getAllCultureLevel();

	public List<Flow_type> getAllFlowType();

	public List<Popattr> getAllPopAttr();

	public Integer addOnePopInfo(Population pop);

	public Integer getNewPopPopId();

	public Integer addOnePopFlowInfo(Flow flow);

	public Integer addOnePopAttrInfo(List<Pop_attr> pop_attrs);

	public Integer deletePopInfoForError(@Param("popId")Integer popId);

	public Integer deleteFlowInfoForError(@Param("popId")Integer popId);

	public List<Population> getOnePopInfo(@Param("popId")Integer popId);

	public List<Flow> getOnePopFlowInfo(@Param("popId")Integer popId);

	public List<Popattr> getOnePopAttrsInfo(@Param("popId")Integer popId);

	public Integer editOnePopInfoById(Population pop);

	public Integer editOnePopFlowInfo(Flow flow);

	public Integer deleteOnePopAttrInfo(@Param("popId")Integer popId);

	public Integer alterOnePopAuditStatusInfo(List<Integer> delPopIds);

	public List<Population> getOnePopDetailInfo(@Param("popId")Integer popId);
	
}
