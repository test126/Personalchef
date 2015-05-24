package com.goodfriends.personalchef;

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
import com.goodfriends.personalchef.bean.Caixi;
import com.goodfriends.personalchef.bean.Loca;
import com.goodfriends.personalchef.biz.VolleyImage;
import com.goodfriends.personalchef.common.App;
import com.goodfriends.personalchef.common.Common;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.UIHelper;


public class LoadingActivity extends Activity {
	private TextView tv_version;
	public static List<Caixi> caixis = null;
	public static List<String> jiguans;
	public static List<String> distinctStrings;
	public static List<String> foodStrings;
	
	public static Boolean isNetWorkOK = true;
	private String[] distincts = {"罗湖区","福田区","南山区","宝安区","龙岗区","盐田区","其他区"};
	public static Loca loca;
	private boolean b;
	private AlertDialog.Builder builder_call;

	public LocationClient mLocationClient = null;

	public VolleyImage volleyImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		new Thread(getFoodsRunnable).start();
		new Thread(getDistrict).start();
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_loading);

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
				// TODO Auto-generated method stub
				if (location == null)
					return;
//				String lat = location.getAddrStr();
				/*
				String pro = location.getProvince();
				String city = location.getCity();
				String dis = location.getDistrict();
				String lat = location.getStreet() + location.getStreetNumber();
				*/
				String pro = location.getCity();
				String city = location.getDistrict();
				String dis = location.getProvince();
				String lat = location.getStreet() + location.getStreetNumber();
				loca = new Loca(pro, city, dis, lat);
				new Thread(jiguanRunnable).start();
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
		
		
		
		volleyImage =  new VolleyImage(this,myHandler);
	}

	protected void onDestroy() {
		super.onDestroy();
		mLocationClient.stop();
	};

	Runnable caixiRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			caixis = CommonFun.getCaixi(LoadingActivity.this);
			if (caixis != null) {
				myHandler.sendEmptyMessage(456);
			} else {
				myHandler.sendEmptyMessage(444);
			}
		}
	};
	
	
	Runnable getDistrict = new Runnable() {
		@Override
		public void run() {
			distinctStrings = CommonFun.getDistrict(LoadingActivity.this);
			if(distinctStrings != null)
			{
				distinctStrings.add(0, "由近到远");
				myHandler.sendEmptyMessage(GETFOODSYES);
			}else {
				myHandler.sendEmptyMessage(GETFOODSNO);
			}
		}
	};
	
	private final int GETFOODSYES = 11;
	private final int GETFOODSNO = 444;
	Runnable getFoodsRunnable = new Runnable() {
		
		@Override
		public void run() {
			foodStrings = CommonFun.getFoods(LoadingActivity.this);
			if(foodStrings != null)
			{
				myHandler.sendEmptyMessage(GETFOODSYES);
			}else {
				myHandler.sendEmptyMessage(GETFOODSNO);
			}
		}
	};

	public Runnable jiguanRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			jiguans = CommonFun.getjiguan(LoadingActivity.this);
			if (jiguans != null) {
				myHandler.sendEmptyMessage(1);
			} else {
				myHandler.sendEmptyMessage(444);
			}
		}
	};
	
	private void toHome()
	{
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
					startActivity(intent);
					LoadingActivity.this.finish();
				} else {
					startActivity(new Intent(LoadingActivity.this,
							MainActivity.class));
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
			case 0:
				toHome();
				break;
			case 1:
				new Thread(caixiRunnable).start();
				break;
			case 456:
				//下载广告图片
				volleyImage.requestAdvs();
				break;
			case 444:
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
			case GETFOODSYES:
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
