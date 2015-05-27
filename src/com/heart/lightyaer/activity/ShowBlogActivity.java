package com.heart.lightyaer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ShowBlogActivity extends Activity {
	/**
	 * 当前上下文环境
	 */
	private Context currentContext = ShowBlogActivity.this;
	/**
	 * 顶部显示的用户名文本框
	 */
	private TextView userNameViewBlogText;
	/**
	 * 日志标题文本框
	 */
	private TextView blogNameViewBlogText;
	/**
	 * 日志内容文本框
	 */
	private TextView blogContentViewBlogText;
	/**
	 * 修改日志按钮
	 */
	private Button modifyBlogViewBlogButton;
	/**
	 * 用户名
	 */
	private String personName;
	/**
	 * 日志ID
	 */
	private int blogId;
	/**
	 * 日志标题
	 */
	private String blogTitle;
	/**
	 * 日志内容
	 */
	private String blogContent;
	/**
	 * 是否可以修改
	 */
	private boolean canModifyBlogOrNot;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.viewblog);
		/**
		 * 获取传递来的参数
		 */
		Intent getParamIntent = getIntent();
		personName = getParamIntent.getStringExtra("personName");
		blogId = getParamIntent.getIntExtra("blogId", -1);
		blogTitle = getParamIntent.getStringExtra("blogTitle");
		blogContent = getParamIntent.getStringExtra("blogContent");
		canModifyBlogOrNot = getParamIntent.getBooleanExtra("canModifyOrNot", false);
		/**
		 * 初始化控件
		 */
		initView();
		
	}
	/**
	 * 初始化控件
	 */
	private void initView(){
		userNameViewBlogText = (TextView) this.findViewById(R.id.userNameViewBlog);
		blogNameViewBlogText = (TextView) this.findViewById(R.id.blogNameViewBlog);
		blogContentViewBlogText = (TextView) this.findViewById(R.id.blogContentViewBlog);
		modifyBlogViewBlogButton = (Button) this.findViewById(R.id.modifyBlogButtonViewBlog);
		/**
		 * 根据获取的参数设置控件
		 */
		userNameViewBlogText.setText(personName);
		blogNameViewBlogText.setText(blogTitle);
		blogContentViewBlogText.setText(blogContent);
		/**
		 * 是否有修改权限
		 */
		if(!canModifyBlogOrNot){
			modifyBlogViewBlogButton.setVisibility(View.GONE);
		}else{
			modifyBlogViewBlogButton.setOnClickListener(modifyBlogButtonListener);
		}
	}
	/**
	 * 修改按钮事件
	 */
	private View.OnClickListener modifyBlogButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Intent modifyBlogIntent = new Intent(currentContext,ModifyBlogActivity.class);
			modifyBlogIntent.putExtra("personName", personName);
			modifyBlogIntent.putExtra("blogId", blogId);
			modifyBlogIntent.putExtra("blogTitle", blogTitle);
			modifyBlogIntent.putExtra("blogContent", blogContent);
			/**
			 * 还可以使用startActivityForResult
			 */
			//startActivity(modifyBlogIntent);
			startActivityForResult(modifyBlogIntent, 1);
		}
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			blogNameViewBlogText.setText(data.getStringExtra("blogTitle"));
			blogContentViewBlogText.setText(data.getStringExtra("blogContent"));
		}
	}
	
}
