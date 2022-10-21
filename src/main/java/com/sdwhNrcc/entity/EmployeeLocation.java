package com.sdwhNrcc.entity;

public class EmployeeLocation {
	
	public static final String SPORT="0";
	public static final String WARN="1";
	public static final String SLEEP="2";

	private String company_social_code;
	public String getCompany_social_code() {
		return company_social_code;
	}
	public void setCompany_social_code(String company_social_code) {
		this.company_social_code = company_social_code;
	}
	public String getFloor_no() {
		return floor_no;
	}
	public void setFloor_no(String floor_no) {
		this.floor_no = floor_no;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	private String floor_no;
	private String card_no;
	private String time_stamp;
	private String status;
	private String longitude;
	private String latitude;
}
