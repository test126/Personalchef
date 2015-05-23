package com.goodfriends.personalchef;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfriends.personalchef.adapter.EvalAdapter;
import com.goodfriends.personalchef.adapter.XuanAdapter;
import com.goodfriends.personalchef.adapter.XuanAdapter.viewHolder;
import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.bean.Eval;
import com.goodfriends.personalchef.bean.EvalBean;
import com.goodfriends.personalchef.bean.OrderRepeatJudge;
import com.goodfriends.personalchef.bean.Pack;
import com.goodfriends.personalchef.bean.ShareInfo;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.common.PreferencesObj;
import com.goodfriends.personalchef.common.UIHelper;
import com.goodfriends.personalchef.util.ImageCallback;
import com.goodfriends.personalchef.util.LoadImage;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshListView;
import com.goodfriends.personalchef.view.PullToRefreshListView.PullToRefreshBase.OnRefreshListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ChefDetailActivity extends Activity implements OnClickListener {

	private int id, userid;// 厨师,用户id
	public static int grade, orderFee;// 菜品数量
	private int dishCount = 0;
	private ImageView back, head, certification, gold, star, collect;
	private Chefs chef;
	private TextView name, age, jiguan,distance ,jieshao, xuancai, pingjia, times, desc;
	private List<Dish> lists;
	public static List<Pack> packs;
	private GridView gridView;
	private XuanAdapter adapter;
	private ProgressDialog progressDialog;
	private ImageView grade1, grade3, grade2, grade4, grade5;
	private Button yijian;
	private static Button yuyue;
	private PullToRefreshListView listView;
	private String dishids = "";
	private ShareInfo shareInfo;
	private StringBuffer sb = new StringBuffer();
	private EvalBean evalBean;
	private List<Eval> evals = null;
	private EvalAdapter evalAdapter;
	private ListView mListView;
	private boolean b = false;
	private int orderfee;
	private int chefEvalPage = 1;
	private int EvalPageCount = 10;
	private Boolean isPullUp = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_chefdetail);
		id = getIntent().getExtras().getInt("chefId");
		userid = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
		initView();
		new Thread(detailRunnable).start();
		new Thread(chefdishsRunnable).start();
		if (userid != 0) {
			new Thread(isCollectRunnable).start();
		}

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (userid != 0) {
					chefEvalPage=1;
					isPullUp = true;
					new Thread(chefpingRunnable).start();
				}
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				listView.onPullDownRefreshComplete();
				listView.onPullUpRefreshComplete();
				chefEvalPage++;
				isPullUp = false;
				setLastUpdateTime();
				new Thread(chefpingRunnable).start();
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				viewHolder vHollder = (viewHolder) arg1.getTag();
				
				// TODO Auto-generated method stub
