package com.app.service;

import java.util.List;

import com.app.entity.Goods;
import com.app.entity.PageBean;
import com.app.entity.Store;

public interface StoreService {
	public List<Store> getStoreInfo();
	public PageBean<Goods> getAllGoodsInfo(int sid,int size,int page);
	public Goods getGoods(int id);
	public String getGoodsMerChant(int id);
}
