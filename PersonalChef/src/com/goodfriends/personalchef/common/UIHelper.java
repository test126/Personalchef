package com.goodfriends.personalchef.common;

import java.io.ByteArrayOutputStream;

import com.goodfriends.personalchef.ChefSearchActivity;
import com.goodfriends.personalchef.EvalOrderActivity;
import com.goodfriends.personalchef.RegActivity;
import com.goodfriends.personalchef.city.City;
import com.goodfriends.personalchef.city.CitySelect1Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.widget.Toast;

@SuppressLint("NewApi")
public class UIHelper {
	/**
	 * 去搜索厨师界面
	 * @param mContext
	 */
	public static void toSearchChef(Context mContext)
	{
		Intent intent = new Intent();
		intent.setClass(mContext, ChefSearchActivity.class);
		mContext.startActivity(intent);
	}
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getVersionName(Context mContext) throws Exception
	{
		     PackageManager packageManager = mContext.getPackageManager();
		     PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(),0);
		      String version = packInfo.versionName;
		      return version;
	}
	/**
	 * 提示没有网络链接
	 * @param mContext
	 */
	public static void errotNetwork(Context mContext)
	{
		Toast.makeText(mContext,"网络连接异常",Toast.LENGTH_LONG).show();
	}

	/**
	 * 缩放bitmap
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }
	/**
	 * Bitmap2Bytes
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	/**
	 * 跳转到评价页面
	 * @param mContext
	 */
	public static void toEvalOrderActivity(Context mContext)
	{
		Intent intent = new Intent();
		intent.setClass(mContext, EvalOrderActivity.class);
		intent.putExtra("orderId", "12313");
		mContext.startActivity(intent);
	}
	/**
	 * 跳转到登录界面
	 * @param mContext
	 */
	public static void toSignup(Context mContext)
	{
		Intent intent = new Intent();
		intent.setClass(mContext, RegActivity.class);
		mContext.startActivity(intent);
	}
	/**
	 * 跳转到选城市界面
	 * @param mActivity
	 * @param city
	 */
	public static void toCity(Activity mActivity,City city)
	{
		Intent in = new Intent(mActivity, CitySelect1Activity.class);
		in.putExtra("city", city);
		mActivity.startActivityForResult(in, 1);
	}
	
	  /** 
	   * 友好的距离显示
	   * @param mobiles 
	   * @return 
	   */  
	 public static String friendly_distance(double distance)
	 {
		
		 long juli = ((long)distance);
		 String minter = "m";
		 String tmpString  =  juli + "";
//		 Log.e("距离计算", tmpString.length() + " "  + tmpString );
		 StringBuffer tmp = new StringBuffer();
		 if(tmpString.length() == 1)
		 {
			 tmp.append(tmpString.charAt(0));
			 tmp.append(minter);
		 }else if(tmpString.length() == 2)
		 {
			tmp.append(tmpString.charAt(0));
			tmp.append('0');
			tmp.append(minter);
		 }else if(tmpString.length() == 3){
			 tmp.append(tmpString.charAt(0));
			 tmp.append('0');
			 tmp.append('0');
			 tmp.append(minter);
		}else {
			int len = tmpString.length() - 3;
			for(int i=0;i<len;i++)
			{
				tmp.append(tmpString.charAt(i));
			}
			tmp.append("km");
		}
		 return tmp.toString();
	 }
}
