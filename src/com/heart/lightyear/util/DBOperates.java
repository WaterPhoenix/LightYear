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
	 * ���ݿ���
	 */
	private static final String DB_NAME = "LightYear.db";
	/**
	 * ʹ�÷���ʱ��ʵ��
	 */
	private static Class<?> instance = null;
	/**
	 * �������
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
	 * �������ݿ�
	 * @param context
	 */
	public static void createDatabase(Context context){
		dbHelper = new DatabaseHelper(context,DB_NAME,createTableStatements);
	}
	/**
	 * �����ݿ⣬��ȡ���ݿ�ʵ��
	 * @return
	 */
	public static void openDatabase(){
		if(db == null){
			db = dbHelper.getWritableDatabase();
		}
	}
	/**
	 * �ر����ݿ�
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
	 * �����ݿ������һ�������и������Ե�����
	 * @param tableName      ����������
	 * @param obj            ����Ķ���
	 * @return
	 */
	public static boolean insertObject(String tableName,ObjectUtil obj){
		try{
			ContentValues cv = new ContentValues();  
			/**
			 * ��ȡ�����ݿ��ֶζ�Ӧ��get������
			 */
			HashMap<String,String> methodNameWithParams = obj.getMethodNameWithParams();
			String[] params = obj.getParams();
			for(String param : params){
				/**
				 * ��ȡget�����ķ���ֵ
				 */
				Object result = getValueFromMethod(obj,methodNameWithParams.get(param));
				/**
				 * ���ݲ�ͬ���������ͣ���cv�����
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
	 * ���ݷ������objʵ����Ӧ�����е�methodName�����ķ���ֵ
	 * @param obj          �����Ŀ�����ʵ��
	 * @param methodName   ��Ҫ��ȡ����ֵ�ķ�����
	 * @return
	 */
	public static Object getValueFromMethod(ObjectUtil obj,String methodName){
		Object result = null;
		try {
			instance = Class.forName(obj.getClassName());
			/**
			 * ����instance��Ӧ�����е�methodName����
			 */
			Method method=instance.getMethod(methodName);
			result = method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}
	
	/**
	 * ��ѯ���е�PersonInfo
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
	 * �����û�ID��ѯ��Ӧ��������Ƭ��Ϣ
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
	 * �����û�ID��ѯ��Ӧ������Blog��Ϣ
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
	 * �����û�����ѯ�Ƿ���ڸ��û��������жϸ��û����Ƿ����ע�� �����û���¼ʱ�ж��Ƿ�ע��
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
	 * ���ȸ����û�����ѯ��¼����У�����룬���ڵ�½ʱУ��
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public static String verifyWhenLogin(String userName,String passWord){
		String verifyResult = "";
		try{
			c = db.rawQuery("select password from person_info where username = '" + userName + "'", null);  
			/**
			 * ��ѯ�������˵���Ѿ�ע�ᣬ��У������
			 * ��ѯ����˵����û��ע��
			 */
			if(c.moveToNext()){
				if(c.getString(0).equals(passWord)){
					verifyResult = "clear";
				}else{
					verifyResult = "�������!";
				}
			}else{
				verifyResult = "��û��ע�ᣬ����ע��!";
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 c.close();
		}
		return verifyResult;
	}
	/**
	 * �����û�����ѯ�û�ID
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
	 * �����û�ID��ѯ�û����л�����Ϣ
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
	 * �����û�ID��ѯBlog����
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
	 * ����blogId��ѯblog��������ݣ�������ʾbolg
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
	 * ����blogId���µı��⼰���ݸ�����־
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
	 * ����personId��ѯperson_picture���ȡͷ��
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
	 * �����û�ID�����û�ǩ��
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
