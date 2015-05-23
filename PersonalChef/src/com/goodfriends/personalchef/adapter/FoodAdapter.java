package com.goodfriends.personalchef.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.bean.Food;

public class FoodAdapter extends BaseAdapter {

	private List<Food> lists;
	private LayoutInflater layoutInflater;

	public FoodAdapter(Context context, List<Food> lists) {
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
			convertView = layoutInflater.inflate(R.layout.food_list_item, null);
			holder = new viewHolder();
			holder.text_name = (TextView) convertView
					.findViewById(R.id.food_name);
			holder.text_time = (TextView) convertView
					.findViewById(R.id.food_weight);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.text_name.setText(lists.get(position).getFoodname());
		holder.text_time.setText("x" + lists.get(position).getWeight());
		return convertView;
	}

	static class viewHolder {
		private TextView text_name;
		private TextView text_time;
	}

}
