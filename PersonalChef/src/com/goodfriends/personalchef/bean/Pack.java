package com.goodfriends.personalchef.bean;

public class Pack {

	private String desc, name;
	private int fromcount, id, mastergrade, price, tocount;

	public Pack(String desc, String name, int fromcount, int id,
			int mastergrade, int price, int tocount) {
		this.desc = desc;
		this.name = name;
		this.fromcount = fromcount;
		this.id = id;
		this.mastergrade = mastergrade;
		this.price = price;
		this.tocount = tocount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFromcount() {
		return fromcount;
	}

	public void setFromcount(int fromcount) {
		this.fromcount = fromcount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMastergrade() {
		return mastergrade;
	}

	public void setMastergrade(int mastergrade) {
		this.mastergrade = mastergrade;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTocount() {
		return tocount;
	}

	public void setTocount(int tocount) {
		this.tocount = tocount;
	}

}
