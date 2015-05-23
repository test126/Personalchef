package com.goodfriends.personalchef.bean;

import java.util.List;

public class DishInfo {

	private int countPage, countRecord, currentPage, onePageCount;
	private List<Dish> list;

	public DishInfo(int countPage, int countRecord, int currentPage,
			int onePageCount, List<Dish> list) {
		this.countPage = countPage;
		this.countRecord = countRecord;
		this.currentPage = currentPage;
		this.onePageCount = onePageCount;
		this.list = list;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public int getCountRecord() {
		return countRecord;
	}

	public void setCountRecord(int countRecord) {
		this.countRecord = countRecord;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getOnePageCount() {
		return onePageCount;
	}

	public void setOnePageCount(int onePageCount) {
		this.onePageCount = onePageCount;
	}

	public List<Dish> getList() {
		return list;
	}

	public void setList(List<Dish> list) {
		this.list = list;
	}
}
