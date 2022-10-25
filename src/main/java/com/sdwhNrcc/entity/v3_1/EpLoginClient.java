package com.sdwhNrcc.entity.v3_1;

public class EpLoginClient {

	private String access_token;
	public EpLoginClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EpLoginClient(String access_token, String client_id) {
		this.access_token = access_token;
		this.client_id = client_id;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	private String client_id;
}
