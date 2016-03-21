package com.app.action;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Map<String,Object> data=new HashMap<String,Object>();

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	protected void setDatas(boolean state,String desc,String key,Object values){
		data.clear();
		data.put("state",state);
		data.put("desc",desc);
		data.put(key,values);
	}
}
