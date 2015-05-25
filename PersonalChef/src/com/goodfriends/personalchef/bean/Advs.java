package com.goodfriends.personalchef.bean;

import java.io.Serializable;

public class Advs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imgurl;
	private String url;

	public Advs(String imgurl, String url) {
		this.imgurl = imgurl;
		this.url = url;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
