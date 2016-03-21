package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="orders")
public class Orders {
	private int id;
	private String uid;
	private int uid2;
	private int gid;
	private double mount;
	private double cast;
	private String time;
	private String deadline;
	private int state;
	private int unpaid;
	private int undelivery;
	private int unrecieve;
	private int uncomment;
	
	@Id
	@GeneratedValue(generator="_native")
	@GenericGenerator(name="_native",strategy="native")
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
	public int getUid2() {
		return uid2;
	}
	public void setUid2(int uid2) {
		this.uid2 = uid2;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public double getMount() {
		return mount;
	}
	public void setMount(double mount) {
		this.mount = mount;
	}
	public double getCast() {
		return cast;
	}
	public void setCast(double cast) {
		this.cast = cast;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getUnpaid() {
		return unpaid;
	}
	public void setUnpaid(int unpaid) {
		this.unpaid = unpaid;
	}
	public int getUndelivery() {
		return undelivery;
	}
	public void setUndelivery(int undelivery) {
		this.undelivery = undelivery;
	}
	public int getUnrecieve() {
		return unrecieve;
	}
	public void setUnrecieve(int unrecieve) {
		this.unrecieve = unrecieve;
	}
	public int getUncomment() {
		return uncomment;
	}
	public void setUncomment(int uncomment) {
		this.uncomment = uncomment;
	}
	
}
