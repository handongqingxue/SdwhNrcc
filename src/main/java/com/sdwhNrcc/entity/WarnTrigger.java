package com.sdwhNrcc.entity;

public class WarnTrigger {

	public static final String CHAO_SU_NAME="超速报警";
	public static final String AN_JIAN_NAME="按键报警";
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getWarnType() {
		return warnType;
	}
	public void setWarnType(Integer warnType) {
		this.warnType = warnType;
	}
	private String name;
	private Integer warnType;
}