//				final TextView choice = (TextView) arg1.findViewById(R.id.xuancai_choice);
				if (!vHollder.cBox.isChecked()) {
					if (ChefDetailActivity.grade == 1) {
						if (dishCount < 8) {
							/*
							choice.setText(getResources().getString(
									R.string.choice1));
							choice.setBackgroundResource(R.drawable.catelog_btn_02_press);
							choice.setTextColor(getResources().getColor(
									R.color.white));
							choice.setGravity(Gravity.RIGHT
									| Gravity.CENTER_VERTICAL);
							choice.setTag(true);
							*/
							vHollder.cBox.toggle();
							XuanAdapter.isSelected.put(position,vHollder.cBox.isChecked());
							dishCount = dishCount + 1;
							// new Thread(caculateRunnable).start();
							if (dishids.equals("")) {
								sb.append(lists.get(position).getDishid());
							} else if (dishids.indexOf(Integer.toString(lists
									.get(position).getDishid())) >= 0) {
							} else {
								sb.append("," + lists.get(position).getDishid());
							}
							dishids = sb.toString();
						} else {
							Toast.makeText(ChefDetailActivity.this,
									"亲，厨师一次做那么多菜会很累哦！", Toast.LENGTH_SHORT)
									.show();
						}
					} else {
						if (dishCount < 12) {
							/*
							choice.setText(getResources().getString(
									R.string.choice1));
							choice.setBackgroundResource(R.drawable.catelog_btn_02_press);
							choice.setTextColor(getResources().getColor(
									R.color.white));
							choice.setGravity(Gravity.RIGHT
									| Gravity.CENTER_VERTICAL);
							*/
							vHollder.cBox.toggle();
							XuanAdapter.isSelected.put(position,vHollder.cBox.isChecked());
//							choice.setTag(true);
							dishCount = dishCount + 1;
							// new Thread(caculateRunnable).start();
							if (dishids.equals("")) {
								sb.append(lists.get(position).getDishid());
							} else if (dishids.indexOf(Integer.toString(lists
									.get(position).getDishid())) >= 0) {
							} else {
								sb.append("," + lists.get(position).getDishid());
							}
							dishids = sb.toString();
						} else {
							Toast.makeText(ChefDetailActivity.this,
									"亲，厨师一次做那么多菜会很累哦！", Toast.LENGTH_SHORT)
									.show();
						}
					}
				} else {
					/*
					choice.setText(getResources().getString(R.string.choice));
					choice.setBackgroundResource(R.drawable.catelog_btn_02_normal);
					choice.setTextColor(getResources().getColor(R.color.orange));
					choice.setGravity(Gravity.CENTER);
					*/
					vHollder.cBox.toggle();
					XuanAdapter.isSelected.put(position,vHollder.cBox.isChecked());
//					choice.setTag(false);
					dishCount = dishCount - 1;
					// new Thread(caculateRunnable).start();
					if (dishids.indexOf(","
							+ Integer.toString(lists.get(position).getDishid())) >= 0) {
						dishids = dishids.replace(
								","
										+ Integer.toString(lists.get(position)
												.getDishid()), "");
					} else if (dishids.indexOf(Integer.toString(lists.get(position)
							.getDishid()) + ",") >= 0) {
						dishids = dishids.replace(
								Integer.toString(lists.get(position).getDishid())
										+ ",", "");
					} else {
						dishids = "";
					}
					sb = new StringBuffer(dishids);
				}
				myHandler1.sendEmptyMessage(4);
			}
			
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		head = (ImageView) findViewById(R.id.chefdetail_head);
		certification = (ImageView) findViewById(R.id.chefdetail_certification);
		gold = (ImageView) findViewById(R.id.chefdetail_gold);
		star = (ImageView) findViewById(R.id.chefdetail_star);
		grade1 = (ImageView) findViewById(R.id.grade1);
		grade2 = (ImageView) findViewById(R.id.grade2);
		grade3 = (ImageView) findViewById(R.id.grade3);
		grade4 = (ImageView) findViewById(R.id.grade4);
		grade5 = (ImageView) findViewById(R.id.grade5);
		name = (TextView) findViewById(R.id.chefdetail_name);
		age = (TextView) findViewById(R.id.chefdetail_age);
		distance = (TextView) findViewById(R.id.chefdetail_distance);
		jiguan = (TextView) findViewById(R.id.chefdetail_jiguan);
		times = (TextView) findViewById(R.id.chefdetail_times);
		jieshao = (TextView) findViewById(R.id.chefdetail_jieshao);
		pingjia = (TextView) findViewById(R.id.chefdetail_pingjia);
		xuancai = (TextView) findViewById(R.id.chefdetail_xuancai);
		gridView = (GridView) findViewById(R.id.xuancai_gridview);
		collect = (ImageView) findViewById(R.id.title_collect);
		yijian = (Button) findViewById(R.id.chefdetail_yijian);
		yuyue = (Button) findViewById(R.id.chefdetail_yuyue);
		yuyue.setOnClickListener(this);
		yijian.setOnClickListener(this);
		collect.setOnClickListener(this);
		back.setOnClickListener(this);
		pingjia.setOnClickListener(this);
		xuancai.setOnClickListener(this);
		listView = (PullToRefreshListView) findViewById(R.id.pinglun);
		listView.setPullLoadEnabled(false);
		listView.setScrollLoadEnabled(true);
		mListView = listView.getRefreshableView();
		mListView.setDivider(getResources().getDrawable(R.color.line_divide));
		mListView.setDividerHeight(1);
		desc = (TextView) findViewById(R.id.xuancai_desc);
