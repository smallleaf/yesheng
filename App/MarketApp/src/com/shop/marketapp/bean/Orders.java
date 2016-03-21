package com.shop.marketapp.bean;

import java.io.Serializable;

import android.R.integer;

public class Orders implements Serializable{
	private int id;
	private String uid;
	private int uid2;
	private int gid;
	private double mount;
	private double cast;
	private String time;
	private String deadline;
	private int state;
	private boolean checked=true;
	
	//标记商品是否被修改
		private boolean changed=false;
		
		public boolean isChanged() {
			return changed;
		}
		public void setChanged(boolean changed) {
			this.changed = changed;
		}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
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
	
}
