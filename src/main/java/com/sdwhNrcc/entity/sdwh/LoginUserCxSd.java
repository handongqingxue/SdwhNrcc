package com.sdwhNrcc.entity.sdwh;

public class LoginUserCxSd {

	private String token;
	private String username;
	public LoginUserCxSd() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginUserCxSd(String token, String username) {
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
