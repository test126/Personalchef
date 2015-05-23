package com.goodfriends.personalchef.bean;

public class Times {

	private int status, time;

	public Times(int status, int time) {
		this.status = status;
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
