package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Area;
import com.eollse.po.Grid;

public interface GridDao {

    public List<Grid> getAllGridByAreaId(@Param("list") List<Integer> areaIds, @Param("x") Integer x, @Param("y") Integer y);

    public int getAllGridCount(List<Integer> areaIds);

    public Integer saveGrid(Grid grid);

    public List<Grid> getGridById(Integer gridId);

    public Integer editGridById(Grid grid);

    public Integer updateAuditGrid(List<Integer> gridIds);

    public List<Grid> getOneGridById(Grid grid);

    public List<Grid> getAllGrid(@Param("list") List<Integer> areaIds);

    public Integer deleteGrid(Integer gridId);

    public List<Integer> getAllGridIdsByAreaId(@Param("list") List<Integer> areaIds);

    public Area getAreaByGridId(Integer gridId);


}
