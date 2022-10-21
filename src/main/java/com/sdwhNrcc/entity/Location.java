package com.sdwhNrcc.entity;

public class Location {

	private String deviceType;
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getRootAreaId() {
		return rootAreaId;
	}
	public void setRootAreaId(Integer rootAreaId) {
		this.rootAreaId = rootAreaId;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Long getLocationTime() {
		return locationTime;
	}
	public void setLocationTime(Long locationTime) {
		this.locationTime = locationTime;
	}
	public Long getLostTime() {
		return lostTime;
	}
	public void setLostTime(Long lostTime) {
		this.lostTime = lostTime;
	}
	public Float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public Float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public Float getZ() {
		return z;
	}
	public void setZ(Float z) {
		this.z = z;
	}
	public Boolean getAbslute() {
		return abslute;
	}
	public void setAbslute(Boolean abslute) {
		this.abslute = abslute;
	}
	public Float getSpeed() {
		return speed;
	}
	public void setSpeed(Float speed) {
		this.speed = speed;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public Boolean getOut() {
		return out;
	}
	public void setOut(Boolean out) {
		this.out = out;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getAltitude() {
		return altitude;
	}
	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	private String uid;
	private String userId;
	private Integer rootAreaId;
	private Integer areaId;
	private Long locationTime;
	private Long lostTime;
	private Float x;
	private Float y;
	private Float z;
	private Boolean abslute;
	private Float speed;
	private Integer floor;
	private Boolean out;
	private Float longitude;
	private Float latitude;
	private Float altitude;
	private String entityType;
	private String entityName;
	
}
