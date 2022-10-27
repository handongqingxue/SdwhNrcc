package com.sdwhNrcc.entity.v3_1;

public class KeyWarning {

	public static final int UNSYNC=0;
	public static final int SYNCED=1;
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
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
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public Boolean getSync() {
		return sync;
	}
	public void setSync(Boolean sync) {
		this.sync = sync;
	}
	private String tagId;
	private Integer entityId;
	private String entityName;
	private Integer areaId;
	private Long raiseTime;
	private String raiseTimeYMD;
	private Float x;
	private Float y;
	private Float z;
	private Integer floor;
	private Boolean sync;
}
