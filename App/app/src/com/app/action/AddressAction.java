package com.app.action;

import java.util.List;

import javax.annotation.Resource;

import com.app.entity.Address;
import com.app.service.AddressService;
import com.google.gson.Gson;

public class AddressAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private AddressService addressService;
	
	private String jsonStr;
	private int id;
	private String uid;
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
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

	public String addAddress(){
		Gson gson=new Gson();
		Address addr=null;
		if(jsonStr!=null && jsonStr.length()>0){
			addr=gson.fromJson(jsonStr,Address.class);
			addressService.addAddress(addr);
			setDatas(true,"ok","address","ok");
			return SUCCESS;
		}
//			Address temp=null;
//			temp=addressService.addAddress(addr);
//			System.out.println(temp);
//			if(temp!=null){
//				setDatas(true,"ok","address",temp);
//			}else{
//				setDatas(false,"保存地址失败","address",null);
//			}
//		}else{
//			setDatas(false,"数据为空","address",null);
//		}
		return SUCCESS;
	}
	
	public String updateAddress(){
		Gson gson=new Gson();
		Address addr=gson.fromJson(jsonStr,Address.class);
		Address temp=addressService.updateAddress(addr);
		if(temp!=null){
			setDatas(true,"ok","address",temp);
		}else{
			setDatas(false,"更新地址失败","address",null);
		}
		return SUCCESS;
	}
	
	public String deleteAddress(){
		if(addressService.deleteAddress(id)>0){
			setDatas(true,"ok","id",id);
		}else{
			setDatas(false,"删除失败","id",null);
		}
		return SUCCESS;
	}
	
	public String queryAddress(){
		List<Address> list=addressService.queryAddress(uid);
		if(list!=null && list.size()>0){
			setDatas(true,"ok","addrs",list);
		}else{
			setDatas(false,"查找失败","addrs",null);
		}
		return SUCCESS;
	}
}
