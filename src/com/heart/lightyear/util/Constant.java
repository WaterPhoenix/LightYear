package com.heart.lightyear.util;

import android.content.Context;

public class Constant {
	private static Configuration configuration;
	public Constant(Context context){
		configuration = new Configuration(context);
	}
	/**
	 * 是否自动登录标志位
	 */
	public String getLoadSelf(){
		return configuration.getValue("loadself", "0");
	}
	/**
	 * 是否保存密码标志位
	 */
	public String getSavePass(){
		return configuration.getValue("savepass", "0");
	}
	/**
	 * 保存的用户名
	 */
	public String getUserName(){
		return configuration.getValue("username", "");
	}
	/**
	 * 保存的用户密码
	 */
	public String getPassWord(){
		return configuration.getValue("password", "");
	}
	/**
	 * 保存信息
	 * @param key
	 * @param value
	 */
	public void putParam(String key,String value){
		configuration.putValue(key, value);
	}
	/**
	 * 提交设置
	 */
	public void commit(){
		configuration.commit();
	}
	
	public void saveConfig(boolean loginSelfOrNot,boolean savePassOrNot,String userName,String passWord){
		/**
		 * 保存是否保存密码及是否自动登录
		 */
		if(loginSelfOrNot){
			/**
			 * 设置保存密码、自动登录标志位为1
			 */
			putParam("loadself", "1");
			putParam("savepass", "1");
			/**
			 * 保存用户名、密码
			 */
			putParam("username", userName);
			putParam("password", passWord);
		}else if(savePassOrNot){
			/**
			 * 设置保存密码标志位为1,自动登录位0
			 */
			putParam("loadself", "0");
			putParam("savepass", "1");
			/**
			 * 保存用户名、密码
			 */
			putParam("username", userName);
			putParam("password", passWord);
		}else{
			/**
			 * 设置保存密码标志位为0,自动登录位0
			 */
			putParam("loadself", "0");
			putParam("savepass", "0");
			putParam("username", "");
			putParam("password", "");
		}
		commit();
	}
}
