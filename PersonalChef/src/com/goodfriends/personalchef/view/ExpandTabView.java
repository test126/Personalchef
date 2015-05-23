package com.goodfriends.personalchef.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.goodfriends.personalchef.R;

/**
 * 菜单控件头部，封装了下拉动画，动态生成头部按钮个数
 * 
 * @author jour
 */

public class ExpandTabView extends LinearLayout implements OnDismissListener {

	private ToggleButton selectedButton;
	private ArrayList<String> mTextArray = new ArrayList<String>();
	private ArrayList<RelativeLayout> mViewArray = new ArrayList<RelativeLayout>();
	private ArrayList<ToggleButton> mToggleButton = new ArrayList<ToggleButton>();
	private Context mContext;
	private final int SMALL = 0;
	private int displayWidth;
	private int displayHeight;
	private PopupWindow popupWindow;
	private int selectPosition;

	public ExpandTabView(Context context) {
		super(context);
		init(context);
	}

	public ExpandTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * 根据选择的位置设置tabitem显示的值
	 */
	public void setTitle(String valueText, int position,int listindex) {
		if (position < mToggleButton.size()) {
			mToggleButton.get(position).setText(valueText);
			if(listindex == -1)
			{
				mToggleButton.get(position).setTextColor(0xff707070);
			}else {
				mToggleButton.get(position).setTextColor(mContext.getResources().getColor(R.color.orange));
			}
		}
	}
	
	public String getFoodsTitle()
	{
		return (String) mToggleButton.get(1).getText();
	}
	
	public String getDistrict()
	{
		return (String) mToggleButton.get(2).getText();
	}

	/**
	 * 根据选择的位置获取tabitem显示的值
	 */
	public String getTitle(int position) {
		if (position < mToggleButton.size()
				&& mToggleButton.get(position).getText() != null) {
			return mToggleButton.get(position).getText().toString();
		}
		return "";
	}

	/**
	 * 设置tabitem的个数和初始值
	 */
	public void setValue(ArrayList<String> textArray, ArrayList<View> viewArray) {
		if (mContext == null) {
			return;
		}
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mTextArray = textArray;	//菜系，籍贯，距离
		for (int i = 0; i < viewArray.size(); i++) {
			final RelativeLayout r = new RelativeLayout(mContext);
			RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			r.addView(viewArray.get(i), rl);
			mViewArray.add(r);
			r.setTag(SMALL);
			ToggleButton tButton = (ToggleButton) inflater.inflate(
					R.layout.toggle_button, this, false);
			addView(tButton);
			View line = new TextView(mContext);
			line.setBackgroundResource(R.drawable.choosebar_line);
			if (i < viewArray.size() - 1) {
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(2,
						LinearLayout.LayoutParams.MATCH_PARENT);
				addView(line, lp);
			}
			mToggleButton.add(tButton);
			tButton.setTag(i);
			tButton.setText(mTextArray.get(i));

			r.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					onPressBack();
				}
			});

			r.setBackgroundColor(mContext.getResources().getColor(
					R.color.popup_main_background));
			tButton.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					// initPopupWindow();
					ToggleButton tButton = (ToggleButton) view;

					if (selectedButton != null && selectedButton != tButton) {
						selectedButton.setChecked(false);
					}
					selectedButton = tButton;
					selectPosition = (Integer) selectedButton.getTag();
					startAnimation();
					if (mOnButtonClickListener != null && tButton.isChecked()) {
						mOnButtonClickListener.onClick(selectPosition);
					}
				}
			});
		}
	}

	private void startAnimation() {
		if (popupWindow == null) {
			popupWindow = new PopupWindow(mViewArray.get(selectPosition),
					displayWidth, displayHeight);
//			View contentView = LayoutInflater.from(mContext).inflate(R.layout.just5test, null);
//			popupWindow = new PopupWindow(contentView, displayWidth, displayHeight);
			popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
		}
		popupWindow.setFocusable(true);
		if (selectedButton.isChecked()) {
			if (!popupWindow.isShowing()) {
				showPopup(selectPosition);
			} else {
				popupWindow.setOnDismissListener(this);
				popupWindow.dismiss();
				hideView();
			}
		} else {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
				hideView();
			}
		}
	}

	private void showPopup(int position) {
		View tView = mViewArray.get(selectPosition).getChildAt(0);
		if (tView instanceof ViewBaseAction) {
			ViewBaseAction f = (ViewBaseAction) tView;
			f.show();
		}
		if (popupWindow.getContentView() != mViewArray.get(position)) {
//			View contentView = LayoutInflater.from(mContext).inflate(R.layout.just5test, null);
//			popupWindow.setContentView(contentView);
			popupWindow.setContentView(mViewArray.get(position));
		}
		popupWindow.showAsDropDown(this, 0, 0);
	}

	/**
	 * 如果菜单成展开状态，则让菜单收回去
	 */
	public boolean onPressBack() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			hideView();
			if (selectedButton != null) {
				selectedButton.setChecked(false);
			}
			return true;
		} else {
			return false;
		}

	}

	private void hideView() {
		View tView = mViewArray.get(selectPosition).getChildAt(0);
		if (tView instanceof ViewBaseAction) {
			ViewBaseAction f = (ViewBaseAction) tView;
			f.hide();
		}
	}

	private void init(Context context) {
		mContext = context;
		displayWidth = context.getResources().getDisplayMetrics().widthPixels;
		displayHeight = context.getResources().getDisplayMetrics().heightPixels;
		setOrientation(LinearLayout.HORIZONTAL);
	}

	public void onDismiss() {
		showPopup(selectPosition);
		popupWindow.setOnDismissListener(null);
	}

	private OnButtonClickListener mOnButtonClickListener;

	/**
	 * 设置tabitem的点击监听事件
	 */
	public void setOnButtonClickListener(OnButtonClickListener l) {
		mOnButtonClickListener = l;
	}

	/**
	 * 自定义tabitem点击回调接口
	 */
	public interface OnButtonClickListener {
		public void onClick(int selectPosition);
	}
}