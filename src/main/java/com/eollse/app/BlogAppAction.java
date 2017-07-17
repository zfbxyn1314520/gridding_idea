package com.eollse.app;

/**
 * author 李宁财
 * content App日志控制器
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eollse.action.CommonAction;
import com.eollse.bo.BlogBo;
import com.eollse.bo.GridBo;
import com.eollse.bo.GridStaffBo;
import com.eollse.bo.UserBo;
import com.eollse.po.Blog;
import com.eollse.po.User;

@Controller
@RequestMapping("/app/blog")
public class BlogAppAction extends CommonAction{

	@Autowired
	private BlogBo blogBo;
	@Autowired
	private UserBo userBo;
	@Autowired
	private GridBo gridBo;
	@Autowired
	private GridStaffBo gridStaffBo;
	
	
	/**
	 * 获取app用户所有工作日志/根据名字或者电话号码筛选日志
	 * @param session 
	 * @param field 搜索的网格姓名或手机号码值 ==> String类型
	 * @param editTime 搜索的时间值 ==> String类型
	 * @param pageSize 分页大小 ==> Integer类型
	 * @param pageCurrent 当前页码 ==> Integer类型
	 * @return
	 */
	@RequestMapping(value="/getOneStaffBlogById", produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public String getOneStaffBlogById(HttpSession session,String field,String editTime,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		List<Integer> gridStaffIds = new ArrayList<Integer>();
		Map<String,Object> blogs = new HashMap<String,Object>();
		if(user.getRole().getRoleLevel()<6){
			List<Integer> gridIds=this.gridBo.getAllGridIdsByAreaId(areaIds);
			if(gridIds.size()>0){
				gridStaffIds=this.gridStaffBo.getAllGridStaffIdByAreaId(gridIds);
			}
		}else{
			Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
			if(user.getRole().getRoleName().equals("社区网格员")){
				gridStaffIds.add(gridStaffId);
			}else if(user.getRole().getRoleName().equals("社区网格长")){
				List<Integer> gridIds=new ArrayList<Integer>();
				gridIds.add(this.gridStaffBo.getGridIdByGridStaffId(gridStaffId));
				gridStaffIds=this.gridStaffBo.getAllGridStaffIdByAreaId(gridIds);
			}
		}
		if(editTime == null)
			editTime = "";
		if(field == null)
			field = "";
		blogs = this.blogBo.getOneStaffBlogById(gridStaffIds,field,editTime,pageSize,pageCurrent);
		return "{\"statusCode\":200,\"data\":"+this.createPageJSONString(blogs)+"}";
	}
	
	
	/**
	 * 添加网格员工作日志
	 * @param blog 日志实体
	 * @param session 用户登录session
	 * @param blogName 标题
	 * @param blogType 日志类型
	 * @param blogContent 日志内容
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/0", produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public String saveBlog(Blog blog, HttpSession session){
		User user = (User) session.getAttribute("user");
		if(user.getRole().getRoleLevel()<6){
			return "{\"statusCode\":300,\"message\":\"你的账户不需要添加工作日志！\"}";
		}else{
			Integer gridStaffId = this.userBo.getStaffIdByUserId(user.getUserId());
			blog.setAreaId(user.getAreaId());
			blog.setGridId(this.gridStaffBo.getGridIdByGridStaffId(gridStaffId));
			blog.setGridStaffId(gridStaffId);
			blog.setEditBlogDate(new Date());
			Integer result=this.blogBo.saveBlog(blog);
			if(result>0){
				logger.info("成功添加工作日志"+ blog.getBlogName() +"！");
				return "{\"statusCode\":200,\"message\":\"添加工作日志成功！\"}";
			}else{
				logger.error("添加工作日志"+ blog.getBlogName() +"失败！");
				return "{\"statusCode\":300,\"message\":\"添加工作日志失败！\"}";
			}
		}
	}
	
	
	/**
	 * 上传App工作日志图片信息
	 * @param request
	 * @param mainName 用户上传图片文件夹名称 not null
	 * @param picName 如何是上传多张图片应该传图片名称用户生成文件夹
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/uploadBlogImg", produces = {"application/json;charset=utf-8"})
	@ResponseBody
	public String uploadClockInImg(@RequestParam("mFile")MultipartFile[] files, HttpSession session) {
		User user=(User)session.getAttribute("user");
		if(user.getRole().getRoleLevel()<6){
			return "{\"statusCode\":300,\"message\":\"你的账户不需要添加工作日志！\"}";
		}else{
			return this.mutilFileUpload(files, "app/blog/", user.getUserName()+"/");
		}
	}
	
	
}
