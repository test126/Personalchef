package com.goodfriends.personalchef;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.Advs;
import com.goodfriends.personalchef.bean.Caixi;
import com.goodfriends.personalchef.bean.Loca;
import com.goodfriends.personalchef.biz.VolleyImage;
import com.goodfriends.personalchef.common.App;
import com.goodfriends.personalchef.common.Common;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.Msg;
import com.goodfriends.personalchef.common.UIHelper;


public class LoadingActivity extends Activity {
	private TextView tv_version;
	public static List<Caixi> caixis = null;
	public static List<String> jiguans;
	public static List<String> distinctStrings;
	public static List<String> foodStrings;
	 List<Advs> advs;
	
	public static Boolean isNetWorkOK = true;
	private String[] distincts = {"罗湖区","福田区","南山区","宝安区","龙岗区","盐田区","其他区"};
	public static Loca loca;
	private boolean b;
	private AlertDialog.Builder builder_call;

	public LocationClient mLocationClient = null;

	public VolleyImage volleyImage;
	
	private boolean receiveLocation = false;
	private boolean isTurnNext = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_loading);
		volleyImage =  new VolleyImage(this,myHandler);
        DisplayMetrics dm = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(dm);
        App.screenHeight = dm.widthPixels;
 
        App.screenWidth = dm.heightPixels;
		
		
		tv_version = (TextView) findViewById(R.id.tv_version);
		try {
			tv_version.setText("版本: V" + UIHelper.getVersionName(this));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null)
					return;
				String pro = location.getCity();
				String city = location.getDistrict();
				String dis = location.getProvince();
				String lat = location.getStreet() + location.getStreetNumber();
				loca = new Loca(pro, city, dis, lat);
				 System.out.println("地图定位返回");
				 if(receiveLocation == false){
					 new Thread(getDistrict).start();
				 }
				 receiveLocation = true;
				 
			}
		});

		SharedPreferences preferences = getSharedPreferences("ISFIRST",
				MODE_PRIVATE);
		b = preferences.getBoolean("isfirst", true);
		try {
			if (Common.checkNetWork(getApplicationContext())) {
				InitLocation();
				mLocationClient.start();
			} else {
				myHandler.sendEmptyMessage(440);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void onDestroy() {
		super.onDestroy();
		mLocationClient.stop();
	};
	
	
	Runnable getDistrict = new Runnable() {
		@Override
		public void run() {
			System.out.println("地区线程启动");
			distinctStrings = CommonFun.getDistrict(LoadingActivity.this);
			if (distinctStrings != null) {
				new Thread(jiguanRunnable).start();
			} else {
				myHandler.sendEmptyMessage(Msg.Get_Fail);
			}
			
		}
	};
	
	public Runnable jiguanRunnable = new Runnable() {

		@Override
		public void run() {
			
			 System.out.println("籍贯线程启动");
			jiguans = CommonFun.getjiguan(LoadingActivity.this);
			if (jiguans != null) {
				new Thread(caixiRunnable).start();
			} else {
				myHandler.sendEmptyMessage(Msg.Get_Fail);
			}
		}
	};

	Runnable caixiRunnable = new Runnable() {
		@Override
		public void run() {
			System.out.println("菜系线程启动");
			caixis = CommonFun.getCaixi(LoadingActivity.this);
			if (caixis != null) {
				new Thread(getFoodsRunnable).start();
			} else {
				myHandler.sendEmptyMessage(Msg.Get_Fail);
			}
		}
	};
	
	Runnable getFoodsRunnable = new Runnable() {
		@Override
		public void run() {
			System.out.println("食物线程启动");
			foodStrings = CommonFun.getFoods(LoadingActivity.this);
			if (foodStrings != null) {
				new Thread(advRunnable).start();
			} else {
				myHandler.sendEmptyMessage(Msg.Get_Fail);
			}
		}
	};
	
	Runnable advRunnable = new Runnable() {
		public void run() {
			System.out.println("广告线程启动");
			// TODO Auto-generated method stub
			advs = CommonFun.getAdv(LoadingActivity.this);
			if (advs != null) {
				volleyImage.requestAdvs();
			} else {
				myHandler.sendEmptyMessage(Msg.Get_Fail);
			}
		}
	};
	
	
	


	
	
	private void toHome()
	{
		
		
		if(isTurnNext == true)
		{
			return;
		}
		System.out.println("toHome");
		isTurnNext = true;
		new Handler().postDelayed(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				
				if (b) {
					SharedPreferences preferences = getSharedPreferences(
							"ISFIRST", MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("isfirst", false);
					editor.commit();
					Intent intent = new Intent(LoadingActivity.this,
							GuideViewActivity.class);
					intent.putExtra("advs", (Serializable) advs);
					
					startActivity(intent);
					LoadingActivity.this.finish();
				} else {
					Intent intentMain = new Intent(LoadingActivity.this,
							MainActivity.class);
					intentMain.putExtra("advs", (Serializable) advs);
					startActivity(intentMain);
					LoadingActivity.this.finish();
				}
			}
		}, 100);
	}
	
	private void errotNetwork()
	{
		LoadingActivity.isNetWorkOK = false;
		Toast.makeText(this,"网络连接异常",Toast.LENGTH_LONG).show();
	}
	
	private void errotServer()
	{
		Toast.makeText(this, "服务器连接异常",Toast.LENGTH_SHORT).show();
	}


	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Msg.Get_Ads_Images:
				toHome();
				break;
			case Msg.Get_Fail:
				errotServer();
				builder_call = new AlertDialog.Builder(LoadingActivity.this);
				builder_call.setMessage("服务器连接异常");
				builder_call.setCancelable(false);
				builder_call.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								LoadingActivity.this.finish();
								dialog.dismiss();
							}
						});
				builder_call.create();
				builder_call.show();
				break;
			case 440:
				/*
				errotNetwork();
				toHome();
				*/
				builder_call = new AlertDialog.Builder(LoadingActivity.this);
				builder_call.setMessage("网络连接异常");
				builder_call.setCancelable(false);
				builder_call.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								LoadingActivity.this.finish();
								dialog.dismiss();
								
							}
						});
				builder_call.create();
				builder_call.show();
				break;
			default:
				break;
			}
		};
	};

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
	}
}
