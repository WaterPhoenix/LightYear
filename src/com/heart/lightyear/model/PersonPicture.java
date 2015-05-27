package com.heart.lightyear.model;

import java.util.HashMap;

import com.heart.lightyear.util.ObjectUtil;

public class PersonPicture implements ObjectUtil{
	/**
	 * 类的完整路径，入库的时候使用反射时用到
	 */
	//public String className = PersonPicture.class.getName();
	/**
	 * 与类中属性对应的数据库中的字段
	 */
	//public String[] params = new String[]{"personid","pictureblob","isfaceornot","uploadtime"};
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
	 * 照片ID
	 */
	private int pictureId;
	/**
	 * 用户ID
	 */
	private int personId;
	/**
	 * 照片路径
	 */
	private byte[] pictureBlob;
	/**
	 * 是否设置为头像
	 */
	private int isFaceOrNot;
	/**
	 * 上传时间
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
