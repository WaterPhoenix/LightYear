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
	 * ��ǰ�����Ļ���
	 */
	private Context currentContext = PersonPageActivity.this;
	/**
	 * �û�ID
	 */
	private int personId;
	/**
	 * �û���
	 */
	private String userName;
	/**
	 * ��־����
	 */
	private int blogCount;
	/**
	 * �û�ͷ��
	 */
	private byte[] imageByte;
	/**
	 * �û�ǩ��
	 */
	private String sign;
	/**
	 * �û�ע��ʱ��
	 */
	private String registerTime;
	/**
	 * �û����ı���
	 */
	private TextView userNameText;
	/**
	 * ע��ʱ���ı���
	 */
	private TextView registerTimeText;
	/**
	 * ����ͷ��
	 */
	private ImageView personImage;
	/**
	 * ����ͷ��ť
	 */
	private Button setImageButton;
	/**
	 * ����ǩ��
	 */
	private TextView personSignText;
	/**
	 * �༭����ǩ����ť
	 */
	private Button writeSignButton;
	/**
	 * ������־�����ı���
	 */
	private TextView personBlogCountText;
	/**
	 * �鿴������־�ı���
	 */
	private TextView viewPersonBlogListText;
	/**
	 * �༭��־��ť
	 */
	private Button writeBlogButton;
	/**
	 * ���ּ�����
	 */
	private LayoutInflater factory;
	/**
	 * ����ǩ��ҳ�沼��
	 */
	private View writeSignView;
	/**
	 * ������ʵ��
	 */
	private AlertDialog.Builder builder;
	/**
	 * ����ǩ������ǩ���ı���
	 */
	private EditText writeSignText;
	/**
	 * ��д��־ҳ�沼��
	 */
	private View writeBlogView;
	/**
	 * ��д��־������־�����ı���
	 */
	private EditText writeBlogTitleText;
	/**
	 * ��д��־������־�����ı���
	 */
	private EditText writeBlogContentText;
	/**
	 * �޸�ͷ�����
	 */
	private View changeFaceView;
	/**
	 * �޸�ͷ������ϵĴ����ѡ��ť
	 */
	private Button selectPictureButton;
	/**
	 * �޸�ͷ�������ȥ���ఴť
	 */
	private Button picPhotoButton;
	/**
	 * ͷ����
	 */
	private static final int PERSON_IMAGE_WIDTH = 30;
	/**
	 * ͷ��߶�
	 */
	private static final int PERSON_IMAGE_HEIGHT = 30;
	/**
	 * �޸�ͷ�񵯳���
	 */
	private Dialog selectPictureDialog;
	/**
	 * �û����õ�ͷ��
	 */
	Bitmap bitmap = null;
	/**
	 * �û�ͷ��
	 */
	Bitmap image = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.personpage);
		/**
		 * ��ȡ�������Ĳ���
		 */
		Intent intent = getIntent();
		///personId = intent.getIntExtra("personId", -1);
		//blogCount = intent.getIntExtra("blogCount", 0);
		//imageByte = intent.getByteArrayExtra("faceImage");
		//sign = intent.getStringExtra("personSign");
		userName = intent.getStringExtra("userName");
		//registerTime = intent.getStringExtra("registerTime");
		/**
		 * ��ʼ���ؼ�
		 */
		initView();
		/**
		 * ��ѯ�û���Ϣ�����ÿؼ�������
		 */
		queryPersonInfoAndSetValue(userName);
	}
	/**
	 * ��ʼ���ؼ�
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
		 * ���ÿؼ�������
		 */
		/*userNameText.setText(userName);
		registerTimeText.setText(registerTime);
		if(imageByte == null){
		}else{
			image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
			personImage.setImageBitmap(image);
		}
		personSignText.setText(sign);
		personBlogCountText.setText(blogCount+"ƪ");*/
		/**
		 * ע�ᰴť�¼�
		 */
		setImageButton.setOnClickListener(onCilckListener);
		writeSignButton.setOnClickListener(onCilckListener);
		writeBlogButton.setOnClickListener(onCilckListener);
		viewPersonBlogListText.setOnClickListener(onCilckListener);
	}
	/**
	 * �����û�����ѯ�û���Ϣ������
	 */
	private void queryPersonInfoAndSetValue(String userName){
		try{
			/**
			 * �����ݿ�
			 */
			DBOperates.openDatabase();
			/**
			 * ��ѯ�����ÿؼ�ֵ
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
			personBlogCountText.setText(blogCount+"ƪ");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
			 * �ر����ݿ�
			 */
			DBOperates.closeDatabase();
		}
	}
	/**
     * ��ť�¼�������
     */
    private View.OnClickListener onCilckListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
				case R.id.changeImageButtonPersonPage:
					/**
					 * ��������ͷ��ť�¼�
					 */
					handleChangeImageButtonListener();
					break;
				case R.id.changeSignButtonPersonPage:
					/**
					 * �������ǩ����ť�¼�
					 */
					handleChangeSignButtonListener();
					break;
				case R.id.viewBlogTextPersonPage:
					/**
					 * ��ת����־�б�ҳ�棬
					 * �����û�ID����־�б�ҳ������û�ID��ѯ�û�������־����ʾ
					 */
					Intent gotoBlogListActivity = new Intent(currentContext,BlogListActivity.class);
					gotoBlogListActivity.putExtra("canModifyOrNot", true);
					gotoBlogListActivity.putExtra("personId", personId);
					gotoBlogListActivity.putExtra("userName", userName);
					startActivity(gotoBlogListActivity);
					break;
				case R.id.writeBlogButtonPersonPage:
					/**
	        		 * ����д��־��ť�¼�
	        		 */
					handleWriteBlogButtonListener();
					break;
			}
		}
	};
	/**
	 * ����ͷ��ť�¼�����
	 */
	private void handleChangeImageButtonListener(){
		factory = LayoutInflater.from(currentContext);
		builder = new AlertDialog.Builder(currentContext);
		changeFaceView = factory.inflate(R.layout.changeface, null);
		selectPictureButton = (Button) changeFaceView.findViewById(R.id.selectPictureChangeFace);
		picPhotoButton = (Button) changeFaceView.findViewById(R.id.picPhotoChangeFace);
		selectPictureButton.setOnClickListener(changeFaceOnClickListener);
		picPhotoButton.setOnClickListener(changeFaceOnClickListener);
		
		builder.setTitle("����ͷ��");
        builder.setView(changeFaceView);
        selectPictureDialog = builder.create();
        selectPictureDialog.show();
		
	}
	/**
	 * �����ѡ����Ƭ��ť��ȥ���ఴť�¼�����
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
				 * �����ѡ����Ƭ
				 */
				bitmap = (Bitmap) data.getExtras().get("data");
				personImage.setImageBitmap(bitmap);
				break;
			case 2:
				/**
				 * ȥ����
				 */
				Uri uriPohto = data.getData();
				bitmap = BitmapFactory.decodeFile(uriPohto.getPath());
				/**
				 * ѹ��ͼƬ
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
	    	 * ��ͼƬ�������ݿ�
	    	 */
	    	DBOperates.openDatabase();
	    	/**
	    	 * ��װ��Ƭ����
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
	    	 * �޸�ԭ��ͷ���IsFaceOrNot�ֶ�Ϊ0
	    	 */
	    	DBOperates.updatePictureIsFaceOrNot(personId);
	    	/**
	    	 * ���
	    	 */
	    	DBOperates.insertObject("person_picture", personPicture);
	    	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
	    	 * �ر����ݿ�
	    	 */
	    	DBOperates.closeDatabase();
		}
	}
	/**
	 * д��־���¼�����
	 */
	public void handleWriteBlogButtonListener(){
		factory = LayoutInflater.from(currentContext);
		builder = new AlertDialog.Builder(currentContext);
		writeBlogView = factory.inflate(R.layout.writeblog, null);
		writeBlogTitleText = (EditText) writeBlogView.findViewById(R.id.blogNameTextWriteBlog);
		writeBlogContentText = (EditText) writeBlogView.findViewById(R.id.blogContentTextWriteBlog);
	
		builder.setTitle("��д����");
        builder.setView(writeBlogView);
        builder.setPositiveButton("����", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		/**
        		 * ������־������
        		 */
        		publicBlog();
        	}
        });
    	builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    		}
    	});
    	builder.create().show();
	}
	/**
	 * ������־
	 */
	private void publicBlog(){
		try{
			String blogTitle = writeBlogTitleText.getText().toString().trim();
			String blogContent = writeBlogContentText.getText().toString().trim();
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
				 * ��װblog����
				 */
				PersonBlog personBlog = new PersonBlog();
				personBlog.setPersonId(personId);
				personBlog.setBlogTitle(blogTitle);
				personBlog.setBlogContent(blogContent);
				personBlog.setBlogLastTime(ActivityUtil.dateFormat.format(System.currentTimeMillis()));
				/**
				 * ���
				*/
				boolean registerResult = DBOperates.insertObject("person_blog", personBlog);
				if(registerResult){
					ActivityUtil.showMessageByToast(currentContext, "������־�ɹ�!");
					/**
					 * ���½�����־����
					 */
					blogCount = blogCount + 1;
					personBlogCountText.setText(blogCount+"ƪ");
				}else{
					ActivityUtil.showMessageByToast(currentContext, "������־ʧ��!");
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
	/**
	 * �������ǩ����ť�¼�������������ǩ�����棬
	 * �û�����󷢱���Ⲣ���½�������
	 */
	private void handleChangeSignButtonListener(){
		factory = LayoutInflater.from(currentContext);
		builder = new AlertDialog.Builder(currentContext);
        writeSignView = factory.inflate(R.layout.changesign, null);
        writeSignText = (EditText) writeSignView.findViewById(R.id.signTextChangeSign);
        
        builder.setTitle("����ǩ��");
        builder.setView(writeSignView);
        builder.setPositiveButton("����", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		updateSign();
        	}
        });
    	builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    		}
    	});
    	builder.create().show();
	}
	/**
	 * ����ǩ��
	 */
	private void updateSign(){
		try{
			String newSign = writeSignText.getText().toString().trim();
			if(newSign == null){
				newSign = "";
			}
			/**
			 * �����ݿ�
			 */
			DBOperates.openDatabase();
			/**
			 * �������ݿ⣬�ɹ�����½����ϵ�ǩ���ؼ�����
			 */
			if(DBOperates.updateSignWithPersonId(personId, newSign)){
				ActivityUtil.showMessageByToast(currentContext, "�޸�ǩ���ɹ�!");
				personSignText.setText(newSign);
			}else{
				ActivityUtil.showMessageByToast(currentContext, "�޸�ǩ��ʧ��!");
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
