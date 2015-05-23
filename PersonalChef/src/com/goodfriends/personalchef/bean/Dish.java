package com.goodfriends.personalchef.bean;

import java.util.List;

public class Dish {

	private String bigimgurl, desc, name, smallimgurl, middleimgurl, imgurl;
	private int dishid, ordercount;
	private List<Chefs> lists;

	public Dish(String imgurl, int dishid) {
		this.imgurl = imgurl;
		this.dishid = dishid;
	}

	public Dish(String bigimgurl, String desc, int dishid, String name,
			String smallimgurl, int ordercount) {
		this.bigimgurl = bigimgurl;
		this.desc = desc;
		this.name = name;
		this.smallimgurl = smallimgurl;
		this.dishid = dishid;
		this.ordercount = ordercount;
	}

	public Dish(String bigimgurl, int dishid, String smallimgurl,
			String middleimgurl, String imgurl) {
		this.bigimgurl = bigimgurl;
		this.smallimgurl = smallimgurl;
		this.dishid = dishid;
		this.middleimgurl = middleimgurl;
		this.imgurl = imgurl;
	}

	public Dish(String bigimgurl, String desc, int dishid, String name,
			String smallimgurl, int ordercount, List<Chefs> lists) {
		this.bigimgurl = bigimgurl;
		this.desc = desc;
		this.name = name;
		this.smallimgurl = smallimgurl;
		this.dishid = dishid;
		this.ordercount = ordercount;
		this.lists = lists;
	}

	public Dish(String bigimgurl, String desc, int dishid, String name,
			String smallimgurl, int ordercount, List<Chefs> lists,
			String middleimgurl) {
		this.bigimgurl = bigimgurl;
		this.desc = desc;
		this.name = name;
		this.smallimgurl = smallimgurl;
		this.dishid = dishid;
		this.ordercount = ordercount;
		this.lists = lists;
		this.middleimgurl = middleimgurl;
	}

	public Dish(String bigimgurl, String desc, int dishid, String name,
			String smallimgurl, String imgurl) {
		this.bigimgurl = bigimgurl;
		this.desc = desc;
		this.name = name;
		this.smallimgurl = smallimgurl;
		this.dishid = dishid;
		this.imgurl = imgurl;
	}

	public Dish(String bigimgurl, String desc, int dishid, String name,
			String smallimgurl, int ordercount, String middleimgurl) {
		this.bigimgurl = bigimgurl;
		this.desc = desc;
		this.name = name;
		this.smallimgurl = smallimgurl;
		this.dishid = dishid;
		this.ordercount = ordercount;
		this.middleimgurl = middleimgurl;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public List<Chefs> getLists() {
		return lists;
	}

	public void setLists(List<Chefs> lists) {
		this.lists = lists;
	}

	public String getMiddleimgurl() {
		return middleimgurl;
	}

	public void setMiddleimgurl(String middleimgurl) {
		this.middleimgurl = middleimgurl;
	}

	public int getordercount() {
		return ordercount;
	}

	public void setordercount(int ordercount) {
		this.ordercount = ordercount;
	}

	public String getBigimgurl() {
		return bigimgurl;
	}

	public void setBigimgurl(String bigimgurl) {
		this.bigimgurl = bigimgurl;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSmallimgurl() {
		return smallimgurl;
	}

	public void setSmallimgurl(String smallimgurl) {
		this.smallimgurl = smallimgurl;
	}

	public int getDishid() {
		return dishid;
	}

	public void setDishid(int dishid) {
		this.dishid = dishid;
	}
}
