package com.heart.lightyear.util;

import java.util.HashMap;

public interface ObjectUtil {
	/**
	 * �������·��������ʱ��ʹ�÷���ʱ�õ�
	 */
	public String getClassName();
	/**
	 * ���������Զ�Ӧ�����ݿ��е��ֶ�
	 */
	public String[] getParams();
	/**
	 * ��һ���ֶ�ȡ����������е�Ԫ�أ���Ӧ���ݿ��ֶ�
	 * �ڶ�������Ϊ�����ݿ��ֶζ�Ӧ�����е����Ե�get����
	 */
	public HashMap<String,String> getMethodNameWithParams();
	
}
