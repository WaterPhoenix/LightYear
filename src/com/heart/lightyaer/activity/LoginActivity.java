package com.heart.lightyaer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.heart.lightyear.model.PersonInfo;
import com.heart.lightyear.util.ActivityUtil;
import com.heart.lightyear.util.Constant;
import com.heart.lightyear.util.DBOperates;
import com.heart.lightyear.util.ObjectUtil;

public class LoginActivity extends Activity {
    /** Called when the activity is first created. */
	/**
	 * ��ǰ�����Ļ���
	 */
	private Context currentContext = LoginActivity.this;
	/**
	 * �û����ı���
	 */
	private EditText userNameText;
	/**
	 * �����ı���
	 */
	private EditText passWordText;
	/**
	 * �û���
	 */
	private String userName;
	/**
	 * ����
	 */
	private String passWord;
	/**
	 * ��������ѡ��ť
	 */
	private CheckBox savePassCheckBox;
	/**
	 * �Զ���¼ѡ��ť
	 */
	private CheckBox loginSelfCheckBox;
	/**
	 * �Ƿ񱣴������־λ
	 */
	private boolean savePassOrNot = false;
	/**
	 * �Ƿ��Զ���¼��־λ
	 */
	private boolean loginSelfOrNot = false;
	/**
	 * ��¼��ť
	 */
	private Button loginButton;
	/**
	 * ע�ᰴť
	 */
	private Button registerButton;
	/**
	 * ���ּ�����
	 */
	private LayoutInflater factory;
	/**
	 * ע��ҳ�沼��
	 */
	private View registerView;
	/**
	 * ������ʵ��
	 */
	private AlertDialog.Builder builder;
	/**
	 * ע������û����ı���
	 */
	private EditText userNameRegisterText;
	/**
	 * ע����������ı���
	 */
	private EditText passWordRegisterText;
	/**
	 * ע���������ȷ���ı���
	 */
	private EditText passWordSureRegisterText;
	/**
	 * ע�����ע����ɺ��Զ���¼
	 */
	private CheckBox loginSelfAfterRegisterCheckBox;
	/**
	 * ע�����Ƿ��Զ���¼
	 */
	private boolean loginSelfAfterRegisterOrNot = false;
	
