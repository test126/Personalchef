package com.goodfriends.personalchef.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodfriends.personalchef.AddressActivity;
import com.goodfriends.personalchef.CollectActivity;
import com.goodfriends.personalchef.CouponActivity;
import com.goodfriends.personalchef.FeedBackActivity;
import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.RegActivity;
import com.goodfriends.personalchef.SettingActivity;
import com.goodfriends.personalchef.WebUrlActivity;
import com.goodfriends.personalchef.bean.IndexFun;
import com.goodfriends.personalchef.bean.ShareInfo;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.CommonFun1;
import com.goodfriends.personalchef.common.CommonUrl;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeConfig;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class MeFragment extends Fragment implements OnClickListener {

	private ImageView setting;
	public static SharedPreferences preferences;
	private String name;// , phone;
	private TextView login, feedback, address, collect, authchef, kehu, share,
			coupon;
	private LinearLayout userinfo;
	private String num = "4009989126";
	private ShareInfo shareinfo;

	private TextView tv_name;// , tv_phone;
	private ProgressDialog progressDialog;

	private UMSocialService mController;
	
	private SnsPostListener mSharedCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.activity_me, null);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mSharedCallback = new SnsPostListener() {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
//				Toast.makeText(getActivity(), "分享开始",Toast.LENGTH_SHORT).show(); 
			}
			
			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
				// 新浪:SINA QQ:QQ 微信:WEIXIN 朋友圈:WEIXIN_CIRCLE 
//				Toast.makeText(getActivity(), arg0.name(), Toast.LENGTH_LONG).show();
				final SocializeEntity shareEntity = arg2;
				int channel = -1;
				if(arg0.name().equals("SINA"))
				{
					channel = 1;
				}else if(arg0.name().equals("QQ")){
					channel = 2;
				}else if(arg0.name().equals("WEIXIN"))
				{
					channel = 3;
				}else {
					channel = 4;
				}
//				Log.e("asdasd", "sadasdasd====================");
				final int channelcopy = channel;
				SharedPreferences preferences = getActivity().getSharedPreferences(
						"USERINFO", Context.MODE_PRIVATE);
//				userId = preferences.getInt("userId", 0);
				new Thread(new Runnable() {
					 @Override
					public void run() {
//						 Log.e("asdasd", "sadasdasd====================1");
						// TODO Auto-generated method stub
						CommonFun1.notifyShare(
						getActivity().getSharedPreferences("USERINFO",
						Context.MODE_PRIVATE).getInt("userId", 0),
						channelcopy, shareEntity.getShareContent());
					}
				 }).start();
				
				SocializeConfig.getSocializeConfig().cleanListeners();
			}
		};
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getActivity();
		preferences = getActivity().getSharedPreferences("USERINFO",
				Context.MODE_PRIVATE);
		name = preferences.getString("name", "");
		// phone = preferences.getString("phone", "");

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		login = (TextView) getActivity().findViewById(R.id.mine_login);
		setting = (ImageView) getActivity().findViewById(R.id.mine_setting);
		userinfo = (LinearLayout) getActivity().findViewById(R.id.userinfo);
		feedback = (TextView) getActivity().findViewById(R.id.mine_feedback);
		address = (TextView) getActivity().findViewById(R.id.mine_address);
		collect = (TextView) getActivity().findViewById(R.id.mine_collect);
		authchef = (TextView) getActivity().findViewById(R.id.mine_auth);
		kehu = (TextView) getActivity().findViewById(R.id.mine_phone);
		share = (TextView) getActivity().findViewById(R.id.mine_share);
		tv_name = (TextView) getActivity().findViewById(R.id.me_name);
		// tv_phone = (TextView) getActivity().findViewById(R.id.me_phone);
		coupon = (TextView) getActivity().findViewById(R.id.mine_coupon);
		if (name.equals("")) {
			login.setVisibility(View.VISIBLE);
			userinfo.setVisibility(View.GONE);
		} else {
			login.setVisibility(View.GONE);
			userinfo.setVisibility(View.VISIBLE);
			tv_name.setText(name);
			// tv_phone.setText(phone);
		}

		setting.setOnClickListener(this);
		login.setOnClickListener(this);
		feedback.setOnClickListener(this);
		address.setOnClickListener(this);
		collect.setOnClickListener(this);
		authchef.setOnClickListener(this);
		kehu.setOnClickListener(this);
		share.setOnClickListener(this);
		coupon.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.mine_setting:
			Intent intent = new Intent(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;

		case R.id.mine_login:

			Intent intent2 = new Intent(getActivity(), RegActivity.class);
			startActivity(intent2);
			break;
		case R.id.mine_feedback:

			Intent intent3 = new Intent(getActivity(), FeedBackActivity.class);
			startActivity(intent3);
			break;
		case R.id.mine_address:
			if (!name.equals("")) {
				startActivity(new Intent(getActivity(), AddressActivity.class));
			} else {
				startActivity(new Intent(getActivity(), RegActivity.class));
			}
			break;
		case R.id.mine_collect:
			if (!name.equals("")) {
				startActivity(new Intent(getActivity(), CollectActivity.class));
			} else {
				startActivity(new Intent(getActivity(), RegActivity.class));
			}
			break;
		case R.id.mine_auth:
			getIndexFun(1);
			break;
		case R.id.mine_phone:
			AlertDialog.Builder builder_call = new AlertDialog.Builder(
					getActivity());
			builder_call.setMessage("确定拨打" + num + "吗？");
			builder_call.setCancelable(false);
			builder_call.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(Intent.ACTION_CALL, Uri
									.parse("tel:" + num));
							startActivity(intent);
							dialog.dismiss();
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
		case R.id.mine_share:
			showDialog("获取分享内容中...");
			new Thread(shareRunnable).start();
			break;
		case R.id.mine_coupon:
			if (!name.equals("")) {
				Intent intent4 = new Intent(getActivity(), CouponActivity.class);
				intent4.putExtra("isorder", false);
				startActivity(intent4);
			} else {
				startActivity(new Intent(getActivity(), RegActivity.class));
			}
			break;
		default:
			break;
		}
	}

	Runnable shareRunnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			shareinfo = CommonFun1.getShareInfo();
			if (!shareinfo.equals("")) {
				myHandler.sendEmptyMessage(1);
			} else {
				myHandler.sendEmptyMessage(404);
			}
		}
	};

	private List<IndexFun> indexFuns;

	private void getIndexFun(final int count) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				indexFuns = CommonFun.getIndexFun(count);
				if (indexFuns != null) {
					myHandler.sendEmptyMessage(0);
				}
			}
		}).start();

	}

	@SuppressLint("HandlerLeak")
	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Intent intent = new Intent(getActivity(), WebUrlActivity.class);
				intent.putExtra("url", indexFuns.get(0).getUrl());
				startActivity(intent);
				break;
			case 1:
				closeDialog();
				mController = UMServiceFactory
						.getUMSocialService("com.umeng.share");
				// 设置分享内容
