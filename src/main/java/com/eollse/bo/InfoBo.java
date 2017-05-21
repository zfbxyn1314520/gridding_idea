package com.eollse.bo;

import java.util.List;

import com.eollse.po.Info;
import com.eollse.po.Info_type;

public interface InfoBo {

	public List<Info_type> getAllInfoType();

	public Integer addNewInfo(Info info);

	public List<Info_type> getInfoTypeById(Integer infoTypeId);

	public List<Info> getAllInfoByAreaId(Integer areaId);

	public Integer alterInfo(Info info);

	public Info getOneAreaIntrInfo(Info info);

}
