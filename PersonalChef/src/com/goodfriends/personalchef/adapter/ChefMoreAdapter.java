package com.goodfriends.personalchef.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.util.ImageCallback;
import com.goodfriends.personalchef.util.LoadImage;

public class ChefMoreAdapter extends BaseAdapter {

	private List<Chefs> lists;
	private LayoutInflater layoutInflater;
	private Context context;
	private HashMap<Integer, Integer> map;

	public ChefMoreAdapter(Context context, List<Chefs> lists,
			HashMap<Integer, Integer> map) {
		// TODO Auto-generated constructor stub
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lists = lists;
		this.context = context;
		this.map = map;
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
			convertView = layoutInflater.inflate(R.layout.morechef_item, null);
			holder = new viewHolder();
			holder.text_name = (TextView) convertView
					.findViewById(R.id.more_name);
			holder.text_caixi = (TextView) convertView
					.findViewById(R.id.more_caixi);
			holder.text_jiguan = (TextView) convertView
					.findViewById(R.id.more_jiguan);
			holder.text_fuwu = (TextView) convertView
					.findViewById(R.id.more_fuwu);
			holder.text_pingjia = (TextView) convertView
					.findViewById(R.id.more_pingjia);
			// holder.isChecked = (RadioButton) convertView
			// .findViewById(R.id.isChecked);
			holder.grade1 = (ImageView) convertView.findViewById(R.id.grade1);
			holder.grade2 = (ImageView) convertView.findViewById(R.id.grade2);
			holder.grade3 = (ImageView) convertView.findViewById(R.id.grade3);
			holder.grade4 = (ImageView) convertView.findViewById(R.id.grade4);
			holder.grade5 = (ImageView) convertView.findViewById(R.id.grade5);
			holder.iv_head = (ImageView) convertView
					.findViewById(R.id.chef_img);
			holder.gold = (ImageView) convertView.findViewById(R.id.more_gold);
			holder.star = (ImageView) convertView.findViewById(R.id.more_star);
			holder.collect = (ImageView) convertView
					.findViewById(R.id.more_certification);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.text_name.setText(lists.get(position).getName());
		holder.text_caixi.setText(lists.get(position).getCookstyles());
		holder.text_jiguan
				.setText("籍贯：" + lists.get(position).getNativeplace());
		holder.text_fuwu.setText(lists.get(position).getServicecount() + "");
		holder.text_pingjia.setText(lists.get(position).getEvalcount() + "");
		// holder.isChecked.setChecked(map.get(position) == null ? false :
		// true);
		if (map.get(position) != null) {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.blue));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.white));
		}
		if (lists.get(position).getIsauth() == 1) {
			holder.collect.setVisibility(View.VISIBLE);
		}
		switch (lists.get(position).getGrade()) {
		case 1:
			break;
		case 2:
			holder.gold.setVisibility(View.VISIBLE);
			break;
		case 3:
			holder.gold.setVisibility(View.VISIBLE);
			break;
		case 4:
			holder.star.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
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
		LoadImage loadImage = new LoadImage(context.getResources());
		loadImage.loadImage(lists.get(position).getHeadpicurl(),
				new ImageCallback() {

					@Override
					public void imageLoaded(Bitmap bitmap) {
						// TODO Auto-generated method stub
						holder.iv_head.setImageBitmap(bitmap);
					}
				});
		return convertView;
	}

	static class viewHolder {
		private TextView text_name;
		private TextView text_caixi;
		private TextView text_jiguan;
		private TextView text_fuwu;
		private TextView text_pingjia;
		private ImageView iv_head;
		// private RadioButton isChecked;
		private ImageView grade1, grade3, grade2, grade4, grade5;
		private ImageView gold, star, collect;
	}
}
