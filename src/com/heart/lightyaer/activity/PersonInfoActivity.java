package com.heart.lightyaer.activity;

import com.heart.lightyear.util.DBOperates;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonInfoActivity extends Activity {
	/**
	 * 当前上下文环境
	 */
	private Context currentContext = PersonInfoActivity.this;
	/**
	 * 顶部显示的用户名文本框
	 */
	private TextView userNamePersonInfoText;
	/**
	 * 好友头像控件
	 */
	private ImageView viewPersonFaceImage;
	/**
	 * 好友姓名文本框
	 */
	private TextView viewPersonNameText;
	/**
	 * 好友签名文本框
	 */
	private TextView viewPersonSignText;
	/**
	 * 好友注册时间文本框
	 */
	private TextView viewPersonRegisterTimeText;
	/**
	 * 好友日志数量文本框
	 */
	private TextView viewPersonBlogCountText;
	/**
	 * 好友查看日志文本框
	 */
	private TextView viewPersonBlogListText;
	/**
	 * 当前用户名
	 */
	//private String personName;
	/**
	 * 查看的好友ID
	 */
	private int viewPersonId;
	/**
	 * 查看的好友名
	 */
	private String viewPersonName;
	/**
	 * 查看的好友签名
	 */
	private String viewPersonSign;
	/**
	 * 查看的好友注册时间
	 */
	private String viewPersonRegisterTime;
	/**
	 * 好友头像
	 */
	private Bitmap viewPersonBitmap;
	/**
	 * 好友日志数量
	 */
	private int viewPersonBlogCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.personinfo);
		/**
		 * 获取传递来的参数
		 */
		Intent intentParams = getIntent();
		//personName = intentParams.getStringExtra("personName");
		viewPersonId = intentParams.getIntExtra("viewPersonId", -1);
		viewPersonName = intentParams.getStringExtra("viewPersonName");
		viewPersonSign = intentParams.getStringExtra("viewPersonSign");
		viewPersonRegisterTime = intentParams.getStringExtra("viewPersonRegisterTime");
		byte[] viewPersonFace = intentParams.getByteArrayExtra("viewPersonFace");
		if(viewPersonFace == null){
		}else{
			viewPersonBitmap = BitmapFactory.decodeByteArray(viewPersonFace, 0, viewPersonFace.length);
		}
		/**
		 * 初始化控件
		 */
		initView();
		
	}
	/**
	 * 查看日志文本点击事件
	 */
	private OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			/**
			 * 跳转到日志列表页面，
			 * 传递用户ID，日志列表页面根据用户ID查询用户所有日志并显示
			 */
			Intent gotoBlogListActivity = new Intent(currentContext,BlogListActivity.class);
			gotoBlogListActivity.putExtra("canModifyOrNot", false);
			/**
			 * 查看好友ID
			 */
			gotoBlogListActivity.putExtra("personId", viewPersonId);
			/**
			 * 查看好友名
			 */
			gotoBlogListActivity.putExtra("userName", viewPersonName);
			startActivity(gotoBlogListActivity);
		}
	};
	/**
	 * 初始化控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		userNamePersonInfoText = (TextView) this.findViewById(R.id.userNamePersonInfo);
		viewPersonFaceImage = (ImageView) this.findViewById(R.id.imagePersonInfo);
		viewPersonNameText = (TextView) this.findViewById(R.id.loadNumTextPersonInfo);
		viewPersonSignText = (TextView) this.findViewById(R.id.signTextPersonInfo);
		viewPersonRegisterTimeText = (TextView) this.findViewById(R.id.registerTextPersonInfo);
		viewPersonBlogCountText = (TextView) this.findViewById(R.id.blogTextPersonInfo);
		viewPersonBlogListText = (TextView) this.findViewById(R.id.viewBlogTextPersonInfo);
		
		viewPersonBlogCount = getBlogCountByPersonId(viewPersonId);
		viewPersonBlogListText.setOnClickListener(onClickListener);
		
		userNamePersonInfoText.setText(viewPersonName);
		viewPersonNameText.setText(viewPersonName);
		viewPersonSignText.setText(viewPersonSign);
		viewPersonRegisterTimeText.setText(viewPersonRegisterTime);
		viewPersonBlogCountText.setText(viewPersonBlogCount+"篇");
		if(viewPersonBitmap == null){
		}else{
			viewPersonFaceImage.setImageBitmap(viewPersonBitmap);
		}
	}
	/**
	 * 根据好友ID查询日志数量
	 */
	private int getBlogCountByPersonId(int personId){
		int blogCount = -1;
		try{
			/**
			 * 打开数据库
			 */
			DBOperates.openDatabase();
			/**
			 * 查询数据
			 */
			blogCount = DBOperates.selectBlogCountWithPersonId(personId);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
			 * 关闭数据库
			 */
			DBOperates.closeDatabase();
		}
		return blogCount;
	}
	

}
