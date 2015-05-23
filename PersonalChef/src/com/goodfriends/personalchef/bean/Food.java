package com.goodfriends.personalchef.bean;

public class Food {

	private String foodname, weight;

	public Food(String foodname, String weight) {
		this.foodname = foodname;
		this.weight = weight;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
}
