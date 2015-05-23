package com.goodfriends.personalchef.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodfriends.personalchef.R;

public class DateAdapter extends BaseAdapter {

	private Context context;
	/** 列表. */
	private List<String> lstDate;
	/** 名字. */
	private TextView txtName;

	// 每页显示的Item个数
	public static final int SIZE = 5;

	public TextView getTxtName() {
		return txtName;
	}

	public DateAdapter(Context mContext, List<String> list, int page) {
		this.context = mContext;
		lstDate = new ArrayList<String>();
		int i = page * SIZE;
		int iEnd = i + SIZE;
		while ((i < list.size()) && (i < iEnd)) {
			lstDate.add(list.get(i));
			i++;
		}
	}

	@Override
	public int getCount() {
		return lstDate.size();
	}

	@Override
	public Object getItem(int position) {
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.item, null);

		txtName = (TextView) convertView.findViewById(R.id.txt_userName);

		if (position == 0) {
			parent.setTag(convertView);
			convertView
					.setBackgroundResource(R.drawable.bill_btn_calendar_selected);
		}
		txtName.setText(lstDate.get(position));

		return convertView;
	}

}
