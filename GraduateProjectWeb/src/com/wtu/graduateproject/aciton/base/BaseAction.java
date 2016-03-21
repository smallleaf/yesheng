package com.wtu.graduateproject.aciton.base;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.wtu.graduateproject.service.FacadeManager;


public class BaseAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected FacadeManager wtumgr;
	
	
	public void setWtumgr(FacadeManager wtumgr) {
		this.wtumgr = wtumgr;
	}

	protected Map<String,Object> data=new HashMap<String,Object>();

	public Map<String, Object> getData() {
		return data;
	}
	
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	/**
	 * 
	 * @param state ±íÊ¾ÏìÓ¦×´Ì¬
	 * @param desc
	 * @param key
	 * @param values
	 */
	protected void setDatas(boolean state,String desc,String key,Object values){
		data.clear();
		data.put("state",state);
		data.put("desc",desc);
		data.put(key,values);
	}
	
}
