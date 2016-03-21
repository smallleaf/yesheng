package com.wtu.graduateproject.po.domain;

public class Student {
	private String studentId;
	private String password;
	private String tel;
	private int majorId;
	private String name;
	private int classId;
	private int first_volunteer;
	private int second_volunteer;
	
	
	
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
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
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
	public int getMajorId() {
		return majorId;
	}
	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", password=" + password
				+ ", tel=" + tel + ", majorId=" + majorId + ", name=" + name
				+ ", classId=" + classId + ", first_volunteer="
				+ first_volunteer + ", second_volunteer=" + second_volunteer
				+ "]";
	}
	
	
}
