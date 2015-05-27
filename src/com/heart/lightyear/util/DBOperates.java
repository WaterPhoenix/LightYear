package com.heart.lightyear.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heart.lightyear.model.PersonBlog;
import com.heart.lightyear.model.PersonInfo;
import com.heart.lightyear.model.PersonPicture;

public class DBOperates {

	private static DatabaseHelper dbHelper = null;
	private static SQLiteDatabase db = null;
	private static Cursor c = null;
	/**
	 * 数据库名
	 */
	private static final String DB_NAME = "LightYear.db";
	/**
	 * 使用反射时的实例
	 */
	private static Class<?> instance = null;
	/**
	 * 建表语句
	 */
	private static List<String> createTableStatements = new ArrayList<String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("create table person_info (personid integer PRIMARY KEY autoincrement,username varchar(20),password varchar(10),personsign 	varchar(100),registertime varchar(20));");
			add("create table person_picture(pictureid integer PRIMARY KEY autoincrement ,personid integer ,pictureblob Blob,isfaceornot integer,uploadtime varchar(20));");
			add("create table person_blog(blogid integer PRIMARY KEY autoincrement,personid integer,blogtitle varchar(50),blogcontent varchar(246),lasttime varchar(20));");
		}
	};
	
	public DBOperates(Context context){
		//this.dbHelper = new DatabaseHelper(context,DB_NAME,createTableStatements);
	}
	/**
	 * 创建数据库
	 * @param context
	 */
	public static void createDatabase(Context context){
		dbHelper = new DatabaseHelper(context,DB_NAME,createTableStatements);
	}
	/**
	 * 打开数据库，获取数据库实例
	 * @return
	 */
	public static void openDatabase(){
		if(db == null){
			db = dbHelper.getWritableDatabase();
		}
	}
	/**
	 * 关闭数据库
	 */
	public static void closeDatabase(){
		if(db != null){
			if(db.isOpen()){
				db.close();
				db = null;
			}
		}
	}
	/**
	 * 向数据库中添加一个对象中各个属性的数据
	 * @param tableName      插入表的名称
	 * @param obj            插入的对象
	 * @return
	 */
	public static boolean insertObject(String tableName,ObjectUtil obj){
		try{
			ContentValues cv = new ContentValues();  
			/**
			 * 获取与数据库字段对应的get方法名
			 */
			HashMap<String,String> methodNameWithParams = obj.getMethodNameWithParams();
			String[] params = obj.getParams();
			for(String param : params){
				/**
				 * 获取get方法的返回值
				 */
				Object result = getValueFromMethod(obj,methodNameWithParams.get(param));
				/**
				 * 根据不同的数据类型，向cv中添加
				 */
				if(result instanceof Integer){
					cv.put(param, (Integer)result);
				}else if(result instanceof String){
					cv.put(param, (String)result);
				}else if(result instanceof byte[]){
					cv.put(param, (byte[])result);
				}
			}
			db.insert(tableName, null, cv);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 根据反射机制obj实例对应的类中的methodName方法的返回值
	 * @param obj          反射的目标类的实例
	 * @param methodName   需要获取返回值的方法名
	 * @return
	 */
	public static Object getValueFromMethod(ObjectUtil obj,String methodName){
		Object result = null;
		try {
			instance = Class.forName(obj.getClassName());
			/**
			 * 调用instance对应的类中的methodName方法
			 */
			Method method=instance.getMethod(methodName);
			result = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}
	
	/**
	 * 查询所有的PersonInfo
	 * @return
	 */
	public static List<PersonInfo> selectAllPerson(){
		List<PersonInfo> personInfoList = new ArrayList<PersonInfo>();  
		try{
			c = db.rawQuery("SELECT * FROM person_info", null);  
	        while (c.moveToNext()){
	        	PersonInfo personInfo = new PersonInfo();
	        	personInfo.setPersonId(c.getInt(0));
	        	personInfo.setUserName(c.getString(1));
	        	personInfo.setPersonSign(c.getString(3));
	        	personInfo.setRegisterTime(c.getString(4));
	        	personInfoList.add(personInfo);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 c.close();  
		}
        return personInfoList;
	}
	/**
	 * 根据用户ID查询对应的所有照片信息
	 * @param personId
	 * @return
	 */
	public static List<PersonPicture> selectPictureWithPersonId(int personId){
		List<PersonPicture> personPictureList = new ArrayList<PersonPicture>();  
		try{
			//c = db.query("person_picture", new String[]{"pictureid","pictureblob","isfaceornot","uploadtime"}, "where personid=", new String[]{""+ personId}, null, null, "id");  
			c = db.rawQuery("select pictureid,pictureblob,isfaceornot,uploadtime from person_picture where personid='"+personId+"'", null);
			while (c.moveToNext()){
				PersonPicture personPicture = new PersonPicture();
				personPicture.setPictureId(c.getInt(0));
				personPicture.setPersonId(personId);
				personPicture.setPictureBlob(c.getBlob(1));
				personPicture.setIsFaceOrNot(c.getInt(2));
				personPicture.setUpLoadTime(c.getString(3));
				personPictureList.add(personPicture);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 c.close();
		}
		return personPictureList;
	}
	/**
	 * 根据用户ID查询对应的所有Blog信息
	 * @param personId
	 * @return
	 */
	public static List<PersonBlog> selectBlogWithPersonId(int personId){
		List<PersonBlog> personBlogList = new ArrayList<PersonBlog>();
		try{
			//c = db.query("person_blog", new String[]{"blogid","blogtitle","blogcontent","lasttime"}, "where personid=?", new String[]{""+ personId}, null, null, "blogid");  
			c = db.rawQuery("select blogid,blogtitle,blogcontent,lasttime from person_blog where personid='"+personId+"' order by blogid" , null);
			while (c.moveToNext()){
				PersonBlog personBlog = new PersonBlog();
				personBlog.setBlogId(c.getInt(0));
				personBlog.setPersonId(personId);
				personBlog.setBlogTitle(c.getString(1));
				personBlog.setBlogContent(c.getString(2));
				personBlog.setBlogLastTime(c.getString(3));
				personBlogList.add(personBlog);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 c.close();
		}
		return personBlogList;
	}
	/**
	 * 根据用户名查询是否存在该用户，用于判断该用户名是否可以注册 或者用户登录时判断是否注册
	 * @param userName
	 * @return
	 */
	public static boolean canThisUserNameRegister(String userName){
		boolean canRegister = false;
		try{
			c = db.rawQuery("select * from person_info where username = '" + userName + "'", null);  
			if(c.moveToNext()){
				canRegister = false;
			}else{
				canRegister = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 c.close();
		}
		return canRegister;
	}
	/**
	 * 首先根据用户名查询记录，再校验密码，用于登陆时校验
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public static String verifyWhenLogin(String userName,String passWord){
		String verifyResult = "";
		try{
			c = db.rawQuery("select password from person_info where username = '" + userName + "'", null);  
			/**
			 * 查询到结果，说明已经注册，则校验密码
			 * 查询不到说明还没有注册
			 */
			if(c.moveToNext()){
				if(c.getString(0).equals(passWord)){
					verifyResult = "clear";
				}else{
					verifyResult = "密码错误!";
				}
			}else{
				verifyResult = "还没有注册，请先注册!";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 c.close();
		}
		return verifyResult;
	}
	/**
	 * 根据用户名查询用户ID
	 * @param userName
	 * @return
	 */
	public static int selectPersinIdWithPersonName(String userName){
		int personId = -1;
		try{
			//c = db.query("person_info", new String[]{"personid"}, "where username=?", new String[]{userName}, null, null, null);  
			c = db.rawQuery("select persinid from person_info where username='"+userName+"'", null);
			c.moveToNext();
			personId = c.getInt(0);
		}catch(Exception e){
			return -1;
		}finally{
			 c.close();
		}
		return personId;
	}
	/**
	 * 根据用户ID查询用户所有基本信息
	 * @param userName
	 * @return
	 */
	public static PersonInfo selectPersonInfoWithPersonName(String userName){
		PersonInfo personInfo = null;
		try{
			//c = db.query("person_info", new String[]{"personid","personsign","registertime"}, "username", new String[]{userName}, null, null, null);  
			c = db.rawQuery("SELECT personid,personsign,registertime FROM person_info where username = '" + userName + "'", null);  
			c.moveToNext();
			personInfo = new PersonInfo(c.getInt(0),userName,null,c.getString(1),c.getString(2));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			 c.close();
		}
		return personInfo;
	}
	/**
	 * 根据用户ID查询Blog数量
	 * @param personId
	 * @return
	 */
	public static int selectBlogCountWithPersonId(int personId){
		int count = 0;
		try{
			c = db.rawQuery("SELECT COUNT(*) FROM person_blog where personid = '" + personId + "'", null);  
			c.moveToNext();  
			count =c.getInt(0);   
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			c.close();  
		}
		return count;
	}
	/**
	 * 根据blogId查询blog标题和内容，用于显示bolg
	 * @param bolgId
	 * @return
	 */
	public static String selectBlogWithBolgId(int bolgId){
		String bolgTitleAndContent = "";
		try{
			//c = db.query("person_blog", new String[]{"blogtitle","blogcontent"}, "where blogid=?", new String[]{""+ bolgId}, null, null, null);  
			c = db.rawQuery("select blogtitle,blogcontent from person_blog where blogid='" + bolgId +"'", null);
			c.moveToNext();
			bolgTitleAndContent = c.getString(0) + "|" + c.getString(1)	;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 c.close();
		}
		return bolgTitleAndContent;
	}
	/**
	 * 根据blogId和新的标题及内容更新日志
	 * @param blogId
	 * @param blogTitle
	 * @param blogContent
	 * @param lastTime
	 * @return
	 */
	public static boolean updateBlog(int blogId,String blogTitle,String blogContent,String lastTime){
		try{
			db.execSQL("UPDATE person_blog SET blogtitle=?, blogcontent=?,lasttime=? WHERE blogid=?", new Object[] {blogTitle,blogContent,lastTime,blogId}); 
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			 c.close();
		}
		return true;
	}
	/**
	 * 根据personId查询person_picture表获取头像
	 * @param personId
	 * @return
	 */
	public static byte[] selectFaceWithPersonId(int personId){
		byte[] faceByte = null;
		try{
			//c = db.query("person_picture", new String[]{"pictureblob"}, "where personid=? and isfaceornot=1", new String[]{""+ personId}, null, null, null);  
			c = db.rawQuery("select pictureblob from person_picture where isfaceornot=1 and personid='"+personId+"'", null);
			if(c.moveToNext()){
				faceByte = c.getBlob(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			c.close();
		}
		return faceByte;
	}
	/**
	 * 根据用户ID更新用户签名
	 * @param personId
	 * @param sign
	 * @return
	 */
	public static boolean updateSignWithPersonId(int personId,String sign){
		try{
			db.execSQL("UPDATE person_info SET personsign=? WHERE personid=?", new Object[] {sign,personId}); 
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			 c.close();
		}
		return true;
	}
	
	public static boolean updatePictureIsFaceOrNot(int personId){
		try{
			db.execSQL("UPDATE person_picture SET isfaceornot=? WHERE personid=?", new Object[] {0,personId}); 
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			 c.close();
		}
		return true;
	}
}
