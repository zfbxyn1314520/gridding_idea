package com.eollse.action;
/**
 * author 李宁财
 * content 角色控制器
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eollse.bo.AreaBo;
import com.eollse.bo.RoleBo;
import com.eollse.bo.RolePerBo;
import com.eollse.bo.UserBo;
import com.eollse.po.Role;
import com.eollse.po.Role_per;
import com.eollse.po.User;

@Controller
@RequestMapping("/role")
public class RoleAction extends CommonAction {
	
	@Autowired
	private RoleBo roleBo;
	@Autowired
	private AreaBo areaBo;
	@Autowired
	private UserBo userBo;
	@Autowired
	private RolePerBo rolePerBo;
	
	@RequestMapping("/getAllRoles")
	@ResponseBody
	public List<Role> getAllRoles(HttpSession session){
		User user=(User)session.getAttribute("user"); 
		return this.roleBo.getAllRoles(user.getRole().getRoleLevel());
	}
	
	@RequestMapping("/getAllRoleByAreaId")
	@ResponseBody
	public String getAllRoleByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String, Object> map=this.roleBo.getAllRoleByAreaId(user,areaIds,pageSize,pageCurrent);
			String content=this.createPageJSONString(map);
			return content;		
		}else{
			return null;
		}
	}
	
	
	@RequestMapping("/addNewRole")
	@ResponseBody
	public String addNewRole(Role role,HttpServletRequest request,HttpSession session) {
		User s_user=(User)session.getAttribute("user");
		role.setEditRoleName(s_user.getUserName());
		role.setEditRoleDate(new Date());
		Integer result = this.roleBo.addNewRole(role);
		if(result>0){
			logger.info("添加角色"+role.getRoleName()+"！");
		}
		return result>0 ? "1" : "0";
	}
	
	@RequestMapping("/getOneRoleInfo")
	@ResponseBody
	public Role getOneRoleInfo(Role role) {
		
		return this.roleBo.getRoleById(role.getRoleId());
	}
	
	@RequestMapping("/alterRoleInfo")
	@ResponseBody
	public String alterRoleInfo(Role role,HttpServletRequest request,HttpSession session){
		User s_user=(User)session.getAttribute("user");
		role.setEditRoleName(s_user.getUserName());
		role.setEditRoleDate(new Date());
		Integer result = this.roleBo.alterRoleInfo(role);
		if(result>0){
			logger.info("修改角色"+role.getRoleName()+"信息！");
		}
		return result > 0 ? "1" : "0";
	}

	@RequestMapping("/deleteRoleByIds")
	@ResponseBody
	public String deleteRoleByIds(@RequestParam(value = "roleIds[]")Integer[] roleIds,HttpSession session,HttpServletRequest request) {
		User s_user=(User)session.getAttribute("user");
		List<Integer> delRoleIds = new ArrayList<Integer>();
		Integer roleId=null;
		for (int i = 0; i < roleIds.length; i++) {
			if(roleIds[i].equals(s_user.getRoleId())){
				continue;
			}
			delRoleIds.add(roleIds[i]);
		}
		List<Role> roles=this.roleBo.selectRoleName("其他");
		if(roles.size()==0){
			Role role=new Role("其他","备用角色，删除某个角色之后，系统会将该角色下的所有用户的角色设置为本角色！",s_user.getUserName(),new Date(),1,5);
			this.roleBo.addNewRole(role);
			List<Role> list=this.roleBo.selectRoleName("其他");
			roleId=list.get(0).getRoleId();
		}else{
			roleId=roles.get(0).getRoleId();		
		}
		//将要删除角色下的用户修改为其他
		this.userBo.alterUserByRoleId(delRoleIds,roleId);
		this.rolePerBo.deletePerByRoleId(delRoleIds);
		Integer result = this.roleBo.deleteRoleByIds(delRoleIds);
		if(result>0){
//			Log log =new Log(0,s_user.getUserId(),s_user.getUserName(),getIpAddr(request),new Date(),"删除角色"+role.getRoleName()+"信息！");
//			this.logBo.keepOneLog(log);	
			//logger.info("删除角色"+role.getRoleName()+"信息！");
		}
		return result > 0 ? "1" : "0";
	}
	
	@RequestMapping("/saveRolePer")
	@ResponseBody
	public String saveRolePer(@RequestParam(value = "perIds[]") String[] perIds,Role role){
		List<Role_per> rolePers=new ArrayList<Role_per>();
		this.rolePerBo.deleteRolePer(role);
		for(int i=0;i<perIds.length;i++){
			rolePers.add(i, new Role_per(
				role.getRoleId(),					
				Integer.parseInt(perIds[i].substring(perIds[i].length()-1)),
				Integer.parseInt(perIds[i].substring(0,perIds[i].length()-1)))
			);
		}
		Integer result=this.rolePerBo.saveRolePer(rolePers);
		return result > 0 ? "1" : "0";
	}
	
	/**
	 * 初始化角色权限信息
	 * @param role
	 * @return
	 */
	@RequestMapping("/initRolePer")
	@ResponseBody
	public String initRolePer(Role role){
		Integer result=this.rolePerBo.deleteRolePer(role);
		return result > 0 ? "1" : "0";
	}
	
	/**
	 * 修改角色使用状态
	 * @param user 用户实体类
	 */
	@RequestMapping("/alterRoleUseStatus")
	@ResponseBody
	public void alterRoleUseStatus(Role role,HttpSession session) {
		if(role.getRole_enable()==0){
			List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
			List<User> users= this.userBo.userCountByRoleId(role, areaIds);
			if(users.size()>0){
				this.userBo.alterUserStatusOfRole(users);
			}
		}
		this.roleBo.alterRoleUseStatus(role);
	}
}
