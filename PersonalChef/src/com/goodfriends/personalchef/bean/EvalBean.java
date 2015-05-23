package com.goodfriends.personalchef.bean;

import java.util.List;

public class EvalBean {

	private int errcode;
	private String errmsg;
	private List<Eval> evals;

	public EvalBean(int errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public EvalBean(int errcode, String errmsg, List<Eval> evals) {
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.evals = evals;
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

	public List<Eval> getEvals() {
		return evals;
	}

	public void setEvals(List<Eval> evals) {
		this.evals = evals;
	}
}
