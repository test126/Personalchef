package com.goodfriends.personalchef;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.goodfriends.personalchef.adapter.CouponAdapter;
import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.CouPon;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase.OnRefreshListener;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshListView;

public class CouponActivity extends Activity implements OnClickListener {

	private int userid;

	private ImageView back;

	private List<CouPon> lists;
	private PullToRefreshListView listView;
	private ListView mListView;
	private CouponAdapter adapter;
	private boolean b;
	public static int i = 0;

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_coupon);

		userid = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
		b  = getIntent().getExtras().getBoolean("isorder");
		initView();

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				new Thread(runnable).start();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (b) {
					i = position;
					CouponActivity.this.finish();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		userid = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
		setLastUpdateTime();
		listView.doPullRefreshing(true, 500);
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		// add = (TextView) findViewById(R.id.title_collect);
		back.setOnClickListener(this);
		// add.setOnClickListener(this);
		listView = (PullToRefreshListView) findViewById(R.id.listview);
		listView.setPullLoadEnabled(false);
		listView.setScrollLoadEnabled(true);
		mListView = listView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.color.line_divide));
		mListView.setDividerHeight(10);
		setLastUpdateTime();
		listView.doPullRefreshing(true, 500);
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			lists = CommonFun1.getCouponList(userid);
			if (lists != null) {
				myHandler.sendEmptyMessage(0);
			} else {
				myHandler.sendEmptyMessage(404);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter = new CouponAdapter(getApplicationContext(), lists);
				mListView.setAdapter(adapter);
				listView.onPullDownRefreshComplete();
				listView.onPullUpRefreshComplete();
				setLastUpdateTime();
				break;
			case 404:
				listView.onPullDownRefreshComplete();
				listView.onPullUpRefreshComplete();
				setLastUpdateTime();
				Toast.makeText(getApplicationContext(), "没有可用的优惠券",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

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

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.title_collect:
			Intent intent = new Intent(getApplicationContext(),
					AddAddressActivity.class);
			intent.putExtra("isChange", false);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
