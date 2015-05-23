package com.goodfriends.personalchef.bean;

import java.util.List;

public class AddressBean {

	private int errcode;
	private String errmsg;
	private List<Address> list;

	public AddressBean(int errcode, String errmsg, List<Address> list) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.list = list;
	}

	public AddressBean(int errcode, String errmsg) {
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

	public List<Address> getList() {
		return list;
	}

	public void setList(List<Address> list) {
		this.list = list;
	}

}
