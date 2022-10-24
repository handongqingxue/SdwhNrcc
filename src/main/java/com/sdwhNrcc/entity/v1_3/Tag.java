package com.sdwhNrcc.entity.v1_3;

public class Tag {

	private Boolean temporary;
	public Boolean getTemporary() {
		return temporary;
	}
	public void setTemporary(Boolean temporary) {
		this.temporary = temporary;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getTagStyle() {
		return tagStyle;
	}
	public void setTagStyle(String tagStyle) {
		this.tagStyle = tagStyle;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getEngineMask() {
		return engineMask;
	}
	public void setEngineMask(Integer engineMask) {
		this.engineMask = engineMask;
	}
	private String entityType;
	private String tagStyle;
	private String id;
	private String userId;
	private Integer engineMask;
}
