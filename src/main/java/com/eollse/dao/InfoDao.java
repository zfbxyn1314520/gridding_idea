package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Info;
import com.eollse.po.Info_type;

public interface InfoDao {

	public List<Info_type> getAllInfoType();

	public Integer addNewInfo(Info info);

	public List<Info_type> getInfoTypeById(Integer infoTypeId);

	public List<Info> getAllInfoByAreaId(@Param("areaId")Integer areaId);

	public Integer alterInfo(Info info);

	public Info getOneAreaIntrInfo(Info info);

}
