package com.goodfriends.personalchef.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.util.ImageCallback;
import com.goodfriends.personalchef.util.LoadImage;
import com.umeng.socialize.controller.impl.InitializeController;

public class XuanAdapter extends BaseAdapter {

	private List<Dish> lists;
	private Context context;
	private LayoutInflater layoutInflater;
	public static Map<Integer, Boolean> isSelected;

	public XuanAdapter(Context context, List<Dish> lists) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.lists = lists;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		isSelected = new HashMap<Integer, Boolean>();
		for(int i=0;i<lists.size();i++)
		{
			isSelected.put(i, false);
		}
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
	public View getView(int pos, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final viewHolder holder;
		if (null == convertView) {
			convertView = layoutInflater.inflate(R.layout.xancai_grid_item,null);
			holder = new viewHolder();
			holder.text_name = (TextView) convertView
					.findViewById(R.id.xuancai_name);
			holder.text_times = (TextView) convertView
					.findViewById(R.id.xuancai_times);
			holder.text_intro = (TextView) convertView
					.findViewById(R.id.xuancai_intro);
			holder.iv_head = (ImageView) convertView
					.findViewById(R.id.xuancai_iv);
			/*
			holder.choice = (TextView) convertView
					.findViewById(R.id.xuancai_choice);
			*/
			holder.cBox = (CheckBox) convertView.findViewById(R.id.cb);
//			holder.choice.setTag(pos);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		
		holder.cBox.setChecked(isSelected.get(pos));
		holder.text_name.setText(lists.get(pos).getName());
		holder.text_times.setText(lists.get(pos).getordercount() + "");
		holder.text_intro.setText(lists.get(pos).getDesc());
		LoadImage loadImage = new LoadImage(context.getResources());
		loadImage.loadImage(lists.get(pos).getBigimgurl(),
				new ImageCallback() {
					public void imageLoaded(Bitmap bitmap) {
						// TODO Auto-generated method stub
						holder.iv_head.setImageBitmap(bitmap);
					}
				});
		return convertView;
	}

	public static  class viewHolder {
		private TextView text_name;
		private TextView text_times;
		private TextView text_intro;
		private ImageView iv_head;
		private TextView choice;
		public CheckBox cBox;
//		public static TextView choice;
	}

}
