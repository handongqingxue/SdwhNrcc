package com.sdwhNrcc.entity;

public class EpLoginUser {

	private String cookie;
	private String userId;
	private Integer role;//1 ~ ����Ա,�����޸����� 2 ~ ��ͨ�û�,�߱����Ȩ�ޣ��������޸����õ�Ȩ�� 3 ~ �����û�,�����������չʾҳ�� 4 ~ �ֻ��û�,�û�����������Ϣ�������ֳ�����
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
