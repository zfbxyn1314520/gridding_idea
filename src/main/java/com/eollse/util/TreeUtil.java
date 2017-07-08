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

//    public JSONArray treeMenuList(JSONArray menuList, Long parentCode) {
//        JSONArray childMenu = new JSONArray();
//        for (Object object : menuList) {
//            JSONObject jsonMenu = JSONObject.fromObject(object);
//            Long areaCode = jsonMenu.getLong("areaCode");
//            Long areaParentCode = jsonMenu.getLong("areaParentCode");
//            if (parentCode.longValue() == areaParentCode.longValue()) {
//                JSONArray c_node = treeMenuList(menuList, areaCode);
//                jsonMenu.put("childNode", c_node);
//                childMenu.add(jsonMenu);
//            }
//        }
//        return childMenu;
//    }


    public List<Integer> treeMenuList(JSONArray menuList, Long parentCode) {
//        JSONArray childMenu = new JSONArray();
        List<Integer> areaIds = new ArrayList<Integer>();
        for (Object object : menuList) {
            JSONObject jsonMenu = JSONObject.fromObject(object);
            Long areaCode = jsonMenu.getLong("areaCode");
            Long areaParentCode = jsonMenu.getLong("areaParentCode");
            if (parentCode.longValue() == areaParentCode.longValue()) {
                List<Integer> c_node = treeMenuList(menuList, areaCode);
                jsonMenu.put("childNode", c_node);
                areaIds.add(jsonMenu.getInt("areaId"));
            }
        }
        return areaIds;
    }


//子节点
//  static List<Area> childMenu=new ArrayList<Area>();
//
//    /**
//     * 获取某个父节点下面的所有子节点
//     * @param menuList
//     * @param pid
//     * @return
//     */
//    public static List<Area> treeMenuList(List<Area> menuList, Long parentCode){
//        for(Area area: menuList){
//            //遍历出父id等于参数的id，add进子节点集合
//            if(area.getAreaParentCode().longValue()==parentCode.longValue()){
//                //递归遍历下一级
//                treeMenuList(menuList,area.getAreaCode().longValue());
//                childMenu.add(area);
//            }
//        }
//        return childMenu;
//    }



}
