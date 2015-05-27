package com.heart.lightyear.model;

import java.util.HashMap;

import com.heart.lightyear.util.ObjectUtil;

public class PersonInfo implements ObjectUtil{
	/**
	 * 类的完整路径，入库的时候使用反射时用到
	 */
	public String className = PersonInfo.class.getName();
	/**
	 * 与类中属性对应的数据库中的字段
	 */
	public String[] params = new String[]{"username","password","personsign","registertime"};
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
			put("username","getUserName");
			put("password","getPassWord");
			put("personsign","getPersonSign");
			put("registertime","getRegisterTime");
		}
	};
	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return PersonInfo.class.getName();
	}
	@Override
	public HashMap<String, String> getMethodNameWithParams() {
		// TODO Auto-generated method stub
		return methodNameWithParams;
	}
	@Override
	public String[] getParams() {
		// TODO Auto-generated method stub
		return new String[]{"username","password","personsign","registertime"};
	}
	/**
	 * 用户ID
	 */
	private int personId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户密码
	 */
	private String passWord;
	/**
	 * 用户签名
	 */
	private String personSign;
	/**
	 * 注册日期
	 */
	private String registerTime;
	
	public PersonInfo(int personId, String userName, String passWord,
			String personSign, String registerTime) {
		super();
		this.personId = personId;
		this.userName = userName;
		this.passWord = passWord;
		this.personSign = personSign;
		this.registerTime = registerTime;
	}
	public PersonInfo() {
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getPersonSign() {
		return personSign;
	}
	public void setPersonSign(String personSign) {
		this.personSign = personSign;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
}
