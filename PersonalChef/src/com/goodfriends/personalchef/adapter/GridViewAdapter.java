package com.goodfriends.personalchef.adapter;

import java.util.List;

import com.goodfriends.personalchef.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter{
	private Context mContext;
	private List<String> jiguans = null;
	
    public GridViewAdapter(Context c,List<String> jiguans) {
        mContext = c;
        this.jiguans = jiguans;
        
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return jiguans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView iv;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	iv = (TextView) LayoutInflater.from(mContext).inflate(
    				R.layout.gridview_button, parent, false);
//        	bt.setLayoutParams(new GridView.LayoutParams(200, 100));
        	iv.setGravity(Gravity.CENTER);
        } else {
        	iv = (TextView) convertView;
        }
        
        iv.setText(jiguans.get(position));
        iv.setFocusable(false);
        return iv;
	}
}
