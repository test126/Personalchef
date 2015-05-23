package com.goodfriends.personalchef.fragment;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.goodfriends.personalchef.ChefDetailActivity;
import com.goodfriends.personalchef.DishDetailActivity;
import com.goodfriends.personalchef.LoadingActivity;
import com.goodfriends.personalchef.MainActivity;
import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.WebUrlActivity;
import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.Advs;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.bean.IndexFun;
import com.goodfriends.personalchef.common.Common;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.UIHelper;
import com.goodfriends.personalchef.util.AsynImageLoader;
import com.goodfriends.personalchef.viewflipper.InfiniteLoopViewPager;
import com.goodfriends.personalchef.viewflipper.InfiniteLoopViewPagerAdapter;
import com.goodfriends.personalchef.viewflipper.MyPagerAdapter;
import com.goodfriends.personalchef.viewflipper.MyViewPager.OnPageChangeListener;

@SuppressLint({ "HandlerLeak", "ClickableViewAccessibility" })
public class HomeFragment extends Fragment implements OnClickListener{

	private TextView chushi_more, caipin_more,tuijianchushi,tuijiancaipin;//, phone;
	private ImageView chushi1, chushi2, chushi3,chushi4, chushi5, chushi6,chushi7, chushi8, chushi9,caipin1, caipin2, caipin3,
			caipin4;
	public ImageView[] chushis = {chushi1, chushi2, chushi3,chushi4, chushi5, chushi6,chushi7, chushi8, chushi9};
	private int[] chushisid = {R.id.home_chushi_iv1, R.id.home_chushi_iv2, R.id.home_chushi_iv3,R.id.home_chushi_iv4,
			R.id.home_chushi_iv5, R.id.home_chushi_iv6,R.id.home_chushi_iv7, R.id.home_chushi_iv8, R.id.home_chushi_iv9};
	private final int CHEFSIZE = 9;
	private List<Advs> advs = null;
	public static List<Dish> dishs = null;
	private List<Chefs> chefs = null;
	private ViewFlipper adv_viewFlipper;
	private RadioGroup radioGroup;
	private ProgressDialog progressDialog;
	private ImageView huodong1, huodong2;
	private int CHECK = 0;
	
	public static int phonewidth = 960;
	
	// 左右滑动时手指按下的X坐标
	float touchDownX;
	// 左右滑动时手指松开的X坐标
	float touchUpX;
	
