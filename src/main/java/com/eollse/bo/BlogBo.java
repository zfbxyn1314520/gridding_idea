package com.eollse.bo;

import java.util.List;
import java.util.Map;

import com.eollse.po.Blog;
import com.eollse.po.BlogApp;

public interface BlogBo {

	public Map<String, Object> getAllBlogByAreaId(List<Integer> areaIds,Integer pageSize, Integer pageCurrent);

	public Integer saveBlog(Blog blog);

	public Integer getAllBlogCount(List<Integer> areaIds);

	public Blog getBlogById(Integer blogId);

	public Integer editBlogById(Blog blog);

	public List<Blog> getOneBlogById(Integer blogId);

	public Integer deleteBlog(List<Integer> blogIds);

	public Map<String, Object> getOneStaffBlogById(List<Integer> gridStaffIds,String field, String editTime, Integer pageSize, Integer pageCurrent);



}
