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
	 * ��ǰ�����Ļ���
	 */
	private Context currentContext = ModifyBlogActivity.this;
	/**
	 * ��־���������
	 */
	private EditText blogTitleEditText;
	/**
	 * ��־���������
	 */
	private EditText blogContentText;
	/**
	 * �����޸İ�ť
	 */
	private Button saveModifyBlogButton;
	/**
	 * ������ʾ���û����ı���
	 */
	private TextView userNameViewBlogText;
	/**
	 * �û���
	 */
	private String personName;
	/**
	 * ��־����
	 */
	private String blogTitle;
	/**
	 * ��־����
	 */
	private String blogContent;
	/**
	 * ��־ID
	 */
	private int blogId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.modifyblog);
		/**
		 * ��ȡ�������Ĳ���
		 */
		Intent paramIntent = getIntent();
		blogId = paramIntent.getIntExtra("blogId", -1);
		personName = paramIntent.getStringExtra("personName");
		blogTitle = paramIntent.getStringExtra("blogTitle");
		blogContent = paramIntent.getStringExtra("blogContent");
		/**
		 * ��ʼ���ؼ�
		 */
		initView();
		
		
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView(){
		userNameViewBlogText = (TextView) this.findViewById(R.id.userNameModifyBlog);
		blogTitleEditText = (EditText) this.findViewById(R.id.blogNameModifyBlog);
		blogContentText = (EditText) this.findViewById(R.id.blogContentModifyBlog);
		saveModifyBlogButton = (Button) this.findViewById(R.id.saveModifyBlogButtonModifyBlog);
		/**
		 * ���ò���
		 */
		userNameViewBlogText.setText(personName);
		blogTitleEditText.setText(blogTitle);
		blogContentText.setText(blogContent);
		saveModifyBlogButton.setOnClickListener(saveModifyButtonListener);
	}
	/**
	 * �����޸İ�ť�����¼�
	 */
	private View.OnClickListener saveModifyButtonListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			/**
			 * ���洦��
			 */
			saveModifyBlog();
		}
	};
	/**
	 * �����޸���־
	 */
	private void saveModifyBlog(){
		try{
			String blogTitle = blogTitleEditText.getText().toString().trim();
			String blogContent = blogContentText.getText().toString().trim();
			/**
			 * У��
			 */
			if(blogTitle == null || "".equals(blogTitle)){
				ActivityUtil.showMessageByToast(currentContext, "��������־����!");
			}else if(blogContent == null || "".equals(blogContent)){
				ActivityUtil.showMessageByToast(currentContext, "��������־����!");
			}else{
				/**
				 * �����ݿ�
				 */
				DBOperates.openDatabase();
				/**
				 * ���
				*/
				boolean registerResult = DBOperates.updateBlog(blogId, blogTitle, blogContent, ActivityUtil.dateFormat.format(System.currentTimeMillis()));
				if(registerResult){
					ActivityUtil.showMessageByToast(currentContext, "�޸���־�ɹ�!");
					/**
					 * �޸���ɺ���ת���鿴����
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
					ActivityUtil.showMessageByToast(currentContext, "�޸���־ʧ��!");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
			 * �ر����ݿ�
			 */
			DBOperates.closeDatabase();
		}
		
	}
}
