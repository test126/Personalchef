package com.goodfriends.personalchef;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodfriends.personalchef.application.SysApplication;

public class WebUrlActivity extends Activity {

	private WebView wv;
	private ProgressDialog dd;
	private String name;
	private ImageView back;
	private TextView tvname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web);
		dd = new ProgressDialog(this);
		dd.setMessage("系 统 加 载 中  .  .  .");
		dd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dd.show();

		initView();

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				WebUrlActivity.this.finish();
			}
		});
		wv.setWebViewClient(new WebViewClient() {

			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);// 网页加载完事件
				// wview.clearHistory();//告诉WebView清除其内部后退/前进列表。
				dd.dismiss();
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}

			public void onFormResubmission(WebView view, Message dontResend,
					Message resend) {
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {
		// TODO Auto-generated method stub
		wv = (WebView) findViewById(R.id.wv);
		back = (ImageView) findViewById(R.id.title_back);
		tvname = (TextView) findViewById(R.id.title_name);
		String url = getIntent().getExtras().getString("url");
		name = "详细信息";
		tvname.setText(name);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl(url);
	}
}
