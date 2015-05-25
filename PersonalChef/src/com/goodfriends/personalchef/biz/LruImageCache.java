package com.goodfriends.personalchef.biz;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.goodfriends.personalchef.util.CFileCache;

public class LruImageCache implements ImageCache{

  private static LruCache<String, Bitmap> mMemoryCache;
  
  private static LruImageCache lruImageCache;
  
  private CFileCache fileCache;
  private LruImageCache(){
    // Get the Max available memory
    int maxMemory = (int) Runtime.getRuntime().maxMemory();
    int cacheSize = maxMemory / 8;
    mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
      @Override
      protected int sizeOf(String key, Bitmap bitmap){
        return bitmap.getRowBytes() * bitmap.getHeight();
      }
    };	
    
    fileCache = new CFileCache();
  }
  
  public static LruImageCache instance(){
    if(lruImageCache == null){
      lruImageCache = new LruImageCache();
    }
    return lruImageCache;
  }
  
  @Override
  public Bitmap getBitmap(String url) {	
	  Bitmap bitmap = mMemoryCache.get(url);
	  if(bitmap == null){
		  System.out.println("缓冲文件不存在");
		  bitmap = fileCache.getBitmapFromSD(url) ;
	  }else{
		  System.out.println("缓冲存在");  
	  }
	  
    return bitmap;	
  }

  @Override
  public void putBitmap(String url, Bitmap bitmap) {
    if(getBitmap(url) == null){
      mMemoryCache.put(url, bitmap);
      fileCache.putBitmapToSD(url, bitmap);
    }		
  }

}