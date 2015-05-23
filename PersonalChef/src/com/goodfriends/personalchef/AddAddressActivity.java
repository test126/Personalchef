package com.goodfriends.personalchef;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.city.City;
import com.goodfriends.personalchef.city.CitySelect1Activity;
import com.goodfriends.personalchef.common.CommonFun;

public class AddAddressActivity extends Activity implements OnClickListener {

	private ImageView back;
	private TextView baocun, name, delete, baocun1;
	private EditText edit1, edit2, edit4;
	private TextView edit3,edit3_1,edit3_2;
	private int userid, addrid;
	private String s1, s2, s3, s4, s3_1, s3_2;
	private boolean isChange;
	private City city ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		isChange = getIntent().getExtras().getBoolean("isChange");
		setContentView(R.layout.activity_add);
		initView();
		initValues();
	}

	Runnable addRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = false;
			b = CommonFun.setAddress(userid, s4, s3, s3_1, s3_2, s2, s1);
			if (b) {
				myHandler.sendEmptyMessage(0);
			} else {
				myHandler.sendEmptyMessage(400);
			}
		}
	};

	Runnable updataRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = CommonFun.updataAddress(addrid, userid, s4, s3, s3_1,
					s3_2, s2, s1);
			if (b) {
				myHandler.sendEmptyMessage(1);
			} else {
				myHandler.sendEmptyMessage(2);
			}
		}
	};

	Runnable deleteRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = CommonFun.deleteAddress(addrid);
			if (b) {
				myHandler.sendEmptyMessage(3);
			} else {
				myHandler.sendEmptyMessage(4);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				closeDialog();
				Toast.makeText(getApplicationContext(), "保存成功",
						Toast.LENGTH_SHORT).show();
				AddAddressActivity.this.finish();
				break;
			case 1:
				closeDialog();
				Toast.makeText(getApplicationContext(), "修改成功",
						Toast.LENGTH_SHORT).show();
				AddAddressActivity.this.finish();
				break;
			case 2:
				closeDialog();
				Toast.makeText(getApplicationContext(), "修改失败，请重试",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				closeDialog();
				Toast.makeText(getApplicationContext(), "删除成功",
						Toast.LENGTH_SHORT).show();
				AddAddressActivity.this.finish();
				break;
			case 4:
				closeDialog();
				Toast.makeText(getApplicationContext(), "删除失败，请重试",
						Toast.LENGTH_SHORT).show();
				break;
			case 400:
				closeDialog();
				Toast.makeText(getApplicationContext(), "保存失败，请重试",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	@SuppressLint("NewApi")
	private void initValues() {
		city = new City();
		// TODO Auto-generated method stub
		if (isChange) {// 编辑旧地址
			name.setText("编辑地址");
			delete.setVisibility(View.VISIBLE);
			baocun.setVisibility(View.GONE);
			baocun1.setVisibility(View.VISIBLE);
			addrid = getIntent().getExtras().getInt("id");
			edit1.setText(getIntent().getExtras().getString("name", ""));
			edit3.setText(getIntent().getExtras().getString("province", ""));
			edit3_1.setText(getIntent().getExtras().getString("city", ""));
			edit3_2.setText(getIntent().getExtras().getString("area", ""));
			edit4.setText(getIntent().getExtras().getString("add", ""));
			
		} else {// 添加新地址
			name.setText("添加地址");
			delete.setVisibility(View.GONE);
			edit3_1.setText(LoadingActivity.loca.getProvince());
			edit3_2.setText(LoadingActivity.loca.getCity());
			edit3.setText(LoadingActivity.loca.getDistrict());
		}
		SharedPreferences preferences = getSharedPreferences("USERINFO",
				MODE_PRIVATE);
		String phone_num = preferences.getString("phone", "");
		userid = preferences.getInt("userId", 0);
		if (!phone_num.equals("")) {
			edit2.setText(phone_num);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		baocun = (TextView) findViewById(R.id.title_collect);
		baocun1 = (TextView) findViewById(R.id.title_collect1);
		baocun.setOnClickListener(this);
		baocun1.setOnClickListener(this);
		edit1 = (EditText) findViewById(R.id.edit1);
		edit2 = (EditText) findViewById(R.id.edit2);
		edit4 = (EditText) findViewById(R.id.edit4);
		name = (TextView) findViewById(R.id.title_name);
		delete = (TextView) findViewById(R.id.delete);
		
		edit3 = (TextView) findViewById(R.id.edit3);
		edit3_1 = (TextView) findViewById(R.id.edit3_1);
		edit3_2 = (TextView) findViewById(R.id.edit3_2);
		edit3.setOnClickListener(this);
		edit3_1.setOnClickListener(this);
		edit3_2.setOnClickListener(this);
		delete.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0 == edit3 || arg0==edit3_1 || arg0 == edit3_2)
		{
			city.setProvince(edit3.getText().toString().trim());
			city.setCity(edit3_1.getText().toString().trim());
			city.setDistrict(edit3_2.getText().toString().trim());
			Intent in = new Intent(this, CitySelect1Activity.class);
			in.putExtra("city", city);
			startActivityForResult(in, 1);
			return;
		}
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.title_collect:
			s1 = edit1.getText().toString().trim();
			s2 = edit2.getText().toString().trim();
			s3 = edit3.getText().toString().trim();
			s3_1 = edit3_1.getText().toString().trim();
			s3_2 = edit3_2.getText().toString().trim();
			s4 = edit4.getText().toString().trim();
			if (!s1.equals("") && !s2.equals("") && !s3.equals("")
					&& !s3_1.equals("") && !s3_2.equals("") && !s4.equals("")) {
				showDialog("正在保存");
				new Thread(addRunnable).start();
			} else {
				Toast.makeText(getApplicationContext(), "亲，请完善您的地址",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.title_collect1:
			s1 = edit1.getText().toString().trim();
			s2 = edit2.getText().toString().trim();
			s3 = edit3.getText().toString().trim();
			s3_1 = edit3_1.getText().toString().trim();
			s3_2 = edit3_2.getText().toString().trim();
			s4 = edit4.getText().toString().trim();
			if (!s1.equals("") && !s2.equals("") && !s3.equals("")
					&& !s3_1.equals("") && !s3_2.equals("") && !s4.equals("")) {
				showDialog("正在更新最新地址...");
				new Thread(updataRunnable).start();
			} else {
				Toast.makeText(getApplicationContext(), "亲，请完善您的地址",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.delete:
			showDialog("正在删除");
			new Thread(deleteRunnable).start();
			break;
		default:
			break;
		}
	}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 8){
			if(requestCode == 1){
				city = data.getParcelableExtra("city");
				edit3.setText("");
				edit3_1.setText("");
				edit3_2.setText("");
				edit3.setText(city.getProvince());
				edit3_1.setText(city.getCity());
				edit3_2.setText(city.getDistrict());
			}else if(requestCode == 2){
				
			}
		}
	}

	private ProgressDialog progressDialog;

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
}
