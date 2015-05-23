package com.goodfriends.personalchef.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.print.PrintAttributes;
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

public class ViewRight extends RelativeLayout implements ViewBaseAction {

	private ListView mListView;
//	private String[] items = new String[] { "由近到远" };// 显示字段
//	private String[] itemsVaule = new String[] { "1", "2", };// 隐藏id
	private List<String> items = new ArrayList<String>();
	private List<String> itemsVaule = new ArrayList<String>();
//	private String[] itemsVaule = new String[] { "1", "2", };// 隐藏id
	private OnSelectListener mOnSelectListener;
	private TextAdapter adapter;
	private String mDistance;
	private String showText = "距离";

//	public static int position = -1;
	
	public static Button all;

	public String getShowText() {
		return showText;
	}
	
	public void setShowText(String text)
	{
		this.showText = text;
	}

	public ViewRight(Context context) {
		super(context);
		init(context);
	}

	public ViewRight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewRight(Context context, AttributeSet attrs) {
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
		adapter.setTextSize(17);
		for(int i=0;i<LoadingActivity.distinctStrings.size();i++)
		{
			items.add(LoadingActivity.distinctStrings.get(i));
		}
		itemsVaule.add("1");
		itemsVaule.add("2");
		for(int i=0;i<LoadingActivity.distinctStrings.size();i++)
		{
			itemsVaule.add(i+3+"");
		}
		for(int i=0;i<itemsVaule.size();i++)
		{
			Log.e("asdasd", itemsVaule.get(i));
		}
		if (mDistance != null) {
			for (int i = 0; i < itemsVaule.size(); i++) {
				if (itemsVaule.get(i).equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = items.get(i);
					break;
				}
			}
		}

		//mListView.setAdapter(adapter);
		
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setNumColumns(4);
	    gridview.setAdapter(new GridViewAdapter(context,LoadingActivity.distinctStrings));
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//				ViewRight.position = position;
				if (mOnSelectListener != null) {
					showText = items.get(position);
					mOnSelectListener.getValue(itemsVaule.get(position),
							items.get(position),position);
				}
	        }
	    });
		
	    /*
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			public void onItemClick(View view, int position) {
//				ViewRight.position = position;
				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsVaule[position],
							items[position],position);
				}
			}
		});
		*/
		/*
		 * all.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub showText = "籍贯"; } });
		 */
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String distance, String showText,int selectpos);
	}
	
	public void resetSelect()
	{
		adapter.resetPositions();
	}

	public void hide() {

	}

	public void show() {

	}

}
