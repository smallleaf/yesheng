package com.example.graduate.student.bean;

import java.io.Serializable;

public class Institution implements Serializable{
	private int institutionId;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}
	
	
	
}
