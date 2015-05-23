package com.goodfriends.personalchef.bean;

import java.io.Serializable;

import android.util.Log;

/**
 * 为了避免一分钟内下重复的订单，新建这个类做重复订单的判断
 * @author csm
 *
 */
public class OrderRepeatJudge implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3678866698241752856L;
	private int dishCount;
	private int id;
	private String dishids;
	private int ordertype;
	private int orderfee;
	private long timestamp;
	
	public OrderRepeatJudge(int dishCount,int id,String dishids,int ordertype,int orderfee,long timestamp)
	{
		this.dishCount = dishCount;
		this.id = id;
		this.dishids = dishids;
		this.ordertype = ordertype;
		this.orderfee = orderfee;
		this.timestamp = timestamp;
	}
	
	public int getDishCount() {
		return dishCount;
	}
	public void setDishCount(int dishCount) {
		this.dishCount = dishCount;
	}
	public int getId() {
		return id;
	}
	public void setId(int masterid) {
		this.id = masterid;
	}
	public String getDishids() {
		return dishids;
	}
	public void setDishids(String dishids) {
		this.dishids = dishids;
	}
	public int getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}
	public int getOrderfee() {
		return orderfee;
	}
	public void setOrderfee(int orderfee) {
		this.orderfee = orderfee;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean Commpare(OrderRepeatJudge order)
	{
		if(this.dishCount != order.getDishCount())
		{
			return false;
		}else if(this.id != order.getId()){
			return false;
		}else if(!this.dishids.equals(order.getDishids())){
			return false;
		}else if(this.ordertype != order.getOrdertype()){
			return false;
		}else if(this.orderfee != order.getOrderfee()){
			return false;
		}else if(this.timestamp - order.getTimestamp() > 60){
			return false;
		}else {
			return true;
		}
	}
}
