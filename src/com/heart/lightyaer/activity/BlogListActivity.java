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
	 * 当前上下文环境
	 */
	private Context currentContext = BlogListActivity.this;
	/**
	 * 用户名文本框
	 */
	private TextView userNameText;
	/**
	 * 日志列表
	 */
	private ListView blogListView;
	/**
	 * 用户ID
	 */
	private int personId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 所有日志列表
	 */
	private List<Map<String,Object>> dataList;
	/**
	 * 点击列表元素时获取的日志信息
	 */
	private Map<String,Object> blogMap;
	/**
	 * 是否可以修改
	 */
	private boolean canModifyBlogOrNot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.bloglist);
		/**
		 * 初始化控件
		 */
		initView();
		/**
		 * 获取传递来的参数
		 */
		Intent intent = getIntent();
		personId = intent.getIntExtra("personId", -1);
		userName = intent.getStringExtra("userName");
		canModifyBlogOrNot = intent.getBooleanExtra("canModifyOrNot", false);
		/**
		 * 设置用户名
		 */
		userNameText.setText(userName);
		/**
		 * 获取所有日志
		 */
		dataList = getBlogList(personId);
		/**
		 * 初始化Adapter
		 */
		MyBaseAdapter myAdapter = new MyBaseAdapter(currentContext,dataList);
		/**
		 * 列表设置Adapter
		 */
		blogListView.setAdapter(myAdapter);
		
		blogListView.setOnItemClickListener(onItemClickListener);
	}
	/**
	 * 点击列表中元素，跳转到显示日志界面
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
	 * 初始化控件
	 */
	private void initView(){
		userNameText = (TextView) this.findViewById(R.id.userNameBlogList);
		blogListView = (ListView) this.findViewById(R.id.listViewBlogList);
	}
	/**
	 * 根据用户ID获取所有的blog
	 */
	private List<Map<String,Object>> getBlogList(int personId){
		List<Map<String,Object>> blogMapList = new ArrayList<Map<String,Object>>();
		try{
			/**
			 * 打开数据库
			 */
			DBOperates.openDatabase();
			/**
			 * 查询所有blog
			 */
			List<PersonBlog> blogList = DBOperates.selectBlogWithPersonId(personId);
			/**
			 * 关闭数据库
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
			 * 关闭数据库
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
