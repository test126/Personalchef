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
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.common.UIHelper;
import com.goodfriends.personalchef.util.ImageCallback;
import com.goodfriends.personalchef.util.LoadImage;

public class ChefAdapter extends BaseAdapter {

	private List<Chefs> lists;
	private LayoutInflater layoutInflater;
	private Context context;

	public ChefAdapter(Context context, List<Chefs> lists) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final viewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.chef_list_item, null);
			holder = new viewHolder();
			holder.text_name = (TextView) convertView
					.findViewById(R.id.category_chef_name);
			holder.text_caixi = (TextView) convertView
					.findViewById(R.id.category_chef_caixi);
			holder.text_pinglun = (TextView) convertView
					.findViewById(R.id.category_chef_pinglun);
			holder.iv_head = (ImageView) convertView
					.findViewById(R.id.category_chef_headimg);
			holder.grade1 = (ImageView) convertView.findViewById(R.id.iv1);
			holder.grade2 = (ImageView) convertView.findViewById(R.id.iv2);
			holder.grade3 = (ImageView) convertView.findViewById(R.id.iv3);
			holder.grade4 = (ImageView) convertView.findViewById(R.id.iv4);
			holder.grade5 = (ImageView) convertView.findViewById(R.id.iv5);
			holder.dish1 = (ImageView) convertView
					.findViewById(R.id.category_chef_caipin1);
			holder.dish2 = (ImageView) convertView
					.findViewById(R.id.category_chef_caipin2);
			holder.dish3 = (ImageView) convertView
					.findViewById(R.id.category_chef_caipin3);
			holder.dish4 = (ImageView) convertView
					.findViewById(R.id.category_chef_caipin4);
			holder.chef_distance = (TextView) convertView.findViewById(R.id.category_chef_distance);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.text_name.setText(lists.get(position).getName());
		holder.text_caixi.setText(lists.get(position).getCookstyles());
		holder.text_pinglun.setText(lists.get(position).getIntro());
		holder.chef_distance.setText(UIHelper.friendly_distance(lists.get(position).getDistance()));
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
					public void imageLoaded(Bitmap bitmap) {
						// TODO Auto-generated method stub
						holder.iv_head.setImageBitmap(bitmap);
					}
				});
		switch (lists.get(position).getDishs().size()) {
		case 0:
			break;
		case 1:
			loadImage.loadImage(lists.get(position).getDishs().get(0)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish1.setImageBitmap(bitmap);
				}
			});
			break;
		case 2:
			loadImage.loadImage(lists.get(position).getDishs().get(0)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish1.setImageBitmap(bitmap);
				}
			});
			loadImage.loadImage(lists.get(position).getDishs().get(1)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish2.setImageBitmap(bitmap);
				}
			});
			break;
		case 3:
			loadImage.loadImage(lists.get(position).getDishs().get(0)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish1.setImageBitmap(bitmap);
				}
			});
			loadImage.loadImage(lists.get(position).getDishs().get(1)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish2.setImageBitmap(bitmap);
				}
			});
			loadImage.loadImage(lists.get(position).getDishs().get(2)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish3.setImageBitmap(bitmap);
				}
			});
			break;
		case 4:
			loadImage.loadImage(lists.get(position).getDishs().get(0)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish1.setImageBitmap(bitmap);
				}
			});
			loadImage.loadImage(lists.get(position).getDishs().get(1)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish2.setImageBitmap(bitmap);
				}
			});
			loadImage.loadImage(lists.get(position).getDishs().get(2)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish3.setImageBitmap(bitmap);
				}
			});
			loadImage.loadImage(lists.get(position).getDishs().get(3)
					.getImgurl(), new ImageCallback() {
				public void imageLoaded(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.dish4.setImageBitmap(bitmap);
				}
			});
			break;
		default:
			break;
		}
		return convertView;
	}

	static class viewHolder {
		private TextView text_name;
		private TextView text_caixi;
		private TextView text_pinglun;
		private TextView chef_distance;
		private ImageView iv_head;
		private ImageView grade1, grade3, grade2, grade4, grade5;
		private ImageView dish1, dish2, dish3, dish4;
	}

}
