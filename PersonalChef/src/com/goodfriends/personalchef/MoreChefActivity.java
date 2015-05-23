package com.goodfriends.personalchef;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfriends.personalchef.adapter.ChefMoreAdapter;
import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.ChefBean;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase.OnRefreshListener;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshListView;

public class MoreChefActivity extends Activity implements OnClickListener {

	private int dishId, chefId;
	private List<Chefs> chefs = null;
	private ChefBean chefBean;
	private int page = 1, pagecount = 10;

	private ImageView back;
	private TextView name;
	private PullToRefreshListView listView;
	private ListView mListView;
	private boolean hasMoreData;

	private ChefMoreAdapter adapter;

	private Button yuyue;
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_morechef);

		dishId = getIntent().getExtras().getInt("dishId");
		initView();
		listView.setPullLoadEnabled(false);
		listView.setScrollLoadEnabled(true);
		mListView = listView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.color.line_divide));
		mListView.setDividerHeight(1);
		setLastUpdateTime();
		listView.doPullRefreshing(true, 500);

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				new Thread(runnable).start();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadMore();
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				chefId = chefBean.getChefInfo().getList().get(position).getId();
				yuyue.setText("选 "
						+ chefBean.getChefInfo().getList().get(position)
								.getName() + " 预约");
				map.clear();
				map.put(position, 100);
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void loadMore() {

		if (pagecount < chefBean.getChefInfo().getCountRecord()) {
			hasMoreData = true;
			pagecount = pagecount + 10;
			new Thread(runnable).start();
		} else {
			hasMoreData = false;
			myHandler.sendEmptyMessage(444);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		name = (TextView) findViewById(R.id.title_name);
		listView = (PullToRefreshListView) findViewById(R.id.more_listView);
		yuyue = (Button) findViewById(R.id.more_yuyue);
		yuyue.setOnClickListener(this);
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			chefBean = CommonFun1.getChefListFromDish(dishId, page, pagecount);
			if (chefBean != null) {
				myHandler.sendEmptyMessage(chefBean.getErrcode());
			} else {
				myHandler.sendEmptyMessage(400);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				name.setText(chefBean.getChefInfo().getCountRecord() + "位厨师");
				chefs = chefBean.getChefInfo().getList();
				adapter = new ChefMoreAdapter(getApplicationContext(), chefs,
						map);
				mListView.setAdapter(adapter);
				listView.onPullDownRefreshComplete();
				listView.onPullUpRefreshComplete();
				setLastUpdateTime();
				break;
			case 400:
				break;
			case 444:
				listView.onPullDownRefreshComplete();
				listView.onPullUpRefreshComplete();
				listView.setHasMoreData(hasMoreData);
				setLastUpdateTime();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.more_yuyue:
			if (chefId != 0) {
				Intent intent = new Intent(MoreChefActivity.this,
						ChefDetailActivity.class);
				intent.putExtra("chefId", chefId);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "亲,请先选择一位厨师哦",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		listView.setLastUpdatedLabel(text);
	}

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));
	}
}
