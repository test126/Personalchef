/**
 * 
 */
package com.goodfriends.personalchef.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.goodfriends.personalchef.common.CommonFun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * 
 * @author c
 * @desc 文件缓存
 * @date 2015-5-24
 * 
 */
public class CFileCache {
	public Context context;
	public static String externalCacheDir = null;

	public CFileCache(Context context) {
		this.context = context;
		externalCacheDir = getSDCacheDir();
	}

	public CFileCache() {
		super();
	}

	/**
	 * 
	 * @return 获取外部存储器缓存目录
	 */
	public String getSDCacheDir() {
		if(externalCacheDir!=null){
			return externalCacheDir;
		}
		String sdState = Environment.getExternalStorageState();
		if (sdState.equals(Environment.MEDIA_MOUNTED)) {
			File cacheDir = context.getExternalCacheDir();
			externalCacheDir = cacheDir.getAbsolutePath();
			System.out.println("externalCacheDir "+externalCacheDir);
		}
		return externalCacheDir;
	}

	/**
	 * @desc 文件是否存在
	 * @param url
	 * @return
	 */
	public boolean isExist(String url) {
		// SD卡不可用
		if (externalCacheDir == null) {
			return false;
		}
		
		String filePath = getFilePath(url);
		File cacheDir = new File(externalCacheDir);
		File[] cacheFiles = cacheDir.listFiles();
		if (cacheFiles == null) {
			return false;
		}
		// 查找本地文件
		for (int i = 0; i < cacheFiles.length; i++) {
			String fileName = cacheFiles[i].getName();
			if (filePath.equals(fileName)) {
				System.out.println("文件本地存在");
				return true;
			}
		}
		return false;

	}
	
	public String getFilePath(String url){
		// 截取文件名
		int begin = url.lastIndexOf("/");
		String bitmapName = url.substring(begin + 1);
		String fileName = externalCacheDir + "/" + bitmapName;
		return fileName;
	}

	/**
	 * 对本地缓存的查找
	 */
	public Bitmap getBitmapFromSD(String url) {
		if(isExist(url)){
			String filePath = getFilePath(url);
			System.out.println("对本地缓存的查找 "+filePath);
			return BitmapFactory.decodeFile(filePath);
		}else{
			System.out.println("本地文件不存在");
			return null;
		}

	}

	/**
	 * 保存文件到本地缓存
	 */
	public void putBitmapToSD(String url, Bitmap bitmap) {
		System.out.println("保存文件到本地");
		if(isExist(url)){
			System.out.println("保存文件到本地，但是已经存在");
			return;
		}
		

		String fileName = getFilePath(url);
//		
		System.out.println("对本地缓存的保存 "+fileName);
		File file = new File(fileName);
		// 保存
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			if(url.endsWith(".png") || url.endsWith(".PNG")){
				System.out.println("保存文件到本地，png");
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			}else{
				System.out.println("保存文件到本地，jpg");
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			}
			
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
