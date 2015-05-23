package com.goodfriends.personalchef.adapter;

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
import com.goodfriends.personalchef.bean.Order;
import com.goodfriends.personalchef.util.ImageCallback;
import com.goodfriends.personalchef.util.LoadImage;

public class OrderAdapter extends BaseAdapter {

	private List<Order> lists;
	private LayoutInflater layoutInflater;
	private Context context;

	public OrderAdapter(Context context, List<Order> lists) {
		// TODO Auto-generated constructor stub
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.lists = lists;
		this.context = context;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final viewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater
					.inflate(R.layout.order_list_item, null);
			holder = new viewHolder();
			holder.odrer_cName = (TextView) convertView
					.findViewById(R.id.order_cName);
			holder.odrer_status = (TextView) convertView
					.findViewById(R.id.order_state);
			holder.odrer_time = (TextView) convertView
					.findViewById(R.id.order_time);
			holder.odrer_dishs = (TextView) convertView
					.findViewById(R.id.order_dishNum);
			holder.odrer_fee = (TextView) convertView
					.findViewById(R.id.order_fee);
			holder.order_pic = (ImageView) convertView
					.findViewById(R.id.order_pic);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.odrer_cName.setText(lists.get(position).getMastername());
		switch (lists.get(position).getStatus()) {
		case 1:
			holder.odrer_status.setText("已预约");
			break;
		case 2:
			holder.odrer_status.setText("已支付");
			break;
		case 3:
			holder.odrer_status.setText("待分配");
			break;
		case 4:
			holder.odrer_status.setText("已接单");
			break;
		case 5:
			holder.odrer_status.setText("已取消");
			break;
		case 6:
			holder.odrer_status.setText("已出发");
			break;
		case 7:
			holder.odrer_status.setText("服务中");
			break;
		case 200:
			holder.odrer_status.setText("交易成功");
			break;
		case 201:
			holder.odrer_status.setText("已评价");
			break;
		default:
			break;
		}
		holder.odrer_time.setText(lists.get(position).getServicedate() + "点");
		if (lists.get(position).getTotaldishcount() == 0) {
			holder.odrer_dishs.setText(lists.get(position).getPackname());
		} else {
			holder.odrer_dishs.setText("共"
					+ lists.get(position).getTotaldishcount() + "个菜品");
		}
		holder.odrer_fee.setText("共计：￥" + lists.get(position).getTotalfee()
				/ 100 + "元");

		if (!lists.get(position).getMasterheadpic().equals("")) {
			LoadImage loadImage = new LoadImage(context.getResources());
			loadImage.loadImage(lists.get(position).getMasterheadpic(),
					new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bitmap) {
							// TODO Auto-generated method stub
							holder.order_pic.setImageBitmap(bitmap);
						}
					});
		}
		return convertView;
	}

	static class viewHolder {
		private TextView odrer_cName;
		private TextView odrer_status;
		private TextView odrer_time;
		private TextView odrer_dishs;
		private TextView odrer_fee;
		private ImageView order_pic;
	}

}
