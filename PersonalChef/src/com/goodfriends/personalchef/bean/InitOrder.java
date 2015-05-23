package com.goodfriends.personalchef.bean;

import java.util.List;

public class InitOrder {

	private String signature;
	private int masterid, orderfee;
	private List<Dish> dishs;
	private List<Pack> packs;
	private List<ServiceTime> serviceTimes;

	public InitOrder(String signature, int masterid, int orderfee,
			List<Dish> dishs, List<Pack> packs, List<ServiceTime> serviceTimes) {
		this.signature = signature;
		this.masterid = masterid;
		this.orderfee = orderfee;
		this.dishs = dishs;
		this.packs = packs;
		this.serviceTimes = serviceTimes;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getMasterid() {
		return masterid;
	}

	public void setMasterid(int masterid) {
		this.masterid = masterid;
	}

	public int getOrderfee() {
		return orderfee;
	}

	public void setOrderfee(int orderfee) {
		this.orderfee = orderfee;
	}

	public List<Dish> getDishs() {
		return dishs;
	}

	public void setDishs(List<Dish> dishs) {
		this.dishs = dishs;
	}

	public List<Pack> getPacks() {
		return packs;
	}

	public void setPacks(List<Pack> packs) {
		this.packs = packs;
	}

	public List<ServiceTime> getServiceTimes() {
		return serviceTimes;
	}

	public void setServiceTimes(List<ServiceTime> serviceTimes) {
		this.serviceTimes = serviceTimes;
	}
}
