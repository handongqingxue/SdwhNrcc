package com.sdwhNrcc.entity.sdwh;

public class ApiLog {

	private String action;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getCompany_social_code() {
		return company_social_code;
	}
	public void setCompany_social_code(String company_social_code) {
		this.company_social_code = company_social_code;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	private String body;
	private String status;
	private String code;
	private String msg;
	private String data;
	private String company_social_code;
	private String time; 
}
