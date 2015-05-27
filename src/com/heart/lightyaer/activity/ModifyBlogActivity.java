package com.heart.lightyaer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heart.lightyear.util.ActivityUtil;
import com.heart.lightyear.util.DBOperates;

public class ModifyBlogActivity extends Activity {
	/**
	 * 当前上下文环境
	 */
	private Context currentContext = ModifyBlogActivity.this;
	/**
	 * 日志标题输入框
	 */
	private EditText blogTitleEditText;
	/**
	 * 日志内容输入框
	 */
	private EditText blogContentText;
	/**
	 * 保存修改按钮
	 */
	private Button saveModifyBlogButton;
	/**
	 * 顶部显示的用户名文本框
	 */
	private TextView userNameViewBlogText;
	/**
	 * 用户名
	 */
	private String personName;
	/**
	 * 日志标题
	 */
	private String blogTitle;
	/**
	 * 日志内容
	 */
	private String blogContent;
	/**
	 * 日志ID
	 */
	private int blogId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.modifyblog);
		/**
		 * 获取传递来的参数
		 */
		Intent paramIntent = getIntent();
		blogId = paramIntent.getIntExtra("blogId", -1);
		personName = paramIntent.getStringExtra("personName");
		blogTitle = paramIntent.getStringExtra("blogTitle");
		blogContent = paramIntent.getStringExtra("blogContent");
		/**
		 * 初始化控件
		 */
		initView();
		
		
	}
	/**
	 * 初始化控件
	 */
	private void initView(){
		userNameViewBlogText = (TextView) this.findViewById(R.id.userNameModifyBlog);
		blogTitleEditText = (EditText) this.findViewById(R.id.blogNameModifyBlog);
		blogContentText = (EditText) this.findViewById(R.id.blogContentModifyBlog);
		saveModifyBlogButton = (Button) this.findViewById(R.id.saveModifyBlogButtonModifyBlog);
		/**
		 * 设置参数
		 */
		userNameViewBlogText.setText(personName);
		blogTitleEditText.setText(blogTitle);
		blogContentText.setText(blogContent);
		saveModifyBlogButton.setOnClickListener(saveModifyButtonListener);
	}
	/**
	 * 保存修改按钮处理事件
	 */
	private View.OnClickListener saveModifyButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			/**
			 * 保存处理
			 */
			saveModifyBlog();
		}
	};
	/**
	 * 保存修改日志
	 */
	private void saveModifyBlog(){
		try{
			String blogTitle = blogTitleEditText.getText().toString().trim();
			String blogContent = blogContentText.getText().toString().trim();
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
				 * 入库
				*/
				boolean registerResult = DBOperates.updateBlog(blogId, blogTitle, blogContent, ActivityUtil.dateFormat.format(System.currentTimeMillis()));
				if(registerResult){
					ActivityUtil.showMessageByToast(currentContext, "修改日志成功!");
					/**
					 * 修改完成后跳转到查看界面
					 */
					/*Intent gotoViewBlogIntent = new Intent(currentContext,ShowBlogActivity.class);
					gotoViewBlogIntent.putExtra("personName", personName);
					gotoViewBlogIntent.putExtra("blogId", blogId);
					gotoViewBlogIntent.putExtra("blogTitle", blogTitle);
					gotoViewBlogIntent.putExtra("blogContent", blogContent);
					gotoViewBlogIntent.putExtra("canModifyOrNot", true);
					startActivity(gotoViewBlogIntent);*/
					Intent gotoViewBlogIntent = new Intent();
					gotoViewBlogIntent.putExtra("blogTitle", blogTitle);
					gotoViewBlogIntent.putExtra("blogContent", blogContent);
					setResult(Activity.RESULT_OK, gotoViewBlogIntent);
					ModifyBlogActivity.this.finish();
				}else{
					ActivityUtil.showMessageByToast(currentContext, "修改日志失败!");
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
}
