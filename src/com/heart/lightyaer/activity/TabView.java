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
	 * tab�ؼ�
	 */
	private TabHost tabhost;
	/**
	 * ��ת��������ҳintent
	 */
	private Intent gotoPersonPageIntent;
	/**
	 * ��ת���û���Ϣ�б�intent
	 */
	private Intent gotoPersonInfoListIntent;
	/**
	 * �û�ID�����ڸ�����ҳ��
	 */
	private int personId;
	/**
	 * �û���־���������ڸ�����ҳ��
	 */
	private int blogCount;
	/**
	 * �û��������ڸ�����ҳ���û��б�
	 */
	private String userName;
	/**
	 * �û�ǩ�������ڸ�����ҳ��
	 */
	private String personSign;
	/**
	 * �û�ͷ�����ڸ�����ҳ��
	 */
	private byte[] personFace;
	/**
	 * ע��ʱ��
	 */
	private String registerTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.persontab);
        tabhost = this.getTabHost();
        /**
		 * ��ȡ�������Ĳ���
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
        tabhost.addTab(tabhost.newTabSpec("").setIndicator("������ҳ",null).setContent(gotoPersonPageIntent));
       
        gotoPersonInfoListIntent = new Intent(this,PersonInfoListActivity.class);
        gotoPersonInfoListIntent.putExtra("userName", userName);
        tabhost.addTab(tabhost.newTabSpec("").setIndicator("�û��б�", null).setContent(gotoPersonInfoListIntent));
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add("ע��").setOnMenuItemClickListener(onMenuItemClickListener);
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