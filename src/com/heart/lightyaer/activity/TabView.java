package com.heart.lightyaer.activity;

import com.heart.lightyear.util.Constant;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TabHost;

public class TabView extends TabActivity {
    /** Called when the activity is first created. */
	/**
	 * tab控件
	 */
	private TabHost tabhost;
	/**
	 * 跳转到个人主页intent
	 */
	private Intent gotoPersonPageIntent;
	/**
	 * 跳转到用户信息列表intent
	 */
	private Intent gotoPersonInfoListIntent;
	/**
	 * 用户ID（用于个人主页）
	 */
	private int personId;
	/**
	 * 用户日志数量（用于个人主页）
	 */
	private int blogCount;
	/**
	 * 用户名（用于个人主页、用户列表）
	 */
	private String userName;
	/**
	 * 用户签名（用于个人主页）
	 */
	private String personSign;
	/**
	 * 用户头像（用于个人主页）
	 */
	private byte[] personFace;
	/**
	 * 注册时间
	 */
	private String registerTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.persontab);
        tabhost = this.getTabHost();
        /**
		 * 获取传递来的参数
		 */
		Intent intent = getIntent();
		personId = intent.getIntExtra("personId", -1);
		blogCount = intent.getIntExtra("blogCount", 0);
		personSign = intent.getStringExtra("personSign");
		personFace = intent.getByteArrayExtra("faceImage");
		userName = intent.getStringExtra("userName");
		registerTime = intent.getStringExtra("registerTime");
		
        gotoPersonPageIntent = new Intent(this,PersonPageActivity.class);
        gotoPersonPageIntent.putExtra("personId", personId);
        gotoPersonPageIntent.putExtra("userName", userName);
        gotoPersonPageIntent.putExtra("personSign", personSign);
        gotoPersonPageIntent.putExtra("registerTime", registerTime);
        gotoPersonPageIntent.putExtra("faceImage", personFace);
        gotoPersonPageIntent.putExtra("blogCount", blogCount);
        tabhost.addTab(tabhost.newTabSpec("").setIndicator("个人主页",null).setContent(gotoPersonPageIntent));
       
        gotoPersonInfoListIntent = new Intent(this,PersonInfoListActivity.class);
        gotoPersonInfoListIntent.putExtra("userName", userName);
        tabhost.addTab(tabhost.newTabSpec("").setIndicator("用户列表", null).setContent(gotoPersonInfoListIntent));
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add("注销").setOnMenuItemClickListener(onMenuItemClickListener);
		return super.onCreateOptionsMenu(menu);
	}
	
	private OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			// TODO Auto-generated method stub
			Constant constant = new Constant(TabView.this);
			constant.saveConfig(false, false, "", "");
			TabView.this.finish();
			return false;
		}
	};
    
}