	//自动轮播部分
	private ImageView[] imageViews;
	private SysApplication mApplication;
	private Handler mHandler;
	private InfiniteLoopViewPager viewPager;
	private InfiniteLoopViewPagerAdapter pagerAdapter;
	private int sleepTime = 3000;
	private int[] imageViewIds;
	private RadioGroup adv_radioGroup;
	private Boolean isInit = false;
	private final int ADVCOUNT = 4;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_home, null);
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mInflater = LayoutInflater.from(getActivity());
		mApplication = (SysApplication)SysApplication.getInstance();
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,
							true);
					if (mApplication.isRun && !mApplication.isDown) {
						this.sendEmptyMessageDelayed(0, sleepTime);
					}
					break;

				case 1:
					if (mApplication.isRun && !mApplication.isDown) {
						this.sendEmptyMessageDelayed(0, sleepTime);
					}
					break;
				}
			}
		};
		
		initView();
		initViewFlipper(4);

		if (advs != null) {
			myHandler.sendEmptyMessage(0);
		} else {
			new Thread(advRunnable).start();
		}
		if (dishs != null) {
			myHandler.sendEmptyMessage(1);
		} else {
			new Thread(dishRunnable).start();
		}
		if (chefs != null) {
			myHandler.sendEmptyMessage(2);
		} else {
			new Thread(chefRunnable).start();
		}
	};

	
	Runnable advRunnable = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			advs = CommonFun.getAdv(getActivity());
			if (advs != null) {
				myHandler.sendEmptyMessage(0);
			}
		}
	};

	Runnable dishRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			dishs = CommonFun.getRecommendDishs(getActivity(), 1, 4);
			if (dishs != null) {
				myHandler.sendEmptyMessage(1);
			}
		}
	};

	Runnable chefRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			chefs = CommonFun.getRecommendMasters(getActivity(), 1, 9);
			if (chefs != null) {
				myHandler.sendEmptyMessage(2);
			}
		}
	};

	synchronized public void notifyFileChanged() {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();	//这里出现out memery
		timer.schedule(new TimerTask() {
			public void run() {
				timer = null;
				Message message = new Message();
				message.what = MSG_FILE_CHANGED_TIMER;
				handler.sendMessage(message);
			}
		}, 3000);
	}

	private static final int MSG_FILE_CHANGED_TIMER = 100;

	private Timer timer;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_FILE_CHANGED_TIMER:
				updateAdvUI();
				notifyFileChanged();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void updateAdvUI() {
		// TODO Auto-generated method stub
		if(adv_viewFlipper.isFlipping())
		{
			return;
		}
		adv_viewFlipper.setInAnimation(
				AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.push_left_in_));
		adv_viewFlipper.setOutAnimation(
				AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.push_left_out_));
		int lengh = advs.size();
		if (lengh != 0 && CHECK < lengh - 1) {
			CHECK += 1;
			radioGroup.check(CHECK);
		} else if (lengh != 0 && CHECK == (lengh - 1)) {
			CHECK = 0;
			radioGroup.check(0);
		}
		adv_viewFlipper.showNext();
	}

	private void updateAdvUI2() {
		// TODO Auto-generated method stub
		adv_viewFlipper.setInAnimation(
				AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.push_right_in_));
		adv_viewFlipper.setOutAnimation(
				AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.push_right_out_));
		int lengh = advs.size();
		if (lengh != 0 && CHECK > 0) {
			CHECK = CHECK - 1;
			radioGroup.check(CHECK);
		} else if (lengh != 0 && CHECK == 0) {
			CHECK = lengh - 1;
			radioGroup.check(CHECK);
		}
		
		adv_viewFlipper.showPrevious();
	}

	private Handler myHandler = new Handler() {
		@SuppressLint("ClickableViewAccessibility")
		public void handleMessage(android.os.Message msg) {
			AsynImageLoader asynImageLoader = new AsynImageLoader();
			switch (msg.what) {
			case 0:
				int size = advs.size();
				/*
				for (int i = 0; i < size; i++) {
					ImageView adv_image = new ImageView(getActivity());
					adv_image.setScaleType(ScaleType.CENTER_CROP);
					AsynImageLoader asynImageLoader2 = new AsynImageLoader();
					asynImageLoader2.showImageAsyn(adv_image, advs.get(i)
							.getImgurl(), R.drawable.nopic);
					adv_viewFlipper.addView(adv_image);
					RadioButton rb = new RadioButton(getActivity());
					rb.setId(i);
					rb.setClickable(false);
					rb.setPadding(45, 0, 0, 0);
					rb.setButtonDrawable(R.drawable.adv_gallery_mark_selector);
					rb.setBackgroundResource(android.R.color.transparent);
					radioGroup.addView(rb);
				}

				radioGroup.check(CHECK);
				closeDialog();
				notifyFileChanged();
				*/
				
				for (int i = 0; i < imageViews.length; i++) {
					if(i >= advs.size()) break;
					imageViews[i] = new ImageView(getActivity());
					imageViews[i].setScaleType(ScaleType.CENTER_CROP);
					AsynImageLoader asynImageLoader2 = new AsynImageLoader();
					asynImageLoader2.showImageAsyn(imageViews[i], advs.get(i)
							.getImgurl(), R.drawable.nopic);
				}
				
				closeDialog();
				break;
			case 1:
				if (dishs.size() != 0) {
					setDishImage();
				}
				break;
			case 2:
				for(int i=0;i<chefs.size();i++)
				{
					asynImageLoader.showImageAsyn(chushis[i], chefs.get(i)
							.getHeadpicurl(), R.drawable.nopic);
				}
				break;
			case 3:
				Intent intent = new Intent(getActivity(), WebUrlActivity.class);
				intent.putExtra("url", indexFuns.get(0).getUrl());
				startActivity(intent);
				break;
			case 4:
				Intent intent1 = new Intent(getActivity(), WebUrlActivity.class);
				intent1.putExtra("url", indexFuns.get(1).getUrl());
				startActivity(intent1);
				break;
			default:
				break;
			}
		}
	};

	private void setDishImage() {
		// TODO Auto-generated method stub
		switch (dishs.size()) {
		case 1:
			if (dishs.get(0).getBigimgurl() != null) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin1, dishs.get(0)
						.getBigimgurl(), R.drawable.nopic);
			} else {
				caipin1.setImageResource(R.drawable.nopic);
			}
			break;
		case 2:
			if (dishs.get(0).getMiddleimgurl() != null) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin1, dishs.get(0)
						.getMiddleimgurl(), R.drawable.nopic);
			} else {
				caipin1.setImageResource(R.drawable.nopic);
			}
			if (dishs.get(1).getSmallimgurl() != null) {

				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin2, dishs.get(1)
						.getSmallimgurl(), R.drawable.nopic);
			} else {
				caipin2.setImageResource(R.drawable.ic_launcher);
			}
			break;
		case 3:
			if (dishs.get(0).getSmallimgurl() != null) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin1, dishs.get(0)
						.getSmallimgurl(), R.drawable.nopic);
			} else {
				caipin1.setImageResource(R.drawable.nopic);
			}
			if (dishs.get(1).getSmallimgurl() != null) {

				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin2, dishs.get(1)
						.getSmallimgurl(), R.drawable.nopic);
			} else {
				caipin2.setImageResource(R.drawable.ic_launcher);
			}
			if (dishs.get(2).getSmallimgurl() != null) {

				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin3, dishs.get(2)
						.getSmallimgurl(), R.drawable.nopic);
			} else {
				caipin3.setImageResource(R.drawable.ic_launcher);
			}
			break;
		case 4:
			if (dishs.get(0).getSmallimgurl() != null) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin1, dishs.get(0)
						.getSmallimgurl(), R.drawable.nopic);
			} else {
				caipin1.setImageResource(R.drawable.nopic);
			}
			if (dishs.get(1).getSmallimgurl() != null) {

				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin2, dishs.get(1)
						.getSmallimgurl(), R.drawable.nopic);
			} else {
				caipin2.setImageResource(R.drawable.ic_launcher);
			}
			if (dishs.get(2).getSmallimgurl() != null) {

				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin3, dishs.get(2)
						.getSmallimgurl(), R.drawable.nopic);
			} else {
				caipin3.setImageResource(R.drawable.ic_launcher);
			}
			if (dishs.get(3).getSmallimgurl() != null) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(caipin4, dishs.get(3)
						.getSmallimgurl(), R.drawable.nopic);
			} else {
				caipin4.setImageResource(R.drawable.ic_launcher);
			}
			break;
		default:
			break;
		}
	};
	
	

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tuijianchushi:
		case R.id.home_chushi_more:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = 0;
			MainActivity.mTab2.performClick();
			newCategory();
			break;
