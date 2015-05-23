package com.goodfriends.personalchef.bean;

public class CouPon {

	private String couponName, endTime, getTime, fromTime;
	private int couponid, status, value, userCouponId;

	public CouPon(String couponName, String endTime, String getTime,
			String fromTime, int couponid, int status, int value,
			int userCouponId) {
		this.couponName = couponName;
		this.endTime = endTime;
		this.getTime = getTime;
		this.fromTime = fromTime;
		this.couponid = couponid;
		this.status = status;
		this.value = value;
		this.userCouponId = userCouponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public int getCouponid() {
		return couponid;
	}

	public void setCouponid(int couponid) {
		this.couponid = couponid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getUserCouponId() {
		return userCouponId;
	}

	public void setUserCouponId(int userCouponId) {
		this.userCouponId = userCouponId;
	}
}
