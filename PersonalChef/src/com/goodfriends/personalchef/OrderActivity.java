package com.goodfriends.personalchef;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.goodfriends.personalchef.adapter.DateAdapter;
import com.goodfriends.personalchef.adapter.Order_DishAdapter;
import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.Address;
import com.goodfriends.personalchef.bean.AddressBean;
import com.goodfriends.personalchef.bean.CouPon;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.bean.InitOrder;
import com.goodfriends.personalchef.bean.OrderRepeatJudge;
import com.goodfriends.personalchef.bean.ServiceTime;
import com.goodfriends.personalchef.bean.Times;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.common.PreferencesObj;
import com.goodfriends.personalchef.util.Utility;
import com.goodfriends.personalchef.view.ScrollLayout;

public class OrderActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private ProgressDialog progressDialog;
	private ToggleButton toButton1, toButton2;
	private ImageView back;
	private ScrollLayout scrlayout;
	private List<String> lists;
	private static final float PAGE_SIZE = 5.0f;
	private int PageCount;
	private int PageCurrent;
	private int gridID = 0;
	private GridView gridView;
	private int ordertype, masterid;
	private EditText edit_remark;
	private int userId, bringfood = 0, bringcond = 0, pickid, addrid, orderfee,
			dishCount, paytype = 2;
	private String remark = "";
	private Button sure;
	private String servicedate = "";
	private String result;
	private int custdishcount = 0;
	private List<ServiceTime> serviceTimes;
	private List<Times> times;
	private InitOrder initOrder;
	private String signature;
	private RadioGroup radioGroup;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
	
	private int dishCount_;
	private int id_;
	private String dishids_;
	private int ordertype_;
	private int orderfee_;
	private long timestamp_;

	private LinearLayout ll_addr;
	private TextView bill_phone, bill_addr;
	private List<Address> address;
	private AddressBean addressBean;
	private String dishids = "";
	private ListView listView;
	private List<Dish> dishs = null;
	private Order_DishAdapter dishAdapter;
	// private CheckBox checkBox;
	// private TextView textView;

	private TextView coupon;
	private LinearLayout ll_coupon;
	private List<CouPon> coupons = null;
	private int userCouponid = 0, couponValue = 0;

	private LinearLayout intro;
	private TextView introName;

	private TextView packIntro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		userId = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
		setContentView(R.layout.activity_bill);

		showDialog("订单正在初始化，请稍等.");
		dishCount = getIntent().getExtras().getInt("dishCount");
		masterid = getIntent().getExtras().getInt("masterid");
		ordertype = getIntent().getExtras().getInt("ordertype");
		orderfee = getIntent().getExtras().getInt("orderfee");
		
		dishCount_ = getIntent().getExtras().getInt("dishCount");
		id_ = getIntent().getExtras().getInt("masterid");
		dishids_ = getIntent().getExtras().getString("dishids");
		ordertype_ = getIntent().getExtras().getInt("ordertype");
		orderfee_ = getIntent().getExtras().getInt("orderfee");
		
		initView();

		new Thread(initOrderRunnable).start();
		if (userId != 0) {
			new Thread(addrRunnable).start();
			new Thread(couponRunnable).start();
		}
		scrlayout.setPageListener(new ScrollLayout.PageListener() {

			@Override
			public void page(int page) {
			}
		});

		// listView.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub
		// checkBox = (CheckBox) arg1.findViewById(R.id.isChecked);
		// textView = (TextView) arg1.findViewById(R.id.times);
		// if (checkBox.isChecked()) {
		// checkBox.setChecked(false);
		// textView.setText("x1");
		// dishCount = dishCount + 1;
		// new Thread(caculateRunnable).start();
		// } else {
		// checkBox.setChecked(true);
		// dishCount = dishCount - 1;
		// textView.setText("x0");
		// new Thread(caculateRunnable).start();
		// }
		// }
		// });

	}

	Runnable initOrderRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			initOrder = CommonFun.initOrder(masterid, dishids);
			if (initOrder != null) {
				myHandler.sendEmptyMessage(200);
			}
		}
	};

	Runnable addrRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			addressBean = CommonFun1.getAddressList(userId);
			if (addressBean != null) {
				myHandler.sendEmptyMessage(1);
			}
		}
	};

	Runnable couponRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			coupons = CommonFun1.getCouponList(userId);
			if (coupons != null) {
				myHandler.sendEmptyMessage(2);
			} else {
				myHandler.sendEmptyMessage(201);
			}
		}
	};

	private void initValues() {
		// TODO Auto-generated method stub
		lists = new ArrayList<String>();
		for (int i = 0; i < serviceTimes.size(); i++) {
			String s = serviceTimes.get(i).getServicedate().substring(5);
			lists.add(s);
		}
		PageCount = (int) Math.ceil(lists.size() / PAGE_SIZE);
		for (int i = 0; i < PageCount; i++) {
			gridView = new GridView(OrderActivity.this);
			gridView.setAdapter(new DateAdapter(OrderActivity.this, lists, i));
			gridView.setNumColumns(5);
			// 去掉点击时的黄色背景
			gridView.setSelector(R.drawable.bill_btn_calendar_no_selected);
			gridView.setOnItemClickListener(gridListener);
			scrlayout.addView(gridView);
		}
		times = serviceTimes.get(0).getTimes();
		initTimeValues();

		if (dishs != null) {
			dishAdapter = new Order_DishAdapter(getApplicationContext(), dishs);
			listView.setAdapter(dishAdapter);
			Utility.setListViewHeightBasedOnChildren(listView);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		TextView type = (TextView) findViewById(R.id.order_type);
		radioGroup = (RadioGroup) findViewById(R.id.ll_order);
		listView = (ListView) findViewById(R.id.list_orderdish);
		intro = (LinearLayout) findViewById(R.id.order_intro);
		introName = (TextView) findViewById(R.id.order_introName);
		packIntro = (TextView) findViewById(R.id.order_pack_intro);
		if (ordertype == 2) {// 套餐
			type.setText(" 套餐选择");
			initTaocanValues();
		} else {// 非套餐
			type.setText(" 订单菜品");
			intCaipinValue();
		}
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		toButton1 = (ToggleButton) findViewById(R.id.slipButton);
		toButton2 = (ToggleButton) findViewById(R.id.slipButton2);
		toButton1.setOnCheckedChangeListener(this);
		toButton2.setOnCheckedChangeListener(this);
		scrlayout = (ScrollLayout) findViewById(R.id.scrlayout);
		sure = (Button) findViewById(R.id.order_sure);
		sure.setOnClickListener(this);
		edit_remark = (EditText) findViewById(R.id.order_remark);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
		tv7.setOnClickListener(this);
		tv8.setOnClickListener(this);
		ll_addr = (LinearLayout) findViewById(R.id.ll_choice);
		ll_addr.setOnClickListener(this);
		bill_addr = (TextView) findViewById(R.id.bill_addr);
		bill_phone = (TextView) findViewById(R.id.bill_phone);
		coupon = (TextView) findViewById(R.id.bill_coupon);
		ll_coupon = (LinearLayout) findViewById(R.id.coupon_choice);
		ll_coupon.setOnClickListener(this);
	}

	private void initCouponValue() {
		couponValue = coupons.get(0).getValue();
		userCouponid = coupons.get(0).getUserCouponId();
		coupon.setText(coupons.get(0).getCouponName());
	}

	private void intCaipinValue() {
		// TODO Auto-generated method stub
		intro.setVisibility(View.VISIBLE);
		listView.setVisibility(View.VISIBLE);
		dishids = getIntent().getExtras().getString("dishids");
		switch (ChefDetailActivity.packs.size()) {
		case 1:
			pickid = ChefDetailActivity.packs.get(0).getId();
			introName.setText(ChefDetailActivity.packs.get(0).getDesc());
			break;
		case 3:
			if (dishCount > 0 && dishCount < 5) {
				pickid = ChefDetailActivity.packs.get(0).getId();
				introName.setText(ChefDetailActivity.packs.get(0).getDesc());
			} else if (dishCount > 4 && dishCount < 7) {
				pickid = ChefDetailActivity.packs.get(1).getId();
				introName.setText(ChefDetailActivity.packs.get(1).getDesc());
			} else {
				pickid = ChefDetailActivity.packs.get(2).getId();
				introName.setText(ChefDetailActivity.packs.get(2).getDesc());
			}
			break;
		default:
			break;
		}
	}

	void initTaocanValues() {
		radioGroup.setVisibility(View.VISIBLE);
		packIntro.setVisibility(View.VISIBLE);
		float density = getResources().getDisplayMetrics().density;
		if (ChefDetailActivity.packs != null) {
			for (int i = 0; i < ChefDetailActivity.packs.size(); i++) {
				final int j = i;
				RadioGroup.LayoutParams params_rb = new RadioGroup.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				int margin = (int) (8 * density);
				params_rb.setMargins(0, 0, margin, 0);
				RadioButton radioButton = new RadioButton(OrderActivity.this);
				radioButton.setClickable(true);
				radioButton.setPadding(10, 0, 10, 0);
				radioButton.setButtonDrawable(android.R.color.transparent);
				radioButton.setBackgroundResource(R.drawable.order_pack);
				radioButton.setCompoundDrawables(null, null, null, null);
				radioButton.setTextSize(18);
				radioButton.setGravity(Gravity.CENTER);
				radioButton.setTextColor(getResources().getColor(
						R.color.greyword));
				radioButton.setText(ChefDetailActivity.packs.get(i).getName());
				radioGroup.addView(radioButton, params_rb);
				if (ChefDetailActivity.grade != 1) {
					radioButton.performClick();
					radioButton.setTextColor(getResources().getColor(
							R.color.white));
					radioButton.setClickable(false);
					pickid = ChefDetailActivity.packs.get(0).getId();
					dishCount = ChefDetailActivity.packs.get(0).getTocount()
							+ custdishcount;
					packIntro
							.setText(ChefDetailActivity.packs.get(0).getDesc());
				} else {
					radioGroup.getChildAt(0).performClick();
					((RadioButton) radioGroup.getChildAt(0))
							.setTextColor(getResources()
									.getColor(R.color.white));
					pickid = ChefDetailActivity.packs.get(0).getId();
					dishCount = ChefDetailActivity.packs.get(0).getTocount()
							+ custdishcount;
					packIntro
							.setText(ChefDetailActivity.packs.get(0).getDesc());
				}
				radioButton
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(CompoundButton arg0,
									boolean arg1) {
								if (arg1) {
									custdishcount = 0;
									arg0.setTextColor(getResources().getColor(
											R.color.white));
									pickid = ChefDetailActivity.packs.get(j)
											.getId();
									dishCount = ChefDetailActivity.packs.get(j)
											.getTocount() + custdishcount;
									packIntro.setText(ChefDetailActivity.packs
											.get(j).getDesc());
									orderfee = ChefDetailActivity.packs.get(j)
											.getPrice();
									myHandler.sendEmptyMessage(4);
								} else {
									arg0.setTextColor(getResources().getColor(
											R.color.greyword));
								}
							}
						});
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.order_sure:
			remark = edit_remark.getText().toString().trim();
			if (!servicedate.equals("") && addrid != 0) {
				showDialog("下单中");
				new Thread(orderRunnable).start();
			} else {
				if (servicedate.equals("")) {
					Toast.makeText(getApplicationContext(), "请先选择预约时间哦",
							Toast.LENGTH_SHORT).show();
				} else {
					if (addrid == 0) {
						Toast.makeText(getApplicationContext(), "亲，请添加地址先",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
			break;
		case R.id.ll_choice:
			if (address != null) {
				Intent intent = new Intent(OrderActivity.this,
						AddAddressActivity.class);
				intent.putExtra("isChange", true);
				intent.putExtra("add", address.get(0).getAddress());
				intent.putExtra("province", address.get(0).getProvince());
				intent.putExtra("city", address.get(0).getCity());
				intent.putExtra("area", address.get(0).getArea());
				intent.putExtra("id", address.get(0).getId());
				intent.putExtra("name", address.get(0).getContact());
				startActivity(intent);
			} else {
				Intent intent = new Intent(OrderActivity.this,
						AddAddressActivity.class);
				intent.putExtra("isChange", false);
				startActivity(intent);
			}
			break;
		case R.id.tv1:
			if (gridID == -1) {
				Toast.makeText(getApplicationContext(), "亲，请先选择日期",
						Toast.LENGTH_SHORT).show();
			} else {
				if (times.get(0).getStatus() == 1) {
					initTimeValues2();
					tv1.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tv1.setTextColor(getResources().getColor(R.color.white));
					servicedate = serviceTimes.get(gridID).getServicedate()
							+ " " + times.get(0).getTime();
				} else {
					Toast.makeText(getApplicationContext(),
							"亲，厨师这个时间在忙哦，请选择其他时段", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.tv2:
			if (gridID == -1) {
				Toast.makeText(getApplicationContext(), "亲，请先选择日期",
						Toast.LENGTH_SHORT).show();
			} else {
				if (times.get(1).getStatus() == 1) {
					initTimeValues2();
					tv2.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tv2.setTextColor(getResources().getColor(R.color.white));
					servicedate = serviceTimes.get(gridID).getServicedate()
							+ " " + times.get(1).getTime();
				} else {
					Toast.makeText(getApplicationContext(),
							"亲，厨师这个时间在忙哦，请选择其他时段", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.tv3:
			if (gridID == -1) {
				Toast.makeText(getApplicationContext(), "亲，请先选择日期",
						Toast.LENGTH_SHORT).show();
			} else {
				if (times.get(2).getStatus() == 1) {
					initTimeValues2();
					tv3.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tv3.setTextColor(getResources().getColor(R.color.white));
					servicedate = serviceTimes.get(gridID).getServicedate()
							+ " " + times.get(2).getTime();
				} else {
					Toast.makeText(getApplicationContext(),
							"亲，厨师这个时间在忙哦，请选择其他时段", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.tv4:
			if (gridID == -1) {
				Toast.makeText(getApplicationContext(), "亲，请先选择日期",
						Toast.LENGTH_SHORT).show();
			} else {
				if (times.get(3).getStatus() == 1) {
					initTimeValues2();
					tv4.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tv4.setTextColor(getResources().getColor(R.color.white));
					servicedate = serviceTimes.get(gridID).getServicedate()
							+ " " + times.get(3).getTime();
				} else {
					Toast.makeText(getApplicationContext(),
							"亲，厨师这个时间在忙哦，请选择其他时段", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.tv5:
			if (gridID == -1) {
				Toast.makeText(getApplicationContext(), "亲，请先选择日期",
						Toast.LENGTH_SHORT).show();
			} else {
				if (times.get(4).getStatus() == 1) {
					initTimeValues2();
					tv5.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tv5.setTextColor(getResources().getColor(R.color.white));
					servicedate = serviceTimes.get(gridID).getServicedate()
							+ " " + times.get(4).getTime();
				} else {
					Toast.makeText(getApplicationContext(),
							"亲，厨师这个时间在忙哦，请选择其他时段", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.tv6:
			if (gridID == -1) {
				Toast.makeText(getApplicationContext(), "亲，请先选择日期",
						Toast.LENGTH_SHORT).show();
			} else {
				if (times.get(5).getStatus() == 1) {
					initTimeValues2();
					tv6.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tv6.setTextColor(getResources().getColor(R.color.white));
					servicedate = serviceTimes.get(gridID).getServicedate()
							+ " " + times.get(5).getTime();
				} else {
					Toast.makeText(getApplicationContext(),
							"亲，厨师这个时间在忙哦，请选择其他时段", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.tv7:
			if (gridID == -1) {
				Toast.makeText(getApplicationContext(), "亲，请先选择日期",
						Toast.LENGTH_SHORT).show();
			} else {
				if (times.get(6).getStatus() == 1) {
					initTimeValues2();
					tv7.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tv7.setTextColor(getResources().getColor(R.color.white));
					servicedate = serviceTimes.get(gridID).getServicedate()
							+ " " + times.get(6).getTime();
				} else {
					Toast.makeText(getApplicationContext(),
							"亲，厨师这个时间在忙哦，请选择其他时段", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.tv8:
			if (gridID == -1) {
				Toast.makeText(getApplicationContext(), "亲，请先选择日期",
						Toast.LENGTH_SHORT).show();
			} else {
				if (times.get(7).getStatus() == 1) {
					initTimeValues2();
					tv8.setBackgroundColor(getResources().getColor(
							R.color.orange));
					tv8.setTextColor(getResources().getColor(R.color.white));
					servicedate = serviceTimes.get(gridID).getServicedate()
							+ " " + times.get(7).getTime();
				} else {
					Toast.makeText(getApplicationContext(),
							"亲，厨师这个时间在忙哦，请选择其他时段", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.coupon_choice:
			Intent intent4 = new Intent(OrderActivity.this,
					CouponActivity.class);
			intent4.putExtra("isorder", true);
			startActivity(intent4);
			break;
		default:
			break;
		}
	}

	void changeBg(Context context, RadioButton radioButton) {
		servicedate = serviceTimes.get(gridID).getServicedate()
				+ radioButton.getText().toString();
		radioButton.setBackgroundResource(R.color.orange);
		radioButton.setTextColor(getResources().getColor(R.color.white));
	}

	// private Runnable  = new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// orderfee = CommonFun.getCalulate(ChefDetailActivity.grade, pickid,
	// dishCount, bringfood, bringcond);
	// if (orderfee != 0) {
	// myHandler.sendEmptyMessage(4);
	// }
	// }
	// };

	Runnable orderRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			result = CommonFun.Order(signature, masterid, userId, pickid,
					dishids, bringfood, bringcond, servicedate, addrid,
					orderfee, paytype, remark, userCouponid, couponValue);
			if (result.equals("")) {
				myHandler.sendEmptyMessage(202);
			} else {
				myHandler.sendEmptyMessage(0);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				try {
					JSONObject object = new JSONObject(result);
					System.out.println(result);
					if (object.getInt("errcode") == 0) {
						closeDialog();
						timestamp_ = System.currentTimeMillis()/1000;
//						Log.e("saveorder", dishCount + " " +masterid + " " +dishids + " " +ordertype+ " "+orderfee + " " + timestamp);
//						Log.e("预约1:", "dishCount: "+dishCount_ + ", id: " +id_ + ", dishids: " + dishids_ + ",ordertype: " +ordertype_ + ",orderfee: " + orderfee_);
						OrderRepeatJudge saveorder = new OrderRepeatJudge(dishCount_,id_,dishids_,ordertype_,orderfee_,timestamp_);
						PreferencesObj.saveObject(OrderActivity.this, "orderobj", saveorder);
						Toast.makeText(getApplicationContext(), "下单成功",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(OrderActivity.this,
								OrderDetailActivity.class);
						intent.putExtra(
								"orderId",
								object.getJSONObject("content").getString(
										"orderno"));
						startActivity(intent);
						OrderActivity.this.finish();
					} else {
						myHandler.sendEmptyMessage(202);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					closeDialog();
				}
				break;
			case 1:
				address = addressBean.getList();
				if (address != null) {
					bill_addr.setText(address.get(0).getProvince()
							+ address.get(0).getCity()
							+ address.get(0).getArea()
							+ address.get(0).getAddress());
					bill_phone.setText("联系电话：" + address.get(0).getMobile());
					addrid = address.get(0).getId();
				} else {
					bill_addr.setText("");
					bill_phone.setText("点击添加地址");
					addrid = 0;
				}
				break;
			case 2:
				initCouponValue();
				break;
			case 4:
				sure.setText("确定(" + dishCount + "个菜  共计：￥" + orderfee / 100
						+ "元)");
				break;
			case 200:
				closeDialog();
				signature = initOrder.getSignature();
				serviceTimes = initOrder.getServiceTimes();
				dishs = initOrder.getDishs();
				initValues();
				myHandler.sendEmptyMessage(4);
				break;
			case 201:
				coupon.setText("没有可用的优惠券");
				break;
			case 400:
				startActivity(new Intent(OrderActivity.this, RegActivity.class));
				break;
			case 202:
				closeDialog();
				Toast.makeText(getApplicationContext(), "服务器出错",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.slipButton:
			if (arg1) {
				toButton1.setBackgroundResource(R.drawable.bill_btn_switch_on);
				bringfood = 1;
				// new Thread(caculateRunnable).start();
			} else {
				toButton1.setBackgroundResource(R.drawable.bill_btn_switch_off);
				bringfood = 0;
				// new Thread(caculateRunnable).start();
			}
			break;
		case R.id.slipButton2:
			if (arg1) {
				toButton2.setBackgroundResource(R.drawable.bill_btn_switch_on);
				bringcond = 1;
				// new Thread(caculateRunnable).start();
			} else {
				toButton2.setBackgroundResource(R.drawable.bill_btn_switch_off);
				bringcond = 0;
				// new Thread(caculateRunnable).start();
			}
			break;
		default:
			break;
		}
	}

	public OnItemClickListener gridListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			PageCurrent = scrlayout.getCurScreen();
			gridID = arg2 + PageCurrent * 5;

			if (((GridView) arg0).getTag() != null) {
				((View) ((GridView) arg0).getTag())
						.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
			}
			((GridView) arg0).setTag(arg1);
			arg1.setBackgroundResource(R.drawable.bill_btn_calendar_selected);
			times = serviceTimes.get(gridID).getTimes();
			initTimeValues();
		}
	};

	private void initTimeValues() {
		boolean b = false;
		if (times.get(0).getStatus() == 0) {
			tv1.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			b = true;
			tv1.setBackgroundColor(getResources().getColor(R.color.orange));
			tv1.setTextColor(getResources().getColor(R.color.white));
			servicedate = serviceTimes.get(gridID).getServicedate() + " "
					+ times.get(0).getTime();
		}
		if (times.get(1).getStatus() == 0) {
			tv2.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			if (b) {
				tv2.setBackgroundResource(R.drawable.order_shape);
			} else {
				b = true;
				tv2.setBackgroundColor(getResources().getColor(R.color.orange));
				tv2.setTextColor(getResources().getColor(R.color.white));
				servicedate = serviceTimes.get(gridID).getServicedate() + " "
						+ times.get(1).getTime();
			}
		}
		if (times.get(2).getStatus() == 0) {
			tv3.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			if (b) {
				tv3.setBackgroundResource(R.drawable.order_shape);
			} else {
				b = true;
				tv3.setBackgroundColor(getResources().getColor(R.color.orange));
				tv3.setTextColor(getResources().getColor(R.color.white));
				servicedate = serviceTimes.get(gridID).getServicedate() + " "
						+ times.get(2).getTime();
			}
		}
		if (times.get(3).getStatus() == 0) {
			tv4.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			if (b) {
				tv4.setBackgroundResource(R.drawable.order_shape);
			} else {
				b = true;
				tv4.setBackgroundColor(getResources().getColor(R.color.orange));
				tv4.setTextColor(getResources().getColor(R.color.white));
				servicedate = serviceTimes.get(gridID).getServicedate() + " "
						+ times.get(3).getTime();
			}
		}
		if (times.get(4).getStatus() == 0) {
			tv5.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			if (b) {
				tv5.setBackgroundResource(R.drawable.order_shape);
			} else {
				b = true;
				tv5.setBackgroundColor(getResources().getColor(R.color.orange));
				tv5.setTextColor(getResources().getColor(R.color.white));
				servicedate = serviceTimes.get(gridID).getServicedate() + " "
						+ times.get(4).getTime();
			}
		}
		if (times.get(5).getStatus() == 0) {
			tv6.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			if (b) {
				tv6.setBackgroundResource(R.drawable.order_shape);
			} else {
				b = true;
				tv6.setBackgroundColor(getResources().getColor(R.color.orange));
				tv6.setTextColor(getResources().getColor(R.color.white));
				servicedate = serviceTimes.get(gridID).getServicedate() + " "
						+ times.get(5).getTime();
			}
		}
		if (times.get(6).getStatus() == 0) {
			tv7.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			if (b) {
				tv7.setBackgroundResource(R.drawable.order_shape);
			} else {
				b = true;
				tv7.setBackgroundColor(getResources().getColor(R.color.orange));
				tv7.setTextColor(getResources().getColor(R.color.white));
				servicedate = serviceTimes.get(gridID).getServicedate() + " "
						+ times.get(6).getTime();
			}
		}
		if (times.get(7).getStatus() == 0) {
			tv8.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			if (b) {
				tv8.setBackgroundResource(R.drawable.order_shape);
			} else {
				b = true;
				tv8.setBackgroundColor(getResources().getColor(R.color.orange));
				tv8.setTextColor(getResources().getColor(R.color.white));
				servicedate = serviceTimes.get(gridID).getServicedate() + " "
						+ times.get(7).getTime();
			}
		}
		tv1.setText(times.get(0).getTime() + "点");
		tv1.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv2.setText(times.get(1).getTime() + "点");
		tv2.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv3.setText(times.get(2).getTime() + "点");
		tv3.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv4.setText(times.get(3).getTime() + "点");
		tv4.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv5.setText(times.get(4).getTime() + "点");
		tv5.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv6.setText(times.get(5).getTime() + "点");
		tv6.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv7.setText(times.get(6).getTime() + "点");
		tv7.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv8.setText(times.get(7).getTime() + "点");
		tv8.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
	}

	private void initTimeValues2() {
		if (times.get(0).getStatus() == 0) {
			tv1.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			tv1.setBackgroundResource(R.drawable.order_shape);
		}
		if (times.get(1).getStatus() == 0) {
			tv2.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			tv2.setBackgroundResource(R.drawable.order_shape);
		}
		if (times.get(2).getStatus() == 0) {
			tv3.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			tv3.setBackgroundResource(R.drawable.order_shape);
		}
		if (times.get(3).getStatus() == 0) {
			tv4.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			tv4.setBackgroundResource(R.drawable.order_shape);
		}
		if (times.get(4).getStatus() == 0) {
			tv5.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			tv5.setBackgroundResource(R.drawable.order_shape);
		}
		if (times.get(5).getStatus() == 0) {
			tv6.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			tv6.setBackgroundResource(R.drawable.order_shape);
		}
		if (times.get(6).getStatus() == 0) {
			tv7.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			tv7.setBackgroundResource(R.drawable.order_shape);
		}
		if (times.get(7).getStatus() == 0) {
			tv8.setBackgroundResource(R.drawable.bill_btn_calendar_no_selected);
		} else {
			tv8.setBackgroundResource(R.drawable.order_shape);
		}
		tv1.setText(times.get(0).getTime() + "点");
		tv1.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv2.setText(times.get(1).getTime() + "点");
		tv2.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv3.setText(times.get(2).getTime() + "点");
		tv3.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv4.setText(times.get(3).getTime() + "点");
		tv4.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv5.setText(times.get(4).getTime() + "点");
		tv5.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv6.setText(times.get(5).getTime() + "点");
		tv6.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv7.setText(times.get(6).getTime() + "点");
		tv7.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
		tv8.setText(times.get(7).getTime() + "点");
		tv8.setTextColor(OrderActivity.this.getResources().getColor(
				R.color.blackword));
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Thread(addrRunnable).start();
		if (CouponActivity.i != 0) {
			int j = CouponActivity.i;
			couponValue = coupons.get(j).getValue();
			userCouponid = coupons.get(j).getUserCouponId();
			coupon.setText(coupons.get(j).getCouponName());
		}
	}
}
