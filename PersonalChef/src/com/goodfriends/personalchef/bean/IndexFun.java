package com.goodfriends.personalchef.bean;

public class IndexFun {

	private String content, imgurl, title, url;
	private int sort;

	public IndexFun(String content, String imgurl, String title, String url,
			int sort) {
		this.content = content;
		this.imgurl = imgurl;
		this.title = title;
		this.url = url;
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
}