//		case R.id.tuijiancaipin:
		case R.id.home_caipin_more:
			CategaryFragment.SWITCH = Common.CAIPIN;
			CategaryFragment.cookstyleid = 0;
			MainActivity.mTab2.performClick();
			newCategory();
			break;
		case R.id.home_chushi_iv1:
			if (chefs != null) {
				if (chefs.size() > 0) {
					toChefDetail(chefs.get(0).getId());
				}
			}
			break;
		case R.id.home_chushi_iv2:
			if (chefs != null) {
				if (chefs.size() > 1) {
					toChefDetail(chefs.get(1).getId());
				}
			}
			break;
		case R.id.home_chushi_iv3:
			if (chefs != null) {
				if (chefs.size() > 2) {
					toChefDetail(chefs.get(2).getId());
				}
			}
			break;
		case R.id.home_chushi_iv4:
			if (chefs != null) {
				if (chefs.size() > 3) {
					toChefDetail(chefs.get(3).getId());
				}
			}
			break;
		case R.id.home_chushi_iv5:
			if (chefs != null) {
				if (chefs.size() > 4) {
					toChefDetail(chefs.get(4).getId());
				}
			}
			break;
		case R.id.home_chushi_iv6:
			if (chefs != null) {
				if (chefs.size() > 5) {
					toChefDetail(chefs.get(5).getId());
				}
			}
			break;
		case R.id.home_chushi_iv7:
			if (chefs != null) {
				if (chefs.size() > 6) {
					toChefDetail(chefs.get(6).getId());
				}
			}
			break;
		case R.id.home_chushi_iv8:
			if (chefs != null) {
				if (chefs.size() > 7) {
					toChefDetail(chefs.get(7).getId());
				}
			}
			break;
		case R.id.home_chushi_iv9:
			if (chefs != null) {
				if (chefs.size() > 8) {
					toChefDetail(chefs.get(8).getId());
				}
			}
			break;
		case R.id.home_caipin_iv1:
			if (dishs != null) {
				if (dishs.size() > 0) {
					toDishDetail(dishs.get(0).getDishid());
				}
			}
			break;
		case R.id.home_caipin_iv2:
			if (dishs != null) {
				if (dishs.size() > 1) {
					toDishDetail(dishs.get(1).getDishid());
				}
			}
			break;
		case R.id.home_caipin_iv3:
			if (dishs != null) {
				if (dishs.size() > 2) {
					toDishDetail(dishs.get(2).getDishid());
				}
			}
			break;
		case R.id.home_caipin_iv4:
			if (dishs != null) {
				if (dishs.size() > 3) {
					toDishDetail(dishs.get(3).getDishid());
				}
			}
			break;
		case R.id.ll1:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = LoadingActivity.caixis.get(0)
					.getId();
			CategaryFragment.cookstylename = LoadingActivity.caixis.get(0)
					.getName();
			newCategory();
			break;
		case R.id.ll2:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = LoadingActivity.caixis.get(1)
					.getId();
			CategaryFragment.cookstylename = LoadingActivity.caixis.get(1)
					.getName();
			newCategory();
			break;
		case R.id.ll3:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = LoadingActivity.caixis.get(2)
					.getId();
			CategaryFragment.cookstylename = LoadingActivity.caixis.get(2)
					.getName();
			newCategory();
			break;
		case R.id.ll4:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = LoadingActivity.caixis.get(3)
					.getId();
			CategaryFragment.cookstylename = LoadingActivity.caixis.get(3)
					.getName();
			newCategory();
			break;
		case R.id.ll5:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = LoadingActivity.caixis.get(4)
					.getId();
			CategaryFragment.cookstylename = LoadingActivity.caixis.get(4)
					.getName();
			newCategory();
			break;
		case R.id.ll6:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = LoadingActivity.caixis.get(5)
					.getId();
			CategaryFragment.cookstylename = LoadingActivity.caixis.get(5)
					.getName();
			newCategory();
			break;
		case R.id.ll7:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = LoadingActivity.caixis.get(6)
					.getId();
			CategaryFragment.cookstylename = LoadingActivity.caixis.get(6)
					.getName();
			newCategory();
			break;
		case R.id.ll8:
			CategaryFragment.SWITCH = Common.CHUSHI;
			CategaryFragment.cookstyleid = LoadingActivity.caixis.get(7)
					.getId();
			CategaryFragment.cookstylename = LoadingActivity.caixis.get(7)
					.getName();
			newCategory();
			break;
		case R.id.home_huodong1:
			getIndexFun1();
			break;
		case R.id.home_huodong2:
			getIndexFun2();
			break;
		//客服电话，现在去掉
		/*
		case R.id.phone:
			AlertDialog.Builder builder_call = new AlertDialog.Builder(
					getActivity());
			builder_call.setMessage("拨打客服电话" + "4009916999" + "？");
			builder_call.setCancelable(false);
			builder_call.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + "4009916999"));
							startActivity(intent);
							dialog.dismiss();
						}
					}).setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			builder_call.create();
			builder_call.show();
			break;
		*/
		/*
		case R.id.news_body_veiw:
			Intent intent = new Intent(getActivity(), WebUrlActivity.class);
			String url = advs.get(CHECK).getUrl();
			intent.putExtra("url", url);
			startActivity(intent);
			break;
		*/
		default:
			break;
		}
	}

	private List<IndexFun> indexFuns;

	private void getIndexFun1() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				indexFuns = CommonFun.getIndexFun(1);
				if (indexFuns != null) {
					myHandler.sendEmptyMessage(3);
				}
			}
		}).start();
	}

	private void getIndexFun2() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				indexFuns = CommonFun.getIndexFun(2);
				if (indexFuns != null) {
					myHandler.sendEmptyMessage(4);
				}
			}
		}).start();
	}

	private void newCategory() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FragmentTransaction tx = fm.beginTransaction();
		if (MainActivity.categoryFragment == null) {
			MainActivity.categoryFragment = new CategaryFragment();
		} else {
			tx.remove(MainActivity.categoryFragment);
			MainActivity.categoryFragment = new CategaryFragment();
		}
		tx.replace(R.id.container, MainActivity.categoryFragment);
		tx.addToBackStack(null);
		tx.commit();
		MainActivity.mTab2.performClick();
	}

	private void toChefDetail(int id) {
		Intent intent = new Intent(getActivity(), ChefDetailActivity.class);
		intent.putExtra("chefId", id);
		startActivity(intent);
	}

	private void toDishDetail(int id) {
		Intent intent = new Intent(getActivity(), DishDetailActivity.class);
		intent.putExtra("dishId", id);
		startActivity(intent);
	}

	private void initViewFlipper(int size)
	{
		adv_radioGroup = (RadioGroup)getActivity().findViewById(
				R.id.advs_gallery_mark1);
		for(int i=0;i<size;i++)
		{
			RadioButton rb = new RadioButton(getActivity());
			rb.setId(i);
			rb.setClickable(false);
			rb.setPadding(45, 0, 0, 0);
			rb.setButtonDrawable(R.drawable.adv_gallery_mark_selector);
			rb.setBackgroundResource(android.R.color.transparent);
			adv_radioGroup.addView(rb);
		}
		adv_radioGroup.check(CHECK);
		
		imageViewIds = new int[] { R.drawable.nopic, R.drawable.nopic, R.drawable.nopic,R.drawable.nopic};
		imageViews = new ImageView[size];
		for (int i = 0; i < imageViews.length; i++) {
			imageViews[i] = new ImageView(getActivity());
			imageViews[i].setImageResource(imageViewIds[i]);
		}
		viewPager = (InfiniteLoopViewPager) getActivity().findViewById(R.id.viewpager);
		pagerAdapter = new InfiniteLoopViewPagerAdapter(new MyLoopViewPagerAdatper());
		viewPager.setInfinateAdapter(mHandler, pagerAdapter);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setOnTouchListener(viewPagerListener);
	}
	
	@SuppressWarnings("deprecation")
	private void initView() {
		showDialog("loading");
		chushi_more = (TextView) getActivity().findViewById(
				R.id.home_chushi_more);
		chushi_more.setOnClickListener(this);
		caipin_more = (TextView) getActivity().findViewById(
				R.id.home_caipin_more);
		caipin_more.setOnClickListener(this);
		tuijianchushi = (TextView) getActivity().findViewById(R.id.tuijianchushi);
		tuijianchushi.setOnClickListener(this);
//		tuijiancaipin = (TextView) getActivity().findViewById(R.id.tuijiancaipin);
//		tuijiancaipin.setOnClickListener(this);
		
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int chefwidth = width/3-7;
		phonewidth = width;
		for(int i=0;i<CHEFSIZE;i++)
		{
			chushis[i] = (ImageView) getActivity().findViewById(chushisid[i]);
			chushis[i].setOnClickListener(this);
			LayoutParams lp = chushis[i].getLayoutParams();
			lp.width = chefwidth;
			chushis[i].setLayoutParams(lp);
		}
		
		caipin1 = (ImageView) getActivity().findViewById(R.id.home_caipin_iv1);
		caipin1.setOnClickListener(this);
		caipin2 = (ImageView) getActivity().findViewById(R.id.home_caipin_iv2);
		caipin2.setOnClickListener(this);
		caipin3 = (ImageView) getActivity().findViewById(R.id.home_caipin_iv3);
		caipin3.setOnClickListener(this);
		caipin4 = (ImageView) getActivity().findViewById(R.id.home_caipin_iv4);
		caipin4.setOnClickListener(this);
		/*
		radioGroup = (RadioGroup) getActivity().findViewById(
				R.id.advs_gallery_mark);
		*/
		adv_radioGroup = (RadioGroup)getActivity().findViewById(
				R.id.advs_gallery_mark1);
		huodong1 = (ImageView) getActivity().findViewById(R.id.home_huodong1);
		huodong1.setOnClickListener(this);
		huodong2 = (ImageView) getActivity().findViewById(R.id.home_huodong2);
		huodong2.setOnClickListener(this);
		caixi_ll1 = (LinearLayout) getActivity().findViewById(R.id.ll1);
		caixi_ll1.setOnClickListener(this);
		caixi_ll2 = (LinearLayout) getActivity().findViewById(R.id.ll2);
		caixi_ll2.setOnClickListener(this);
		caixi_ll3 = (LinearLayout) getActivity().findViewById(R.id.ll3);
		caixi_ll3.setOnClickListener(this);
		caixi_ll4 = (LinearLayout) getActivity().findViewById(R.id.ll4);
		caixi_ll4.setOnClickListener(this);
		caixi_ll5 = (LinearLayout) getActivity().findViewById(R.id.ll5);
		caixi_ll5.setOnClickListener(this);
		caixi_ll6 = (LinearLayout) getActivity().findViewById(R.id.ll6);
		caixi_ll6.setOnClickListener(this);
		caixi_ll7 = (LinearLayout) getActivity().findViewById(R.id.ll7);
		caixi_ll7.setOnClickListener(this);
		caixi_ll8 = (LinearLayout) getActivity().findViewById(R.id.ll8);
		caixi_ll8.setOnClickListener(this);
		//这是客服电话，现在去掉
		/*
		phone = (TextView) getActivity().findViewById(R.id.phone);
		phone.setOnClickListener(this);
		*/
		/*
		adv_viewFlipper = (ViewFlipper) getActivity().findViewById(
				R.id.news_body_veiw);
		adv_viewFlipper.setOnTouchListener(touchListener);
		*/
		/*
		initChefData();
		initChefView();
		*/
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		CHECK = 0;
	}

	private void showDialog(String s) {
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(s);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(true);
		progressDialog.show();
	}

	private void closeDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	private LinearLayout caixi_ll1, caixi_ll2, caixi_ll3, caixi_ll4, caixi_ll5,
			caixi_ll6, caixi_ll7, caixi_ll8;

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
	
	/*
	public OnTouchListener touchListener = new OnTouchListener() {
		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			adv_viewFlipper.stopFlipping();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchDownX = event.getX();
				break;
			case MotionEvent.ACTION_MOVE:
				if(event.getX()-touchDownX > 20)
				{
					updateAdvUI2();
				}
				if(touchDownX-event.getX() > 20)
				{
					updateAdvUI();
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
			default:
				break;
			}
			return true;
		}
		
	}; 
	*/
	
	private class MyOnPageChangeListener implements OnPageChangeListener {
		/**
		 * Indicates that the pager is in an idle, settled state. The current
		 * page is fully in view and no animation is in progress.
		 */
		public static final int SCROLL_STATE_IDLE = 0;

		/**
		 * Indicates that the pager is currently being dragged by the user.
		 */
		
		public static final int SCROLL_STATE_DRAGGING = 1;

		/**
		 * Indicates that the pager is in the process of settling to a final
		 * position.
		 */
		public static final int SCROLL_STATE_SETTLING = 2;

		@Override
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			case SCROLL_STATE_IDLE:
				break;
			case SCROLL_STATE_DRAGGING:
				break;
			case SCROLL_STATE_SETTLING:
				break;
			}

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			adv_radioGroup.check(position%(imageViews.length));
		}

		@Override
		public void onPageSelected(int position) {
		}

	}
	
	private class MyLoopViewPagerAdatper extends MyPagerAdapter {

		@Override
		public int getCount() {
			return imageViews.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (View) object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// return super.instantiateItem(container, position);
			imageViews[position].setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
//					System.out.println("========>>> 点击了viewpager的第 " + position + " 项");
					if(position >= advs.size())
					{
						return;
					}
					Intent intent = new Intent(getActivity(), WebUrlActivity.class);
					String url = advs.get(position).getUrl();
					intent.putExtra("url", url);
					startActivity(intent);
				}
			});
			container.addView(imageViews[position]);
			return imageViews[position];
		}
	}

	public OnTouchListener viewPagerListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			v.getParent().requestDisallowInterceptTouchEvent(true);
			return false;
		}
	};
	
	private LinearLayout mGallery;
	private int[] mImgIds;
	private LayoutInflater mInflater;
	private void initChefData()
	{
		mImgIds = new int[] { R.drawable.guide1, R.drawable.guide2, R.drawable.guide3,
				R.drawable.guide1, R.drawable.guide2, R.drawable.guide3,R.drawable.guide1,
				R.drawable.guide2, R.drawable.guide3 };
	}
	
	private void initChefView()
	{
//		mGallery = (LinearLayout) getActivity().findViewById(R.id.id_gallery);
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels; 
		for (int i = 0; i < mImgIds.length; i++)
		{
			
			View view = mInflater.inflate(R.layout.activity_index_gallery_item,
					mGallery, false);
			ImageView img = (ImageView) view
					.findViewById(R.id.id_index_gallery_item_image);
			LayoutParams lp = img.getLayoutParams();
			lp.width = width/3; //设置图片控件的大小
			img.setLayoutParams(lp);
			/*
			ImageView img1 = (ImageView) view
					.findViewById(R.id.id_index_gallery_item_image1);
			ImageView img2 = (ImageView) view
					.findViewById(R.id.id_index_gallery_item_image2);
			*/
			img.setImageResource(mImgIds[i]);
			/*
			img1.setImageResource(mImgIds[i*3+1]);
			img2.setImageResource(mImgIds[i*3+2]);
			*/
			mGallery.addView(view);
		}
	}
	
	
	public void toChef(View v)
	{
		CategaryFragment.SWITCH = Common.CHUSHI;
		CategaryFragment.cookstyleid = 0;
		MainActivity.mTab2.performClick();
		newCategory();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mApplication.isRun = false;
		mHandler.removeCallbacksAndMessages(null);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mApplication.isRun = true;
		mHandler.sendEmptyMessageDelayed(0, sleepTime);
	}	
}