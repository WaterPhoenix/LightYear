package com.heart.lightyear.model;

import java.util.HashMap;

import com.heart.lightyear.util.ObjectUtil;

public class PersonBlog implements ObjectUtil{
	/**
	 * �������·��������ʱ��ʹ�÷���ʱ�õ�
	 */
	public String className = PersonBlog.class.getName();
	/**
	 * ���������Զ�Ӧ�����ݿ��е��ֶ�
	 */
	public String[] params = new String[]{"blogid","personid","blogtitle","blogcontent","lasttime"};
	/**
	 * ��һ���ֶ�ȡ����������е�Ԫ�أ���Ӧ���ݿ��ֶ�
	 * �ڶ�������Ϊ�����ݿ��ֶζ�Ӧ�����е����Ե�get����
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
	 * ��־ID
	 */
	private int blogId;
	/**
	 * �û�ID
	 */
	private int personId;
	/**
	 * ��־����
	 */
	private String blogTitle;
	/**
	 * ��־����
	 */
	private String blogContent;
	/**
	 * ����������
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
