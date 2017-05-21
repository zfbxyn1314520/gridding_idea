package com.eollse.bo.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eollse.bo.BlogBo;
import com.eollse.dao.BlogDao;
import com.eollse.po.Blog;
import com.eollse.po.BlogApp;
import com.eollse.util.HtmlConvertTextUtil;

@Service
public class BlogBoImpl implements BlogBo {
	
	@Autowired
	private BlogDao blogDao;

	@Override
	public Map<String, Object> getAllBlogByAreaId(List<Integer> areaIds,Integer pageSize, Integer pageCurrent) {
		// TODO Auto-generated method stub
		Integer x= (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Blog> blogs=this.blogDao.getAllBlogByAreaId(areaIds,x,y);
		int totalRow = this.blogDao.getAllBlogCount(areaIds);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", blogs);
		return map;
	}

	@Override
	public Integer saveBlog(Blog blog) {
		// TODO Auto-generated method stub
		return this.blogDao.saveBlog(blog);
	}

	@Override
	public Integer getAllBlogCount(List<Integer> areaIds) {
		// TODO Auto-generated method stub
		return this.blogDao.getAllBlogCount(areaIds);
	}

	@Override
	public Blog getBlogById(Integer blogId) {
		// TODO Auto-generated method stub
		return this.blogDao.getBlogById(blogId);
	}

	@Override
	public Integer editBlogById(Blog blog) {
		// TODO Auto-generated method stub
		return this.blogDao.editBlogById(blog);
	}

	@Override
	public List<Blog> getOneBlogById(Integer blogId) {
		// TODO Auto-generated method stub
		return this.blogDao.getOneBlogById(blogId);
	}

	@Override
	public Integer deleteBlog(List<Integer> blogIds) {
		// TODO Auto-generated method stub
		return this.blogDao.deleteBlog(blogIds);
	}

	@Override
	public Map<String, Object> getOneStaffBlogById(List<Integer> gridStaffIds,String field, String editTime, Integer pageSize, Integer pageCurrent) {
		// TODO Auto-generated method stub
		Integer x= (pageCurrent - 1) * pageSize;
		Integer y = pageCurrent*pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		HtmlConvertTextUtil parser = new HtmlConvertTextUtil();
		Reader reader = null;
		List<BlogApp> blogs=this.blogDao.getOneStaffBlogById(gridStaffIds,field,editTime,x,y);
		Integer length=blogs.size();
		for(int i=0;i<length;i++){
	        reader=new StringReader(blogs.get(i).getBlogContent());      
	        try {
				parser.parse(reader);
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        blogs.get(i).setBlogContent(parser.getText());
		}
		Integer totalRow = this.blogDao.getOneStaffBlogCount(gridStaffIds,field,editTime);
		map.put("totalRow", totalRow);
		map.put("pageCurrent", pageCurrent);
		map.put("list", blogs);
		return map;
	}
	
	

	
}
