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
	 * 当前上下文环境
	 */
	private Context currentContext = LoginActivity.this;
	/**
	 * 用户名文本框
	 */
	private EditText userNameText;
	/**
	 * 密码文本框
	 */
	private EditText passWordText;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 保存密码选择按钮
	 */
	private CheckBox savePassCheckBox;
	/**
	 * 自动登录选择按钮
	 */
	private CheckBox loginSelfCheckBox;
	/**
	 * 是否保存密码标志位
	 */
	private boolean savePassOrNot = false;
	/**
	 * 是否自动登录标志位
	 */
	private boolean loginSelfOrNot = false;
	/**
	 * 登录按钮
	 */
	private Button loginButton;
	/**
	 * 注册按钮
	 */
	private Button registerButton;
	/**
	 * 布局加载器
	 */
	private LayoutInflater factory;
	/**
	 * 注册页面布局
	 */
	private View registerView;
	/**
	 * 弹出框实例
	 */
	private AlertDialog.Builder builder;
	/**
	 * 注册界面用户名文本框
	 */
	private EditText userNameRegisterText;
	/**
	 * 注册界面密码文本框
	 */
	private EditText passWordRegisterText;
	/**
	 * 注册界面密码确认文本框
	 */
	private EditText passWordSureRegisterText;
	/**
	 * 注册界面注册完成后自动登录
	 */
	private CheckBox loginSelfAfterRegisterCheckBox;
	/**
	 * 注册完是否自动登录
	 */
	private boolean loginSelfAfterRegisterOrNot = false;
	
	private Constant constant;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.login);
        /**
         * 初始化控件
         */
        initView();
    }
    /**
     * 初始化控件，并添加事件
     */
    private void initView(){
    	userNameText = (EditText) this.findViewById(R.id.userNameTextLogin);
    	passWordText = (EditText) this.findViewById(R.id.userPassTextLogin);
    	savePassCheckBox = (CheckBox) this.findViewById(R.id.checkSavePassLogin);
    	loginSelfCheckBox = (CheckBox) this.findViewById(R.id.checkSelfLoadLogin);
    	loginButton = (Button) this.findViewById(R.id.loginButtonLogin);
    	registerButton = (Button) this.findViewById(R.id.registerButtonLogin);
    	/**
    	 * 按钮注册监听器
    	 */
    	savePassCheckBox.setOnClickListener(buttonOnCilckListener);
    	loginSelfCheckBox.setOnClickListener(buttonOnCilckListener);
    	loginButton.setOnClickListener(buttonOnCilckListener);
    	registerButton.setOnClickListener(buttonOnCilckListener);
    	
    	/**
    	 * 是否保存了密码,自动登录
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
     * 按钮事件监听器
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
	 * 处理登录按钮事件
	 */
	private void handleLoginButtonListener(String userName,String passWord){
		try{
			/**
			 * 校验用户名和密码输入的正确性
			 */
			if(userName == null || "".equals(userName)){
				ActivityUtil.showMessageByToast(currentContext, "请输入用户名!");
			}else if(passWord == null || "".equals(passWord)){
				ActivityUtil.showMessageByToast(currentContext, "请输入密码!");
			}else{
				/**
				 * 打开数据库
				 */
				DBOperates.openDatabase();
				/**
				 * 校验用户是否已经注册
				 */
				String loginResult = DBOperates.verifyWhenLogin(userName, passWord);
				
				if(!loginResult.equals("clear")){
					/**
					 * 密码校验失败
					 */
					ActivityUtil.showMessageByToast(currentContext, loginResult);
					userNameText.setText("");
					passWordText.setText("");
					
				}else{
					/**
					 * 校验通过，查询用户信息，跳转到个人主页
					 */
				   PersonInfo personInfoSelect = DBOperates.selectPersonInfoWithPersonName(userName);
				   if(personInfoSelect == null){
					   ActivityUtil.showMessageByToast(currentContext, "查询用户信息为空!");
				   }else{
					   /**
					    * 保存配置信息
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
			 * 关闭数据库
			 */
			DBOperates.closeDatabase();
		}
	}
	/**
	 * 处理注册按钮事件，弹出注册界面
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
		
        builder.setTitle("免费注册");
        builder.setView(registerView);
        builder.setPositiveButton("注册", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int whichButton) {
        		/**
        		 * 注册界面注册按钮响应
        		 */
        		registerButtonOnRegisterViewListener();
        	}
        });
    	builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    		}
    	});
    	builder.create().show();
	}
	/**
	 * 注册界面上的注册按钮处理
	 */
	private void registerButtonOnRegisterViewListener(){
		try{

 		   String userNameRegister = userNameRegisterText.getText().toString().trim();
 		   String passWordRegister = passWordRegisterText.getText().toString().trim();
 		   String passWordSureRegister = passWordSureRegisterText.getText().toString().trim();
 		   /**
 		    * 校验输入的注册信息
 		    */
 		   if(userNameRegister == null || "".equals(userNameRegister)){
 			   ActivityUtil.showMessageByToast(currentContext, "请输入注册的用户名!");
 		   }else if(passWordRegister == null || "".equals(passWordRegister)){
 			   ActivityUtil.showMessageByToast(currentContext, "请输入注册的密码!");
 		   }else if(passWordSureRegister == null || "".equals(passWordSureRegister)){
 			   ActivityUtil.showMessageByToast(currentContext, "请输入注册的确认密码!");
 		   }else if(!passWordRegister.equals(passWordSureRegister)){
 			   ActivityUtil.showMessageByToast(currentContext, "两次输入的密码不一致!");
 		   }else{
 			   /**
 			    * 打开数据库
 			    */
 			   DBOperates.openDatabase();
 			   /**
 			    * 校验注册的用户名是否已经存在
 			    */
 			   if(DBOperates.canThisUserNameRegister(userNameRegister)){
 				   /**
 				    * 可以注册
 				    */
 				   ObjectUtil personInfo = new PersonInfo();
 				   ((PersonInfo) personInfo).setUserName(userNameRegister);
 				   ((PersonInfo) personInfo).setPassWord(passWordRegister);
 				   ((PersonInfo) personInfo).setPersonSign("我加入了光年!");
 				   ((PersonInfo) personInfo).setRegisterTime(ActivityUtil.dateFormat.format(System.currentTimeMillis()));
 				   /**
 				    * 入库
 				    */
 				   boolean registerResult = DBOperates.insertObject("person_info", personInfo);
 				   if(registerResult){
 					   if(loginSelfAfterRegisterOrNot){
 						   /**
 						    * 注册后，查询用户信息，直接登录到个人主页
 						    */
 						   PersonInfo personInfoSelect = DBOperates.selectPersonInfoWithPersonName(userNameRegister);
 						   if(personInfoSelect == null){
 							   ActivityUtil.showMessageByToast(currentContext, "查询用户信息出错!");
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
 						  ActivityUtil.showMessageByToast(currentContext, "注册成功!");
 						  userNameText.setText(userNameRegister);
 					   }
 				   }else{
 					   ActivityUtil.showMessageByToast(currentContext, "注册失败!");
 				   }
 				   
 			   }else{
 				   ActivityUtil.showMessageByToast(currentContext, "你注册的用户名已存在!");
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