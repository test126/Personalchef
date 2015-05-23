package com.goodfriends.personalchef.bean;

import java.util.List;

public class ServiceTime {

	private String servicedate;
	private List<Times> times;

	public ServiceTime(String servicedate, List<Times> times) {
		this.servicedate = servicedate;
		this.times = times;
	}

	public String getServicedate() {
		return servicedate;
	}

	public void setServicedate(String servicedate) {
		this.servicedate = servicedate;
	}

	public List<Times> getTimes() {
		return times;
	}

	public void setTimes(List<Times> times) {
		this.times = times;
	}
}
