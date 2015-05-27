package com.heart.lightyaer.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heart.lightyear.model.PersonBlog;
import com.heart.lightyear.util.DBOperates;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BlogListActivity extends Activity {
	/**
	 * ��ǰ�����Ļ���
	 */
	private Context currentContext = BlogListActivity.this;
	/**
	 * �û����ı���
	 */
	private TextView userNameText;
	/**
	 * ��־�б�
	 */
	private ListView blogListView;
	/**
	 * �û�ID
	 */
	private int personId;
	/**
	 * �û���
	 */
	private String userName;
	/**
	 * ������־�б�
	 */
	private List<Map<String,Object>> dataList;
	/**
	 * ����б�Ԫ��ʱ��ȡ����־��Ϣ
	 */
	private Map<String,Object> blogMap;
	/**
	 * �Ƿ�����޸�
	 */
	private boolean canModifyBlogOrNot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.bloglist);
		/**
		 * ��ʼ���ؼ�
		 */
		initView();
		/**
		 * ��ȡ�������Ĳ���
		 */
		Intent intent = getIntent();
		personId = intent.getIntExtra("personId", -1);
		userName = intent.getStringExtra("userName");
		canModifyBlogOrNot = intent.getBooleanExtra("canModifyOrNot", false);
		/**
		 * �����û���
		 */
		userNameText.setText(userName);
		/**
		 * ��ȡ������־
		 */
		dataList = getBlogList(personId);
		/**
		 * ��ʼ��Adapter
		 */
		MyBaseAdapter myAdapter = new MyBaseAdapter(currentContext,dataList);
		/**
		 * �б�����Adapter
		 */
		blogListView.setAdapter(myAdapter);
		
		blogListView.setOnItemClickListener(onItemClickListener);
	}
	/**
	 * ����б���Ԫ�أ���ת����ʾ��־����
	 */
	private OnItemClickListener onItemClickListener = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int pos,
				long arg3) {
			// TODO Auto-generated method stub
			blogMap  =  dataList.get(pos);
			Intent gotoViewBlogIntent = new Intent(currentContext,ShowBlogActivity.class);
			gotoViewBlogIntent.putExtra("personName", userName);
			gotoViewBlogIntent.putExtra("blogId", (Integer)blogMap.get("blogId"));
			gotoViewBlogIntent.putExtra("blogTitle", (String)blogMap.get("blogTitle"));
			gotoViewBlogIntent.putExtra("blogContent", (String)blogMap.get("blogContent"));
			gotoViewBlogIntent.putExtra("canModifyOrNot", canModifyBlogOrNot);
			startActivity(gotoViewBlogIntent);
		}
		
	};
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView(){
		userNameText = (TextView) this.findViewById(R.id.userNameBlogList);
		blogListView = (ListView) this.findViewById(R.id.listViewBlogList);
	}
	/**
	 * �����û�ID��ȡ���е�blog
	 */
	private List<Map<String,Object>> getBlogList(int personId){
		List<Map<String,Object>> blogMapList = new ArrayList<Map<String,Object>>();
		try{
			/**
			 * �����ݿ�
			 */
			DBOperates.openDatabase();
			/**
			 * ��ѯ����blog
			 */
			List<PersonBlog> blogList = DBOperates.selectBlogWithPersonId(personId);
			/**
			 * �ر����ݿ�
			 */
			DBOperates.closeDatabase();
			for(PersonBlog personBlog : blogList){
				Map<String,Object> mapBlog = new HashMap<String,Object>();
				mapBlog.put("blogId", personBlog.getBlogId());
				mapBlog.put("blogTitle", personBlog.getBlogTitle());
				mapBlog.put("blogContent", personBlog.getBlogContent());
				mapBlog.put("blogLastTime", personBlog.getBlogLastTime());
				blogMapList.add(mapBlog);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			/**
			 * �ر����ݿ�
			 */
			DBOperates.closeDatabase();
		}
		return blogMapList;
	}

	public class MyBaseAdapter extends BaseAdapter{
		private LayoutInflater layoutInflater;
		private List<Map<String,Object>> dataList;
		public MyBaseAdapter(Context context,List<Map<String,Object>> dataList){
			layoutInflater = LayoutInflater.from(context);
			this.dataList = dataList;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataList.size();
		}

		@Override
		public Object getItem(int pos) {
			// TODO Auto-generated method stub
			return dataList.get(pos);
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
				view = layoutInflater.inflate(R.layout.bloglistelement, null);
				viewHolder.blogTitleText = (TextView) view.findViewById(R.id.blogNameTextBlogListElement);
				viewHolder.blogLastTimeText = (TextView) view.findViewById(R.id.blogLastTimeTextListElement);
				view.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.blogTitleText.setText((CharSequence) dataList.get(pos).get("blogTitle"));
			viewHolder.blogLastTimeText.setText((CharSequence) dataList.get(pos).get("blogLastTime"));
			return view;
		}
	}
	private class ViewHolder{
		public TextView blogTitleText;
		public TextView blogLastTimeText;
	}
	
	
}
