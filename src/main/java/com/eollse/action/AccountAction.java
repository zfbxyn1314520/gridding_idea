package com.eollse.action;

/**
 * author 刘春晓
 * content 户口控制器
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AccountBo;
import com.eollse.bo.BlockBo;
import com.eollse.bo.CourtBo;
import com.eollse.bo.HouseBo;
import com.eollse.po.Account;
import com.eollse.po.Block;
import com.eollse.po.Court;
import com.eollse.po.House;
import com.eollse.po.Unit;
import com.eollse.po.User;

@Controller
@RequestMapping(value="/account")
public class AccountAction extends CommonAction {
	@Autowired
	private AccountBo accountBo;
	@Autowired
	private CourtBo courtBo;
	@Autowired
	private BlockBo blockBo;
	@Autowired
	private HouseBo houseBo;
	
	/**
	 * 
	 * 获取所有用户区域下的户口信息
	 * @param session 用户session的areaId
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页号
	 * @return
	 */
	@RequestMapping(value="/getAllAccountByHouseId")
	@ResponseBody
	public String getAllAccountByHouseId(HttpSession session,Integer pageSize,Integer pageCurrent){
		User user = (User) session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if (user != null) {
			Map<String, Object> map = this.courtBo.getAllCourtByAreaId(areaIds, pageSize, pageCurrent);
			List<Court> courts = (List<Court>) map.get("list");
			List<Integer> courtIds = new ArrayList<Integer>();
			for (Court court : courts) {
				courtIds.add(court.getCourtId());
			}
			Map<String, Object> map1 = this.blockBo.getAllBlockByCourtId(courtIds, pageSize, pageCurrent);
			List<Block> blocks = (List<Block>) map1.get("list");
			List<Integer> blockIds = new ArrayList<Integer>();
			for (Block block : blocks) {
				blockIds.add(block.getBlockId());
			}
			List<Unit> units = this.houseBo.getAllUnitByBlockId(blockIds);
			List<Integer> unitIds = new ArrayList<Integer>();
			for (Unit unit : units) {
				unitIds.add(unit.getUnitId());
			}
			Map<String, Object> map2 = this.houseBo.getAllHouseByUnitId(
					unitIds, pageSize, pageCurrent);
			List<House> houses=(List<House>)map2.get("list");
			List<Integer> houseIds=new ArrayList<Integer>();
			for(House house:houses){
				houseIds.add(house.getHouseId());
			}
			Map<String,Object> map3=this.accountBo.getAllAccountByHouseId(houseIds,pageSize,pageCurrent);
			String content=this.createPageJSONString(map3);
			return content;
		}else{
			return null;
		}
	}
	

	/**
	 * 获取修改页面所需数据
	 * @param upAid 户口Id
	 * @return 户口实体类
	 */
	@RequestMapping(value="/getAccountById")
	@ResponseBody
	public Account getAccountById(String upAid){
		Integer accountId=Integer.parseInt(upAid);
		List<Account> account=this.accountBo.getAccountById(accountId);
		return account.get(0);
	}
	
	/**
	 * 审核户口
	 * @param audAid 户口Id组成的字符串
	 * @return string “1”==>表示审核成功 “0”==>表示审核失败
	 */
	@RequestMapping(value="/updateAuditAccount")
	@ResponseBody
	public String updateAuditAccount(String audAid){
		String[] str=audAid.split(",");
		List<Integer> accountIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			accountIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.accountBo.updateAuditAccount(accountIds);
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 查看方法
	 * @param accountId 户口Id
	 * @return 户口实体类
	 */
	@RequestMapping(value="/getAccountByAccountId")
	@ResponseBody
	public Account getAccountByAccountId(Integer accountId){
		Account account=this.accountBo.getAccountByAccountId(accountId);
		System.out.println(account);
		return account;
	}
	
	/**
	 * 获取户口下的人口信息
	 * @param accountId 户口Id
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAccountPopByAccountId")
	@ResponseBody
	public String getAccountPopByAccountId(Integer accountId,Integer pageSize,Integer pageCurrent){
		Map<String, Object> map = this.accountBo.getAccountPopByAccountId(accountId, pageSize, pageCurrent);
		String content=this.createPageJSONString(map);
		return content;
	}
	
}
