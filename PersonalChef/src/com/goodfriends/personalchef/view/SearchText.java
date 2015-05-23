package com.goodfriends.personalchef.view;


import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.fragment.HomeFragment;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchText extends LinearLayout {
	private ImageView ib_searchtext_delete;
	private EditText et_searchtext_search;
	public Button tv_search_cancel;
	public SearchText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view = LayoutInflater.from(context).inflate(R.layout.view_searchtext, null);
		addView(view);
		ib_searchtext_delete = (ImageView) view.findViewById(R.id.ib_searchtext_delete);
		et_searchtext_search = (EditText) view.findViewById(R.id.et_searchtext_search);
		et_searchtext_search.setHint("厨师名、菜名");
		tv_search_cancel = (Button) view.findViewById(R.id.tv_search_cancel);
		int shearchwidth = HomeFragment.phonewidth*8/10;
		int tv_search_cancel_width = HomeFragment.phonewidth*2/10;
		et_searchtext_search.setWidth(shearchwidth);
		tv_search_cancel.setWidth(tv_search_cancel_width);
		invalidate();
		ib_searchtext_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_searchtext_search.setText("");
			}
		});
		et_searchtext_search.addTextChangedListener(new MyTextWatcher());
	}
	
	public String getSearchText()
	{
		return et_searchtext_search.getText().toString().trim();
	}
	
	private class MyTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if(s.length() > 0){
				ib_searchtext_delete.setVisibility(View.VISIBLE);
			}else{
				ib_searchtext_delete.setVisibility(View.GONE);
			}
		}
		
	}
}
