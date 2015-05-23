package com.goodfriends.personalchef;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.fragment.ChefFragment;
import com.goodfriends.personalchef.fragment.DishFragment;

public class CollectActivity extends FragmentActivity implements
		OnClickListener {

	private RadioButton chef, dish;
	private ImageView back;
	private ChefFragment cFragment;
	private DishFragment dFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_collect);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		chef = (RadioButton) findViewById(R.id.collect_chef);
		dish = (RadioButton) findViewById(R.id.collect_dish);
		back = (ImageView) findViewById(R.id.title_back);

		back.setOnClickListener(this);
		chef.setOnClickListener(this);
		dish.setOnClickListener(this);

		chef.performClick();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction tx = fm.beginTransaction();
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.collect_chef:
			if (cFragment == null) {
				cFragment = new ChefFragment();
			}
			if (!cFragment.isAdded()) {
				tx.add(R.id.collect_container, cFragment);
				tx.addToBackStack(null);
				tx.commit();
			}
			break;
		case R.id.collect_dish:
			if (dFragment == null) {
				dFragment = new DishFragment();
			}
			tx.replace(R.id.collect_container, dFragment);
			tx.addToBackStack(null);
			tx.commit();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
