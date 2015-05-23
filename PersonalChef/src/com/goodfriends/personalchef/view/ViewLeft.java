package com.goodfriends.personalchef.view;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.goodfriends.personalchef.LoadingActivity;
import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.adapter.GridViewAdapter;
import com.goodfriends.personalchef.adapter.TextAdapter;
import com.goodfriends.personalchef.bean.Caixi;
import com.goodfriends.personalchef.fragment.CategaryFragment;

public class ViewLeft extends RelativeLayout implements ViewBaseAction {

	private ListView mListView;
	private String[] items = null;
	private String[] itemsVaule = null;
	private OnSelectListener mOnSelectListener;
	public static TextAdapter adapter;
	private String mDistance;
	private String showText = CategaryFragment.cookstylename;

	public static Button all;
	public static int position = -1;

	public String getShowText() {
		return showText;
	}
	
	public void setShowText(String showText)
	{
		this.showText = showText;
	}

	public ViewLeft(Context context, List<Caixi> caixis) {
		super(context);
		if(caixis == null) return;
		int size = caixis.size();
		items = new String[size];
		itemsVaule = new String[size];
		for (int i = 0; i < caixis.size(); i++) {
			String name = caixis.get(i).getName();
			items[i] = name;
			itemsVaule[i] += i + 1;
		}
		init(context);
	}

	public ViewLeft(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewLeft(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_distance, this, true);
		setBackgroundColor(getResources().getColor(R.color.white));
		mListView = (ListView) findViewById(R.id.listView);
		all = (Button) findViewById(R.id.all);
		adapter = new TextAdapter(context, items, R.drawable.choose_item_right,
				R.drawable.choose_eara_item_selector);
		adapter.setTextSize(20);
		if (mDistance != null) {
			for (int i = 0; i < itemsVaule.length; i++) {
				if (itemsVaule[i].equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = items[i];
					break;
				}
			}
		}
		
//		mListView.setAdapter(adapter);
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setNumColumns(4);
	    List<String> caixiStrings = new ArrayList<String>();
	    for(int i=0;i<LoadingActivity.caixis.size();i++)
	    {
	    	caixiStrings.add(LoadingActivity.caixis.get(i).getName());
	    }
	    gridview.setAdapter(new GridViewAdapter(context,caixiStrings));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				ViewLeft.position = position;
				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsVaule[position],items[position],position);
				}
	        }
	    });

	    adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			public void onItemClick(View view, int position) {
				ViewLeft.position = position;
				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsVaule[position],items[position],position);
				}
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String distance, String showText,int position);
	}

	public void hide() {

	}

	public void show() {

	}

	// private class GridAdapter extends BaseAdapter {
	//
	// private Context context;
	// private LayoutInflater layoutInflater;
	// private List<Caixi> lists;
	//
	// public GridAdapter(Context context, List<Caixi> lists) {
	// // TODO Auto-generated constructor stub
	// this.context = context;
	// this.lists = lists;
	// layoutInflater = (LayoutInflater) context
	// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// }
	//
	// @Override
	// public int getCount() {
	// // TODO Auto-generated method stub
	// return lists.size();
	// }
	//
	// @Override
	// public Object getItem(int arg0) {
	// // TODO Auto-generated method stub
	// return lists.get(arg0);
	// }
	//
	// @Override
	// public long getItemId(int arg0) {
	// // TODO Auto-generated method stub
	// return arg0;
	// }
	//
	// @Override
	// public View getView(int arg0, View convertView, ViewGroup arg2) {
	// // TODO Auto-generated method stub
	// final viewHolder holder;
	// convertView = layoutInflater.inflate(R.layout.grid_item, null);
	// holder = new viewHolder();
	// holder.textView = (TextView) convertView
	// .findViewById(R.id.grid_name);
	// holder.imageView = (ImageView) convertView
	// .findViewById(R.id.grid_iv);
	// holder.textView.setText(lists.get(arg0).getName());
	//
	// AsynImageLoader asynImageLoader = new AsynImageLoader();
	// asynImageLoader.showImageAsyn(holder.imageView, lists.get(arg0)
	// .getImgurl(), R.drawable.nopic);
	// return null;
	// }
	//
	// }

	// static class viewHolder {
	// private TextView textView;
	// private ImageView imageView;
	// }

}
