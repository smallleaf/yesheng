package com.app.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.app.entity.Goods;
import com.app.entity.Orders;
import com.app.service.OrderService;
import com.app.service.StoreService;
import com.app.util.ImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.log.Log;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class OrderAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jsonStr;
	private String uid;

	/**
	 * 订单类型  0---全部  1--未付款  2--代发货 3---待收货 4--待评价
	 * @return
	 */
	private String typeID;
	
	
	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}



	@Resource
	private OrderService orderService;
	@Resource
	private StoreService storeService;
	/**
	 * 获得订单
	 * @return
	 */
	public String getOrder(){
		System.out.println("dfs"+uid);
		List<Orders> list=orderService.searchOrders(uid,typeID);
		List<Goods> goods=new ArrayList<Goods>();
		ArrayList<String> goodsMerchant=new ArrayList<String>();
		//获得商品的信息
		for(Orders orders:list){
			goods.add(storeService.getGoods(orders.getGid()));
			
		}
		for(Goods goods2:goods){
			try {
				goods2.setPic(ImageUtils.encodeImage(goods2.getPic()));
				goodsMerchant.add(storeService.getGoodsMerChant(goods2.getSid()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(list!=null){
			setDatas(true,"ok","orders",list);
			data.put("goods", goods);
			data.put("merChantName", goodsMerchant);
		}else{
			setDatas(false,"订单为空","orders","订单为空");
		}
		return SUCCESS;
		
	}
	private class Zids{
		private int id;
	}
	/*
	 * 删除订单
	 */
	public String deleteOrders(){
		System.out.println("正在删除");
		Gson gson=new Gson();
		ArrayList<Integer> zids=gson.fromJson(jsonStr,new TypeToken<ArrayList<Integer>>(){}.getType());
//		ArrayList<Integer> list=new ArrayList<Integer>();
//		for(Zids xid:zids){
//			list.add(xid.id);
//		}
		
		int count=orderService.deleteOrders(zids);
		if(count==zids.size()){
			setDatas(true,"ok","ids",zids);
		}else{
			setDatas(false,"删除订单失败","ids",null);
		}
		return SUCCESS;
	}
	//批量添加订单
	public String ordering(){
		Gson gson=new Gson();
		System.out.println(jsonStr);
		List<Orders> orders=gson.fromJson(jsonStr, new TypeToken<ArrayList<Orders>>(){}.getType());
		List<Orders> list=orderService.addOrders(orders);
		if(orders!=null){
			setDatas(true,"ok","orders",orders);
		}else{
			setDatas(false,"添加订单失败","orders",null);
		}
		return SUCCESS;
	}
	
	/*
	 * 修改订单
	 * 买家
	 */
	public String updateOrders(){
		Gson gson=new Gson();
		List<Orders> orders=gson.fromJson(jsonStr, new TypeToken<ArrayList<Orders>>(){}.getType());
		try {
			orderService.updateOrders(orders);
			setDatas(true,"ok","orders",orders);
		} catch (Exception e) {
			// TODO: handle exception
			setDatas(false,"ok","orders",orders);
		}
		
		return SUCCESS;
	}
	
	/*
	 * 处理订单
	 * 卖家
	 */
	
	/*
	 * 查询订单
	 * 买家
	 * 卖家
	 */
	public String searchOrders(){
		
		return SUCCESS;
	}
	
	/*private class Orders{
		List<Orders> orders;
		public List<Orders> getOrders() {
			return orders;
		}
		public void setOrders(List<Orders> orders) {
			this.orders = orders;
		}
	}*/
}
