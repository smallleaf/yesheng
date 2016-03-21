package com.example.graduate.student.bean;

public class ChooseCourceState {
	private int id;
	private int state;
	private int volunnteerKind;
	private String studentName;
	private String class_name;
	
	private boolean verify=false;//审核 默认为false 当选中后 变为true
	
	
	public boolean isVerify() {
		return verify;
	}
	public void setVerify(boolean verify) {
		this.verify = verify;
	}
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
	
}
