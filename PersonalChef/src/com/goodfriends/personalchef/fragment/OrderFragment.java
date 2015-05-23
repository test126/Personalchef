package com.goodfriends.personalchef.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.goodfriends.personalchef.OrderDetailActivity;
import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.RegActivity;
import com.goodfriends.personalchef.adapter.OrderAdapter;
import com.goodfriends.personalchef.bean.Order;
import com.goodfriends.personalchef.bean.OrderBean;
import com.goodfriends.personalchef.bean.OrderInfos;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase.OnRefreshListener;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshListView;

public class OrderFragment extends Fragment {

	private TextView tv1;
	private int userId;
	private int page = 1, pageCount = 10, countRecord;
	private PullToRefreshListView listView;
	private ListView mListView;
	private List<Order> orders = null;
	private boolean hasMoreData;
	private OrderAdapter adapter;
	private OrderBean orderBean;
	private OrderInfos orderInfos;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_order, null);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (userId != 0) {
			setLastUpdateTime();
			listView.doPullRefreshing(true, 0);
		} else {
			myHandler.sendEmptyMessage(401);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		SharedPreferences preferences = getActivity().getSharedPreferences(
				"USERINFO", Context.MODE_PRIVATE);
		userId = preferences.getInt("userId", 0);
		initView();

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (userId == 0) {
					tv1.setText("尚未登录");
				} else {
					page = 1;
					pageCount = 10;
					new Thread(orderRunnable).start();
				}
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadMore();
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int positon, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						OrderDetailActivity.class);
				intent.putExtra("orderId",
						orderBean.getList().getList().get(positon).getOrderno());
				startActivity(intent);
			}
		});
	}

	private void loadMore() {
		countRecord = orderInfos.getCountRecord();
		if (pageCount < countRecord) {
			hasMoreData = true;
			pageCount = pageCount + 10;
			new Thread(orderRunnableLoadMore).start();
		} else {
			hasMoreData = false;
			myHandler.sendEmptyMessage(444);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv1 = (TextView) getActivity().findViewById(R.id.order_name);
		listView = (PullToRefreshListView) getActivity().findViewById(
				R.id.listview);
		listView.setPullLoadEnabled(false);
		listView.setScrollLoadEnabled(true);
		mListView = listView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.color.line_divide));
		mListView.setDividerHeight(16);
	}

	Runnable orderRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			orderBean = CommonFun1.getOrderList(userId, page, pageCount);
			if (orderBean != null) {
				myHandler.sendEmptyMessage(orderBean.getErrcode());
			} else {
				myHandler.sendEmptyMessage(400);
			}
		}
	};
	

	Runnable orderRunnableLoadMore = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			orderBean = CommonFun1.getOrderList(userId, page, pageCount);
			if (orderBean != null) {
				myHandler.sendEmptyMessage(1);
			} else {
				myHandler.sendEmptyMessage(400);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				listView.setVisibility(View.VISIBLE);
				orderInfos = orderBean.getList();
				if (orderInfos != null) {
					orders = orderInfos.getList();
					adapter = new OrderAdapter(getActivity(), orders);
					mListView.setAdapter(adapter);
					listView.onPullDownRefreshComplete();
					listView.onPullUpRefreshComplete();
					setLastUpdateTime();
				} else {
					myHandler.sendEmptyMessage(400);
				}
				break;
			case 1:
				orderInfos = orderBean.getList();
				if (orderInfos != null) {
					orders.addAll(orderInfos.getList());
//					adapter = new OrderAdapter(getActivity(), orders);
//					mListView.setAdapter(adapter);
					listView.onPullDownRefreshComplete();
					listView.onPullUpRefreshComplete();
					setLastUpdateTime();
				} else {
					myHandler.sendEmptyMessage(400);
				}
				break;
			case 400:
				listView.setVisibility(View.GONE);
				tv1.setText("暂无订单");
				break;
			case 401:
				listView.setVisibility(View.GONE);
				tv1.setText("尚未登录");
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

	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		listView.setLastUpdatedLabel(text);
	}

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}
		return mDateFormat.format(new Date(time));
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
}