	private Constant constant;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.login);
        /**
         * ��ʼ���ؼ�
         */
        initView();
    }
    /**
     * ��ʼ���ؼ���������¼�
     */
    private void initView(){
    	userNameText = (EditText) this.findViewById(R.id.userNameTextLogin);
    	passWordText = (EditText) this.findViewById(R.id.userPassTextLogin);
    	savePassCheckBox = (CheckBox) this.findViewById(R.id.checkSavePassLogin);
    	loginSelfCheckBox = (CheckBox) this.findViewById(R.id.checkSelfLoadLogin);
    	loginButton = (Button) this.findViewById(R.id.loginButtonLogin);
    	registerButton = (Button) this.findViewById(R.id.registerButtonLogin);
    	/**
    	 * ��ťע�������
    	 */
    	savePassCheckBox.setOnClickListener(buttonOnCilckListener);
    	loginSelfCheckBox.setOnClickListener(buttonOnCilckListener);
    	loginButton.setOnClickListener(buttonOnCilckListener);
    	registerButton.setOnClickListener(buttonOnCilckListener);
    	
    	/**
    	 * �Ƿ񱣴�������,�Զ���¼
    	 */
    	constant = new Constant(LoginActivity.this);
    	if(constant.getLoadSelf().equals("1")){
    		savePassCheckBox.setChecked(true);
    		loginSelfCheckBox.setChecked(true);
    		loginSelfOrNot = true;
    		userNameText.setText(constant.getUserName());
    		passWordText.setText(constant.getPassWord());
    		handleLoginButtonListener(constant.getUserName(),constant.getPassWord());
    	}else if(constant.getSavePass().equals("1")){
    		savePassCheckBox.setChecked(true);
    		savePassOrNot = true;
    		userNameText.setText(constant.getUserName());
    		passWordText.setText(constant.getPassWord());
    	}
    }
    /**
     * ��ť�¼�������
     */
    private View.OnClickListener buttonOnCilckListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
				case R.id.loginButtonLogin:
					userName = userNameText.getText().toString().trim();
					passWord = passWordText.getText().toString().trim();
					handleLoginButtonListener(userName,passWord);
					break;
				case R.id.registerButtonLogin:
					handleRegisterButtonListener();
					break;
				case R.id.checkSavePassLogin:
					if(savePassCheckBox.isChecked()){
						savePassOrNot = true;
					}else{
						savePassOrNot = false;
					}
					break;
				case R.id.checkSelfLoadLogin:
					if(loginSelfCheckBox.isChecked()){
						savePassCheckBox.setChecked(true);
						savePassOrNot = true;
						loginSelfOrNot = true;
					}else{
						loginSelfOrNot = false;
					}
					break;
				case R.id.checkLoadAfterRegister:
					if(loginSelfAfterRegisterCheckBox.isChecked()){
						loginSelfAfterRegisterOrNot = true;
					}else{
						loginSelfAfterRegisterOrNot = false;
					}
					break;
			}
		}
	};
	/**
	 * �����¼��ť�¼�
	 */
	private void handleLoginButtonListener(String userName,String passWord){
		try{
			/**
			 * У���û����������������ȷ��
			 */
			if(userName == null || "".equals(userName)){
				ActivityUtil.showMessageByToast(currentContext, "�������û���!");
			}else if(passWord == null || "".equals(passWord)){
				ActivityUtil.showMessageByToast(currentContext, "����������!");
			}else{
				/**
				 * �����ݿ�
				 */
				DBOperates.openDatabase();
				/**
				 * У���û��Ƿ��Ѿ�ע��
				 */
				String loginResult = DBOperates.verifyWhenLogin(userName, passWord);
				
				if(!loginResult.equals("clear")){
					/**
					 * ����У��ʧ��
					 */
					ActivityUtil.showMessageByToast(currentContext, loginResult);
					userNameText.setText("");
					passWordText.setText("");
					
				}else{
					/**
					 * У��ͨ������ѯ�û���Ϣ����ת��������ҳ
					 */
				   PersonInfo personInfoSelect = DBOperates.selectPersonInfoWithPersonName(userName);
				   if(personInfoSelect == null){
					   ActivityUtil.showMessageByToast(currentContext, "��ѯ�û���ϢΪ��!");
				   }else{
					   /**
					    * ����������Ϣ
					    */
					    constant.saveConfig(loginSelfOrNot, savePassOrNot, userName, passWord);
						
					    //int personId = personInfoSelect.getPersonId();
					    //byte[] faceImage = DBOperates.selectFaceWithPersonId(personId);
					    //int blogCount = DBOperates.selectBlogCountWithPersonId(personId);
						
					    Intent toPersonActivityIntent = new Intent(currentContext,TabView.class);
					    //Intent toPersonActivityIntent = new Intent(currentContext,PersonPageActivity.class);
					    //Intent toPersonActivityIntent = new Intent(currentContext,PersonInfoListActivity.class);
					    
					    //toPersonActivityIntent.putExtra("personId", personInfoSelect.getPersonId());
						toPersonActivityIntent.putExtra("userName", userName);
						//toPersonActivityIntent.putExtra("personSign", personInfoSelect.getPersonSign());
						//toPersonActivityIntent.putExtra("registerTime", personInfoSelect.getRegisterTime());
						//toPersonActivityIntent.putExtra("faceImage", faceImage);
						//toPersonActivityIntent.putExtra("blogCount", blogCount);
						startActivity(toPersonActivityIntent);
						
						LoginActivity.this.finish();
				   }
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
	 * ����ע�ᰴť�¼�������ע�����
	 */
	private void handleRegisterButtonListener(){
		factory = LayoutInflater.from(currentContext);
		builder = new AlertDialog.Builder(currentContext);
        registerView = factory.inflate(R.layout.register, null);
        userNameRegisterText = (EditText) registerView.findViewById(R.id.userNameTextRegister);
		passWordRegisterText = (EditText) registerView.findViewById(R.id.userPassTextRegister);
		passWordSureRegisterText = (EditText) registerView.findViewById(R.id.userPassSureTextRegister);
		loginSelfAfterRegisterCheckBox = (CheckBox) registerView.findViewById(R.id.checkLoadAfterRegister);
		
		loginSelfAfterRegisterCheckBox.setOnClickListener(buttonOnCilckListener);
		
        builder.setTitle("���ע��");
        builder.setView(registerView);
        builder.setPositiveButton("ע��", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		/**
        		 * ע�����ע�ᰴť��Ӧ
        		 */
        		registerButtonOnRegisterViewListener();
        	}
        });
    	builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    		}
    	});
    	builder.create().show();
	}
	/**
	 * ע������ϵ�ע�ᰴť����
	 */
	private void registerButtonOnRegisterViewListener(){
		try{

 		   String userNameRegister = userNameRegisterText.getText().toString().trim();
 		   String passWordRegister = passWordRegisterText.getText().toString().trim();
 		   String passWordSureRegister = passWordSureRegisterText.getText().toString().trim();
 		   /**
 		    * У�������ע����Ϣ
 		    */
 		   if(userNameRegister == null || "".equals(userNameRegister)){
 			   ActivityUtil.showMessageByToast(currentContext, "������ע����û���!");
 		   }else if(passWordRegister == null || "".equals(passWordRegister)){
 			   ActivityUtil.showMessageByToast(currentContext, "������ע�������!");
 		   }else if(passWordSureRegister == null || "".equals(passWordSureRegister)){
 			   ActivityUtil.showMessageByToast(currentContext, "������ע���ȷ������!");
 		   }else if(!passWordRegister.equals(passWordSureRegister)){
 			   ActivityUtil.showMessageByToast(currentContext, "������������벻һ��!");
 		   }else{
 			   /**
 			    * �����ݿ�
 			    */
 			   DBOperates.openDatabase();
 			   /**
 			    * У��ע����û����Ƿ��Ѿ�����
 			    */
 			   if(DBOperates.canThisUserNameRegister(userNameRegister)){
 				   /**
 				    * ����ע��
 				    */
 				   ObjectUtil personInfo = new PersonInfo();
 				   ((PersonInfo) personInfo).setUserName(userNameRegister);
 				   ((PersonInfo) personInfo).setPassWord(passWordRegister);
 				   ((PersonInfo) personInfo).setPersonSign("�Ҽ����˹���!");
 				   ((PersonInfo) personInfo).setRegisterTime(ActivityUtil.dateFormat.format(System.currentTimeMillis()));
 				   /**
 				    * ���
 				    */
 				   boolean registerResult = DBOperates.insertObject("person_info", personInfo);
 				   if(registerResult){
 					   if(loginSelfAfterRegisterOrNot){
 						   /**
 						    * ע��󣬲�ѯ�û���Ϣ��ֱ�ӵ�¼��������ҳ
 						    */
 						   PersonInfo personInfoSelect = DBOperates.selectPersonInfoWithPersonName(userNameRegister);
 						   if(personInfoSelect == null){
 							   ActivityUtil.showMessageByToast(currentContext, "��ѯ�û���Ϣ����!");
 						   }else{
 							   //Intent toPersonActivityIntent = new Intent(currentContext,PersonPageActivity.class);
 							   Intent toPersonActivityIntent = new Intent(currentContext,TabView.class);
 							   int personId = personInfoSelect.getPersonId();
 							   byte[] faceImage = DBOperates.selectFaceWithPersonId(personInfoSelect.getPersonId());
 							   int blogCount = DBOperates.selectBlogCountWithPersonId(personId);
 								
 							   toPersonActivityIntent.putExtra("personId", personInfoSelect.getPersonId());
 							   toPersonActivityIntent.putExtra("userName", personInfoSelect.getUserName());
 							   toPersonActivityIntent.putExtra("personSign", personInfoSelect.getPersonSign());
 							   toPersonActivityIntent.putExtra("registerTime", personInfoSelect.getRegisterTime());
 							   toPersonActivityIntent.putExtra("faceImage", faceImage);
 							   toPersonActivityIntent.putExtra("blogCount", blogCount);
 							   startActivity(toPersonActivityIntent);
 							   LoginActivity.this.finish();
 						   }
 					   }else{
 						  ActivityUtil.showMessageByToast(currentContext, "ע��ɹ�!");
 						  userNameText.setText(userNameRegister);
 					   }
 				   }else{
 					   ActivityUtil.showMessageByToast(currentContext, "ע��ʧ��!");
 				   }
 				   
 			   }else{
 				   ActivityUtil.showMessageByToast(currentContext, "��ע����û����Ѵ���!");
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