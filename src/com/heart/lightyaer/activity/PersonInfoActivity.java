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
	 * ��ǰ�����Ļ���
	 */
	private Context currentContext = PersonInfoActivity.this;
	/**
	 * ������ʾ���û����ı���
	 */
	private TextView userNamePersonInfoText;
	/**
	 * ����ͷ��ؼ�
	 */
	private ImageView viewPersonFaceImage;
	/**
	 * ���������ı���
	 */
	private TextView viewPersonNameText;
	/**
	 * ����ǩ���ı���
	 */
	private TextView viewPersonSignText;
	/**
	 * ����ע��ʱ���ı���
	 */
	private TextView viewPersonRegisterTimeText;
	/**
	 * ������־�����ı���
	 */
	private TextView viewPersonBlogCountText;
	/**
	 * ���Ѳ鿴��־�ı���
	 */
	private TextView viewPersonBlogListText;
	/**
	 * ��ǰ�û���
	 */
	//private String personName;
	/**
	 * �鿴�ĺ���ID
	 */
	private int viewPersonId;
	/**
	 * �鿴�ĺ�����
	 */
	private String viewPersonName;
	/**
	 * �鿴�ĺ���ǩ��
	 */
	private String viewPersonSign;
	/**
	 * �鿴�ĺ���ע��ʱ��
	 */
	private String viewPersonRegisterTime;
	/**
	 * ����ͷ��
	 */
	private Bitmap viewPersonBitmap;
	/**
	 * ������־����
	 */
	private int viewPersonBlogCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.personinfo);
		/**
		 * ��ȡ�������Ĳ���
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
		 * ��ʼ���ؼ�
		 */
		initView();
		
	}
	/**
	 * �鿴��־�ı�����¼�
	 */
	private OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			/**
			 * ��ת����־�б�ҳ�棬
			 * �����û�ID����־�б�ҳ������û�ID��ѯ�û�������־����ʾ
			 */
			Intent gotoBlogListActivity = new Intent(currentContext,BlogListActivity.class);
			gotoBlogListActivity.putExtra("canModifyOrNot", false);
			/**
			 * �鿴����ID
			 */
			gotoBlogListActivity.putExtra("personId", viewPersonId);
			/**
			 * �鿴������
			 */
			gotoBlogListActivity.putExtra("userName", viewPersonName);
			startActivity(gotoBlogListActivity);
		}
	};
	/**
	 * ��ʼ���ؼ�
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
		viewPersonBlogCountText.setText(viewPersonBlogCount+"ƪ");
		if(viewPersonBitmap == null){
		}else{
			viewPersonFaceImage.setImageBitmap(viewPersonBitmap);
		}
	}
	/**
	 * ���ݺ���ID��ѯ��־����
	 */
	private int getBlogCountByPersonId(int personId){
		int blogCount = -1;
		try{
			/**
			 * �����ݿ�
			 */
			DBOperates.openDatabase();
			/**
			 * ��ѯ����
			 */
			blogCount = DBOperates.selectBlogCountWithPersonId(personId);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
			 * �ر����ݿ�
			 */
			DBOperates.closeDatabase();
		}
		return blogCount;
	}
	

}
