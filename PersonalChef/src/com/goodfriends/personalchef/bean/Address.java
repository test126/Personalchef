package com.goodfriends.personalchef.bean;

public class Address {

	private String address, contact, mobile, province, city, area;
	private int id, state;

	public Address(String address, String contact, String mobile) {
		// TODO Auto-generated constructor stub
		this.address = address;
		this.contact = contact;
		this.mobile = mobile;
	}

	public Address(String address, String contact, String mobile, int id,
			String province, String city, String area, int state) {
		// TODO Auto-generated constructor stub
		this.address = address;
		this.contact = contact;
		this.mobile = mobile;
		this.id = id;
		this.state = state;
		this.province = province;
		this.city = city;
		this.area = area;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
