package com.goodfriends.personalchef.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {

	public static final int CHUSHI = 1;
	public static final int CAIPIN = 2;

	public static String post(String url, List<NameValuePair> nameValuePairs) {
		String result = null;
		try {
			BufferedReader reader = null;
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost();
			HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs,
					HTTP.UTF_8);
			request.setEntity(entity);
			request.setURI(new URI(url));
			HttpResponse response = httpClient.execute(request);
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String get(String url, List<NameValuePair> nameValuePairs) {
		String result = null;
		try {
			BufferedReader reader = null;
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = httpClient.execute(request);
			reader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent()));
			StringBuffer strBuffer = new StringBuffer("");
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static boolean checkNetWork(Context context) throws Exception {
		boolean b = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
			b = true;
		} else {
			info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
				b = true;
			}
		}
		return b;
	}
}
