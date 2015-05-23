package com.goodfriends.personalchef.util;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import com.goodfriends.personalchef.util.Img$Key;

import android.graphics.Bitmap;

/**
 * 全局缓存管理类
 * 
 * @author le7mo27
 * 
 */
public class CacheManager {

	private static final int chche_length = 40;// 缓存上限

	private static ArrayList<Img$Key> list_cahce = null;

	public CacheManager() {
		if (list_cahce == null)
			list_cahce = new ArrayList<Img$Key>();
	}

	public synchronized void addData(String key, Bitmap bitmap) {
		
		if (list_cahce.size() >= chche_length) {
			System.gc();
			for (Img$Key img$Key : list_cahce) {
				img$Key.isUse = false;
				list_cahce.remove(img$Key);
				break;
			}
		}
		list_cahce
				.add(new Img$Key(true, key, new SoftReference<Bitmap>(bitmap)));
	}

	public synchronized Bitmap getBitmap(String key) {
		for (Img$Key img$Key : list_cahce) {
			if (img$Key.keyString.equals(key)) {
				SoftReference<Bitmap> softReference = img$Key.softReference;
				if (softReference != null) {
					img$Key.isUse = true;
					return softReference.get();
				} else {
					list_cahce.remove(img$Key);
				}
			}
			break;
		}
		return null;
	}

	/**
	 * 改变所有缓存的使用状态
	 */
	public synchronized void changeUse() {
		for (Img$Key img$Key : list_cahce) {
			img$Key.isUse = false;
		}
	}

}
