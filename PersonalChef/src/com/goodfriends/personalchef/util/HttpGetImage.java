package com.goodfriends.personalchef.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpGetImage {

	public static final Bitmap get1_5BitmapByNet(String imageUrl) {

		if (imageUrl == null)
			return null;

		Bitmap bitmap = null;
		URL url = null;
		HttpURLConnection httpconnection = null;
		try {
			url = new URL(imageUrl);
			httpconnection = (HttpURLConnection) url.openConnection();
			httpconnection.setConnectTimeout(5000);
			httpconnection.setReadTimeout(5000);
			httpconnection.connect();
			InputStream is = httpconnection.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
			options.inSampleSize = 8; // width，hight设为原来的x分一
			bitmap = BitmapFactory.decodeStream(is, null, options);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpconnection != null) {
				httpconnection.disconnect();
				System.gc(); // 提醒系统及时回收
			}
		}
		return bitmap;
	}
	
	public static final Bitmap getBitmapByNet(String imageUrl) {

		if (imageUrl == null)
			return null;

		Bitmap bitmap = null;
		URL url = null;
		HttpURLConnection httpconnection = null;
		try {
			url = new URL(imageUrl);
			httpconnection = (HttpURLConnection) url.openConnection();
			httpconnection.setConnectTimeout(5000);
			httpconnection.setReadTimeout(5000);
			httpconnection.connect();
			InputStream is = httpconnection.getInputStream();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1; // width，hight设为原来的x分一
			bitmap = BitmapFactory.decodeStream(is, null, options);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpconnection != null) {
				httpconnection.disconnect();
				System.gc(); // 提醒系统及时回收
			}
		}
		return bitmap;
	}

	// public static final Bitmap getBitmapByNet1(String imageName, String
	// imageUrl) {
	//
	// if (imageUrl == null)
	// return null;
	// Bitmap bitmap = null;
	//
	// String name = imageName + ".jpg";
	// String path = Environment.getExternalStorageDirectory().toString();
	//
	// if (checkSDCard()) {
	// String fileName = path + "/nichacha/" + name;
	// File file = new File(fileName);
	// if (file.exists()) {
	// bitmap = BitmapFactory.decodeFile(fileName);
	// return bitmap;
	// } else {
	// URL url = null;
	// HttpURLConnection httpconnection = null;
	// try {
	// url = new URL(imageUrl);
	// httpconnection = (HttpURLConnection) url.openConnection();
	// httpconnection.setConnectTimeout(3000);
	// httpconnection.setReadTimeout(5000);
	// httpconnection.connect();
	// InputStream is = httpconnection.getInputStream();
	// bitmap = BitmapFactory.decodeStream(is);
	// is.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// if (httpconnection != null)
	// httpconnection.disconnect();
	// }
	//
	// if (bitmap != null) {
	// ImageHelper.saveBitmap(path + "/nichacha", name, bitmap);
	// return bitmap;
	// } else {
	// return null;
	// }
	// }
	// } else {
	// return null;
	// }
	// }
	//
	// public static Boolean checkSDCard() {
	// // return null;
	// if (android.os.Environment.getExternalStorageState().equals(
	// android.os.Environment.MEDIA_MOUNTED)) {
	// File sdcardDir = Environment.getExternalStorageDirectory();
	// File checkDir = new File(sdcardDir, "nichacha");
	// if (checkDir.exists()) {
	// return true;
	// } else {
	// checkDir.mkdirs();
	// return true;
	// }
	// } else {
	// return false;
	// }
	// }
}
