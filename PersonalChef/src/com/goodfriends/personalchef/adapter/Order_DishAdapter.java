package com.goodfriends.personalchef.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.util.AsynImageLoader;

public class Order_DishAdapter extends BaseAdapter {

	private List<Dish> lists;
	private LayoutInflater layoutInflater;

	public Order_DishAdapter(Context context, List<Dish> lists) {
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
			convertView = layoutInflater.inflate(
					R.layout.orderdeatail_listitem, null);
			holder = new viewHolder();
			holder.text_name = (TextView) convertView.findViewById(R.id.name);
			holder.iv_img = (ImageView) convertView.findViewById(R.id.pic);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.text_name.setText(lists.get(position).getName());

		AsynImageLoader asynImageLoader = new AsynImageLoader();
		asynImageLoader.showImageAsyn(holder.iv_img, lists.get(position)
				.getBigimgurl(), R.drawable.nopic);
		return convertView;
	}

	 static class viewHolder {
		private TextView text_name;
		private ImageView iv_img;
	}
}
