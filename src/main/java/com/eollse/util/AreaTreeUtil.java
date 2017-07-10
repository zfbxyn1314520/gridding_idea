package com.eollse.util;

import com.eollse.po.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eollse on 2017/7/7.
 */
public class AreaTreeUtil {

//    子节点集合
    private List<Area> childArea;
//    子节点id集合
    private List<Integer> areaIds;

    public AreaTreeUtil() {
        this.childArea = new ArrayList<Area>();
        this.areaIds = new ArrayList<Integer>();
    }

    /**
     * 获取某个父节点下面的所有子节点
     * @param menuList 遍历节点的集合
     * @param parentCode 父节点区域编码
     * @return
     */
    public List<Area> treeMenuList(List<Area> menuList, Long parentCode) {
        for (Area area : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (area.getAreaParentCode().longValue() == parentCode.longValue()) {
                //递归遍历下一级
                treeMenuList(menuList, area.getAreaCode().longValue());
                childArea.add(area);
                areaIds.add(area.getAreaId());
            }
        }
        return childArea;
    }

    public List<Area> getChildArea() {
        return childArea;
    }

    public void setChildArea(List<Area> childArea) {
        this.childArea = childArea;
    }

    public List<Integer> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Integer> areaIds) {
        this.areaIds = areaIds;
    }
}
