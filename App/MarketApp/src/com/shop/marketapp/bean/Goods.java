package com.shop.marketapp.bean;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 *��Ʒ����Ϣ
 */
public class Goods implements Serializable{
	private int id;
	private int sid;
	private String name;
	private int type;
	private double price;
	private double stocks;
	private double sales;
	private int camount;
	private String pic;
	private double goodsale;
	
	//��Ʒ����ʱĬ�ϱ�ѡ����
	private boolean checked=true;
	//�����Ʒ�Ƿ��޸�
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
	public double getGoodsale() {
		return goodsale;
	}
	public void setGoodsale(double goodsale) {
		this.goodsale = goodsale;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getStocks() {
		return stocks;
	}
	public void setStocks(double stocks) {
		this.stocks = stocks;
	}
	public double getSales() {
		return sales;
	}
	public void setSales(double sales) {
		this.sales = sales;
	}
	public int getCamount() {
		return camount;
	}
	public void setCamount(int camount) {
		this.camount = camount;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
}
