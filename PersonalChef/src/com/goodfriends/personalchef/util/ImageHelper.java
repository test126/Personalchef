package com.goodfriends.personalchef.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class ImageHelper {
	
	private static BitmapFactory.Options options;
	
	/** 
	 * @param url 文件路径
	 * @param fileName 文件名
	 * @param b 要写到sdcard的Bitmap对象
	 * @return 是否成功保存
	 * 
	 * 图片保存到SD卡
	 */
	public static boolean saveBitmap(String url, String fileName, Bitmap b){
		createSdcardPath(url);
		
		CompressFormat format = Bitmap.CompressFormat.JPEG;
		int quality = 100;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(url + "/" + fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b.compress(format, quality, stream);
		
	}
	
	synchronized public static void createSdcardPath(String url){
		File file = new File(url);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	
	/**
	 * @param url 文件路径
	 * @param fileName 文件名
	 * @return Bitmap
	 * 
	 * SD卡取图片
	 */
	public static Bitmap getBitmapFromSD(String url, String fileName){
		File file = new File(url);
		if(!file.exists()){
			return null;
		}
		return BitmapFactory.decodeFile(url + "/" + fileName);
	}
	
	
	/**
	 * @param url 文件路径
	 * @param fileName 文件名
	 * @param per 图片读取时缩小的比例
	 * @return Bitmap
	 * 
	 * SD卡取图片
	 */
	public static Bitmap getBitmapFromSD(String url, String fileName, int per){
		options.inSampleSize = per;
		
		File file = new File(url);
		if(!file.exists()){
			return null;
		}
		return BitmapFactory.decodeFile(url + "/" + fileName, options);
	}

}
