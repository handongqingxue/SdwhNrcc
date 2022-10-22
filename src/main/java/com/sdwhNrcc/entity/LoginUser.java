package com.sdwhNrcc.entity;

public class LoginUser {

	private String token;
	private String username;
	public LoginUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginUser(String token, String username) {
		this.token = token;
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
