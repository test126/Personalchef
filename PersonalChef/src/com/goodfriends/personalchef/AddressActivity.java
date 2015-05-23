package com.goodfriends.personalchef;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfriends.personalchef.adapter.AddressAdapter;
import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.Address;
import com.goodfriends.personalchef.bean.AddressBean;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase.OnRefreshListener;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshListView;

public class AddressActivity extends Activity implements OnClickListener {

	private int userid;

	private TextView add, nodata;

	private ImageView back;

	private List<Address> lists;
	private PullToRefreshListView listView;
	private ListView mListView;
	private AddressAdapter adapter;
	private AddressBean addressBean;

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_address);

		userid = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
		initView();

		if (userid != 0) {
			listView.setPullLoadEnabled(false);
			listView.setScrollLoadEnabled(true);
			mListView = listView.getRefreshableView();
			mListView.setDivider(getResources()
					.getDrawable(R.color.line_divide));
			mListView.setDividerHeight(10);
		} else {
			myHandler.sendEmptyMessage(400);
		}

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("PullToRefreshBase", "PullToRefreshBase");
				new Thread(addrRunnable).start();
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
				Intent intent = new Intent(AddressActivity.this,
						AddAddressActivity.class);
				intent.putExtra("isChange", true);
				intent.putExtra("add", lists.get(position).getAddress());
				intent.putExtra("province", lists.get(position).getProvince());
				intent.putExtra("city", lists.get(position).getCity());
				intent.putExtra("area", lists.get(position).getArea());
				intent.putExtra("id", lists.get(position).getId());
				intent.putExtra("name", lists.get(position).getContact());
				startActivity(intent);
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
		listView.onPullDownRefreshComplete();
		listView.onPullUpRefreshComplete();
		listView.setPullRefreshEnabled(true);
		listView.doPullRefreshing(true, 500);
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		add = (TextView) findViewById(R.id.title_collect);
		back.setOnClickListener(this);
		add.setOnClickListener(this);
		nodata = (TextView) findViewById(R.id.nodata);
		listView = (PullToRefreshListView) findViewById(R.id.listview);
	}

	Runnable addrRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			addressBean = CommonFun1.getAddressList(userid);
			if (addressBean != null) {
				myHandler.sendEmptyMessage(addressBean.getErrcode());
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
				lists = addressBean.getList();
				if (lists != null) {
					nodata.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
					adapter = new AddressAdapter(getApplicationContext(), lists);
					mListView.setAdapter(adapter);
					listView.onPullDownRefreshComplete();
					listView.onPullUpRefreshComplete();
					setLastUpdateTime();
				} else {
					myHandler.sendEmptyMessage(400);
				}
				break;
			case 400:
				Toast.makeText(getApplicationContext(), "亲，您还木有添加过地址呢",
						Toast.LENGTH_SHORT).show();
				nodata.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
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
