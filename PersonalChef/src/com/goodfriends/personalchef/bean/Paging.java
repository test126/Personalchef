package com.goodfriends.personalchef.bean;

import java.util.List;

public class Paging {

	private int currentPage, countPage;
	private List<Object> list;

	public Paging(int currentPage, int countPage, List<Object> list) {
		// TODO Auto-generated constructor stub
		this.currentPage = currentPage;
		this.countPage = countPage;
		this.list = list;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
}
