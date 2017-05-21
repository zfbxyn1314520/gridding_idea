package com.eollse.app;
/**
 * author 李宁财
 * content App用户控制器
 */
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.action.CommonAction;
import com.eollse.bo.AreaBo;
import com.eollse.bo.InfoBo;
import com.eollse.po.Info;
import com.eollse.po.User;

@Controller
@RequestMapping("/app/info")
public class InfoAppAction extends CommonAction {
	
	@Autowired
	private InfoBo infoBo;
	@Autowired
	private AreaBo areaBo;
	
	/**
	 * 获取网格所在社区介绍信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getOneAreaIntrInfo", produces = {"text/html;charset=utf-8"})
	@ResponseBody
	public String getOneAreaIntrInfo(HttpSession session){
		User user=(User)session.getAttribute("user");
		Info info =new Info();
		info.setAreaId(user.getAreaId());
		info.setInfoTypeId(1);
		Info list=this.infoBo.getOneAreaIntrInfo(info);
		if(list!=null){
			return list.getInfoContent();
		}else{
			return "<div style='width:500px;height:200px;position:absolute;left:50%;top:50%;margin:-100px 0 0 -150px '><h1>该区域暂无介绍信息！</h1></div>";
		}
	}

}
