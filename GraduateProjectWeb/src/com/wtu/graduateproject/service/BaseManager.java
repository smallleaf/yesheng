package com.wtu.graduateproject.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wtu.graduateproject.dao.Dao;




public class BaseManager implements Manager{

	protected Dao dao=null;
	protected final Log log=LogFactory.getLog(getClass());
	
	public void setDao(Dao dao){
		this.dao=dao;
	}

	public List getObjects(Class class1) {
		// TODO Auto-generated method stub
		return dao.getObjects(class1);
	}

	public Object getObject(Class class1, Serializable id) {
		// TODO Auto-generated method stub
		return dao.getObject(class1, id);
	}

	public void saveObject(Object o) {
		// TODO Auto-generated method stub
		dao.saveObject(o);
	}

	public void removeObject(Class class1, Serializable id) {
		// TODO Auto-generated method stub
		dao.removeObject(class1, id);
	}

}
