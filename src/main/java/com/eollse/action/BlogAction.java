package com.eollse.action;

/**
 * author 刘春晓
 * content 工作日志控制器
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eollse.bo.BlogBo;
import com.eollse.po.Blog;
import com.eollse.po.User;

@Controller
@RequestMapping(value="/blog")
public class BlogAction extends CommonAction {
	
	@Autowired
	private BlogBo blogBo;
	
	/**
	 * 获取所有工作日志
	 * @param session 
	 * @param pageSize 分页大小
	 * @param pageCurrent 当前页码
	 * @return
	 */
	@RequestMapping(value="/getAllBlogByAreaId")
	@ResponseBody
	public String getAllBlogByAreaId(HttpSession session,Integer pageSize, Integer pageCurrent){
		User user=(User)session.getAttribute("user");
		List<Integer> areaIds=(List<Integer>)session.getAttribute("areaIds");
		if(user!=null){
			Map<String,Object> map=this.blogBo.getAllBlogByAreaId(areaIds,pageSize,pageCurrent);
			String content=this.createPageJSONString(map);
			return content;
		}else{
			return null;
		}
	}
	
	/**
	 * 添加工作日志保存方法
	 * @param blog 日志实体
	 * @param session 用户登录session
	 * @param request 请求
	 * @return string “1”==>表示保存成功 “0”==>表示保存失败
	 */
	@RequestMapping(value="/saveBlog")
	@ResponseBody
	public String saveBlog(Blog blog,HttpSession session,HttpServletRequest request){
		User user = (User) session.getAttribute("user");
		if(user!=null){
			Integer result=this.blogBo.saveBlog(blog);
			if(result>0){
				logger.info("成功添加工作日志"+ blog.getBlogName() +"！");
			}
			return result>0 ? "1" : "0";
		}else{
			return "0";
		}
	}
	
	/**
	 * 修改弹窗所需数据
	 * @param upBLid 日志Id
	 * @return 日志实体
	 */
	@RequestMapping(value="/getBlogById")
	@ResponseBody
	public Blog getBlogById(String upBLid){
		Integer blogId=Integer.parseInt(upBLid);
		Blog blog=this.blogBo.getBlogById(blogId);
		return blog;
	}
	
	/**
	 * 修改日志
	 * @param blog 日志实体
	 * @param session
	 * @param request
	 * @return string “1”==>表示修改成功 “0”==>表示修改失败
	 */
	@RequestMapping(value="/editBlogById")
	@ResponseBody
	public String editBlogById(Blog blog,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		Integer result=this.blogBo.editBlogById(blog);
		if(result>0){
			logger.info("成功修改工作日志"+ blog.getBlogName()  +"！");
		}
		return result>0 ? "1" : "0";
	}
	
	/**
	 * 查看方法
	 * @param blogId 日志Id
	 * @return
	 */
	@RequestMapping(value="/getOneBlogById")
	@ResponseBody
	public Blog getOneBlogById(Integer blogId){
		Blog blog=this.blogBo.getOneBlogById(blogId).get(0);
		return blog;
	}
	
	
	/**
	 * 删除日志
	 * @param deBlid 日志Id组成的字符串
	 * @param nameStr 日志名称组成字符串
	 * @param session
	 * @param request
	 * @return string “1”==>表示删除成功 “0”==>表示删除失败
	 */
	@RequestMapping(value="/deleteBlog")
	@ResponseBody
	public String deleteBlog(String deBlid,String nameStr,HttpSession session,HttpServletRequest request){
		User user=(User)session.getAttribute("user");
		String[] str=deBlid.split(",");
		List<Integer> blogIds=new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			blogIds.add(i,Integer.parseInt(str[i]));
		}
		Integer result=this.blogBo.deleteBlog(blogIds);
		if(result>0){
			logger.info("成功删除工作日志"+nameStr+ "信息！");
		}
		return result>0 ? "1" : "0";
	}
	
	@RequestMapping(value="/uploadFile", produces = {"text/x-json;charset=utf-8"})
	@ResponseBody
	public String uploadHeadIcon(@RequestParam("blogPic")MultipartFile file,HttpSession session,HttpServletRequest request, 
			String mainName,String picName) throws IOException, ServletException {
		User user=(User)session.getAttribute("user");
		// 判断提交来的文件是否为空
        if (!file.isEmpty()) {
        	// 文件类型限制
        	boolean result=true;
        	String[] allowedType = { "image/bmp", "image/gif", "image/jpeg", "image/png" };
        	for(int i=0;i<allowedType.length;i++){
        		if(file.getContentType().equals(allowedType[i])){
        			result=false;
        		}
        	}
        	if(result){
        		return "error|上传文件类型错误，请重新选择上传！";
        	}
        	// 文件大小限制
        	if (file.getSize() > 1 * 1024 * 1024) {
            	return "error|上传文件大小超过1M，请重新选择上传！";
            }
        }
        picName = user.getUserName();
    	return this.fileUpload(request, mainName, picName);
	}
}