//				Log.e("share_content", share_content + "");
				String share_content = shareinfo.getContent();
				String url = shareinfo.getDownloadurl();
				mController.unregisterListener(mSharedCallback);
				mController.setShareContent(share_content);
				// 设置分享图片, 参数2为图片的url地址
				mController.setShareMedia(new UMImage(getActivity(),
						R.drawable.logo_128));
				mController.getConfig().setPlatformOrder(SHARE_MEDIA.SINA,
						SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN,
						SHARE_MEDIA.WEIXIN_CIRCLE);
				mController.getConfig().removePlatform(SHARE_MEDIA.TENCENT,
						SHARE_MEDIA.QZONE);
				mController.registerListener(mSharedCallback);
//				String appId = "wx0d4c3545413845cc";
//				String appSecret = "ea67e785d875780f10aa0ad9bfc41c68";
				String appId = "wxfef379c3b4e202ca";
				String appSecret = "b03099b0d674535ba902c3734d589e6d";
				// 添加微信平台
				UMWXHandler wxHandler = new UMWXHandler(getActivity(), appId,
						appSecret);
				wxHandler.addToSocialSDK();
				// 支持微信朋友圈
				UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(),
						appId, appSecret);
				wxCircleHandler.setToCircle(true);
				wxCircleHandler.addToSocialSDK();
				// 设置微信好友分享内容
				WeiXinShareContent weixinContent = new WeiXinShareContent();
				weixinContent.setShareContent(share_content);
				weixinContent.setTitle("私厨汇--名厨汇聚，美食到家");
				weixinContent.setTargetUrl(url);
				weixinContent.setShareImage(new UMImage(getActivity(),
						R.drawable.logo_128));
				mController.setShareMedia(weixinContent);
				// 设置微信朋友圈分享内容
				CircleShareContent circleMedia = new CircleShareContent();
				circleMedia.setShareContent(share_content);
				circleMedia.setTitle("私厨汇--名厨汇聚，美食到家");
				circleMedia.setShareImage(new UMImage(getActivity(),
						R.drawable.logo_128));
				circleMedia.setTargetUrl(url);
				mController.setShareMedia(circleMedia);
				UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(getActivity(),
						"1104138513", "nIaMyw53eCeHCBRg");
				qqSsoHandler.addToSocialSDK();
				QQShareContent qqShareContent = new QQShareContent();
				// 设置分享文字
				qqShareContent.setShareContent(share_content);
				// 设置分享title
				qqShareContent.setTitle("私厨汇--名厨汇聚，美食到家");
				// 设置分享图片
				qqShareContent.setShareImage(new UMImage(getActivity(),
						R.drawable.logo_128));
				qqShareContent.setTargetUrl(url);
				mController.setShareMedia(qqShareContent);
				mController.openShare(getActivity(), false);
				break;
			case 404:
				closeDialog();
				Toast.makeText(getActivity(), "获取分享内容失败", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};

	private void initWxValues() {

	}
	
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		  /**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    Log.e("回调了吗？", "到底回调了没有1");
	    if(ssoHandler != null){
	    	Log.e("回调了吗？", "到底回调了没有");
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}

	private void showDialog(String s) {
		progressDialog = new ProgressDialog(getActivity());
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
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
}
