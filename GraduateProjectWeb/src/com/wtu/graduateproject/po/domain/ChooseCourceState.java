package com.wtu.graduateproject.po.domain;

public class ChooseCourceState {
	private int id;
	private int state;
	private int volunnteerKind;
	private String studentName;
	private String class_name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getVolunnteerKind() {
		return volunnteerKind;
	}
	public void setVolunnteerKind(int volunnteerKind) {
		this.volunnteerKind = volunnteerKind;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	@Override
	public String toString() {
		return "ChooseCourceState [id=" + id + ", state=" + state
				+ ", volunnteerKind=" + volunnteerKind + ", studentName="
				+ studentName + ", class_name=" + class_name + "]";
	}
	
}
