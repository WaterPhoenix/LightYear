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
	 * ��ǰ�����Ļ���
	 */
	private Context currentContext = ShowBlogActivity.this;
	/**
	 * ������ʾ���û����ı���
	 */
	private TextView userNameViewBlogText;
	/**
	 * ��־�����ı���
	 */
	private TextView blogNameViewBlogText;
	/**
	 * ��־�����ı���
	 */
	private TextView blogContentViewBlogText;
	/**
	 * �޸���־��ť
	 */
	private Button modifyBlogViewBlogButton;
	/**
	 * �û���
	 */
	private String personName;
	/**
	 * ��־ID
	 */
	private int blogId;
	/**
	 * ��־����
	 */
	private String blogTitle;
	/**
	 * ��־����
	 */
	private String blogContent;
	/**
	 * �Ƿ�����޸�
	 */
	private boolean canModifyBlogOrNot;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.viewblog);
		/**
		 * ��ȡ�������Ĳ���
		 */
		Intent getParamIntent = getIntent();
		personName = getParamIntent.getStringExtra("personName");
		blogId = getParamIntent.getIntExtra("blogId", -1);
		blogTitle = getParamIntent.getStringExtra("blogTitle");
		blogContent = getParamIntent.getStringExtra("blogContent");
		canModifyBlogOrNot = getParamIntent.getBooleanExtra("canModifyOrNot", false);
		/**
		 * ��ʼ���ؼ�
		 */
		initView();
		
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView(){
		userNameViewBlogText = (TextView) this.findViewById(R.id.userNameViewBlog);
		blogNameViewBlogText = (TextView) this.findViewById(R.id.blogNameViewBlog);
		blogContentViewBlogText = (TextView) this.findViewById(R.id.blogContentViewBlog);
		modifyBlogViewBlogButton = (Button) this.findViewById(R.id.modifyBlogButtonViewBlog);
		/**
		 * ���ݻ�ȡ�Ĳ������ÿؼ�
		 */
		userNameViewBlogText.setText(personName);
		blogNameViewBlogText.setText(blogTitle);
		blogContentViewBlogText.setText(blogContent);
		/**
		 * �Ƿ����޸�Ȩ��
		 */
		if(!canModifyBlogOrNot){
			modifyBlogViewBlogButton.setVisibility(View.GONE);
		}else{
			modifyBlogViewBlogButton.setOnClickListener(modifyBlogButtonListener);
		}
	}
	/**
	 * �޸İ�ť�¼�
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
			 * ������ʹ��startActivityForResult
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
