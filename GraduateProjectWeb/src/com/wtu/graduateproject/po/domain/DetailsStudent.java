package com.wtu.graduateproject.po.domain;

public class DetailsStudent {
	private String studentId;
	private String password;
	private String tel;
	private String major;
	private String name;
	private String class_name;
	private String institution;
	private int first_volunteer;
	private int second_volunteer;
	private String first_volunteer_name;
	private String second_volunteer_name;
	private int first_volunteer_state;
	private int second_volunteer_state;
	
	
	private int first_volunteer_id;
	private int second_volunteer_id;
	
	
	
	public int getFirst_volunteer_id() {
		return first_volunteer_id;
	}
	public void setFirst_volunteer_id(int first_volunteer_id) {
		this.first_volunteer_id = first_volunteer_id;
	}
	public int getSecond_volunteer_id() {
		return second_volunteer_id;
	}
	public void setSecond_volunteer_id(int second_volunteer_id) {
		this.second_volunteer_id = second_volunteer_id;
	}
	public int getFirst_volunteer_state() {
		return first_volunteer_state;
	}
	public void setFirst_volunteer_state(int first_volunteer_state) {
		this.first_volunteer_state = first_volunteer_state;
	}
	public int getSecond_volunteer_state() {
		return second_volunteer_state;
	}
	public void setSecond_volunteer_state(int second_volunteer_state) {
		this.second_volunteer_state = second_volunteer_state;
	}
	public String getFirst_volunteer_name() {
		return first_volunteer_name;
	}
	public void setFirst_volunteer_name(String first_volunteer_name) {
		this.first_volunteer_name = first_volunteer_name;
	}
	public String getSecond_volunteer_name() {
		return second_volunteer_name;
	}
	public void setSecond_volunteer_name(String second_volunteer_name) {
		this.second_volunteer_name = second_volunteer_name;
	}
	public int getFirst_volunteer() {
		return first_volunteer;
	}
	public void setFirst_volunteer(int first_volunteer) {
		this.first_volunteer = first_volunteer;
	}
	public int getSecond_volunteer() {
		return second_volunteer;
	}
	public void setSecond_volunteer(int second_volunteer) {
		this.second_volunteer = second_volunteer;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	@Override
	public String toString() {
		return "DetailsStudent [studentId=" + studentId + ", password="
				+ password + ", tel=" + tel + ", major=" + major + ", name="
				+ name + ", class_name=" + class_name + ", institution="
				+ institution + ", first_volunteer=" + first_volunteer
				+ ", second_volunteer=" + second_volunteer
				+ ", first_volunteer_name=" + first_volunteer_name
				+ ", second_volunteer_name=" + second_volunteer_name
				+ ", first_volunteer_state=" + first_volunteer_state
				+ ", second_volunteer_state=" + second_volunteer_state
				+ ", first_volunteer_id=" + first_volunteer_id
				+ ", second_volunteer_id=" + second_volunteer_id + "]";
	}
	
	
	
}
