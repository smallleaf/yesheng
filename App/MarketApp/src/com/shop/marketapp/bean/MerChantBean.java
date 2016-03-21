package com.shop.marketapp.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * 
 * @author small leaf
 *商家实体类序列化
 */
public class MerChantBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -778036782603731053L;
	//商家id
	private int id;
	//商家名
	private String name;
	//商家图片
	private String bitmapstr;
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
	public String getBitmapstr() {
		return bitmapstr;
	}
	public void setBitmapstr(String bitmapstr) {
		this.bitmapstr = bitmapstr;
	}
	
}
