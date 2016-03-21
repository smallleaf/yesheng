package com.wtu.graduateproject.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao {
	//返回全部物品
	@SuppressWarnings("rawtypes")
	public List getObjects(Class class1);
	public Object getObject(Class class1,Serializable id);
	
	public void saveObject(Object o);
	
	public void removeObject(Class class1,Serializable id);
}
