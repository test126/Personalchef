package com.goodfriends.personalchef.bean;

public class Caixi {

	private int id;
	private String name;
	private String imgurl;

	public Caixi(int id, String name, String imgurl) {
		this.id = id;
		this.name = name;
		this.imgurl = imgurl;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
