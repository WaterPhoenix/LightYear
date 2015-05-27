package com.heart.lightyaer.activity;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.heart.lightyear.model.PersonBlog;
import com.heart.lightyear.model.PersonInfo;
import com.heart.lightyear.model.PersonPicture;
import com.heart.lightyear.util.ActivityUtil;
import com.heart.lightyear.util.DBOperates;

public class PersonPageActivity extends Activity {
	/**
	 * 当前上下文环境
	 */
	private Context currentContext = PersonPageActivity.this;
	/**
	 * 用户ID
	 */
	private int personId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 日志数量
	 */
	private int blogCount;
	/**
	 * 用户头像
	 */
	private byte[] imageByte;
	/**
	 * 用户签名
	 */
	private String sign;
	/**
	 * 用户注册时间
	 */
	private String registerTime;
	/**
	 * 用户名文本框
	 */
	private TextView userNameText;
	/**
	 * 注册时间文本框
	 */
	private TextView registerTimeText;
	/**
	 * 个人头像
	 */
	private ImageView personImage;
	/**
	 * 设置头像按钮
	 */
	private Button setImageButton;
	/**
	 * 个人签名
	 */
	private TextView personSignText;
	/**
	 * 编辑个人签名按钮
	 */
	private Button writeSignButton;
	/**
	 * 个人日志数量文本框
	 */
	private TextView personBlogCountText;
	/**
	 * 查看个人日志文本框
	 */
	private TextView viewPersonBlogListText;
	/**
	 * 编辑日志按钮
	 */
	private Button writeBlogButton;
	/**
	 * 布局加载器
	 */
	private LayoutInflater factory;
	/**
	 * 更新签名页面布局
	 */
	private View writeSignView;
	/**
	 * 弹出框实例
	 */
	private AlertDialog.Builder builder;
	/**
	 * 更新签名界面签名文本框
	 */
	private EditText writeSignText;
	/**
	 * 编写日志页面布局
	 */
	private View writeBlogView;
	/**
	 * 编写日志界面日志标题文本框
	 */
	private EditText writeBlogTitleText;
	/**
	 * 编写日志界面日志内容文本框
	 */
	private EditText writeBlogContentText;
	/**
	 * 修改头像界面
	 */
	private View changeFaceView;
	/**
	 * 修改头像界面上的从相册选择按钮
	 */
	private Button selectPictureButton;
	/**
	 * 修改头像界面上去照相按钮
	 */
	private Button picPhotoButton;
	/**
	 * 头像宽度
	 */
	private static final int PERSON_IMAGE_WIDTH = 30;
	/**
	 * 头像高度
	 */
	private static final int PERSON_IMAGE_HEIGHT = 30;
	/**
	 * 修改头像弹出框
	 */
	private Dialog selectPictureDialog;
	/**
	 * 用户设置的头像
	 */
	Bitmap bitmap = null;
	/**
	 * 用户头像
	 */
	Bitmap image = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.personpage);
		/**
		 * 获取传递来的参数
		 */
		Intent intent = getIntent();
		///personId = intent.getIntExtra("personId", -1);
		//blogCount = intent.getIntExtra("blogCount", 0);
		//imageByte = intent.getByteArrayExtra("faceImage");
		//sign = intent.getStringExtra("personSign");
		userName = intent.getStringExtra("userName");
		//registerTime = intent.getStringExtra("registerTime");
		/**
		 * 初始化控件
		 */
		initView();
		/**
		 * 查询用户信息并设置控件的数据
		 */
		queryPersonInfoAndSetValue(userName);
	}
	/**
	 * 初始化控件
	 */
	private void initView(){
		userNameText = (TextView) this.findViewById(R.id.userNamePersonPage);
		registerTimeText = (TextView) this.findViewById(R.id.registerTimeTextPersonPage);
		personImage = (ImageView) this.findViewById(R.id.imagePersonPage);
		setImageButton = (Button) this.findViewById(R.id.changeImageButtonPersonPage);
		personSignText = (TextView) this.findViewById(R.id.signTextPersonPage);
		writeSignButton = (Button) this.findViewById(R.id.changeSignButtonPersonPage);
		personBlogCountText = (TextView) this.findViewById(R.id.blogTextPersonPage);
		viewPersonBlogListText = (TextView) this.findViewById(R.id.viewBlogTextPersonPage);
		writeBlogButton = (Button) this.findViewById(R.id.writeBlogButtonPersonPage);
		/**
		 * 设置控件的数据
		 */
		/*userNameText.setText(userName);
		registerTimeText.setText(registerTime);
		if(imageByte == null){
		}else{
			image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
			personImage.setImageBitmap(image);
		}
		personSignText.setText(sign);
		personBlogCountText.setText(blogCount+"篇");*/
		/**
		 * 注册按钮事件
		 */
		setImageButton.setOnClickListener(onCilckListener);
		writeSignButton.setOnClickListener(onCilckListener);
		writeBlogButton.setOnClickListener(onCilckListener);
		viewPersonBlogListText.setOnClickListener(onCilckListener);
	}
	/**
	 * 根据用户名查询用户信息并设置
	 */
	private void queryPersonInfoAndSetValue(String userName){
		try{
			/**
			 * 打开数据库
			 */
			DBOperates.openDatabase();
			/**
			 * 查询并设置控件值
			 */
			PersonInfo personInfoSelect = DBOperates.selectPersonInfoWithPersonName(userName);
			personId = personInfoSelect.getPersonId();
			byte[] faceImage = DBOperates.selectFaceWithPersonId(personId);
			int blogCount = DBOperates.selectBlogCountWithPersonId(personId);
			userNameText.setText(userName);
			registerTimeText.setText(personInfoSelect.getRegisterTime());
			if(faceImage == null){
			}else{
				image = BitmapFactory.decodeByteArray(faceImage, 0, faceImage.length);
				personImage.setImageBitmap(image);
			}
			personSignText.setText(personInfoSelect.getPersonSign());
			personBlogCountText.setText(blogCount+"篇");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
			 * 关闭数据库
			 */
			DBOperates.closeDatabase();
		}
	}
	/**
     * 按钮事件监听器
     */
    private View.OnClickListener onCilckListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
				case R.id.changeImageButtonPersonPage:
					/**
					 * 处理设置头像按钮事件
					 */
					handleChangeImageButtonListener();
					break;
				case R.id.changeSignButtonPersonPage:
					/**
					 * 处理更新签名按钮事件
					 */
					handleChangeSignButtonListener();
					break;
				case R.id.viewBlogTextPersonPage:
					/**
					 * 跳转到日志列表页面，
					 * 传递用户ID，日志列表页面根据用户ID查询用户所有日志并显示
					 */
					Intent gotoBlogListActivity = new Intent(currentContext,BlogListActivity.class);
					gotoBlogListActivity.putExtra("canModifyOrNot", true);
					gotoBlogListActivity.putExtra("personId", personId);
					gotoBlogListActivity.putExtra("userName", userName);
					startActivity(gotoBlogListActivity);
					break;
				case R.id.writeBlogButtonPersonPage:
					/**
	        		 * 处理写日志按钮事件
	        		 */
					handleWriteBlogButtonListener();
					break;
			}
		}
	};
	/**
	 * 设置头像按钮事件处理
	 */
	private void handleChangeImageButtonListener(){
		factory = LayoutInflater.from(currentContext);
		builder = new AlertDialog.Builder(currentContext);
		changeFaceView = factory.inflate(R.layout.changeface, null);
		selectPictureButton = (Button) changeFaceView.findViewById(R.id.selectPictureChangeFace);
		picPhotoButton = (Button) changeFaceView.findViewById(R.id.picPhotoChangeFace);
		selectPictureButton.setOnClickListener(changeFaceOnClickListener);
		picPhotoButton.setOnClickListener(changeFaceOnClickListener);
		
		builder.setTitle("设置头像");
        builder.setView(changeFaceView);
        selectPictureDialog = builder.create();
        selectPictureDialog.show();
		
	}
	/**
	 * 从相册选择照片按钮和去照相按钮事件处理
	 */
	private OnClickListener changeFaceOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Intent intent = null;
			selectPictureDialog.cancel();
			switch(view.getId()){
			case R.id.selectPictureChangeFace:
				intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
			    intent.setType("image/*");
			    intent.putExtra("crop", "true");
			    intent.putExtra("aspectX", 1);
			    intent.putExtra("aspectY", 1);
			    intent.putExtra("outputX", PERSON_IMAGE_WIDTH);
			    intent.putExtra("outputY", PERSON_IMAGE_HEIGHT);
			    intent.putExtra("return-data", true);
				startActivityForResult(Intent.createChooser(intent, "selectPicture"), 1);
				break;
			case R.id.picPhotoChangeFace:
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent,2);
				break;
			}
		}
	};
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try{
			//Bitmap bitmap = null;
			switch(requestCode){
			case 1:
				/**
				 * 从相册选择照片
				 */
				bitmap = (Bitmap) data.getExtras().get("data");
				personImage.setImageBitmap(bitmap);
				break;
			case 2:
				/**
				 * 去照相
				 */
				Uri uriPohto = data.getData();
				bitmap = BitmapFactory.decodeFile(uriPohto.getPath());
				/**
				 * 压缩图片
				 */
				/*int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				int newWidth = PERSON_IMAGE_WIDTH;
				int newHeight = PERSON_IMAGE_HEIGHT;
				float scaleWidth = ((float) newWidth) / width;  
		    	float scaleHeight = ((float) newHeight) / height;
		    	Matrix matrix = new Matrix();
		    	matrix.postScale(scaleWidth, scaleHeight);
		    	bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);*/
		    	personImage.setImageBitmap(bitmap);
		    	break;
			}
			/**
	    	 * 将图片存入数据库
	    	 */
	    	DBOperates.openDatabase();
	    	/**
	    	 * 组装照片对象
	    	 */
	    	PersonPicture personPicture = new PersonPicture();
	    	personPicture.setPersonId(personId);
	    	personPicture.setIsFaceOrNot(1);
	    	personPicture.setUpLoadTime(ActivityUtil.dateFormat.format(System.currentTimeMillis()));
	    	ByteArrayOutputStream os = new ByteArrayOutputStream(); 
	    	bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
	    	personPicture.setPictureBlob(os.toByteArray());
	    	os.close();
	    	/**
	    	 * 修改原来头像的IsFaceOrNot字段为0
	    	 */
	    	DBOperates.updatePictureIsFaceOrNot(personId);
	    	/**
	    	 * 入库
	    	 */
	    	DBOperates.insertObject("person_picture", personPicture);
	    	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
	    	 * 关闭数据库
	    	 */
	    	DBOperates.closeDatabase();
		}
	}
	/**
	 * 写日志是事件处理
	 */
	public void handleWriteBlogButtonListener(){
		factory = LayoutInflater.from(currentContext);
		builder = new AlertDialog.Builder(currentContext);
		writeBlogView = factory.inflate(R.layout.writeblog, null);
		writeBlogTitleText = (EditText) writeBlogView.findViewById(R.id.blogNameTextWriteBlog);
		writeBlogContentText = (EditText) writeBlogView.findViewById(R.id.blogContentTextWriteBlog);
	
		builder.setTitle("书写心情");
        builder.setView(writeBlogView);
        builder.setPositiveButton("发表", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		/**
        		 * 发表日志处理方法
        		 */
        		publicBlog();
        	}
        });
    	builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    		}
    	});
    	builder.create().show();
	}
	/**
	 * 发表日志
	 */
	private void publicBlog(){
		try{
			String blogTitle = writeBlogTitleText.getText().toString().trim();
			String blogContent = writeBlogContentText.getText().toString().trim();
			/**
			 * 校验
			 */
			if(blogTitle == null || "".equals(blogTitle)){
				ActivityUtil.showMessageByToast(currentContext, "请输入日志标题!");
			}else if(blogContent == null || "".equals(blogContent)){
				ActivityUtil.showMessageByToast(currentContext, "请输入日志内容!");
			}else{
				/**
				 * 打开数据库
				 */
				DBOperates.openDatabase();
				/**
				 * 组装blog对象
				 */
				PersonBlog personBlog = new PersonBlog();
				personBlog.setPersonId(personId);
				personBlog.setBlogTitle(blogTitle);
				personBlog.setBlogContent(blogContent);
				personBlog.setBlogLastTime(ActivityUtil.dateFormat.format(System.currentTimeMillis()));
				/**
				 * 入库
				*/
				boolean registerResult = DBOperates.insertObject("person_blog", personBlog);
				if(registerResult){
					ActivityUtil.showMessageByToast(currentContext, "发表日志成功!");
					/**
					 * 更新界面日志数量
					 */
					blogCount = blogCount + 1;
					personBlogCountText.setText(blogCount+"篇");
				}else{
					ActivityUtil.showMessageByToast(currentContext, "发表日志失败!");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
			 * 关闭数据库
			 */
			DBOperates.closeDatabase();
		}
		
	}
	/**
	 * 处理更新签名按钮事件，弹出输入新签名界面，
	 * 用户输入后发表，入库并更新界面内容
	 */
	private void handleChangeSignButtonListener(){
		factory = LayoutInflater.from(currentContext);
		builder = new AlertDialog.Builder(currentContext);
        writeSignView = factory.inflate(R.layout.changesign, null);
        writeSignText = (EditText) writeSignView.findViewById(R.id.signTextChangeSign);
        
        builder.setTitle("更新签名");
        builder.setView(writeSignView);
        builder.setPositiveButton("发表", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		updateSign();
        	}
        });
    	builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    		}
    	});
    	builder.create().show();
	}
	/**
	 * 更新签名
	 */
	private void updateSign(){
		try{
			String newSign = writeSignText.getText().toString().trim();
			if(newSign == null){
				newSign = "";
			}
			/**
			 * 打开数据库
			 */
			DBOperates.openDatabase();
			/**
			 * 更新数据库，成功后更新界面上的签名控件内容
			 */
			if(DBOperates.updateSignWithPersonId(personId, newSign)){
				ActivityUtil.showMessageByToast(currentContext, "修改签名成功!");
				personSignText.setText(newSign);
			}else{
				ActivityUtil.showMessageByToast(currentContext, "修改签名失败!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
			 * 关闭数据库
			 */
			DBOperates.closeDatabase();
		}
	}
}
