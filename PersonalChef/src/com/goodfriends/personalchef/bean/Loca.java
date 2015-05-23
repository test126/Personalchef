package com.goodfriends.personalchef.bean;

public class Loca {

	private String province, city, district, addr;

	public Loca(String province, String city, String district, String addr) {
		this.province = province;
		this.city = city;
		this.district = district;
		this.addr = addr;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

}
