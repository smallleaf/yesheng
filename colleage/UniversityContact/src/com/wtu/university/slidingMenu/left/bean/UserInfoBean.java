package com.wtu.university.slidingMenu.left.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class UserInfoBean implements Serializable{
	private String name;
	private String picture;
	private String location;
	private String colleage;
	private String major;
	private String job;
	private String hobby;
	private String association;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getColleage() {
		return colleage;
	}
	public void setColleage(String colleage) {
		this.colleage = colleage;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getAssociation() {
		return association;
	}
	public void setAssociation(String association) {
		this.association = association;
	}
}
