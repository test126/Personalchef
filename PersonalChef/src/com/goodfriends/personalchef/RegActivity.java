package com.goodfriends.personalchef;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.UserInfo;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.CommonFun1;

public class RegActivity extends Activity implements OnClickListener {

	private ImageView close;
	private EditText phone, auth;
	private Button send, reg;
	private String msg, phone_num, auth_num;
	private TimeCount time;
	private UserInfo info;
	private ProgressDialog progressDialog;
	private int errcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_reg);

		initView();
	}

	Runnable authRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			msg = CommonFun.sendMsg(phone_num);
			if (msg != null) {
				myHandler.sendEmptyMessage(0);
			}

		}
	};

	Runnable regRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			info = CommonFun1.reg(phone_num, auth_num);
			if (info != null) {
				errcode = info.getErrcode();
				myHandler1.sendEmptyMessage(errcode);
			} else {
				myHandler1.sendEmptyMessage(500);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler1 = new Handler() {
		public void handleMessage(Message mesg) {
			switch (mesg.what) {
			case 0:
				closeDialog();
				Toast.makeText(getApplicationContext(), "登录成功!",
						Toast.LENGTH_SHORT).show();
				SharedPreferences preferences = getSharedPreferences(
						"USERINFO", Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putInt("userId", info.getUserid());
				editor.putString("name", info.getName());
				editor.putString("headUrl", info.getHeadpicurl());
				editor.putString("phone", info.getMobile());
				editor.commit();
				RegActivity.this.finish();
				break;
			case 201:
				closeDialog();
				Toast.makeText(getApplicationContext(), info.getErrmsg(),
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(Message mesg) {
			switch (mesg.what) {
			case 0:
				closeDialog();
				Toast.makeText(getApplicationContext(), "验证码已发送，请注意查看短信信息。",
						Toast.LENGTH_SHORT).show();
				time = new TimeCount(120000, 1000);
				time.start();
				break;
			case 500:
				closeDialog();
				Toast.makeText(getApplicationContext(), "登录失败!",
						Toast.LENGTH_SHORT).show();
				break;
			case 101:
				Toast.makeText(getApplicationContext(), "请输入正确的手机号码",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	private void initView() {
		// TODO Auto-generated method stub
		close = (ImageView) findViewById(R.id.reg_close);
		close.setOnClickListener(this);
		phone = (EditText) findViewById(R.id.reg_phone);
		auth = (EditText) findViewById(R.id.reg_auth);
		send = (Button) findViewById(R.id.reg_send);
		send.setOnClickListener(this);
		reg = (Button) findViewById(R.id.reg_reg);
		reg.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.reg_close:
			this.finish();
			break;
		case R.id.reg_send:
			phone_num = phone.getText().toString().trim();
			int length = phone_num.length();
			if (length == 11) {
				showDialog("正在发送验证码，请稍等片刻");
				new Thread(authRunnable).start();
			} else {
				myHandler.sendEmptyMessage(101);
			}
			break;
		case R.id.reg_reg:
			phone_num = phone.getText().toString().trim();
			auth_num = auth.getText().toString().trim();
			if (!phone_num.equals("") && !auth_num.equals("")) {
				showDialog("登录中");
				new Thread(regRunnable).start();
			} else if (auth_num.equals("") && !phone_num.equals("")) {
				Toast.makeText(getApplicationContext(), "验证码不能为空",
						Toast.LENGTH_SHORT).show();
			} else if (!auth_num.equals("") && phone_num.equals("")) {
				Toast.makeText(getApplicationContext(), "手机号不能为空",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "内容不能为空",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	private void showDialog(String s) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(s);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	private void closeDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	private class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			send.setText("重新发送");
			send.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			send.setClickable(false);
			send.setText(millisUntilFinished / 1000 + "秒");
		}
	}
}
