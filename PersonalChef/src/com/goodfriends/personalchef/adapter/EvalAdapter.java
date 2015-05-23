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
import com.goodfriends.personalchef.bean.Eval;

public class EvalAdapter extends BaseAdapter {

	private List<Eval> lists;
	private LayoutInflater layoutInflater;

	public EvalAdapter(Context context, List<Eval> lists) {
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
			convertView = layoutInflater.inflate(R.layout.eval_list_item, null);
			holder = new viewHolder();
			holder.text_name = (TextView) convertView.findViewById(R.id.name);
			holder.text_time = (TextView) convertView.findViewById(R.id.time);
			holder.text_cont = (TextView) convertView
					.findViewById(R.id.content);
			holder.grade1 = (ImageView) convertView.findViewById(R.id.grade1);
			holder.grade2 = (ImageView) convertView.findViewById(R.id.grade2);
			holder.grade3 = (ImageView) convertView.findViewById(R.id.grade3);
			holder.grade4 = (ImageView) convertView.findViewById(R.id.grade4);
			holder.grade5 = (ImageView) convertView.findViewById(R.id.grade5);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.text_name.setText(lists.get(position).getUsername());
		holder.text_time.setText(lists.get(position).getEvaldate());
		holder.text_cont.setText(lists.get(position).getContent());
		switch (lists.get(position).getScore()) {
		case 1:
			holder.grade1
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 2:
			holder.grade1
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade2
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 3:
			holder.grade1
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade2
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade3
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 4:
			holder.grade1
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade2
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade3
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade4
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 5:
			holder.grade1
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade2
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade3
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade4
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			holder.grade5
					.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		default:
			break;
		}
		return convertView;
	}

	static class viewHolder {
		private TextView text_name;
		private TextView text_time;
		private TextView text_cont;
		private ImageView grade1, grade3, grade2, grade4, grade5;
	}

}
