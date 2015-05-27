package com.heart.lightyaer.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heart.lightyear.model.PersonInfo;
import com.heart.lightyear.util.DBOperates;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PersonInfoListActivity extends Activity{
	/**
	 * ��ǰ�����Ļ���
	 */
	private Context currentContext = PersonInfoListActivity.this;
	/**
	 * ������ʾ���û����ı���
	 */
	private TextView userNamePersonListText;
	/**
	 * �б�ؼ�
	 */
	private ListView personListView;
	/**
	 * ������ϢList
	 */
	private List<Map<String,Object>> personListMap;
	/**
	 * �û���
	 */
	private String personName;
	/**
	 * ����б�Ԫ��ʱ��ȡ���û���Ϣ
	 */
	private Map<String,Object> personMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.personlist);
		 /**
		  * ��ȡ�������Ĳ���
		  */
		 Intent intentParams = getIntent();
		 personName = intentParams.getStringExtra("userName");
		 /**
		  * ��ʼ���ؼ�
		  */
		 initView();
		 /**
		  * ��ȡ����
		  */
		 personListMap = getPersonInfoListMap();
		 /**
		  * ��ȡ������
		  */
		 MyAdapter myAdapter = new MyAdapter();
		 /**
		  * listView����������
		  */
		 personListView.setAdapter(myAdapter);
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		// TODO Auto-generated method stub
		userNamePersonListText = (TextView) this.findViewById(R.id.userNamePersonList);
		userNamePersonListText.setText(personName);
		personListView = (ListView) this.findViewById(R.id.listViewPersonList);
		personListView.setOnItemClickListener(onItemClickListener);
	}
	
	/**
	 * �б�Ԫ��ѡ���¼�
	 */
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			personMap = personListMap.get(pos);
			Intent gotoPersonInfoIntent = new Intent(currentContext,PersonInfoActivity.class);
			gotoPersonInfoIntent.putExtra("personName", personName);
			gotoPersonInfoIntent.putExtra("viewPersonId", (Integer)personMap.get("personId"));
			gotoPersonInfoIntent.putExtra("viewPersonName", (String)personMap.get("personName"));
			gotoPersonInfoIntent.putExtra("viewPersonSign", (String)personMap.get("personSign"));
			gotoPersonInfoIntent.putExtra("viewPersonRegisterTime", (String)personMap.get("registerTime"));
			gotoPersonInfoIntent.putExtra("viewPersonFace", (byte[])personMap.get("personFace"));
			startActivity(gotoPersonInfoIntent);
		}
	};
	/**
	 * ��ѯ���к���
	 */
	private List<Map<String,Object>> getPersonInfoListMap(){
		List<Map<String,Object>> PersonInfoListMap = new ArrayList<Map<String,Object>>();
		Map<String,Object> personInfoMap = null;
		int personId = -1;
		try{
			/**
			 * �����ݿ�
			 */
			DBOperates.openDatabase();
			/**
			 * ��ѯ����
			 */
			List<PersonInfo> personInfoList = DBOperates.selectAllPerson();
			if(personInfoList.size() > 0){
				for(PersonInfo personInfo : personInfoList){
					personInfoMap = new HashMap<String,Object>();
					personId = personInfo.getPersonId();
					personInfoMap.put("personId", personId);
					personInfoMap.put("personName", personInfo.getUserName());
					personInfoMap.put("personSign", personInfo.getPersonSign());
					personInfoMap.put("registerTime", personInfo.getRegisterTime());
					/**
					 * �����û�ID��ȡͷ��
					 */
					byte[] personFace = DBOperates.selectFaceWithPersonId(personId);
					personInfoMap.put("personFace", personFace);
					PersonInfoListMap.add(personInfoMap);
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
		return PersonInfoListMap;
	}

	/**
	 * ʵ��BaseAdapter
	 */
	public class MyAdapter extends BaseAdapter{
		private LayoutInflater layoutInflater;
		//private List<Map<String,Object>> personInfoListMap;
		public MyAdapter(){
			layoutInflater = LayoutInflater.from(currentContext);
			//this.personInfoListMap = personInfoListMap;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return personListMap.size();
		}

		@Override
		public Object getItem(int pos) {
			// TODO Auto-generated method stub
			return personListMap.get(pos);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int pos, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if(view == null){
				viewHolder = new ViewHolder();
				view = layoutInflater.inflate(R.layout.personinfolistelement, null);
				viewHolder.personFaceImage = (ImageView) view.findViewById(R.id.imagePersonInfoList);
				viewHolder.personNameText = (TextView) view.findViewById(R.id.loadNumTextPersonInfoList);
				viewHolder.personSignText = (TextView) view.findViewById(R.id.signTextPersonInfoList);
				viewHolder.personRegisterText = (TextView) view.findViewById(R.id.registerTextPersonInfoList);
				view.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.personNameText.setText((CharSequence) personListMap.get(pos).get("personName"));
			viewHolder.personSignText.setText((CharSequence) personListMap.get(pos).get("personSign"));
			viewHolder.personRegisterText.setText((CharSequence) personListMap.get(pos).get("registerTime"));
			byte[] personImage = (byte[]) personListMap.get(pos).get("personFace");
			if(personImage == null){
			}else{
				Bitmap bitmap = BitmapFactory.decodeByteArray(personImage, 0, personImage.length);
				viewHolder.personFaceImage.setImageBitmap(bitmap);
			}
			return view;
		}
		private class ViewHolder{
			public ImageView personFaceImage;
			public TextView personNameText;
			public TextView personSignText;
			public TextView personRegisterText;
		}
	}
}
