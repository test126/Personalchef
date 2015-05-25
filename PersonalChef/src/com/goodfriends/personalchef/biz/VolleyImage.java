package com.goodfriends.personalchef.biz;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.ErrorListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.goodfriends.personalchef.R;
import com.goodfriends.personalchef.common.App;
import com.goodfriends.personalchef.common.CommonFun;
import com.goodfriends.personalchef.common.Msg;
import com.goodfriends.personalchef.util.CFileCache;

public class VolleyImage {
	private RequestQueue mQueue;
	private Handler handler;
	private CFileCache fileCache = null;
	private LruImageCache lruImageCache;
	private Context context;
	public VolleyImage(Context context, Handler handler) {
		super();
		this.context = context;
		this.handler = handler;
		mQueue = Volley.newRequestQueue(context);
		fileCache = new CFileCache(context);
		lruImageCache = LruImageCache.instance();
	}

	/**
	 * 读广告图片
	 * 
	 * @param url
	 */
	public void requestAdvs() {
		Listener listener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					JSONArray content = response.getJSONArray("content");
					for(int i =0 ;i<content.length();i++){
						JSONObject adv = content.getJSONObject(i);
						final String url = adv.getString("imgurl");
						if(!fileCache.isExist(url)){
							 Listener<Bitmap> imgListener = new Response.Listener<Bitmap>(){
								public void onResponse(Bitmap response) {
									lruImageCache.putBitmap(url, response);
									handler.sendEmptyMessage(Msg.Get_Ads_Images);
								}
							 };
							 ImageRequest imgRequest=new ImageRequest(url, imgListener , 0, 0, Config.ARGB_8888, null);
							 mQueue.add(imgRequest);
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		JsonObjectRequest  request = new JsonObjectRequest(CommonFun.advurl, null, listener, null);
		mQueue.add(request);
		mQueue.start();
	}
	
	
	/**
	 * 读广告图片
	 * 
	 * @param url
	 */
	public void requestChefs() {
		Listener listener = new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					JSONArray content = response.getJSONArray("content");
					for(int i =0 ;i<content.length();i++){
						JSONObject adv = content.getJSONObject(i);
						final String url = adv.getString("imgurl");
						if(!fileCache.isExist(url)){
							 Listener<Bitmap> imgListener = new Response.Listener<Bitmap>(){
								public void onResponse(Bitmap response) {
									lruImageCache.putBitmap(url, response);
									handler.sendEmptyMessage(Msg.Get_Ads_Images);
								}
							 };
							 ImageRequest imgRequest=new ImageRequest(url, imgListener , 0, 0, Config.ARGB_8888, null);
							 mQueue.add(imgRequest);
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		JsonObjectRequest  request = new JsonObjectRequest(CommonFun.advurl, null, listener, null);
		mQueue.add(request);
		mQueue.start();
	}
	


}
