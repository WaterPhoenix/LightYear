package com.heart.lightyear.util;

import java.util.HashMap;

public interface ObjectUtil {
	/**
	 * 类的完整路径，入库的时候使用反射时用到
	 */
	public String getClassName();
	/**
	 * 与类中属性对应的数据库中的字段
	 */
	public String[] getParams();
	/**
	 * 第一个字段取自上面参数中的元素，对应数据库字段
	 * 第二个参数为该数据库字段对应的类中的属性的get方法
	 */
	public HashMap<String,String> getMethodNameWithParams();
	
}
