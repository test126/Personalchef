package com.goodfriends.personalchef.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.goodfriends.personalchef.LoadingActivity;
import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.adapter.GridViewAdapter;
import com.goodfriends.personalchef.adapter.TextAdapter;
import com.goodfriends.personalchef.fragment.CategaryFragment;

public class ViewMiddle1 extends RelativeLayout implements ViewBaseAction {

	private ListView mListView;
	private String[] items = null;
	private String[] itemsVaule = null;
	private OnSelectListener mOnSelectListener;
	private TextAdapter adapter;
	private String mDistance;
	private String showText = "籍贯";
	public static int position = -1;

	public static Button all;

	public String getShowText() {
		if (CategaryFragment.nativeplace.equals("0")) {
			showText = "籍贯";
		}
		return showText;
	}

	public ViewMiddle1(Context context, List<String> strings) {
		super(context);
		if(strings == null) return;
		int size = strings.size();
		items = new String[size];
		itemsVaule = new String[size];
		for (int i = 0; i < strings.size(); i++) {
			String name = strings.get(i);
			items[i] = name;
			itemsVaule[i] += i + 1;
		}
		init(context);
	}

	public ViewMiddle1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewMiddle1(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(final Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_distance, this, true);
		setBackgroundColor(getResources().getColor(R.color.white));
		mListView = (ListView) findViewById(R.id.listView);
		all = (Button) findViewById(R.id.all);
		adapter = new TextAdapter(context, items, R.drawable.choose_item_right,
				R.drawable.choose_eara_item_selector);
		adapter.setTextSize(18);
		if (mDistance != null) {
			for (int i = 0; i < itemsVaule.length; i++) {
				if (itemsVaule[i].equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = items[i];
					break;
				}
			}
		}
		//这个是
		//mListView.setAdapter(adapter);
		
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setNumColumns(4);
	    gridview.setAdapter(new GridViewAdapter(context,LoadingActivity.jiguans));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				ViewMiddle1.position = position;
				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsVaule[position],
							items[position],position);
				}
	        }
	    });
	    
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			public void onItemClick(View view, int position) {

				ViewMiddle1.position = position;
				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsVaule[position],
							items[position],position);
				}
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String distance, String showText,int selectindex);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
