package com.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.entity.Orders;
import com.app.dao.BaseDAO;
import com.app.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	@Resource
	private BaseDAO<Orders> baseDao;
	
	@Override
	public List<Orders> addOrders(List<Orders> orders) {
		int count=0;
		if(orders==null){
			return null;
		}
	
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(Orders order:orders){
			order.setTime(format.format(new Date()));
			order.setState(0);
			baseDao.save(order);
			count++;
		}
//		String hql="from Orders where uid=? order by deadline";
//		List<Orders> list=baseDao.find(hql,new Object[]{uid},0,count);
		return null;
	}
	
	@Override
	public void updateOrders(List<Orders> orders){
		for(Orders order:orders){
			baseDao.saveOrUpdate(order);
		}
	}
	
	@Override
	public List<Orders> searchOrders(String uid,String typeID) {
		String hql="from Orders where uid=?";
		int type=Integer.valueOf(typeID);
		System.out.println("类型+"+type);
		if(type==0)
		{ 
			hql="from Orders where uid=?";
		}
			//待付款
			if(type==1){
				hql="from Orders where uid=? and unpaid=0";
			}
			//待发货
			else if(type==2){
				hql="from Orders where uid=? and undelivery=0";
			}
			//待收货
			else if(type==3){
				hql="from Orders where uid=? and unrecieve=0";
			}
			//待评价
			else if(type==4){
				hql="from Orders where uid=? and uncomment=0";
			}
		return baseDao.find(hql, new Object[]{uid});

	}

	@Override
	public void deleteOrders(int id) {
		// TODO Auto-generated method stub
	}

	@Override
	public int deleteOrders(List<Integer> ids) {
		String hql="delete from Orders where id = ?";
		int count=0;
		for(int id:ids){
			baseDao.executeHql(hql,new Integer[]{id});
			count++;
		}
		return count;
	}
}
