package com.shop.marketapp.bean;

import android.graphics.Bitmap;

public class BuyCarGoods {
	private String merchantName;
	private Bitmap goodsPicture;
	private String goodsName;
	private float goodsUnitPrice;
	private float money;
	private boolean isChoose;
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Bitmap getGoodsPicture() {
		return goodsPicture;
	}
	public void setGoodsPicture(Bitmap goodsPicture) {
		this.goodsPicture = goodsPicture;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public float getGoodsUnitPrice() {
		return goodsUnitPrice;
	}
	public void setGoodsUnitPrice(float goodsUnitPrice) {
		this.goodsUnitPrice = goodsUnitPrice;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public boolean isChoose() {
		return isChoose;
	}
	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}
	
	
}
