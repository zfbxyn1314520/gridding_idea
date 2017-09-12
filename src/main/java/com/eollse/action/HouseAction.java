package com.eollse.action;

/**
 * author 刘春晓
 * content 房屋控制器
 */

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.eollse.util.AreaTreeUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AreaBo;
import com.eollse.bo.BlockBo;
import com.eollse.bo.CourtBo;
import com.eollse.bo.HouseBo;
import com.eollse.po.Account;
import com.eollse.po.Area;
import com.eollse.po.Block;
import com.eollse.po.Court;
import com.eollse.po.House;
import com.eollse.po.House_status;
import com.eollse.po.House_type;
import com.eollse.po.Unit;
import com.eollse.po.User;

@Controller
@RequestMapping(value = "/house")
public class HouseAction extends CommonAction {
    @Autowired
    private HouseBo houseBo;
    @Autowired
    private AreaBo areaBo;
    @Autowired
    private CourtBo courtBo;
    @Autowired
    private BlockBo blockBo;


    /**
     * 获取所有房屋
     *
     * @param session     用户登录session
     * @param pageSize    分页大小
     * @param pageCurrent 当前页码
     * @return
     */
    @RequestMapping(value = "/getAllHouseByUnitId")
    @ResponseBody
    public String getAllHouseByUnitId(HttpSession session, Integer pageSize,
                                      Integer pageCurrent) {
        User user = (User) session.getAttribute("user");
        List<Integer> areaIds = (List<Integer>) session.getAttribute("areaIds");
        if (user != null) {
            List<Integer> courtIds = this.courtBo.getAllCourtId(areaIds);
            List<Integer> blockIds = this.blockBo.getAllBlockId(courtIds);
            if (blockIds.size() > 0) {
                List<Unit> units = this.houseBo.getAllUnitByBlockId(blockIds);
                List<Integer> unitIds = new ArrayList<Integer>();
                for (Unit unit : units) {
                    unitIds.add(unit.getUnitId());
                }
                Map<String, Object> map2 = this.houseBo.getAllHouseByUnitId(
                        unitIds, pageSize, pageCurrent);
                String content = this.createPageJSONString(map2);
                return content;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 添加房屋、户口保存方法
     *
     * @param house   房屋实体
     * @param account 户口实体
     * @param session
     * @param request 请求
     * @return string “1”==>表示保存成功 “0”==>表示保存失败
     */
    @RequestMapping(value = "/saveHouse")
    @ResponseBody
    public String saveHouse(House house, Account account, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        Map params = new HashMap();
        if (user != null) {
            house.setEditHouseName(user.getUserName());
            house.setEditHouseDate(new Date());
            house.setHouseHolder(account.getAccountHolder());

            AreaTreeUtil areaTreeUtil = new AreaTreeUtil();
            List<Area> areas = this.areaBo.getAllAreaByLevel(user.getAreaId());
            areaTreeUtil.treeMenuList(areas, user.getArea().getAreaCode());
            areaTreeUtil.getAreaIds().add(user.getAreaId());
            List<Court> courts = this.courtBo.getAllCourtsByAreaId(areaTreeUtil.getAreaIds());
            List<Integer> courtIds = new ArrayList<Integer>();
            for (Court court : courts) {
                courtIds.add(court.getCourtId());
            }
            List<Block> blocks = this.blockBo.getBlockByCourtIds(courtIds);
            List<Integer> blockIds = new ArrayList<Integer>();
            for (Block block : blocks) {
                blockIds.add(block.getBlockId());
            }
            List<Unit> units = this.houseBo.getAllUnitByBlockId(blockIds);
            List<Integer> unitIds = new ArrayList<Integer>();
            for (Unit unit : units) {
                unitIds.add(unit.getUnitId());
            }
            Integer result = this.houseBo.saveHouse(house);
            if (result > 0) {
                Integer houseId = this.houseBo.getMaxHouseId();
                account.setHouseId(houseId);
                account.setEditAccountName(user.getUserName());
                account.setEditAccountDate(new Date());
                Integer resultA = this.houseBo.saveAccount(account);
                if (resultA > 0) {
                    logger.info("成功添加房屋" + house.getHouseNum() + "！" + "成功添加户口" + account.getAccountHolder() + "!");
                    params.put("statusCode", 200);
                    params.put("message", "添加成功!");
                } else {
                    params.put("statusCode", 400);
                    params.put("message", "添加失败!");
                }
            } else {
                params.put("statusCode", 400);
                params.put("message", "添加失败!");
            }
        } else {
            params.put("statusCode", 400);
            params.put("message", "添加失败!");
        }
        return JSONObject.fromObject(params).toString();
    }

    /**
     * 获取修改弹窗所需数据
     *
     * @param upHid 房屋Id
     * @return 户口实体
     */
    @RequestMapping(value = "/getHouseById")
    @ResponseBody
    public Account getHouseById(String upHid) {
        Integer houseId = Integer.parseInt(upHid);
        List<Account> account = this.houseBo.getHouseById(houseId);
        return account.get(0);
    }

    /**
     * 修改方法
     *
     * @param house   房屋实体
     * @param account 户口实体
     * @param session
     * @param request
     * @return string “1”==>表示修改成功 “0”==>表示修改失败
     */
    @RequestMapping(value = "/editHouseById")
    @ResponseBody
    public String editHouseById(House house, Account account, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        Map params = new HashMap();
        house.setEditHouseName(user.getUserName());
        house.setEditHouseDate(new Date());
        Integer result = this.houseBo.editHouseById(house);
        if (result > 0) {
            account.setHouseId(house.getHouseId());
            account.setEditAccountName(user.getUserName());
            account.setEditAccountDate(new Date());
            Integer resultA = this.houseBo.editAccountById(account);
            if (resultA > 0) {
                params.put("statusCode", 200);
                params.put("message", "修改成功!");
                logger.info("成功修改房屋" + house.getHouseNum() + "！" + "成功修改该房屋户主信息！");
            } else {
                params.put("statusCode", 400);
                params.put("message", "修改失败!");
            }
        } else {
            params.put("statusCode", 200);
            params.put("message", "修改失败!");
        }
        return JSONObject.fromObject(params).toString();
    }

    /**
     * 查看方法
     *
     * @param house 房屋实体
     * @return 房屋实体
     */
    @RequestMapping(value = "/getOneHouseInfoById")
    @ResponseBody
    public Account getOneHouseById(House house) {

        return this.houseBo.getOneHouseInfoById(house);
    }

    /**
     * 审核方法
     *
     * @param audHid 房屋Id组成字符串
     * @return string “1”==>表示修改成功 “0”==>表示修改失败
     */
    @RequestMapping(value = "/updateAuditHouse")
    @ResponseBody
    public String updateAuditHouse(String audHid) {
        String[] str = audHid.split(",");
        List<Integer> houseIds = new ArrayList<Integer>();
        for (int i = 0; i < str.length; i++) {
            houseIds.add(i, Integer.parseInt(str[i]));
        }
        Integer result = this.houseBo.updateAuditHouse(houseIds);
        return result > 0 ? "1" : "0";
    }

    /**
     * 获取所有楼栋
     *
     * @param courtId 小区Id
     * @return 楼栋集合
     */
    @RequestMapping(value = "/getBlockName")
    @ResponseBody
    public List<Block> getBlockName(String courtId) {
        Integer courtIds = Integer.parseInt(courtId);
        List<Block> blocks = this.blockBo.getBlockName(courtIds);
        return blocks;
    }

    /**
     * 获取所有单元
     *
     * @param blockId 楼栋Id
     * @return 单元集合
     */
    @RequestMapping(value = "/getUnitName")
    @ResponseBody
    public List<Unit> getUnitName(String blockId) {
        Integer blockIds = Integer.parseInt(blockId);
        List<Unit> units = this.houseBo.getUnitName(blockIds);
        return units;
    }


    /**
     * 获取所有房屋状态
     *
     * @return 房屋状态集合
     */
    @RequestMapping(value = "/getStatusName")
    @ResponseBody
    public List<House_status> getStatusName() {
        List<House_status> houseStatus = this.houseBo.getStatusName();
        return houseStatus;
    }

    /**
     * 获取所有户型
     *
     * @return 户型集合
     */
    @RequestMapping(value = "/getTypeName")
    @ResponseBody
    public List<House_type> getTypeName() {
        List<House_type> houseType = this.houseBo.getTypeName();
        return houseType;
    }

    /**
     * 获取楼栋下所有房屋数
     *
     * @param blockId 楼栋Id
     * @return
     */
    @RequestMapping(value = "/getHouseCountById")
    @ResponseBody
    public String getHouseCountById(Integer blockId) {
        List<Integer> unitIds = this.blockBo.getUnitIdByBlockId(blockId);
        if (unitIds.size() > 0) {
            List<House> houses = this.houseBo.getHouseCountById(unitIds);
            return "{\"statusCode\":200,\"message\":" + houses.size() + "}";
        } else {
            return "{\"statusCode\":200,\"message\":" + 0 + "}";
        }
    }

    /**
     * 获取楼栋下房屋信息
     *
     * @param blockId     楼栋Id
     * @param pageSize    分页大小
     * @param pageCurrent 当前页码
     * @return 房屋集合
     */
    @RequestMapping(value = "/getHouseByBlockId")
    @ResponseBody
    public List<House> getHouseByBlockId(Integer blockId, Integer pageSize,
                                         Integer pageCurrent) {
        List<Integer> unitIds = this.blockBo.getUnitIdByBlockId(blockId);
        if (unitIds.size() > 0) {
            List<House> houses = this.houseBo.getHouseCountById(unitIds);
            return houses.size() > 0 ? houses : null;
        } else {
            return null;
        }
    }


    /**
     * 获取楼栋单元下所有户口
     *
     * @param unitId 单元Id
     * @return 户口集合
     */
    @RequestMapping(value = "/getAllAccountByUnitId")
    @ResponseBody
    public List<Account> getAllAccountByUnitId(Integer unitId) {
        return this.houseBo.getAllAccountByUnitId(unitId);
    }

}
