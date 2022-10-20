package com.sdwhNrcc.entity;

public class EpLoginUser {

	private String cookie;
	private String userId;
	private Integer role;//1 ~ 管理员,可以修改配置 2 ~ 普通用户,具备浏览权限，不允许修改配置的权限 3 ~ 来宾用户,可以浏览少数展示页面 4 ~ 手机用户,用户下载配置信息，进行现场设置
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
}
