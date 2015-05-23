package com.goodfriends.personalchef.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.bean.CouPon;

public class CouponAdapter extends BaseAdapter {

	private List<CouPon> lists;
	private LayoutInflater layoutInflater;

	public CouponAdapter(Context context, List<CouPon> lists) {
		// TODO Auto-generated constructor stub
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lists = lists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final viewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater
					.inflate(R.layout.coupon_listitem, null);
			holder = new viewHolder();
			holder.text_name = (TextView) convertView
					.findViewById(R.id.couponPrice);
			holder.text_phone = (TextView) convertView
					.findViewById(R.id.couponName);
			holder.text_addr = (TextView) convertView
					.findViewById(R.id.coupontime);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.text_name.setText("￥:" + lists.get(position).getValue() / 100
				+ "");
		holder.text_phone.setText(lists.get(position).getCouponName());
		holder.text_addr.setText(lists.get(position).getFromTime() + " 至 "
				+ lists.get(position).getEndTime());
		return convertView;
	}

	static class viewHolder {
		private TextView text_name;
		private TextView text_phone;
		private TextView text_addr;
	}

}
