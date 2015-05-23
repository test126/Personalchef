package com.goodfriends.personalchef.bean;

public class Eval {

	private int score;
	private String evaldate, content, userheadpic, username;

	public Eval(int score, String evaldate, String content, String userheadpic,
			String username) {
		this.score = score;
		this.evaldate = evaldate;
		this.content = content;
		this.userheadpic = userheadpic;
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getEvaldate() {
		return evaldate;
	}

	public void setEvaldate(String evaldate) {
		this.evaldate = evaldate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserheadpic() {
		return userheadpic;
	}

	public void setUserheadpic(String userheadpic) {
		this.userheadpic = userheadpic;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
