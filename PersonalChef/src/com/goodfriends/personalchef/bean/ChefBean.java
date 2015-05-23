package com.goodfriends.personalchef.bean;

public class ChefBean {

	private int errcode;
	private String errmsg;
	private ChefInfo chefInfo;

	public ChefBean(int errcode, String errmsg, ChefInfo chefInfo) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.chefInfo = chefInfo;
	}

	public ChefBean(int errcode, String errmsg) {
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

	public ChefInfo getChefInfo() {
		return chefInfo;
	}

	public void setChefInfo(ChefInfo chefInfo) {
		this.chefInfo = chefInfo;
	}
}
