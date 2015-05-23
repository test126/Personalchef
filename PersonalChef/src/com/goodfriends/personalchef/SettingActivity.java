package com.goodfriends.personalchef;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.common.CommonFun;

public class SettingActivity extends Activity implements OnClickListener {

	private ImageView back;
	private TextView exit, about, like;
	private String version;
	private TextView version_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		exit = (TextView) findViewById(R.id.exit);
		exit.setOnClickListener(this);
		version_info = (TextView) findViewById(R.id.updata_info);
		version = getVersion(getApplicationContext());
		like = (TextView) findViewById(R.id.likethis);
		like.setOnClickListener(this);
		about = (TextView) findViewById(R.id.about);
		about.setOnClickListener(this);
		new Thread(checkRunnable).start();
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.exit:
			AlertDialog.Builder builder_call = new AlertDialog.Builder(
					SettingActivity.this);
			builder_call.setMessage("确定退出");
			builder_call.setCancelable(false);
			builder_call.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							SharedPreferences preferences = getSharedPreferences(
									"USERINFO", Context.MODE_PRIVATE);
							Editor editor = preferences.edit();
							editor.putInt("userId", 0);
							editor.putString("name", "");
							editor.putString("headUrl", "");
							editor.putString("phone", "");
							editor.commit();
							SysApplication.getInstance().exit();
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
		case R.id.about:
			Toast.makeText(getApplicationContext(), "即将开通，敬请期待",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.likethis:
			Toast.makeText(getApplicationContext(), "即将开通，敬请期待",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	Runnable checkRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = CommonFun.checkApkVersion(version);
			if (b) {
				myHandler.sendEmptyMessage(0);
			} else {
				myHandler.sendEmptyMessage(1);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				version_info.setText("已是最新版本");
				break;
			case 1:
				version_info.setBackgroundResource(R.drawable.mine_icon_new);
				break;
			default:
				break;
			}
		};
	};

	private String getVersion(Context context) {
		String version;
		try {
			version = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			version = "";
			e.printStackTrace();
		}
		return version;
	}
}
