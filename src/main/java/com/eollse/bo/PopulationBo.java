package com.eollse.bo;

import java.util.List;
import java.util.Map;

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

public interface PopulationBo {

	public Integer editPopGridStaff(Integer gridStaffIds, Integer newPgId);

	public Integer editPopGrid(Integer gridId, Integer newPGId);

	public List<Population> getPopByGridId(Integer gridId);

	public List<Population> getPopByGridStaffId(Integer gridStaffIds);

	public Map<String, Object> getAllPopByAreaId(List<Integer> areaIds,Integer pageSize, Integer pageCurrent);

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

	public Integer deletePopInfoForError(Integer popId);

	public Integer deleteFlowInfoForError(Integer popId);

	public List<Population> getOnePopInfo(Integer popId);

	public List<Flow> getOnePopFlowInfo(Integer popId);

	public List<Popattr> getOnePopAttrsInfo(Integer popId);

	public Integer editOnePopInfoById(Population pop);

	public Integer editOnePopFlowInfo(Flow flow);

	public Integer deleteOnePopAttrInfo(Integer popId);

	public Integer alterOnePopAuditStatusInfo(List<Integer> delPopIds);

	public List<Population> getOnePopDetailInfo(Integer popId);

}
