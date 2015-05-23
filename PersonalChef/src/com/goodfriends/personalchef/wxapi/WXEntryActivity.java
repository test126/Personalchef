package com.goodfriends.personalchef.wxapi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.goodfriends.personalchef.common.CommonFun1;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

	
	@Override
	public void onReq(BaseReq req) {
		// TODO Auto-generated method stub
		super.onReq(req);
	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		super.onResp(resp);
//		Log.e("分享的回调", "分享的回调 分享的回调 分享的回调");
		/*
		if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
			 new Thread(new Runnable() {
			 @Override
			 public void run() {
			 // TODO Auto-generated method stub
			 CommonFun1.notifyShare(
			 getSharedPreferences("USERINFO",
			 Context.MODE_PRIVATE).getInt("userId", 0),
			 1, "");
			 }
			 }).start();
//			Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
		}
		*/
	}
	
}