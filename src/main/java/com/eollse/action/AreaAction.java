package com.eollse.action;

/**
 * author 李宁财
 * 区域控制器
 */
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.eollse.util.AreaTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AreaBo;
import com.eollse.po.Area;
import com.eollse.po.User;

@Controller
@RequestMapping("/area")
public class AreaAction extends CommonAction{
	
	@Autowired
	private AreaBo areaBo;
	
	@RequestMapping("/getAllAreaByAreaId")
	@ResponseBody
	public String getAllAreaByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		AreaTreeUtil areaTreeUtil;
		List<Area> areas;
		User s_user = (User) session.getAttribute("user");
		if(s_user!=null){
			areaTreeUtil = new AreaTreeUtil();
			areas = this.areaBo.getAllAreaByLevel(s_user.getAreaId());
			areaTreeUtil.treeMenuList(areas, s_user.getArea().getAreaCode());
			areaTreeUtil.getAreaIds().add(s_user.getAreaId());
			Map<String, Object> map=this.areaBo.getAllAreaByAreaId(areaTreeUtil.getAreaIds(),pageSize,pageCurrent);
			String content=this.createPageJSONString(map);
			return content;
		}else{
			return null;
		}
	}
	
	@RequestMapping("/getAllAreaMenu")
	@ResponseBody
	public List<Area> getAllAreaMenu(){
		List<Area> areas=this.areaBo.getAllAreaMenu();
		for(int i=0;i<areas.size();i++){
			if(areas.get(i).getAreaLevel()==0){
				areas.remove(i);
			}
		}
		return areas;
	}

	@RequestMapping("addNewArea")
	@ResponseBody
	public String addNewArea(Area area){
		Integer result = this.areaBo.addNewArea(area);
		if (result >0){
			this.logger.info("新增区域"+area.getAreaName()+"成功");
			return "1";
		}else{
			this.logger.info("新增区域"+area.getAreaName()+"失败");
			return "0";
		}
	}
	
	

}
