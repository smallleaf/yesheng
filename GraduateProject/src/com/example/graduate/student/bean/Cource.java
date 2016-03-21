package com.example.graduate.student.bean;

import java.io.Serializable;

public class Cource implements Serializable{
	private int id;
	private String name;
	private String demand;
	private String ability;
	private int count_limit;
	private String teacherId;
	private int count_choose;
	
	public int getCount_choose() {
		return count_choose;
	}
	public void setCount_choose(int count_choose) {
		this.count_choose = count_choose;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDemand() {
		return demand;
	}
	public void setDemand(String demand) {
		this.demand = demand;
	}
	public String getAbility() {
		return ability;
	}
	public void setAbility(String ability) {
		this.ability = ability;
	}
	public int getCount_limit() {
		return count_limit;
	}
	public void setCount_limit(int count_limit) {
		this.count_limit = count_limit;
	}
	
	
}
