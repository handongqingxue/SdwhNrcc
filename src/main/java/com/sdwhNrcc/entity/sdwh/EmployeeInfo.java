package com.sdwhNrcc.entity.sdwh;

public class EmployeeInfo {

	public static final String NEI_BU_YUAN_GONG="01";
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLzqId() {
		return lzqId;
	}
	public void setLzqId(String lzqId) {
		this.lzqId = lzqId;
	}
	public String getPost_id() {
		return post_id;
	}
	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}
	public String getPost_name() {
		return post_name;
	}
	public void setPost_name(String post_name) {
		this.post_name = post_name;
	}
	public String getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(String depart_id) {
		this.depart_id = depart_id;
	}
	public String getDepart_name() {
		return depart_name;
	}
	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCompany_social_code() {
		return company_social_code;
	}
	public void setCompany_social_code(String company_social_code) {
		this.company_social_code = company_social_code;
	}
	public String getEmployee_type() {
		return employee_type;
	}
	public void setEmployee_type(String employee_type) {
		this.employee_type = employee_type;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	private String lzqId;
	private String post_id;
	private String post_name;
	private String depart_id;
	private String depart_name;
	private String name;
	private String sex;
	private String card_no;
	private String company_social_code;
	private String employee_type;
	private String deleted;
}
