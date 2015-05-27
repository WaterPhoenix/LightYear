package com.heart.lightyear.util;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	
	private List<String> createTableStatements;
	public DatabaseHelper(Context context, String name, CursorFactory factory,
	   int version) {
		// TODO Auto-generated constructor stub
		/**
		 * ����ͨ��super���ø���Ĺ��캯��
		 */
	  super(context, name, factory, version);
	}
	public DatabaseHelper(Context context, String dbName, int version) {
	  this(context, dbName, null, version);
	}
	public DatabaseHelper(Context context, String name,List<String> createTableStatements) {
		this(context, name, VERSION);
		this.createTableStatements = createTableStatements;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
	  // TODO Auto-generated method stub
	  System.out.println("create a database");
	  /**
	   * execSQL����ִ��SQL���,�������ݿ��
	   * ѭ��ִ��createTableStatements�еĽ������
	   */
	  for(String sqlStr : createTableStatements){
		  db.execSQL(sqlStr);
	  }
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println("update database");
	}
}