//		share = (ImageView) findViewById(R.id.title_share);
//		share.setOnClickListener(this);
	}
	
	public String position;
	Runnable detailRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			position = LoadingActivity.loca.getAddr();
			chef = CommonFun.getChefDetail(ChefDetailActivity.this, id,position);
			if (chef != null) {
				myHandler.sendEmptyMessage(0);
			}
		}
	};

	Runnable chefdishsRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			lists = CommonFun1.getDishListFromchef(id, 10);
			if (lists != null) {
				myHandler.sendEmptyMessage(1);
			}
		}
	};

	Runnable chefpingRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			evalBean = CommonFun1.getEvalList(id, chefEvalPage, EvalPageCount);
			if (evalBean != null) {
				if(isPullUp)
				{
					evalHandler.sendEmptyMessage(evalBean.getErrcode());
				}else {
					evalHandler.sendEmptyMessage(1);
				}
				
			} else {
				evalHandler.sendEmptyMessage(501);
			}
		}
	};

	Runnable collectRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			b = CommonFun.collectChef(userid, id);
			if (b) {
				myHandler.sendEmptyMessage(2);
			} else {
				myHandler.sendEmptyMessage(500);
			}
		}
	};

	Runnable delCollectRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = CommonFun1.delCollectChef(userid, id);
			if (b) {
				myHandler.sendEmptyMessage(5);
			} else {
				myHandler.sendEmptyMessage(502);
			}
		}
	};

	Runnable isCollectRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			b = CommonFun1.chefIsCollect(userid, id);
			if (b) {
				myHandler.sendEmptyMessage(4);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler evalHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				evals = evalBean.getEvals();
				evalAdapter = new EvalAdapter(getApplicationContext(), evals);
				mListView.setAdapter(evalAdapter);
				listView.onPullDownRefreshComplete();
				listView.onPullUpRefreshComplete();
				setLastUpdateTime();
				closeDialog();
				break;
			case 1:
				evals.addAll(evalBean.getEvals());
				evalAdapter.notifyDataSetChanged();
				break;
			case 500:
				closeDialog();
				Toast.makeText(getApplicationContext(), evalBean.getErrmsg(),
						Toast.LENGTH_SHORT).show();
				break;
			case 501:
//				closeDialog();
				Toast.makeText(getApplicationContext(), "暂无评论。",
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
				desc.setText(chef.getPacks().get(0).getDesc());
				name.setText(chef.getName());
				age.setText("厨龄：" + chef.getAge() + "年");
				if(chef.getDistance() == -1)
				{
					distance.setText("距离：计算中");
				}else {
					distance.setText("距离：" + UIHelper.friendly_distance(chef.getDistance()));
				}
				jiguan.setText("籍贯：" + chef.getNativeplace());
				jieshao.setText(chef.getIntro());
				times.setText(chef.getServicecount() + "");
				pingjia.setText("用户评价(" + chef.getEvalcount() + ")条");
				grade = chef.getGrade();
				switch (grade) {
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
				switch (chef.getScore()) {
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
				if (chef.getPacks() != null) {
					packs = chef.getPacks();
				}
				if (chef.getIsauth() == 1) {
					certification.setVisibility(View.VISIBLE);
				}
				if (chef.getHeadpicurl() != null) {
					setHeadImage(chef.getHeadpicurl());
				}
				break;

			case 1:
				adapter = new XuanAdapter(getApplicationContext(), lists);
				gridView.setAdapter(adapter);
				break;
			case 2:
				closeDialog();
				collect.setBackgroundResource(R.drawable.catelog_btn_collected);
				b = true;
				Toast.makeText(getApplicationContext(), "收藏成功",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				collect.setBackgroundResource(R.drawable.catelog_btn_collected);
				break;
			case 500:
				closeDialog();
				Toast.makeText(getApplicationContext(), "您已经收藏过该厨师",
						Toast.LENGTH_SHORT).show();
				break;
			case 501:
				closeDialog();
				Toast.makeText(getApplicationContext(), "暂无评论。",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				closeDialog();
				break;
			case 5:
				closeDialog();
				collect.setBackgroundResource(R.drawable.catelog_btn_collect);
				b = false;
				Toast.makeText(getApplicationContext(), "取消收藏成功。",
						Toast.LENGTH_SHORT).show();
				break;
			case 502:
				closeDialog();
				Toast.makeText(getApplicationContext(), "取消收藏失败。",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};

	// public Runnable caculateRunnable = new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// orderFee = CommonFun.getCalulate(grade, 0, dishCount, 0, 0);
	// if (orderFee != 0) {
	// myHandler1.sendEmptyMessage(4);
	// }
	// }
	// };

	@SuppressLint("HandlerLeak")
	private Handler myHandler1 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				final UMSocialService mController = UMServiceFactory
						.getUMSocialService("com.umeng.share");
				// 设置分享内容
				String share_content = shareInfo.getContent();
				String url = shareInfo.getDownloadurl();
				mController.setShareContent(share_content);
				// 设置分享图片, 参数2为图片的url地址
				// mController.setShareMedia(new UMImage(getActivity(),
				// "http://www.baidu.com/img/bdlogo.png"));
				mController.getConfig().setPlatformOrder(SHARE_MEDIA.SINA,
						SHARE_MEDIA.QQ, SHARE_MEDIA.SMS);
				mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT,
						SHARE_MEDIA.QZONE);
				UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(
						ChefDetailActivity.this, "1104138513",
						"nIaMyw53eCeHCBRg");
				qqSsoHandler.addToSocialSDK();
				QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
						ChefDetailActivity.this, "1104138513",
						"nIaMyw53eCeHCBRg");
				qZoneSsoHandler.addToSocialSDK();
				QQShareContent qqShareContent = new QQShareContent();
				// 设置分享文字
				qqShareContent.setShareContent(share_content);
				// 设置分享title
				qqShareContent.setTitle("私厨汇--名厨汇聚，美食到家");
				// 设置分享图片
				qqShareContent.setShareImage(new UMImage(
						ChefDetailActivity.this, R.drawable.logo_128));
				qqShareContent.setTargetUrl(url);
				String appId = "wx0d4c3545413845cc";
				String appSecret = "ea67e785d875780f10aa0ad9bfc41c68";
				// 添加微信平台
				UMWXHandler wxHandler = new UMWXHandler(
						getApplicationContext(), appId, appSecret);
				wxHandler.addToSocialSDK();
				// 支持微信朋友圈
				UMWXHandler wxCircleHandler = new UMWXHandler(
						getApplicationContext(), appId, appSecret);
				wxCircleHandler.setToCircle(true);
				wxCircleHandler.addToSocialSDK();
				// 设置微信好友分享内容
				WeiXinShareContent weixinContent = new WeiXinShareContent();
				weixinContent.setShareContent(share_content);
				weixinContent.setTitle("私厨汇--名厨汇聚，美食到家");
				weixinContent.setTargetUrl(url);
				weixinContent.setShareImage(new UMImage(
						getApplicationContext(), R.drawable.logo_128));
				mController.setShareMedia(weixinContent);
				// 设置微信朋友圈分享内容
				CircleShareContent circleMedia = new CircleShareContent();
				circleMedia.setShareContent(share_content);
				circleMedia.setTitle("私厨汇--名厨汇聚，美食到家");
				circleMedia.setShareImage(new UMImage(getApplicationContext(),
						R.drawable.logo_128));
				circleMedia.setTargetUrl(url);
				mController.setShareMedia(circleMedia);
				mController.openShare(ChefDetailActivity.this, false);
				break;
			case 404:
				Toast.makeText(getApplicationContext(), "获取分享内容失败",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				if (ChefDetailActivity.grade == 1) {
					if (dishCount == 0) {
						yuyue.setText("马上预约");
						orderfee = packs.get(0).getPrice();
					} else if (dishCount < 5) {
						orderfee = packs.get(0).getPrice();
						yuyue.setText("预约(" + dishCount + "个菜  合计：￥" + orderfee
								/ 100 + ")");
					} else if (dishCount > 4 && dishCount < 7) {
						orderfee = packs.get(1).getPrice();
						yuyue.setText("预约(" + dishCount + "个菜  合计：￥" + orderfee
								/ 100 + ")");
					} else {
						orderfee = packs.get(2).getPrice();
						yuyue.setText("预约(" + dishCount + "个菜  合计：￥" + orderfee
								/ 100 + ")");
					}
				} else {
					if (dishCount == 0) {
						orderfee = packs.get(0).getPrice();
						yuyue.setText("马上预约");
					} else {
						orderfee = packs.get(0).getPrice();
						yuyue.setText("预约(" + dishCount + "个菜  合计：￥" + orderfee
								/ 100 + ")");
					}
				}
				break;
			default:
				break;
			}
		};
	};

	private void setHeadImage(String url) {
		// TODO Auto-generated method stub
		LoadImage loadImage = new LoadImage(getResources());
		loadImage.loadImage(url, new ImageCallback() {
			public void imageLoaded(Bitmap bitmap) {
				// TODO Auto-generated method stub
				head.setImageBitmap(bitmap);
			}
		});
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.chefdetail_xuancai:
			xuancai.setBackgroundResource(R.drawable.catelog_tab_selected_normal);
			xuancai.setTextColor(getResources().getColor(R.color.white));
			pingjia.setBackgroundResource(R.drawable.chefdetail_shape);
			pingjia.setTextColor(getResources().getColor(R.color.orange));
			listView.setVisibility(View.GONE);
			break;
		case R.id.chefdetail_pingjia:
			pingjia.setBackgroundResource(R.drawable.catelog_tab_selected_normal);
			pingjia.setTextColor(getResources().getColor(R.color.white));
			xuancai.setBackgroundResource(R.drawable.chefdetail_shape);
			xuancai.setTextColor(getResources().getColor(R.color.orange));
			listView.setVisibility(View.VISIBLE);
//			showDialog("获取数据中...");
			setLastUpdateTime();
			chefEvalPage = 1;
			listView.doPullRefreshing(true, 500);
			new Thread(chefpingRunnable).start();
			break;
		case R.id.title_collect:
			if (userid != 0) {
				if (b) {
					showDialog("取消收藏中...");
					new Thread(delCollectRunnable).start();
				} else {
					showDialog("收藏中...");
					new Thread(collectRunnable).start();
				}
			} else {
				Intent intent = new Intent(getApplicationContext(),
						RegActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.chefdetail_yijian:
			if (userid != 0) {
				Intent intent = new Intent(getApplicationContext(),
						OrderActivity.class);
				intent.putExtra("dishCount", dishCount);
				intent.putExtra("masterid", id);
				intent.putExtra("dishids", dishids);
				intent.putExtra("ordertype", 2);
				intent.putExtra("orderfee", packs.get(0).getPrice());
				startActivity(intent);
			} else {
				Intent intent = new Intent(getApplicationContext(),
						RegActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.chefdetail_yuyue:
			if (userid != 0) {
				if (dishCount == 0) {
					long timestamp = System.currentTimeMillis()/1000;
					OrderRepeatJudge orderobj = new OrderRepeatJudge(dishCount, id, dishids, 2, packs.get(0).getPrice(), timestamp);
					OrderRepeatJudge orderobj2 = (OrderRepeatJudge) PreferencesObj.readObject(ChefDetailActivity.this, "orderobj");
					if(orderobj2 == null)
					{
						Toast.makeText(ChefDetailActivity.this, "尽情的下单吧", Toast.LENGTH_LONG).show();
					}else {
						if(orderobj.Commpare(orderobj2))
						{
							Toast.makeText(ChefDetailActivity.this, "你已经下过这份单了", Toast.LENGTH_LONG).show();
							return;
						}
					}
					Intent intent2 = new Intent(getApplicationContext(),
							OrderActivity.class);
//					Log.e("预约:", "dishCount: "+dishCount + ", id: " +id + ", dishids: " + dishids + ",ordertype: " +"2" + ",orderfee: " + orderfee );
					intent2.putExtra("dishCount", dishCount);
					intent2.putExtra("masterid", id);
					intent2.putExtra("dishids", dishids);
					intent2.putExtra("ordertype", 2);
					intent2.putExtra("orderfee", packs.get(0).getPrice());
					startActivity(intent2);
				} else {
					long timestamp = System.currentTimeMillis()/1000;
					OrderRepeatJudge orderobj = new OrderRepeatJudge(dishCount, id, dishids, 1, orderfee, timestamp);
					OrderRepeatJudge orderobj2 = (OrderRepeatJudge) PreferencesObj.readObject(ChefDetailActivity.this, "orderobj");
					if(orderobj2 == null)
					{
						Toast.makeText(ChefDetailActivity.this, "尽情的下单吧", Toast.LENGTH_LONG).show();
					}else {
						if(orderobj.Commpare(orderobj2))
						{
							Toast.makeText(ChefDetailActivity.this, "你已经下过这份单了", Toast.LENGTH_LONG).show();
							return;
						}
					}
					Intent intent2 = new Intent(getApplicationContext(),
							OrderActivity.class);
//					Log.e("预约直接预订厨师:", "dishCount: "+dishCount + ", id: " +id + ", dishids: " + dishids + ",ordertype: " +"1" + ",orderfee: " + orderfee );
					intent2.putExtra("dishCount", dishCount);
					intent2.putExtra("masterid", id);
					intent2.putExtra("dishids", dishids);
					intent2.putExtra("ordertype", 1);
					intent2.putExtra("orderfee", orderfee);
					startActivity(intent2);
				}
			} else {
				Intent intent = new Intent(getApplicationContext(),
						RegActivity.class);
				startActivity(intent);
			}
			break;
		/*
		case R.id.title_share:
			new Thread(shareRunnable).start();
			break;
		*/
		default:
			break;
		}
	}

	Runnable shareRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			shareInfo = CommonFun1.getShareInfo();
			if (!shareInfo.equals("")) {
				myHandler1.sendEmptyMessage(1);
			} else {
				myHandler1.sendEmptyMessage(404);
			}
		}
	};

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
		userid = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		listView.setLastUpdatedLabel(text);
	}

	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}
		return mDateFormat.format(new Date(time));
	}
}
