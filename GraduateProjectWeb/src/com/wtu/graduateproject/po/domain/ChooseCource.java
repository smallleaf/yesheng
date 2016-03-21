package com.wtu.graduateproject.po.domain;

public class ChooseCource {
	private int id;
	private String studentId;
	private int courceId;
	private int state;
	private int volunteerKind;
	
	
	public int getVolunteerKind() {
		return volunteerKind;
	}
	public void setVolunteerKind(int volunteerKind) {
		this.volunteerKind = volunteerKind;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public int getCourceId() {
		return courceId;
	}
	public void setCourceId(int courceId) {
		this.courceId = courceId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
}
