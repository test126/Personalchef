package com.goodfriends.personalchef.bean;

import java.util.List;

public class Chefs {

	private String address, headpicurl, intro, mobile, name, nativeplace,
			regdate, cookstyles;
	private int evalcount, grade, id, isauth, isrecommend, servicecount,
			status, price, age, score,distance;
	private List<Dish> dishs;
	private List<Pack> packs;

	public Chefs(String headpicurl, int id) {
		this.headpicurl = headpicurl;
		this.id = id;
	}

	public Chefs(String headpicurl, String intro, String name,
			String nativeplace, int evalcount, int grade, int id, int isauth,
			int servicecount, int age,int distance) {
		this.headpicurl = headpicurl;
		this.intro = intro;
		this.name = name;
		this.nativeplace = nativeplace;
		this.evalcount = evalcount;
		this.grade = grade;
		this.id = id;
		this.isauth = isauth;
		this.servicecount = servicecount;
		this.age = age;
		this.distance = distance;
	}

	public Chefs(String headpicurl, String intro, String name,
			String nativeplace, int evalcount, int grade, int id, int isauth,
			int servicecount, int age, List<Dish> dishs, int score,
			String cookstyles,int distance) {
		this.headpicurl = headpicurl;
		this.intro = intro;
		this.name = name;
		this.nativeplace = nativeplace;
		this.evalcount = evalcount;
		this.grade = grade;
		this.id = id;
		this.isauth = isauth;
		this.servicecount = servicecount;
		this.dishs = dishs;
		this.age = age;
		this.score = score;
		this.cookstyles = cookstyles;
		this.distance = distance;
	}



	public Chefs(String headpicurl, String intro, String name,
			String nativeplace, int evalcount, int grade, int id, int isauth,
			int servicecount, int age, List<Dish> dishs, int score,
			String cookstyles, String mobile) {
		this.headpicurl = headpicurl;
		this.intro = intro;
		this.name = name;
		this.nativeplace = nativeplace;
		this.evalcount = evalcount;
		this.grade = grade;
		this.id = id;
		this.isauth = isauth;
		this.servicecount = servicecount;
		this.dishs = dishs;
		this.age = age;
		this.score = score;
		this.cookstyles = cookstyles;
		this.mobile = mobile;
	}

	public Chefs(String headpicurl, String intro, String name,
			String nativeplace, int evalcount, int grade, int id, int isauth,
			int servicecount, List<Dish> dishs, List<Pack> packs, int age,
			int score,int distance) {
		this.headpicurl = headpicurl;
		this.intro = intro;
		this.name = name;
		this.nativeplace = nativeplace;
		this.evalcount = evalcount;
		this.grade = grade;
		this.id = id;
		this.isauth = isauth;
		this.servicecount = servicecount;
		this.dishs = dishs;
		this.packs = packs;
		this.age = age;
		this.score = score;
		this.distance = distance;
	}
	
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getCookstyles() {
		return cookstyles;
	}

	public void setCookstyles(String cookstyles) {
		this.cookstyles = cookstyles;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Pack> getPacks() {
		return packs;
	}

	public void setPacks(List<Pack> packs) {
		this.packs = packs;
	}

	public List<Dish> getDishs() {
		return dishs;
	}

	public void setDishs(List<Dish> dishs) {
		this.dishs = dishs;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeadpicurl() {
		return headpicurl;
	}

	public void setHeadpicurl(String headpicurl) {
		this.headpicurl = headpicurl;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNativeplace() {
		return nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getEvalcount() {
		return evalcount;
	}

	public void setEvalcount(int evalcount) {
		this.evalcount = evalcount;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsauth() {
		return isauth;
	}

	public void setIsauth(int isauth) {
		this.isauth = isauth;
	}

	public int getIsrecommend() {
		return isrecommend;
	}

	public void setIsrecommend(int isrecommend) {
		this.isrecommend = isrecommend;
	}

	public int getServicecount() {
		return servicecount;
	}

	public void setServicecount(int servicecount) {
		this.servicecount = servicecount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
