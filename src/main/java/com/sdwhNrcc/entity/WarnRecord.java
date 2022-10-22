package com.sdwhNrcc.entity;

public class WarnRecord {

	public static final int UNSYNC=0;
	public static final int SYNCED=1;
	
	private String tagId;
	private Integer warnType;
	private Integer triggerId;
	private String triggerName;
	private String pid;
	private Integer sessionId;
	private String userId;
	private Integer keyCode;
	private String uid;
	private String entityName;
	private Integer areaId;
	private Boolean absolute;
	private Long raiseTime;
	private String raiseTimeYMD;
	private Float x;
	private Float y;
	private Float z;
	private Long startTime;
	private String startTimeYMD;
	private Integer id;
	private Integer rootAreaId;
	private String wtName;
	private String areaName;
	private String xAxisDataLabel;
	private Integer warnCount;
	private Boolean sync;
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public Integer getWarnType() {
		return warnType;
	}
	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}
	public Integer getTriggerId() {
		return triggerId;
	}
	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Integer getSessionId() {
		return sessionId;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(Integer keyCode) {
		this.keyCode = keyCode;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public Boolean getAbsolute() {
		return absolute;
	}
	public void setAbsolute(Boolean absolute) {
		this.absolute = absolute;
	}
	public Long getRaiseTime() {
		return raiseTime;
	}
	public void setRaiseTime(Long raiseTime) {
		this.raiseTime = raiseTime;
	}
	public String getRaiseTimeYMD() {
		return raiseTimeYMD;
	}
	public void setRaiseTimeYMD(String raiseTimeYMD) {
		this.raiseTimeYMD = raiseTimeYMD;
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
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public String getStartTimeYMD() {
		return startTimeYMD;
	}
	public void setStartTimeYMD(String startTimeYMD) {
		this.startTimeYMD = startTimeYMD;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRootAreaId() {
		return rootAreaId;
	}
	public void setRootAreaId(Integer rootAreaId) {
		this.rootAreaId = rootAreaId;
	}
	public String getWtName() {
		return wtName;
	}
	public void setWtName(String wtName) {
		this.wtName = wtName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getxAxisDataLabel() {
		return xAxisDataLabel;
	}
	public void setxAxisDataLabel(String xAxisDataLabel) {
		this.xAxisDataLabel = xAxisDataLabel;
	}
	public Integer getWarnCount() {
		return warnCount;
	}
	public void setWarnCount(Integer warnCount) {
		this.warnCount = warnCount;
	}
	public Boolean getSync() {
		return sync;
	}
	public void setSync(Boolean sync) {
		this.sync = sync;
	}
}
