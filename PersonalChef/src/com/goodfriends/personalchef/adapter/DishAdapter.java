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

public class DishAdapter extends BaseAdapter {

	private List<Dish> lists;
	private LayoutInflater layoutInflater;

	public DishAdapter(Context context, List<Dish> lists) {
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
			convertView = layoutInflater.inflate(R.layout.dish_list_item, null);
			holder = new viewHolder();
			holder.text_name = (TextView) convertView
					.findViewById(R.id.category_dish_name);
			holder.text_times = (TextView) convertView
					.findViewById(R.id.category_dish_times);
			holder.text_intro = (TextView) convertView
					.findViewById(R.id.category_dish_intro);
			holder.iv_img = (ImageView) convertView
					.findViewById(R.id.category_dish_img);
			holder.chef1 = (ImageView) convertView.findViewById(R.id.iv1);
			holder.chef2 = (ImageView) convertView.findViewById(R.id.iv2);
			holder.chef3 = (ImageView) convertView.findViewById(R.id.iv3);
			holder.chef4 = (ImageView) convertView.findViewById(R.id.iv4);
			holder.chef5 = (ImageView) convertView.findViewById(R.id.iv5);
			holder.chef6 = (ImageView) convertView.findViewById(R.id.iv6);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.text_name.setText(lists.get(position).getName());
		holder.text_times.setText(lists.get(position).getordercount() + "");
		holder.text_intro.setText(lists.get(position).getDesc());
		switch (lists.get(position).getLists().size()) {
		case 0:
			holder.chef1.setImageBitmap(null);
			holder.chef2.setImageBitmap(null);
			holder.chef3.setImageBitmap(null);
			holder.chef4.setImageBitmap(null);
			holder.chef5.setImageBitmap(null);
			holder.chef6.setImageBitmap(null);
			break;
		case 1:
			if (!lists.get(position).getLists().get(0).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef1, lists.get(position)
						.getLists().get(0).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef1.setImageResource(R.drawable.nopic);
			}
			holder.chef2.setImageBitmap(null);
			holder.chef3.setImageBitmap(null);
			holder.chef4.setImageBitmap(null);
			holder.chef5.setImageBitmap(null);
			holder.chef6.setImageBitmap(null);
			break;
		case 2:
			if (!lists.get(position).getLists().get(0).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef1, lists.get(position)
						.getLists().get(0).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(1).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef2, lists.get(position)
						.getLists().get(1).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef2.setImageResource(R.drawable.nopic);
			}
			holder.chef3.setImageBitmap(null);
			holder.chef4.setImageBitmap(null);
			holder.chef5.setImageBitmap(null);
			holder.chef6.setImageBitmap(null);
			break;
		case 3:
			if (!lists.get(position).getLists().get(0).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef1, lists.get(position)
						.getLists().get(0).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(1).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef2, lists.get(position)
						.getLists().get(1).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef2.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(2).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef3, lists.get(position)
						.getLists().get(2).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef3.setImageResource(R.drawable.nopic);
			}
			holder.chef4.setImageBitmap(null);
			holder.chef5.setImageBitmap(null);
			holder.chef6.setImageBitmap(null);
			break;
		case 4:
			if (!lists.get(position).getLists().get(0).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef1, lists.get(position)
						.getLists().get(0).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(1).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef2, lists.get(position)
						.getLists().get(1).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef2.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(2).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef3, lists.get(position)
						.getLists().get(2).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef3.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(3).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef4, lists.get(position)
						.getLists().get(3).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef4.setImageResource(R.drawable.nopic);
			}
			holder.chef5.setImageBitmap(null);
			holder.chef6.setImageBitmap(null);
			break;
		case 5:
			if (!lists.get(position).getLists().get(0).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef1, lists.get(position)
						.getLists().get(0).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(1).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef2, lists.get(position)
						.getLists().get(1).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef2.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(2).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef3, lists.get(position)
						.getLists().get(2).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef3.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(3).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef4, lists.get(position)
						.getLists().get(3).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef4.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(4).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef5, lists.get(position)
						.getLists().get(4).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef5.setImageResource(R.drawable.nopic);
			}
			holder.chef6.setImageBitmap(null);
			break;
		case 6:
			if (!lists.get(position).getLists().get(0).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef1, lists.get(position)
						.getLists().get(0).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(1).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef2, lists.get(position)
						.getLists().get(1).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef2.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(2).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef3, lists.get(position)
						.getLists().get(2).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef3.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(3).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef4, lists.get(position)
						.getLists().get(3).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef4.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(4).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef5, lists.get(position)
						.getLists().get(4).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef5.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(position).getLists().get(5).getHeadpicurl()
					.equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(holder.chef6, lists.get(position)
						.getLists().get(5).getHeadpicurl(), R.drawable.nopic);
			} else {
				holder.chef6.setImageResource(R.drawable.nopic);
			}
			break;
		default:
			break;
		}
		AsynImageLoader asynImageLoader = new AsynImageLoader();
		asynImageLoader.showImageAsyn(holder.iv_img, lists.get(position)
				.getBigimgurl(), R.drawable.nopic);
		return convertView;
	}

	static class viewHolder {
		private TextView text_name;
		private TextView text_times;
		private TextView text_intro;
		private ImageView iv_img, chef1, chef2, chef3, chef4, chef5, chef6;
	}
}
