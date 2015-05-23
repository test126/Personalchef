package com.goodfriends.personalchef.viewflipper;

import com.goodfriends.personalchef.application.SysApplication;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author csm
 *
 */
public class InfiniteLoopViewPager extends MyViewPager {
	
	private SysApplication mApplication;
	private Handler handler;
	
	public InfiniteLoopViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mApplication = (SysApplication)SysApplication.getInstance();
	}

	public InfiniteLoopViewPager(Context context) {
		super(context);
		mApplication = (SysApplication)SysApplication.getInstance();
	}

	@Override
	public void setAdapter(MyPagerAdapter adapter) {
		super.setAdapter(adapter);
		//设置当前展示的位置
		setCurrentItem(0);
	}
	
	public void setInfinateAdapter(Handler handler,MyPagerAdapter adapter){
		this.handler = handler;
		setAdapter(adapter);
	}
	
	@Override
	public void setCurrentItem(int item) {
		item = getOffsetAmount() + (item % getAdapter().getCount());
		super.setCurrentItem(item);
	}
	/**
	 * 从0开始向右(负向滑动)可以滑动的次数
	 * @return
	 */
	private int getOffsetAmount() {
		if (getAdapter() instanceof InfiniteLoopViewPagerAdapter) {
			InfiniteLoopViewPagerAdapter infiniteAdapter = (InfiniteLoopViewPagerAdapter) getAdapter();
			// 允许向前滚动400000次
			return infiniteAdapter.getRealCount() * 100000;
		} else {
			return 0;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			mApplication.isRun = false;
			mApplication.isDown = true;
			handler.removeCallbacksAndMessages(null);
			Log.e("ACTION_DOWN","InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_DOWN");
		} else if (action == MotionEvent.ACTION_UP) {
			mApplication.isRun = true;
			mApplication.isDown = false;
			handler.removeCallbacksAndMessages(null);
			handler.sendEmptyMessage(1);
			Log.e("ACTION_UP","InfiniteLoopViewPager  dispatchTouchEvent =====>>> ACTION_UP");
		}
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public void setOffscreenPageLimit(int limit) {
		super.setOffscreenPageLimit(limit);
	}
}
