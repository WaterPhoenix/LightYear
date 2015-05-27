package com.heart.lightyear.model;

import java.util.HashMap;

import com.heart.lightyear.util.ObjectUtil;

public class PersonBlog implements ObjectUtil{
	/**
	 * 类的完整路径，入库的时候使用反射时用到
	 */
	public String className = PersonBlog.class.getName();
	/**
	 * 与类中属性对应的数据库中的字段
	 */
	public String[] params = new String[]{"blogid","personid","blogtitle","blogcontent","lasttime"};
	/**
	 * 第一个字段取自上面参数中的元素，对应数据库字段
	 * 第二个参数为该数据库字段对应的类中的属性的get方法
	 */
	public HashMap<String,String> methodNameWithParams = new HashMap<String,String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("personid","getPersonId");
			put("blogtitle","getBlogTitle");
			put("blogcontent","getBlogContent");
			put("lasttime","getBlogLastTime");
		}
	};
	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return PersonBlog.class.getName();
	}
	@Override
	public HashMap<String, String> getMethodNameWithParams() {
		// TODO Auto-generated method stub
		return methodNameWithParams;
	}
	@Override
	public String[] getParams() {
		// TODO Auto-generated method stub
		return new String[]{"personid","blogtitle","blogcontent","lasttime"};
	}
	/**
	 * 日志ID
	 */
	private int blogId;
	/**
	 * 用户ID
	 */
	private int personId;
	/**
	 * 日志标题
	 */
	private String blogTitle;
	/**
	 * 日志内容
	 */
	private String blogContent;
	/**
	 * 最后更改日期
	 */
	private String blogLastTime;
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getBlogContent() {
		return blogContent;
	}
	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}
	public String getBlogLastTime() {
		return blogLastTime;
	}
	public void setBlogLastTime(String blogLastTime) {
		this.blogLastTime = blogLastTime;
	}
}
