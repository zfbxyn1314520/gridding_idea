package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BlogApp implements Serializable {

	private Integer blogId;
	private Integer areaId;
	private Integer gridId;
	private String blogName;
	private String blogType;
	private String blogContent;
	private String blogPic;
	@DateTimeFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
	private Date editBlogDate;
	private GridStaffApp gridStaffApp;

	public BlogApp() {
		super();
		this.gridStaffApp=new GridStaffApp();
	}

	public String getBlogPic() {
		return blogPic;
	}
	
	public BlogApp(Integer blogId, Integer areaId, Integer gridId,
			String blogName, String blogType, String blogContent,
			String blogPic, Date editBlogDate, GridStaffApp gridStaffApp) {
		super();
		this.blogId = blogId;
		this.areaId = areaId;
		this.gridId = gridId;
		this.blogName = blogName;
		this.blogType = blogType;
		this.blogContent = blogContent;
		this.blogPic = blogPic;
		this.editBlogDate = editBlogDate;
		this.gridStaffApp = gridStaffApp;
	}
	
	@Override
	public String toString() {
		return "BlogApp [blogId=" + blogId + ", areaId=" + areaId + ", gridId="
				+ gridId + ", blogName=" + blogName + ", blogType=" + blogType
				+ ", blogContent=" + blogContent + ", blogPic=" + blogPic
				+ ", editBlogDate=" + editBlogDate + ", gridStaffApp="
				+ gridStaffApp + "]";
	}

	public GridStaffApp getGridStaffApp() {
		return gridStaffApp;
	}

	public void setGridStaffApp(GridStaffApp gridStaffApp) {
		this.gridStaffApp = gridStaffApp;
	}

	public void setBlogPic(String blogPic) {
		this.blogPic = blogPic;
	}

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getGridId() {
		return gridId;
	}

	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}

	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public String getBlogType() {
		return blogType;
	}

	public void setBlogType(String blogType) {
		this.blogType = blogType;
	}

	public String getBlogContent() {
		return blogContent;
	}

	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}

	public Date getEditBlogDate() {
		return editBlogDate;
	}

	public void setEditBlogDate(Date editBlogDate) {
		this.editBlogDate = editBlogDate;
	}


}
