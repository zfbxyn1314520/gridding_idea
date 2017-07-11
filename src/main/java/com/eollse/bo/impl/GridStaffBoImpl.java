package com.eollse.bo.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.GridStaffBo;
import com.eollse.dao.GridDao;
import com.eollse.dao.GridStaffDao;
import com.eollse.po.Block;
import com.eollse.po.Blog;
import com.eollse.po.BlogApp;
import com.eollse.po.Grid;
import com.eollse.po.GridStaffApp;
import com.eollse.po.Grid_post;
import com.eollse.po.Grid_staff;
import com.eollse.util.HtmlConvertTextUtil;

@Service
public class GridStaffBoImpl implements GridStaffBo {
    @Autowired
    private GridStaffDao gridStaffDao;
    @Autowired
    private GridDao gridDao;

    @Override
    public List<Grid> getGridMenuById(List<Integer> areaIds) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridMenuById(areaIds);
    }

    @Override
    public List<Grid> getGridNameById(Integer areaId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridNameById(areaId);
    }

    @Override
    public Integer getGridCount(List<Integer> areaIds) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridCount(areaIds);
    }


    @Override
    public Map<String, Object> getAllGridStaffByGridId(
            Integer gridIds, Integer pageSize, Integer pageCurrent) {
        Integer x = (pageCurrent - 1) * pageSize;
        Integer y = pageCurrent * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Grid_staff> gridStaff = this.gridStaffDao.getAllGridStaffByGridId(gridIds, x, y);
        if (gridStaff.size() > 0) {
            int totalRow = this.gridStaffDao.getGridCountByGridId(gridIds);
            map.put("totalRow", totalRow);
            map.put("pageCurrent", pageCurrent);
            map.put("list", gridStaff);
            return map;
        } else {
            return null;
        }
    }

    @Override
    public List<Grid_post> getGridPostName() {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridPostName();
    }

    @Override
    public Integer saveGridStaff(Grid_staff gridStaff) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.saveGridStaff(gridStaff);
    }

    @Override
    public Integer getGridStaffCount(Integer gridId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridStaffCount(gridId);
    }

    @Override
    public List<Grid_staff> getGridStaffById(Integer gridStaffId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridStaffById(gridStaffId);
    }

    @Override
    public Integer editGridStaffById(Grid_staff gridStaff) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.editGridStaffById(gridStaff);
    }

    @Override
    public List<Block> getBlockByGridId(Integer gridId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getBlockByGridId(gridId);
    }

    @Override
    public List<String> getGridStaffNameById(Integer gridStaffId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridStaffNameById(gridStaffId);
    }

    @Override
    public Integer deleteGridStaff(Integer gridStaffId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.deleteGridStaff(gridStaffId);
    }

    @Override
    public Integer updateAuditGridStaff(List<Integer> gridStaffIds) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.updateAuditGridStaff(gridStaffIds);
    }

    @Override
    public List<Grid_staff> getOneGridStaffById(Grid_staff gridStaff) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getOneGridStaffById(gridStaff);
    }

    @Override
    public Grid_post getGridPostById(Integer gridPostId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridPostById(gridPostId);
    }

    @Override
    public Grid getGridById(Integer gridId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridById(gridId);
    }

    @Override
    public Integer getGridIdByGridStaffId(Integer gridStaffId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridIdByGridStaffId(gridStaffId);
    }

    @Override
    public List<Grid_staff> getAllGridStaff(Integer gridId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getAllGridStaff(gridId);
    }

    @Override
    public List<Grid_staff> getGridStaffCountById(Integer gridId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getGridStaffCountById(gridId);
    }

    @Override
    public Integer editGridStaffGrid(Integer gridId, Integer newSGId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.editGridStaffGrid(gridId, newSGId);
    }

    @Override
    public Map<String, Object> getAllGridStaffByAreaId(List<Integer> areaIds,
                                                       Integer pageSize, Integer pageCurrent) {
        // TODO Auto-generated method stub
        Integer x = (pageCurrent - 1) * pageSize;
        Integer y = pageCurrent * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Integer> gridIds = this.gridDao.getAllGridIdsByAreaId(areaIds);
        if (gridIds.size() > 0) {
            List<Grid_staff> gridStaff = this.gridStaffDao.getAllGridStaffByAreaId(gridIds, x, y);
            if (gridStaff != null) {
                int totalRow = this.gridStaffDao.getGridCount(areaIds);
                map.put("totalRow", totalRow);
                map.put("pageCurrent", pageCurrent);
                map.put("list", gridStaff);
                return map;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public List<Grid> getAllGridByAreaId(List<Integer> areaIds) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getAllGridByAreaId(areaIds);
    }


    @Override
    public void alterAppUserStatusOfStaff(Integer appUser, Integer gridStaffId) {
        // TODO Auto-generated method stub
        this.gridStaffDao.alterAppUserStatusOfStaff(appUser, gridStaffId);
    }

    @Override
    public List<Integer> getAllGridStaffIdByAreaId(List<Integer> gridIds) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getAllGridStaffIdByAreaId(gridIds);
    }

    @Override
    public List<GridStaffApp> getAllGridStaffOfGrid(Integer gridStaffId) {
        // TODO Auto-generated method stub
        return this.gridStaffDao.getAllGridStaffOfGrid(gridStaffId);
    }

    @Override
    public Map<String, Object> getAllGridStaffById(List<Integer> gridIds,
                                                   String field, Integer pageSize, Integer pageCurrent) {
        Integer x = (pageCurrent - 1) * pageSize;
        Integer y = pageCurrent * pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        List<GridStaffApp> lists = this.gridStaffDao.getAllGridStaffById(gridIds, field, x, y);
        Integer totalRow = this.gridStaffDao.getAllGridStaffByIdCount(gridIds, field);
        map.put("totalRow", totalRow);
        map.put("pageCurrent", pageCurrent);
        map.put("list", lists);
        return map;
    }

    @Override
    public List<Integer> getAllGridStaffByAreaIds(List<Integer> areaIds) {
        return this.gridStaffDao.getAllGridStaffByAreaIds(areaIds);
    }


}
