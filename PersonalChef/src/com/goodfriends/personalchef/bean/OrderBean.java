package com.goodfriends.personalchef.bean;

import java.util.List;

/**
 * 订单列表
 * 
 * @author Jour
 *
 */
public class OrderBean {

	private int errcode;
	private String errmsg;
	private Order order;
	private OrderInfos list;
	private Chefs chefs;
	private Address address;
	private Pack pack;
	private List<Dish> dishs;
	private List<Food> foods;

	public OrderBean(int errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public OrderBean(int errcode, String errmsg, Order order, Chefs chefs,
			Address address, Pack pack, List<Dish> dishs, List<Food> foods) {
		this.errcode = errcode;
		this.order = order;
		this.errmsg = errmsg;
		this.chefs = chefs;
		this.address = address;
		this.pack = pack;
		this.dishs = dishs;
		this.foods = foods;
	}

	public OrderBean(int errcode, String errmsg, OrderInfos list) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.list = list;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public List<Dish> getDishs() {
		return dishs;
	}

	public void setDishs(List<Dish> dishs) {
		this.dishs = dishs;
	}

	public Order getOrder() {
		return order;
	}

	public Pack getPack() {
		return pack;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Chefs getChefs() {
		return chefs;
	}

	public void setChefs(Chefs chefs) {
		this.chefs = chefs;
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

	public OrderInfos getList() {
		return list;
	}

	public void setList(OrderInfos list) {
		this.list = list;
	}
}
