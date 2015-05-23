package com.goodfriends.personalchef;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.common.CommonFun;

public class FeedBackActivity extends Activity implements OnClickListener {

	private ImageView back;
	private TextView send;
	private EditText mEditText;
	private ProgressDialog progressDialog;
	private String data;
	private int userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);

		userId = getSharedPreferences("USERINFO", Context.MODE_PRIVATE).getInt(
				"userId", 0);
		initView();

		mEditText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub

				return false;
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		send = (TextView) findViewById(R.id.title_send);
		send.setOnClickListener(this);
		mEditText = (EditText) findViewById(R.id.msg);
	}

	void showDialog() {
		progressDialog = new ProgressDialog(FeedBackActivity.this);
		progressDialog.setMessage("提交中...");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	void closeDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.title_send:
			data = mEditText.getText().toString().trim();
			if (data.equals("")) {
				mHandler.sendEmptyMessage(0);
			} else {
				showDialog();
				new Thread(feedbackRunnable).start();
			}
			break;
		default:
			break;
		}
	}

	Runnable feedbackRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = CommonFun.setFeedBack(getApplicationContext(), userId,
					data, 1);
			if (b) {
				mHandler.sendEmptyMessage(200);
			} else {
				mHandler.sendEmptyMessage(3);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 200:
				closeDialog();
				Toast.makeText(FeedBackActivity.this, "已提交,感谢您宝贵的意见.",
						Toast.LENGTH_LONG).show();
				break;
			case 0:
				Toast.makeText(FeedBackActivity.this, "亲,内容不能为空.",
						Toast.LENGTH_LONG).show();
				break;
			case 3:
				closeDialog();
				Toast.makeText(FeedBackActivity.this, "服务器刚才开小差了，请稍后重试哦",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		};
	};
}
