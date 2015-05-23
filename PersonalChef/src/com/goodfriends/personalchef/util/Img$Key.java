package com.goodfriends.personalchef.util;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;

/**
 * 图片缓存
 * @author le7mo27
 *
 */
public class Img$Key {

	public Img$Key(boolean isUse, String keyString,
			SoftReference<Bitmap> softReference) {
		super();
		this.isUse = isUse;
		this.keyString = keyString;
		this.softReference = softReference;
	}

	public boolean isUse;//是否正在使用
	
	public String keyString;
	
	public SoftReference<Bitmap> softReference;
	
}
