package com.goodfriends.personalchef.bean;

public class DishBean {

	private int errcode;
	private String errmsg;
	private DishInfo dishInfo;

	public DishBean(int errcode, String errmsg, DishInfo dishInfo) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.dishInfo = dishInfo;
	}
	
	public DishBean(int errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public DishInfo getDishInfo() {
		return dishInfo;
	}

	public void setDishInfo(DishInfo dishInfo) {
		this.dishInfo = dishInfo;
	}
}
