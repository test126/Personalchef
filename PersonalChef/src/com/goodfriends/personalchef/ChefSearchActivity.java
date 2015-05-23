package com.goodfriends.personalchef;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.goodfriends.personalchef.adapter.ChefAdapter;
import com.goodfriends.personalchef.bean.ChefBean;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.view.SearchText;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshListView;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("HandlerLeak")
public class ChefSearchActivity extends BaseActivity{
	private PullToRefreshListView list_chushi;
	private ListView mListView;
	private List<Chefs> chefs;
	private ChefBean chefBean;
	private ChefAdapter adapter;
	private SearchText searchText;
	private int page=0,pagecount=0,countRecord;
	private String key = "";
	private boolean hasMoreData;
	private String address;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chefsearch);
		initView();
		initData();
	}
	
	public void initData() {
		address = LoadingActivity.loca.getDistrict()+LoadingActivity.loca.getProvince()+LoadingActivity.loca.getCity()+LoadingActivity.loca.getAddr();
//		Log.e("asdjaskdads", address);
	}
	
	public void initView()
	{
		list_chushi = (PullToRefreshListView) findViewById(R.id.searchlist_chushi);
		list_chushi.setPullLoadEnabled(false);
		list_chushi.setScrollLoadEnabled(true);
		mListView = list_chushi.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.color.line_divide));
		mListView.setDividerHeight(16);
		
		searchText = (SearchText) findViewById(R.id.search_text);
		searchText.tv_search_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(searchText.getSearchText().equals(""))
				{
					Toast.makeText(ChefSearchActivity.this, "厨师名、菜名为空", Toast.LENGTH_SHORT).show();
					return;
				}
				key = searchText.getSearchText();
				list_chushi.doPullRefreshing(true, 0);
			}
		});
	}
	
	//更新厨师
		Runnable chefRunnablePull = new Runnable() {
			@Override
			public void run() {
//				Log.e("获得地址",LoadingActivity.loca.getAddr());
				chefBean = CommonFun1.searchChefList(key, 4, page, pagecount,address);
				if(chefBean != null)
				{
					myHandler.sendEmptyMessage(0);
				}else {
					myHandler.sendEmptyMessage(444);
				}
			}
		};
		
		private final int LOAD_CHEF_COUNT = 3;
		
		Runnable chefRunnableLoadMore = new Runnable() {
			@Override
			public void run() {
				chefBean = CommonFun1.searchChefList(key, 4, page, pagecount,address);
				if (chefBean != null) {
					myHandler.sendEmptyMessage(502);
				} else {
					myHandler.sendEmptyMessage(444);
				}
			}
		};
	
	private Handler myHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			switch (msg.what) {
				case 0:
					chefs = chefBean.getChefInfo().getList();
					adapter = new ChefAdapter(ChefSearchActivity.this, chefBean.getChefInfo()
							.getList());
					mListView.setAdapter(adapter);
					if (chefs.size() > 0) {
						list_chushi.onPullDownRefreshComplete();
						list_chushi.onPullUpRefreshComplete();
						setLastUpdateTime();
					} else {
						myHandler.sendEmptyMessage(444);
					}
					break;
				case 502:
					if(chefBean.getChefInfo() ==null) return;
					chefs.addAll(chefBean.getChefInfo().getList());
					if (chefs.size() > 0) {
						list_chushi.onPullDownRefreshComplete();
						list_chushi.onPullUpRefreshComplete();
						setLastUpdateTime();
					} else {
						myHandler.sendEmptyMessage(444);
					}
					break;
				case 444:
					list_chushi.onPullDownRefreshComplete();
					list_chushi.onPullUpRefreshComplete();
					list_chushi.setHasMoreData(hasMoreData);
					setLastUpdateTime();
			}
		}
	};

	@Override
	protected void onStart() {
		super.onStart();

		list_chushi.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page=1;
				pagecount=0;
				new Thread(chefRunnablePull).start();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				countRecord = chefBean.getChefInfo().getCountRecord();
				if (pagecount < countRecord) {
					hasMoreData = true;
					page++;
					pagecount = pagecount + LOAD_CHEF_COUNT;
					new Thread(chefRunnableLoadMore).start();
				} else {
					hasMoreData = false;
					myHandler.sendEmptyMessage(444);
				}
			}
		});

		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChefSearchActivity.this,ChefDetailActivity.class);
				intent.putExtra("chefId", chefs.get(arg2).getId());
				startActivity(intent);
			}
		});
	}
	
	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		list_chushi.setLastUpdatedLabel(text);
	}
	
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}
		return mDateFormat.format(new Date(time));
	}
}
