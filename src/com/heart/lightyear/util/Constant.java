package com.heart.lightyear.util;

import android.content.Context;

public class Constant {
	private static Configuration configuration;
	public Constant(Context context){
		configuration = new Configuration(context);
	}
	/**
	 * �Ƿ��Զ���¼��־λ
	 */
	public String getLoadSelf(){
		return configuration.getValue("loadself", "0");
	}
	/**
	 * �Ƿ񱣴������־λ
	 */
	public String getSavePass(){
		return configuration.getValue("savepass", "0");
	}
	/**
	 * ������û���
	 */
	public String getUserName(){
		return configuration.getValue("username", "");
	}
	/**
	 * ������û�����
	 */
	public String getPassWord(){
		return configuration.getValue("password", "");
	}
	/**
	 * ������Ϣ
	 * @param key
	 * @param value
	 */
	public void putParam(String key,String value){
		configuration.putValue(key, value);
	}
	/**
	 * �ύ����
	 */
	public void commit(){
		configuration.commit();
	}
	
	public void saveConfig(boolean loginSelfOrNot,boolean savePassOrNot,String userName,String passWord){
		/**
		 * �����Ƿ񱣴����뼰�Ƿ��Զ���¼
		 */
		if(loginSelfOrNot){
			/**
			 * ���ñ������롢�Զ���¼��־λΪ1
			 */
			putParam("loadself", "1");
			putParam("savepass", "1");
			/**
			 * �����û���������
			 */
			putParam("username", userName);
			putParam("password", passWord);
		}else if(savePassOrNot){
			/**
			 * ���ñ��������־λΪ1,�Զ���¼λ0
			 */
			putParam("loadself", "0");
			putParam("savepass", "1");
			/**
			 * �����û���������
			 */
			putParam("username", userName);
			putParam("password", passWord);
		}else{
			/**
			 * ���ñ��������־λΪ0,�Զ���¼λ0
			 */
			putParam("loadself", "0");
			putParam("savepass", "0");
			putParam("username", "");
			putParam("password", "");
		}
		commit();
	}
}
