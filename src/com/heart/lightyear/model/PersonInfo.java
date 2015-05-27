package com.heart.lightyear.model;

import java.util.HashMap;

import com.heart.lightyear.util.ObjectUtil;

public class PersonInfo implements ObjectUtil{
	/**
	 * �������·��������ʱ��ʹ�÷���ʱ�õ�
	 */
	public String className = PersonInfo.class.getName();
	/**
	 * ���������Զ�Ӧ�����ݿ��е��ֶ�
	 */
	public String[] params = new String[]{"username","password","personsign","registertime"};
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
	 * �û�ID
	 */
	private int personId;
	/**
	 * �û���
	 */
	private String userName;
	/**
	 * �û�����
	 */
	private String passWord;
	/**
	 * �û�ǩ��
	 */
	private String personSign;
	/**
	 * ע������
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
