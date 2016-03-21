package com.colleage.assistant.po.domain;

import java.sql.Blob;

public class User {
	private String username;
	private String password;
	private Blob picture;
	private String  university;
	private String major;
	private String job;
	private String address;
	private String hobby1;
	private String hobby2;
	private String hobby3;
	private String associatition;
	
	
	
	
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHobby1() {
		return hobby1;
	}
	public void setHobby1(String hobby1) {
		this.hobby1 = hobby1;
	}
	public String getHobby2() {
		return hobby2;
	}
	public void setHobby2(String hobby2) {
		this.hobby2 = hobby2;
	}
	public String getHobby3() {
		return hobby3;
	}
	public void setHobby3(String hobby3) {
		this.hobby3 = hobby3;
	}
	public String getAssociatition() {
		return associatition;
	}
	public void setAssociatition(String associatition) {
		this.associatition = associatition;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Blob getPicture() {
		return picture;
	}
	public void setPicture(Blob picture) {
		this.picture = picture;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", picture=" + picture + ", university=" + university
				+ ", major=" + major + ", job=" + job + ", address=" + address
				+ ", hobby1=" + hobby1 + ", hobby2=" + hobby2 + ", hobby3="
				+ hobby3 + ", associatition=" + associatition + "]";
	}
	
	
	
}
