package com.eollse.action;
/**
 * author 李宁财
 * content 菜单控制器
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.MenuBo;
import com.eollse.bo.PermissionBo;
import com.eollse.bo.RolePerBo;
import com.eollse.po.Icon;
import com.eollse.po.Menu;
import com.eollse.po.Menu_per;
import com.eollse.po.Permission;
import com.eollse.po.Role;
import com.eollse.po.Role_per;
import com.eollse.po.User;

@Controller
@RequestMapping("menu")
public class MenuAction extends CommonAction{
	
	@Autowired
	private MenuBo menuBo;
	@Autowired
	private PermissionBo perBo;
	@Autowired
	private RolePerBo rolePerBo;
	
	@RequestMapping("/getMenuPerById")
	@ResponseBody
	public List<Menu> getMenuPerById(Menu menu,HttpSession session){
		User s_user = (User) session.getAttribute("user");
		List<Menu> lists=this.menuBo.getMenuPerById(menu,s_user.getRoleId());
		if(lists.size()>0){			
			List<Menu> ParentMenu=this.menuBo.getParentMenuById(lists.get(0).getParentMenuId());
			lists.add(ParentMenu.get(0));
			return lists;
		}else{
			return null;
		}
	}
	
	@RequestMapping("/getAllMenu")
	@ResponseBody
	public List<Menu> getAllMenu(){
		return this.menuBo.getAllMenu();
	}
	
	@RequestMapping("/getAllMenuByMenuId")
	@ResponseBody
	public List<Menu> getAllMenuByMenuId(){
		List<Menu> menus=this.menuBo.getAllMenu();
		for(int i=0;i<menus.size();i++){
			if(menus.get(i).getParentMenuId()!=null && menus.get(i).getMenuUri()!=null){
				menus.get(i).setPermissions(this.perBo.getMenuPer(menus.get(i))); 
			}
		}
		return menus;
	}
	
	@RequestMapping("/getAllMainMenu")
	@ResponseBody
	public List<Menu> getAllMainMenu(){
		List<Menu> menus=this.menuBo.getAllMainMenu();
		for(int i=0;i<menus.size();i++){
			if(menus.get(i).getMenuName().equals("我的主页")){
				menus.remove(i);
			}
		}
		return menus;
	}
	
	@RequestMapping("/getAllPermission")
	@ResponseBody
	public List<Permission> getAllPermission(){
		
		return this.perBo.getAllPermission();
	}
	
	@RequestMapping("/addNewMenu")
	@ResponseBody
	public String addNewMenu(Menu menu,HttpServletRequest request,HttpSession session) {
		User s_user = (User) session.getAttribute("user");
		Integer result = this.menuBo.addNewMenu(menu);
		if(menu.getParentMenuId()!=null){
			List<Menu_per> lists=menu.getPermissions().get(0).getMenuPer();
			Integer menuId=this.menuBo.getLastMenuId();
			if(lists.size()>0){
				Iterator<Menu_per> pers = lists.iterator(); 
				while(pers.hasNext()){ 
					Menu_per per = pers.next();
					if(per.getPerId()==null){
						pers.remove();
					}else{
						per.setMenuId(menuId);
					}
				}
				this.menuBo.addMenuPer(lists);
			}
		}
		if (result > 0) {
			logger.info("成功添加菜单"+ menu.getMenuName() + "！");
		}
		return result > 0 ? "1" : "0";
	}
	
	@RequestMapping("/getOneMenuInfo")
	@ResponseBody
	public Menu getOneMenuInfo(Menu menu){
		List<Menu> menus=this.menuBo.getOneMenuInfo(menu);
		if(menus.get(0).getParentMenuId()!=null && menus.get(0).getMenuUri()!=null){
			menus.get(0).setPermissions(this.perBo.getMenuPer(menus.get(0))); 
		}
		return menus.size()>0 ? menus.get(0): null ;
	}
	
	@RequestMapping("/alterMenuInfo")
	@ResponseBody
	public String alterMenuInfo(Menu menu,HttpServletRequest request,HttpSession session){
		User s_user = (User) session.getAttribute("user");
		Integer result = this.menuBo.alterMenuInfo(menu);
		if(menu.getParentMenuId()!=null){
			List<Menu_per> lists=menu.getPermissions().get(0).getMenuPer();
			this.menuBo.deletePersByMenuId(menu.getMenuId());
			if(lists.size()>0){
				Iterator<Menu_per> pers = lists.iterator(); 
				while(pers.hasNext()){ 
					Menu_per per = pers.next();
					if(per.getPerId()==null){
						pers.remove();
					}else{
						per.setMenuId(menu.getMenuId());
					}
				}
				this.menuBo.addMenuPer(lists);
			}
		}
		if (result > 0) {
			logger.info("成功编辑菜单"+ menu.getMenuName() + "！");
		}
		return result > 0 ? "1" : "0";
	}
	
	@RequestMapping("/deleteMenuByIds")
	@ResponseBody
	public String deleteMenuByIds(@RequestParam(value = "menuIds[]")Integer[] menuIds) {
		List<Integer> delMenuIds = new ArrayList<Integer>();
		for (int i = 0; i < menuIds.length; i++) {
			delMenuIds.add(i, menuIds[i]);
		}
		this.rolePerBo.deletePerByMenuId(delMenuIds);
		this.menuBo.deleteMenuPerByMenuIds(delMenuIds);
		Integer result = this.menuBo.deleteMenuByIds(delMenuIds);
		return result > 0 ? "1" : "0";
	}
	
	
	@RequestMapping("/getRolePerInfo")
	@ResponseBody
	public List<Role_per> getRolePerInfo(Role role){
		List<Role_per> lists=this.rolePerBo.getRolePerInfo(role);
		return lists.size()>0 ? lists: null ;
	}
	
	@RequestMapping("/getAllIcon")
	@ResponseBody
	public List<Icon> getAllIcon(){
		return this.menuBo.getAllIcon();
	}
	

}
