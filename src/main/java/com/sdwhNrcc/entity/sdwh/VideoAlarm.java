package com.sdwhNrcc.entity.sdwh;

import org.springframework.web.multipart.MultipartFile;

public class VideoAlarm {

	private String companyCode;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getAlarmDate() {
		return alarmDate;
	}
	public void setAlarmDate(String alarmDate) {
		this.alarmDate = alarmDate;
	}
	public String getAlarmAction() {
		return alarmAction;
	}
	public void setAlarmAction(String alarmAction) {
		this.alarmAction = alarmAction;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public MultipartFile getAlarmImage() {
		return alarmImage;
	}
	public void setAlarmImage(MultipartFile alarmImage) {
		this.alarmImage = alarmImage;
	}
	private String alarmId;
	private String alarmType;
	private String alarmDate;
	private String alarmAction;
	private String deviceCode;
	private String channelCode;
	private MultipartFile alarmImage;
}
