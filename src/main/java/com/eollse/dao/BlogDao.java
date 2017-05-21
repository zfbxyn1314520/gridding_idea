package com.eollse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eollse.po.Blog;
import com.eollse.po.BlogApp;

public interface BlogDao {

	public List<Blog> getAllBlogByAreaId(
			@Param("list")List<Integer> areaIds, 
			@Param("x")Integer x, 
			@Param("y")Integer y
		);

	public int getAllBlogCount(@Param("list")List<Integer> areaIds);

	public Integer saveBlog(Blog blog);

	public Blog getBlogById(Integer blogId);
  
	public Integer editBlogById(Blog blog);

	public List<Blog> getOneBlogById(Integer blogId);

	public Integer deleteBlog(@Param("list")List<Integer> blogIds);

	public List<BlogApp> getOneStaffBlogById(
			@Param("list")List<Integer> gridStaffIds,
			@Param("field")String field, 
			@Param("editTime")String editTime, 
			@Param("x")Integer x, 
			@Param("y")Integer y
		);

	public Integer getOneStaffBlogCount(
			@Param("list")List<Integer> gridStaffIds,
			@Param("field")String field, 
			@Param("editTime")String editTime
		);



}
