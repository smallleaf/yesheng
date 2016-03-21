package com.app.service;

import java.util.List;

import com.app.entity.Orders;

public interface OrderService {
	public List<Orders> addOrders(List<Orders> orders);
	public void updateOrders(List<Orders> orders);
	public List<Orders> searchOrders(String uid,String typeId);
	public int deleteOrders(List<Integer> ids);
	public void deleteOrders(int id);
}
