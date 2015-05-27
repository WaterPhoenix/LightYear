package com.heart.lightyear.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import android.content.Context;

public class Configuration {
	private Context context;
	private static Properties properties = new Properties();
	
	public Configuration (Context context){
		this.context = context;
		try {
			//InputStream s = this.context.getAssets().open("config.properties");
			InputStream s = this.context.openFileInput("config.properties");
			properties.load(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取配置信息
	 * @param key
	 * @param defaultStr
	 * @return
	 */
	public String getValue(String key,String defaultStr){
		String result = properties.getProperty(key);
		if(result == null){
			return defaultStr;
		}else{
			return result;
		}
	}
	/**
	 * 设置配置信息
	 * @param key
	 * @param value
	 */
	public void putValue(String key,String value){
		properties.put(key, value);
		
	}
	/**
	 * 提交保存设置的配置信息
	 */
	public void commit(){
		try {
			OutputStream out = this.context.openFileOutput("config.properties",Context.MODE_PRIVATE);
			properties.store(out, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
