package com.heart.lightyear.model;

import java.util.HashMap;

import com.heart.lightyear.util.ObjectUtil;

public class PersonPicture implements ObjectUtil{
	/**
	 * �������·��������ʱ��ʹ�÷���ʱ�õ�
	 */
	//public String className = PersonPicture.class.getName();
	/**
	 * ���������Զ�Ӧ�����ݿ��е��ֶ�
	 */
	//public String[] params = new String[]{"personid","pictureblob","isfaceornot","uploadtime"};
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
			put("pictureblob","getPictureBlob");
			put("isfaceornot","getIsFaceOrNot");
			put("uploadtime","getUpLoadTime");
		}
	};
	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return PersonPicture.class.getName();
	}
	@Override
	public HashMap<String, String> getMethodNameWithParams() {
		// TODO Auto-generated method stub
		return methodNameWithParams;
	}
	@Override
	public String[] getParams() {
		// TODO Auto-generated method stub
		return new String[]{"personid","pictureblob","isfaceornot","uploadtime"};
	}
	/**
	 * ��ƬID
	 */
	private int pictureId;
	/**
	 * �û�ID
	 */
	private int personId;
	/**
	 * ��Ƭ·��
	 */
	private byte[] pictureBlob;
	/**
	 * �Ƿ�����Ϊͷ��
	 */
	private int isFaceOrNot;
	/**
	 * �ϴ�ʱ��
	 */
	private String upLoadTime;
	
	public int getPictureId() {
		return pictureId;
	}
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public byte[] getPictureBlob() {
		return pictureBlob;
	}
	public void setPictureBlob(byte[] pictureBlob) {
		this.pictureBlob = pictureBlob;
	}
	public int getIsFaceOrNot() {
		return isFaceOrNot;
	}
	public void setIsFaceOrNot(int isFaceOrNot) {
		this.isFaceOrNot = isFaceOrNot;
	}
	public String getUpLoadTime() {
		return upLoadTime;
	}
	public void setUpLoadTime(String upLoadTime) {
		this.upLoadTime = upLoadTime;
	}
}
