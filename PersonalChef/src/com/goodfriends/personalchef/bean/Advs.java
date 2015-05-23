package com.goodfriends.personalchef.bean;

public class Advs {

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
