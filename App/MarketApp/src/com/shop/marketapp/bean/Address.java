package com.shop.marketapp.bean;

import java.io.Serializable;

public class Address implements Serializable{
	private int id;
	private String uid;
	private String address;
	private String phone;
	private String name;
	
	//是否被选中
	private boolean choosed=false;
	
	
	public boolean isChoosed() {
		return choosed;
	}
	public void setChoosed(boolean choosed) {
		this.choosed = choosed;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
