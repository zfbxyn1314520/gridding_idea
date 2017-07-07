package com.eollse.util;

import com.eollse.po.Area;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eollse on 2017/7/7.
 */
public class TreeUtil {

    static  List<Area> childMenu=new ArrayList<Area>();
//    private static List<Integer> areaIds = new ArrayList<Integer>();

//    public static JSONArray treeMenuList(JSONArray menuList, Long areaParentCode) {
//        JSONArray childMenu = new JSONArray();
//        System.out.println(123);
//        for (Object object : menuList) {
//            System.out.println(111);
//            JSONObject jsonMenu = JSONObject.fromObject(object);
//            Long code = jsonMenu.getLong("areaCode");
//            Long parentCode = jsonMenu.getLong("areaParentCode");
//            if (parentCode == areaParentCode) {
//                areaIds.add(jsonMenu.getInt("areaId"));
//
//                treeMenuList(menuList, code);
//                System.out.println("123321");
////                this.getAreaIds();
////                jsonMenu.put("childNode", c_node);
////                childMenu.add(jsonMenu);
//            }
//        }
//        System.out.println("size1===" + areaIds.size());
//        return childMenu;
//    }

    /**
     * 根据传入的areaCode获取该区域所有子节点
     *
     * @param areaCode 区域行政代码
     * @return
     */
//    public Area getAllAreaIdById(Long areaCode) {
//        Area area = this.areaBo.getAreaByAreaCode(areaCode);
//        List<Area> childAreaNodes = this.areaBo.getSubAreaByAreaId(areaCode);
//        // 遍历子节点
//        for (Area child : childAreaNodes) {
//            // 递归
//            Area a = getAllAreaIdById(child.getAreaCode());
//            a.getNodes().add(a);
//        }
//        areaIds.add(area.getAreaId());
//        return area;
//    }


    /**
     * 获取某个父节点下面的所有子节点
     * //     * @param menuList
     * //     * @param pid
     *
     * @return
     */
    public static List<Area> treeMenuList( List<Area> menuList, Long pid){
        for(Area mu: menuList){
            //遍历出父id等于参数的id，add进子节点集合
            System.out.println(mu.getAreaParentCode());
            if(mu.getAreaParentCode()==pid){
                //递归遍历下一级
                treeMenuList(menuList,Long.valueOf(mu.getAreaCode()));
                childMenu.add(mu);
            }
        }
        return childMenu;
    }
//    public List<Integer> getAreaIds() {
//
//        return areaIds;
//    }
//
//    public void setAreaIds(List<Integer> areaIds) {
//        this.areaIds = areaIds;
//    }
}
