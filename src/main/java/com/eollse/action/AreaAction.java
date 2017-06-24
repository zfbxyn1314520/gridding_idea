package com.eollse.action;

/**
 * author 李宁财
 * 区域控制器
 */
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
		User user=(User)session.getAttribute("user"); 
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String, Object> map=this.areaBo.getAllAreaByAreaId(areaIds,pageSize,pageCurrent);
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
	
	

}
