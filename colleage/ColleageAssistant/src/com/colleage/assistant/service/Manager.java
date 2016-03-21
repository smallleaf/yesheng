package com.colleage.assistant.service;

import java.io.Serializable;
import java.util.List;

public interface Manager {
	public List getObjects(Class class1);
	public Object getObject(Class class1,Serializable id);
	
	public void saveObject(Object o);
	
	public void removeObject(Class class1,Serializable id);
}
