package com.goodfriends.personalchef.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.goodfriends.personalchef.ChefDetailActivity;
import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.adapter.ChefAdapter;
import com.goodfriends.personalchef.bean.ChefBean;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase.OnRefreshListener;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshListView;

public class ChefFragment extends Fragment {

	private List<Chefs> chefs;
	private int page = 1, pagecount = 10, countRecord;
	private int userId;
	private ChefAdapter adapter;
	private PullToRefreshListView listView;
	private ListView mListView;
	private ChefBean chefBean;
	private boolean hasMoreData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.collect_cheffragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		initView();

		setLastUpdateTime();
		listView.doPullRefreshing(true, 500);

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				new Thread(chefRunnable).start();
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
				Intent intent = new Intent(getActivity(),
						ChefDetailActivity.class);
				intent.putExtra("chefId", chefs.get(position).getId());
				startActivity(intent);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		userId = getActivity().getSharedPreferences("USERINFO",
				Context.MODE_PRIVATE).getInt("userId", 0);
		listView = (PullToRefreshListView) getActivity().findViewById(
				R.id.collect_cheflistView);
		listView.setPullLoadEnabled(false);
		listView.setScrollLoadEnabled(true);
		mListView = listView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.color.line_divide));
		mListView.setDividerHeight(10);
	}

	Runnable chefRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			chefBean = CommonFun1
					.getCollectChefList(userId, page, pagecount, 4);
			if (chefBean != null) {
				myHandler.sendEmptyMessage(chefBean.getErrcode());
			} else {
				myHandler.sendEmptyMessage(500);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				chefs = chefBean.getChefInfo().getList();
				if (chefs != null) {
					adapter = new ChefAdapter(getActivity(), chefs);
					mListView.setAdapter(adapter);
					listView.onPullDownRefreshComplete();
					listView.onPullUpRefreshComplete();
					setLastUpdateTime();
				} else {
					myHandler.sendEmptyMessage(500);
				}
				break;
			case 500:
				listView.onPullDownRefreshComplete();
				listView.onPullUpRefreshComplete();
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
}
