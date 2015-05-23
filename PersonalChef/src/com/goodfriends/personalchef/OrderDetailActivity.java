package com.goodfriends.personalchef;

import java.net.NetPermission;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.goodfriends.personalchef.adapter.FoodAdapter;
import com.goodfriends.personalchef.adapter.Order_DishAdapter;
import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.Address;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.bean.Food;
import com.goodfriends.personalchef.bean.Order;
import com.goodfriends.personalchef.bean.OrderBean;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.util.AsynImageLoader;
import com.goodfriends.personalchef.util.Utility;

public class OrderDetailActivity extends Activity implements OnClickListener {

	private String orderId;
	private ImageView back, chefHead, certification, gold, star, grade1,
			grade2, grade3, grade4, grade5;
	private OrderBean orderBean;
	private TextView chefName, jiguan, caixi, times, status, price,
			phone, addr, remark, time;
	private ImageView chefPhone;
	private Chefs chefs;
	private Order order;
	private Address address;
	private List<Food> foods;
	private ListView list_food;
	private FoodAdapter foodAdapter;
	private ToggleButton button1, button2;
	private RadioButton radio;
	private ListView listView;
	private TextView cancle, pingjia;
	private int userid;
	private String reason = "";
	private EditText edit;
	private ProgressDialog progressDialog;
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);

		userid = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
		orderId = getIntent().getExtras().getString("orderId");
		
		setContentView(R.layout.activity_orderdetail);

		initView();
		new Thread(runnable).start();
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			orderBean = CommonFun1.getOrderDeatail(orderId);
			if (orderBean != null) {
				myHandler.sendEmptyMessage(orderBean.getErrcode());
			}
		}
	};

	Runnable cancelRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			OrderBean orderBean = CommonFun1.cancleOrder(orderId, userid,
					reason);
			if (orderBean != null) {
				cancleHandler.sendEmptyMessage(orderBean.getErrcode());
			} else {
				cancleHandler.sendEmptyMessage(1);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler cancleHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				closeDialog();
				Toast.makeText(getApplicationContext(), "订单取消成功",
						Toast.LENGTH_SHORT).show();
				OrderDetailActivity.this.finish();
				break;
			case 1:
				closeDialog();
				Toast.makeText(getApplicationContext(), "服务器出错，请稍候重试",
						Toast.LENGTH_SHORT).show();
				break;
			case 201:
				closeDialog();
				Toast.makeText(getApplicationContext(), "订单已开始服务,不能取消",
						Toast.LENGTH_SHORT).show();
				break;
			case 500:
				closeDialog();
				Toast.makeText(getApplicationContext(), "订单取消异常",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				setChefInfo();
				setAddressInfo();
				setFoodInfo();
				break;

			default:
				break;
			}
		}
	};

	private void setAddressInfo() {
		// TODO Auto-generated method stub
		address = orderBean.getAddress();
		if (address != null) {
			addr.setText(address.getAddress());
			phone.setText("联系电话：" + address.getMobile());
		}
	}

	private void setPachInfo() {
		order = orderBean.getOrder();
		time.setText(order.getServicedate() + "点");
		price.setText("共计：￥" + order.getTotalfee() / 100 + "元");
		if (!order.getRemark().equals("")) {
			remark.setText(order.getRemark());
		}
		if (order.getBringcond() == 1) {
			button2.setBackgroundResource(R.drawable.bill_btn_switch_on);
		}
		if (order.getBringfood() == 1) {
			button1.setBackgroundResource(R.drawable.bill_btn_switch_on);
		}
		if (order.getOrdertype() == 1) {// 非套餐
			listView.setVisibility(View.VISIBLE);
			List<Dish> dishs = orderBean.getDishs();
			Order_DishAdapter adapter = new Order_DishAdapter(
					getApplicationContext(), dishs);
			listView.setAdapter(adapter);
			Utility.setListViewHeightBasedOnChildren(listView);
		} else {
			radio.setVisibility(View.VISIBLE);
			radio.setText(orderBean.getPack().getName());
		}
		switch (order.getStatus()) {
		case 1:
			status.setText("已预约");
			cancle.setVisibility(View.VISIBLE);
			break;
		case 2:
			status.setText("已支付");
			cancle.setVisibility(View.VISIBLE);
			break;
		case 3:
			status.setText("待分配");
			cancle.setVisibility(View.VISIBLE);
			break;
		case 4:
			status.setText("已接单");
			cancle.setVisibility(View.VISIBLE);
			break;
		case 5:
			status.setText("已取消");
			chefPhone.clearAnimation();
			chefPhone.setVisibility(View.GONE);
			break;
		case 6:
			status.setText("已出发");
			chefPhone.setVisibility(View.VISIBLE);
//			chefPhone.setText("电话:" + chefs.getMobile());
			break;
		case 7:
			status.setText("服务中");
			chefPhone.setVisibility(View.VISIBLE);
//			chefPhone.setText("电话:" + chefs.getMobile());
			break;
		case 200:
			status.setText("交易成功");
			pingjia.setVisibility(View.VISIBLE);
			chefPhone.clearAnimation();
			chefPhone.setVisibility(View.GONE);
			break;
		case 201:
			status.setText("已评价");
			chefPhone.clearAnimation();
			chefPhone.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

	private void setChefInfo() {
		// TODO Auto-generated method stub
		chefs = orderBean.getChefs();
		setPachInfo();
		AsynImageLoader asynImageLoader = new AsynImageLoader();
		asynImageLoader.showImageAsyn(chefHead, chefs.getHeadpicurl(),
				R.drawable.nopic);
		chefName.setText(chefs.getName());
		jiguan.setText("籍贯:" + chefs.getNativeplace());
		caixi.setText("擅长:" + chefs.getCookstyles());
		times.setText(chefs.getServicecount() + "");
		if (chefs.getIsauth() == 1) {
			certification.setVisibility(View.VISIBLE);
		}
//		chefPhone.setVisibility(View.VISIBLE);
//		chefPhone.setText("电话:" + chefs.getMobile());
		switch (chefs.getGrade()) {
		case 1:
			break;
		case 2:
			gold.setVisibility(View.VISIBLE);
			break;
		case 3:
			gold.setVisibility(View.VISIBLE);
			break;
		case 4:
			star.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		switch (chefs.getScore()) {
		case 1:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 2:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade2.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 3:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade2.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade3.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 4:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade2.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade3.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade4.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		case 5:
			grade1.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade2.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade3.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade4.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			grade5.setBackgroundResource(R.drawable.cookgrade_icon_orange);
			break;
		default:
			break;
		}
	};

	private void setFoodInfo() {
		// TODO Auto-generated method stub
		foods = orderBean.getFoods();
		if (foods != null) {
			foodAdapter = new FoodAdapter(getApplicationContext(), foods);
			list_food.setAdapter(foodAdapter);
			Utility.setListViewHeightBasedOnChildren(list_food);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		chefHead = (ImageView) findViewById(R.id.orderdetail_head);
		chefName = (TextView) findViewById(R.id.orderdetail_name);
		jiguan = (TextView) findViewById(R.id.orderdetail_jiguan);
		caixi = (TextView) findViewById(R.id.orderdetail_caixi);
		time = (TextView) findViewById(R.id.orderdetail_time);
		times = (TextView) findViewById(R.id.orderdetail_times);
		certification = (ImageView) findViewById(R.id.orderdetail_certification);
		gold = (ImageView) findViewById(R.id.orderdetail_gold);
		star = (ImageView) findViewById(R.id.orderdetail_star);
		status = (TextView) findViewById(R.id.orderdetail_status);
		grade1 = (ImageView) findViewById(R.id.orderdeatail_grade1);
		grade2 = (ImageView) findViewById(R.id.orderdeatail_grade2);
		grade3 = (ImageView) findViewById(R.id.orderdeatail_grade3);
		grade4 = (ImageView) findViewById(R.id.orderdeatail_grade4);
		grade5 = (ImageView) findViewById(R.id.orderdeatail_grade5);
		button1 = (ToggleButton) findViewById(R.id.orderdeatail_slipButton);
		button2 = (ToggleButton) findViewById(R.id.orderdeatail_slipButton2);
		radio = (RadioButton) findViewById(R.id.radioButton);
		price = (TextView) findViewById(R.id.price);
		phone = (TextView) findViewById(R.id.orderdetail_phone);
		addr = (TextView) findViewById(R.id.orderdetail_addr);
		remark = (TextView) findViewById(R.id.orderdetail_remark);
		listView = (ListView) findViewById(R.id.list_orderdish);
		cancle = (TextView) findViewById(R.id.quxiao);
		cancle.setOnClickListener(this);
		pingjia = (TextView) findViewById(R.id.pingjia);
		pingjia.setOnClickListener(this);
		list_food = (ListView) findViewById(R.id.shicai);
		chefPhone = (ImageView) findViewById(R.id.orderdetail_chefphone);
		chefPhone.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.orderdetail_chefphone:
			chefs.getMobile();
			Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+chefs.getMobile()));
			startActivity(callIntent);
			break;
		case R.id.title_back:
			this.finish();
			break;
		case R.id.quxiao:
			edit = new EditText(OrderDetailActivity.this);
			edit.setGravity(Gravity.CENTER);
			final FrameLayout fl = new FrameLayout(this);
			fl.addView(edit, new FrameLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT)); // 给某屏幕添加组件
			edit.setHintTextColor(this.getResources()
					.getColor(R.color.greyword));
			edit.setHint("在这写上订单取消的理由");
			AlertDialog alerdialog = dialogCreate();
			alerdialog.setView(fl);// dialog接收(设置)该组件
			alerdialog.show();// EditText 就可以显示在对话框中了
			break;
		case R.id.pingjia:
			Intent intent = new Intent(getApplicationContext(),
					EvalOrderActivity.class);
			intent.putExtra("orderId", orderId);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	public AlertDialog dialogCreate() {
		return new AlertDialog.Builder(OrderDetailActivity.this)
				.setTitle("订单取消")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						/* User clicked OK so do some stuff */
						showDialog("订单取消中...");
						if (!edit.getText().toString().trim().equals("")) {
							reason = edit.getText().toString().trim();
						}
						new Thread(cancelRunnable).start();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						/* User clicked Cancel so do some stuff */
					}
				}).create();
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
}
