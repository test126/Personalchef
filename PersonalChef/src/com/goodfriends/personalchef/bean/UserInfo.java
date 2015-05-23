package com.goodfriends.personalchef.bean;

public class UserInfo {

	private String headpicurl, mobile, name;
	private int userid;
	private int errcode;
	private String errmsg;

	public UserInfo(String headpicurl, String mobile, String name, int userid,
			int errcode, String errmsg) {
		this.headpicurl = headpicurl;
		this.mobile = mobile;
		this.name = name;
		this.userid = userid;
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public UserInfo(int errcode, String errmsg) {
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

	public String getHeadpicurl() {
		return headpicurl;
	}

	public void setHeadpicurl(String headpicurl) {
		this.headpicurl = headpicurl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
}
