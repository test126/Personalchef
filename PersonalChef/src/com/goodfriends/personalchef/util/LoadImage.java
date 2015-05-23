package com.goodfriends.personalchef.util;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.goodfriends.personalchef.R;

public class LoadImage {

	private Resources res;

	private static CacheManager cacheManager = null;

	public LoadImage(Resources res) {
		this.res = res;
		if (cacheManager == null)
			cacheManager = new CacheManager();
	}

	@SuppressLint("HandlerLeak")
	public void loadImage(final String imageUrl,
			final ImageCallback imageCallback) {

		final Handler myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				imageCallback.imageLoaded((Bitmap) msg.obj);
			}
		};

		// 缓存中读取图片
		Bitmap bitmap = cacheManager.getBitmap(imageUrl);

		if (bitmap != null) {// 存在这样的数据
			Message message = myHandler.obtainMessage(0, bitmap);
			myHandler.sendMessage(message);
			return;
		}

		// 缓存中不存在这样的数据
		new Thread() {
			@Override
			public void run() {
				Bitmap bitmap = null;
				if (imageUrl != null) {
					bitmap = HttpGetImage.getBitmapByNet(imageUrl);
				}
				Message message = null;
				if (bitmap != null) {
					cacheManager.addData(imageUrl, bitmap);// 向缓存中添加一条数据
					message = myHandler.obtainMessage(0, bitmap);
				} else {// 读图失败
					message = myHandler.obtainMessage(0, BitmapFactory
							.decodeResource(res, R.drawable.nopic));
				}
				myHandler.sendMessage(message);
			}
		}.start();

	}

	public void loadImage1_5(final String imageUrl,
			final ImageCallback imageCallback) {

		final Handler myHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				imageCallback.imageLoaded((Bitmap) msg.obj);
			}
		};

		// 缓存中读取图片
		Bitmap bitmap = cacheManager.getBitmap(imageUrl);

		if (bitmap != null) {// 存在这样的数据
			Message message = myHandler.obtainMessage(0, bitmap);
			myHandler.sendMessage(message);
			return;
		}

		// 缓存中不存在这样的数据
		new Thread() {
			@Override
			public void run() {
				Bitmap bitmap = null;
				if (imageUrl != null) {
					bitmap = HttpGetImage.get1_5BitmapByNet(imageUrl);
				}
				Message message = null;
				if (bitmap != null) {
					cacheManager.addData(imageUrl, bitmap);// 向缓存中添加一条数据
					message = myHandler.obtainMessage(0, bitmap);
				} else {// 读图失败
					message = myHandler.obtainMessage(0, BitmapFactory
							.decodeResource(res, R.drawable.nopic));
				}
				myHandler.sendMessage(message);
			}
		}.start();

	}

	// @SuppressLint("HandlerLeak")
	// public void loadImage6(final String name, final String imageUrl,
	// final ImageCallback imageCallback) {
	//
	// final Handler myHandler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// imageCallback.imageLoaded((Bitmap) msg.obj);
	// }
	// };
	//
	// // 缓存中读取图片
	// Bitmap bitmap = cacheManager.getBitmap(imageUrl);
	//
	// if (bitmap != null) {// 存在这样的数据
	// Message message = myHandler.obtainMessage(0, bitmap);
	// myHandler.sendMessage(message);
	// return;
	// }
	//
	// // 缓存中不存在这样的数据
	// new Thread() {
	// @Override
	// public void run() {
	// Bitmap bitmap = null;
	// if (imageUrl != null)
	// bitmap = HttpGetImage.getBitmapByNet1(name, imageUrl);
	// Message message = null;
	// if (bitmap != null) {
	// cacheManager.addData(imageUrl, bitmap);// 向缓存中添加一条数据
	// message = myHandler.obtainMessage(0, bitmap);
	// } else {// 读图失败
	// message = myHandler.obtainMessage(0,
	// BitmapFactory.decodeResource(res, R.drawable.ic_launcher));
	// }
	// myHandler.sendMessage(message);
	// }
	// }.start();
	// }
	// @SuppressLint("HandlerLeak")
	// public void loadImage(final String name, final String imageUrl,
	// final ImageCallback imageCallback) {
	//
	// final Handler myHandler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// imageCallback.imageLoaded((Bitmap) msg.obj);
	// }
	// };
	//
	// // 缓存中读取图片
	// Bitmap bitmap = cacheManager.getBitmap(imageUrl);
	//
	// if (bitmap != null) {// 存在这样的数据
	// Message message = myHandler.obtainMessage(0, bitmap);
	// myHandler.sendMessage(message);
	// return;
	// }
	//
	// // 缓存中不存在这样的数据
	// new Thread() {
	// @Override
	// public void run() {
	// Bitmap bitmap = null;
	// if (imageUrl != null)
	// bitmap = HttpGetImage.getBitmapByNet1(name, imageUrl);
	// Message message = null;
	// if (bitmap != null) {
	// cacheManager.addData(imageUrl, bitmap);// 向缓存中添加一条数据
	// message = myHandler.obtainMessage(0, bitmap);
	// } else {// 读图失败
	// message = myHandler.obtainMessage(0,
	// BitmapFactory.decodeResource(res, R.drawable.ic_launcher));
	// }
	// myHandler.sendMessage(message);
	// }
	// }.start();
	// }
}
