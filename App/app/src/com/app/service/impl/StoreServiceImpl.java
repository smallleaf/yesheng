package com.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.BaseDAO;
import com.app.entity.Goods;
import com.app.entity.PageBean;
import com.app.entity.Store;
import com.app.service.StoreService;

@Service("storeService")
public class StoreServiceImpl implements StoreService{
	@Resource
	private BaseDAO baseDAO;
	
	
	
	@Override
	public List<Store> getStoreInfo() {
		String hql="from Store ";
		List<Store> list=null;
		list=baseDAO.find(hql);
		return list;
	}
	
	@Override
	public PageBean<Goods> getAllGoodsInfo(int sid,int size,int page){
		System.out.println("==========");
		String hql="from Goods where sid = ?";
		int allRow=baseDAO.find(hql, new Object[]{sid}).size();
		int totalPage=PageBean.countTotalPage(size, allRow);
		int offset=PageBean.countOffset(size,page);
		int length=size;
		int currentPage=PageBean.countCurrentPage(page);
		List<Goods> list=baseDAO.find(hql,new Object[]{sid});
		System.out.println("----"+list);
		PageBean<Goods> pageBean=new PageBean<Goods>();
		pageBean.setPageSize(size);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Goods getGoods(int id) {
		// TODO Auto-generated method stub
		String hql="from Goods where id = ?";
		List<Goods> list=baseDAO.find(hql,new Object[]{id});
		return list.get(0);
	}

	@Override
	public String getGoodsMerChant(int id) {
		// TODO Auto-generated method stub
		String hql="from Store where id = ?";
		List<Store> list=baseDAO.find(hql,new Object[]{id});
		return list.get(0).getSname();
	}

}
