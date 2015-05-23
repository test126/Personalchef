package com.goodfriends.personalchef;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfriends.personalchef.application.SysApplication;
import com.goodfriends.personalchef.bean.Chefs;
import com.goodfriends.personalchef.bean.Dish;
import com.goodfriends.personalchef.bean.ShareInfo;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.common.CommonUrl;
import com.goodfriends.personalchef.util.AsynImageLoader;
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

public class DishDetailActivity extends Activity implements OnClickListener {

	private ImageView back, dish_pic, collect;// 返回按钮

	private int dishId, userId;// 菜品id

	private Dish dish;

	private TextView dish_name, dish_num, dish_desc;

	private Button yuyue;
	private ImageView chef1, chef2, chef3, chef4, chef5, chef6;
	private LinearLayout ll1, ll2, ll3, ll4, ll5, ll6, cLayout;

	private List<Chefs> chefs;
	private Chefs chef = null;
	private TextView chefname, cheffuwu, chefpingjia, chefjiguan, chefdesc;
	private ImageView grade1, grade2, grade3, grade4, grade5;

	private ImageView more, share;

	private ShareInfo shareInfo;
	private boolean b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_dishdetail);
		dishId = getIntent().getExtras().getInt("dishId");
		initView();
		userId = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
		if (userId != 0) {
			new Thread(isCollectRunnable).start();
		}
		new Thread(dishRunnable).start();
	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (ImageView) findViewById(R.id.title_back);
		back.setOnClickListener(this);
		dish_pic = (ImageView) findViewById(R.id.dish_pic);
		collect = (ImageView) findViewById(R.id.title_collect);
		collect.setOnClickListener(this);
		dish_num = (TextView) findViewById(R.id.dish_num);
		dish_name = (TextView) findViewById(R.id.dish_name);
		dish_desc = (TextView) findViewById(R.id.dish_desc);
		yuyue = (Button) findViewById(R.id.dish_yuyue);
		yuyue.setOnClickListener(this);
		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		ll3 = (LinearLayout) findViewById(R.id.ll3);
		ll4 = (LinearLayout) findViewById(R.id.ll4);
		ll5 = (LinearLayout) findViewById(R.id.ll5);
		ll6 = (LinearLayout) findViewById(R.id.ll6);
		chef1 = (ImageView) findViewById(R.id.iv1);
		chef2 = (ImageView) findViewById(R.id.iv2);
		chef3 = (ImageView) findViewById(R.id.iv3);
		chef4 = (ImageView) findViewById(R.id.iv4);
		chef5 = (ImageView) findViewById(R.id.iv5);
		chef6 = (ImageView) findViewById(R.id.iv6);
		chefname = (TextView) findViewById(R.id.chefname);
		cheffuwu = (TextView) findViewById(R.id.chef_fuwu);
		chefpingjia = (TextView) findViewById(R.id.chef_pingjia);
		chefjiguan = (TextView) findViewById(R.id.chef_jiguan);
		chefdesc = (TextView) findViewById(R.id.chef_desc);
		grade1 = (ImageView) findViewById(R.id.grade1);
		grade2 = (ImageView) findViewById(R.id.grade2);
		grade3 = (ImageView) findViewById(R.id.grade3);
		grade4 = (ImageView) findViewById(R.id.grade4);
		grade5 = (ImageView) findViewById(R.id.grade5);
		more = (ImageView) findViewById(R.id.moreChef);
		more.setOnClickListener(this);
		chef1.setOnClickListener(this);
		chef2.setOnClickListener(this);
		chef3.setOnClickListener(this);
		chef4.setOnClickListener(this);
		chef5.setOnClickListener(this);
		chef6.setOnClickListener(this);
		share = (ImageView) findViewById(R.id.title_share);
		share.setOnClickListener(this);
	}

	Runnable dishRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			dish = CommonFun.getDishDeatail(dishId);
			if (dish != null) {
				myHandler.sendEmptyMessage(0);
			} else {
				myHandler.sendEmptyMessage(500);
			}
		}
	};

	Runnable collectRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = CommonFun.collectDish(userId, dishId);
			if (b) {
				myHandler.sendEmptyMessage(2);
			} else {
				myHandler.sendEmptyMessage(500);
			}
		}
	};

	Runnable isCollectRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			b = CommonFun1.dishIsCollect(userId, dishId);
			if (b) {
				myHandler.sendEmptyMessage(4);
			}
		}
	};

	Runnable delCollectRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			boolean b = CommonFun1.delCollectDish(userId, dishId);
			if (b) {
				myHandler.sendEmptyMessage(6);
			} else {
				myHandler.sendEmptyMessage(502);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				dish_name.setText(dish.getName());
				dish_num.setText(dish.getordercount() + "");
				yuyue.setText("选 " + "" + " 预约");
				dish_desc.setText(dish.getDesc());
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(dish_pic, dish.getBigimgurl(),
						R.drawable.nopic);
				if (dish.getLists() != null) {
					chefs = dish.getLists();
					setChefImage(chefs);
				}
				break;
			case 1:
				setChefInfo();
				break;
			case 2:
				closeDialog();
				collect.setImageResource(R.drawable.catelog_btn_collected);
				b = true;
				Toast.makeText(getApplicationContext(), "收藏成功",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				collect.setBackgroundResource(R.drawable.catelog_btn_collected);
				break;
			case 5:
				final UMSocialService mController = UMServiceFactory
						.getUMSocialService("com.umeng.share");
				String share_content = shareInfo.getContent();
				String url = shareInfo.getDownloadurl();
				// 设置分享内容
				mController.setShareContent(share_content);
				// 设置分享图片, 参数2为图片的url地址
				// mController.setShareMedia(new UMImage(getActivity(),
				// "http://www.baidu.com/img/bdlogo.png"));
				mController.getConfig().setPlatformOrder(SHARE_MEDIA.SINA,
						SHARE_MEDIA.QQ, SHARE_MEDIA.SMS);
				mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT,
						SHARE_MEDIA.QZONE);
				UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(
						DishDetailActivity.this, "1104138513",
						"nIaMyw53eCeHCBRg");
				qqSsoHandler.addToSocialSDK();
				QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
						DishDetailActivity.this, "1104138513",
						"nIaMyw53eCeHCBRg");
				qZoneSsoHandler.addToSocialSDK();
				QQShareContent qqShareContent = new QQShareContent();
				// 设置分享文字
				qqShareContent.setShareContent(share_content);
				// 设置分享title
				qqShareContent.setTitle("私厨汇--名厨汇聚，美食到家");
				// 设置分享图片
				qqShareContent.setShareImage(new UMImage(
						DishDetailActivity.this, R.drawable.logo_128));
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
				mController.openShare(DishDetailActivity.this, false);
				break;
			case 404:
				Toast.makeText(getApplicationContext(), "获取分享内容失败",
						Toast.LENGTH_SHORT).show();
				break;
			case 500:
				closeDialog();
				Toast.makeText(getApplicationContext(), "您已经收藏过该菜品",
						Toast.LENGTH_SHORT).show();
				break;
			case 6:
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

	private void setChefInfo() {
		chefname.setText(chef.getName());
		chefdesc.setText(chef.getIntro());
		chefpingjia.setText("评价" + chef.getEvalcount() + "条");
		chefjiguan.setText("籍贯：" + chef.getNativeplace());
		cheffuwu.setText("服务" + chef.getServicecount() + "次");
		yuyue.setText("选 " + chef.getName() + " 预约");
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
		closeDialog();
	}

	private void setChefImage(List<Chefs> lists) {
		// TODO Auto-generated method stub
		if (lists.size() > 0) {
			setChefDetail(lists.get(0).getId());
			ll1.setBackgroundResource(R.drawable.disn_deatail_chefs);
			cLayout = ll1;
		}
		switch (lists.size()) {
		case 0:
			chef1.setImageBitmap(null);
			chef2.setImageBitmap(null);
			chef3.setImageBitmap(null);
			chef4.setImageBitmap(null);
			chef5.setImageBitmap(null);
			chef6.setImageBitmap(null);
			chef1.setClickable(false);
			chef2.setClickable(false);
			chef3.setClickable(false);
			chef4.setClickable(false);
			chef5.setClickable(false);
			chef6.setClickable(false);
			break;
		case 1:
			if (!lists.get(0).getHeadpicurl().equals("")) {
				chef1.setClickable(true);
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef1, lists.get(0)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef1.setImageResource(R.drawable.nopic);
			}
			chef2.setImageBitmap(null);
			chef3.setImageBitmap(null);
			chef4.setImageBitmap(null);
			chef5.setImageBitmap(null);
			chef6.setImageBitmap(null);
			chef2.setClickable(false);
			chef3.setClickable(false);
			chef4.setClickable(false);
			chef5.setClickable(false);
			chef6.setClickable(false);
			break;
		case 2:
			if (!lists.get(0).getHeadpicurl().equals("")) {
				chef1.setClickable(true);
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef1, lists.get(0)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(1).getHeadpicurl().equals("")) {
				chef2.setClickable(true);
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef2, lists.get(1)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef2.setImageResource(R.drawable.nopic);
			}
			chef3.setImageBitmap(null);
			chef4.setImageBitmap(null);
			chef5.setImageBitmap(null);
			chef6.setImageBitmap(null);
			chef3.setClickable(false);
			chef4.setClickable(false);
			chef5.setClickable(false);
			chef6.setClickable(false);
			break;
		case 3:
			if (!lists.get(0).getHeadpicurl().equals("")) {
				chef1.setClickable(true);
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef1, lists.get(0)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(1).getHeadpicurl().equals("")) {
				chef2.setClickable(true);
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef2, lists.get(1)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef2.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(2).getHeadpicurl().equals("")) {
				chef3.setClickable(true);
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef3, lists.get(2)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef3.setImageResource(R.drawable.nopic);
			}
			chef4.setImageBitmap(null);
			chef5.setImageBitmap(null);
			chef6.setImageBitmap(null);
			chef4.setClickable(false);
			chef5.setClickable(false);
			chef6.setClickable(false);
			break;
		case 4:
			if (!lists.get(0).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef1, lists.get(0)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(1).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef2, lists.get(1)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef2.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(2).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef3, lists.get(2)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef3.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(3).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef4, lists.get(3)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef4.setImageResource(R.drawable.nopic);
			}
			chef1.setClickable(true);
			chef2.setClickable(true);
			chef3.setClickable(true);
			chef4.setClickable(true);
			chef5.setImageBitmap(null);
			chef6.setImageBitmap(null);
			chef5.setClickable(false);
			chef6.setClickable(false);
			break;
		case 5:
			if (!lists.get(0).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef1, lists.get(0)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(1).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef2, lists.get(1)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef2.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(2).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef3, lists.get(2)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef3.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(3).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef4, lists.get(3)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef4.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(4).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef5, lists.get(4)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef5.setImageResource(R.drawable.nopic);
			}
			chef1.setClickable(true);
			chef2.setClickable(true);
			chef3.setClickable(true);
			chef4.setClickable(true);
			chef5.setClickable(true);
			chef6.setImageBitmap(null);
			chef6.setClickable(false);
			break;
		case 6:
			if (!lists.get(0).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef1, lists.get(0)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef1.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(1).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef2, lists.get(1)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef2.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(2).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef3, lists.get(2)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef3.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(3).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef4, lists.get(3)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef4.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(4).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef5, lists.get(4)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef5.setImageResource(R.drawable.nopic);
			}
			if (!lists.get(5).getHeadpicurl().equals("")) {
				AsynImageLoader asynImageLoader = new AsynImageLoader();
				asynImageLoader.showImageAsyn(chef6, lists.get(5)
						.getHeadpicurl(), R.drawable.nopic);
			} else {
				chef6.setImageResource(R.drawable.nopic);
			}
			chef1.setClickable(true);
			chef2.setClickable(true);
			chef3.setClickable(true);
			chef4.setClickable(true);
			chef5.setClickable(true);
			chef6.setClickable(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.title_back:
			this.finish();
			break;
		case R.id.title_collect:
			if (userId != 0) {
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
		case R.id.iv1:
			if (chefs.get(0).getId() != 0) {
				handleImageView(ll1);
				setChefDetail(chefs.get(0).getId());
			}
			break;
		case R.id.iv2:
			if (chefs.get(1).getId() != 0) {
				handleImageView(ll2);
				setChefDetail(chefs.get(1).getId());
			}
			break;
		case R.id.iv3:
			if (chefs.get(2).getId() != 0) {
				handleImageView(ll3);
				setChefDetail(chefs.get(2).getId());
			}
			break;
		case R.id.iv4:
			if (chefs.get(3).getId() != 0) {
				handleImageView(ll4);
				setChefDetail(chefs.get(3).getId());
			}
			break;
		case R.id.iv5:
			if (chefs.get(4).getId() != 0) {
				handleImageView(ll5);
				setChefDetail(chefs.get(4).getId());
			}
			break;
		case R.id.iv6:
			if (chefs.get(5).getId() != 0) {
				handleImageView(ll6);
				setChefDetail(chefs.get(5).getId());
			}
			break;
		case R.id.dish_yuyue:
			if (chef != null) {
				Intent intent = new Intent(DishDetailActivity.this,
						ChefDetailActivity.class);
				intent.putExtra("chefId", chef.getId());
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "先选择厨师",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.moreChef:
			Intent intent = new Intent(getApplicationContext(),
					MoreChefActivity.class);
			intent.putExtra("dishId", dishId);
			startActivity(intent);
			break;
		case R.id.title_share:
			new Thread(shareRunnable).start();
			break;
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
				myHandler.sendEmptyMessage(5);
			} else {
				myHandler.sendEmptyMessage(404);
			}
		}
	};

	public String position;
	private void setChefDetail(final int chefid) {
		showDialog("加载中...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				position = LoadingActivity.loca.getAddr();
				chef = CommonFun.getChefDetail(DishDetailActivity.this, chefid,position);
				if (chef != null) {
					myHandler.sendEmptyMessage(1);
				}
			}
		}).start();
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		userId = getSharedPreferences("USERINFO", MODE_PRIVATE).getInt(
				"userId", 0);
	}

	public void handleImageView(LinearLayout linearLayout) {
		cLayout.setBackgroundColor(getResources().getColor(R.color.white));
		linearLayout.setBackgroundResource(R.drawable.disn_deatail_chefs);
		cLayout = linearLayout;
	}
}
