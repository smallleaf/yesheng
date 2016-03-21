package com.wtu.graduateproject.po.domain;

public class DetailsTeacher {
	private String id;
	private String password;
	private String name;
	private String phone;
	private String teacherId;
	private String institution;
	private String major;
	
	
	
	
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "DetailsTeacher [id=" + id + ", password=" + password
				+ ", name=" + name + ", phone=" + phone + ", teacherId="
				+ teacherId + ", institution=" + institution + ", major="
				+ major + "]";
	}
	
	
}
