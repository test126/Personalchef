package com.goodfriends.personalchef.bean;

public class Order {

	private String masterheadpic, mastername, orderno, servicedate, packname;
	private int masterid, ordertype, packid, status, totaldishcount, totalfee;
	private int countPage;
	private int bringcond, bringfood, custdishcount;
	private String remark;

	public Order(String remark, String servicedate, int ordertype, int status,
			int totaldishcount, int totalfee, int bringcond, int bringfood,
			int custdishcount) {
		this.servicedate = servicedate;
		this.remark = remark;
		this.ordertype = ordertype;
		this.status = status;
		this.totaldishcount = totaldishcount;
		this.totalfee = totalfee;
		this.bringcond = bringcond;
		this.bringfood = bringfood;
		this.custdishcount = custdishcount;
	}

	public Order(String masterheadpic, String mastername, String orderno,
			String servicedate, String packname, int masterid, int ordertype,
			int packid, int status, int totaldishcount, int totalfee,
			int countPage) {
		this.masterheadpic = masterheadpic;
		this.mastername = mastername;
		this.orderno = orderno;
		this.servicedate = servicedate;
		this.packname = packname;
		this.masterid = masterid;
		this.ordertype = ordertype;
		this.packid = packid;
		this.status = status;
		this.totaldishcount = totaldishcount;
		this.totalfee = totalfee;
		this.countPage = countPage;
	}

	public Order(String masterheadpic, String mastername, String orderno,
			String servicedate, String packname, int masterid, int ordertype,
			int packid, int status, int totaldishcount, int totalfee) {
		this.masterheadpic = masterheadpic;
		this.mastername = mastername;
		this.orderno = orderno;
		this.servicedate = servicedate;
		this.packname = packname;
		this.masterid = masterid;
		this.ordertype = ordertype;
		this.packid = packid;
		this.status = status;
		this.totaldishcount = totaldishcount;
		this.totalfee = totalfee;
	}

	public int getBringcond() {
		return bringcond;
	}

	public void setBringcond(int bringcond) {
		this.bringcond = bringcond;
	}

	public int getBringfood() {
		return bringfood;
	}

	public void setBringfood(int bringfood) {
		this.bringfood = bringfood;
	}

	public int getCustdishcount() {
		return custdishcount;
	}

	public void setCustdishcount(int custdishcount) {
		this.custdishcount = custdishcount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public String getMasterheadpic() {
		return masterheadpic;
	}

	public void setMasterheadpic(String masterheadpic) {
		this.masterheadpic = masterheadpic;
	}

	public String getMastername() {
		return mastername;
	}

	public void setMastername(String mastername) {
		this.mastername = mastername;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getServicedate() {
		return servicedate;
	}

	public void setServicedate(String servicedate) {
		this.servicedate = servicedate;
	}

	public String getPackname() {
		return packname;
	}

	public void setPackname(String packname) {
		this.packname = packname;
	}

	public int getMasterid() {
		return masterid;
	}

	public void setMasterid(int masterid) {
		this.masterid = masterid;
	}

	public int getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(int ordertype) {
		this.ordertype = ordertype;
	}

	public int getPackid() {
		return packid;
	}

	public void setPackid(int packid) {
		this.packid = packid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTotaldishcount() {
		return totaldishcount;
	}

	public void setTotaldishcount(int totaldishcount) {
		this.totaldishcount = totaldishcount;
	}

	public int getTotalfee() {
		return totalfee;
	}

	public void setTotalfee(int totalfee) {
		this.totalfee = totalfee;
	}

}
