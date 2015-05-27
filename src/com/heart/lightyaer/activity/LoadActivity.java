package com.heart.lightyaer.activity;

import com.heart.lightyear.util.ActivityUtil;
import com.heart.lightyear.util.DBOperates;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class LoadActivity extends Activity {
	
	private Context currentContext = LoadActivity.this;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * ����ȫ��
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.load);
        
        Message msg = new Message();
       /**
        * �ж����ݿ��Ƿ񴴽��ɹ�
        */
        if(createDatabase(currentContext)){
        	 msg.what = 1;
        }else{
        	 msg.what = 0;
        }
        handler.sendMessageDelayed(msg, 3000);
    }
	/**
	 * ����message
	 */
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
	         case 1:
	             /**
	              * ȡ��ȫ��
	              */
	             getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	             /**
	              * ��ʾ��¼����
	              * �رյ�ǰactivity
	              */
	             startActivity(new Intent(currentContext,LoginActivity.class));
	             LoadActivity.this.finish();
	             break;
	         case 0:
	        	 ActivityUtil.showMessageByToast(currentContext, "�������ݿ�ʧ��!");
	             break;
	         }
			super.handleMessage(msg);
		}
	};
	/**
	 * �������ݿ�
	 * @param context
	 * @return
	 */
	private boolean createDatabase(Context context){
		try{
			DBOperates.createDatabase(context);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
