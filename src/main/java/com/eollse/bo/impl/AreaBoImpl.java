package com.eollse.bo.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.AreaBo;
import com.eollse.dao.AreaDao;
import com.eollse.po.Area;
import com.eollse.po.Store;

@Service
public class AreaBoImpl implements AreaBo {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaByAreaId(Integer areaId) {
        // TODO Auto-generated method stub
        return this.areaDao.getAreaByAreaId(areaId);
    }

    @Override
    public List<Area> getSubAreaByAreaId(Long areaCode) {
        // TODO Auto-generated method stub
        return this.areaDao.getSubAreaByAreaId(areaCode);
    }

    @Override
    public Area getAreaByAreaCode(Long areaCode) {
        // TODO Auto-generated method stub
        return this.areaDao.getAreaByAreaCode(areaCode);
    }

    @Override
    public List<Area> getAreaMenuById(List<Integer> areaIds) {
        // TODO Auto-generated method stub
        return this.areaDao.getAreaMenuById(areaIds);
    }

    @Override
    public List<Area> getAreaIdByAreaName(String areaName) {
        // TODO Auto-generated method stub
        return this.areaDao.getAreaIdByAreaName(areaName);
    }

    @Override
    public Map<String, Object> getAllAreaByAreaId(List<Integer> areaIds, Integer pageSize, Integer pageCurrent) {
        // 计算起始数值
        Integer x = (pageCurrent - 1) * pageSize;
        Integer y = pageCurrent * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Store> stores = this.areaDao.getAllAreaByAreaId(areaIds, x, y);
        Integer totalRow = this.areaDao.getAllAreasCount(areaIds);
        map.put("totalRow", totalRow);
        map.put("pageCurrent", pageCurrent);
        map.put("list", stores);
        return map;
    }

    @Override
    public List<Area> getAllAreaMenu() {
        // TODO Auto-generated method stub
        return this.areaDao.getAllAreaMenu();
    }

    @Override
    public List<Area> getAllAreaByLevel(Integer areaId) {
        return this.areaDao.getAllAreaByLevel(areaId);
    }

    @Override
    public Integer addNewArea(Area area) {
        return this.areaDao.addNewArea(area);
    }


}
