package com.wtu.graduateproject.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wtu.graduateproject.dao.Dao;





public class BaseDaoHibernate extends HibernateDaoSupport implements Dao{
	protected final Log log =LogFactory.getLog(getClass());
	public List getObjects(Class class1) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().loadAll(class1);
	}

	public Object getObject(Class class1, Serializable id) {
		// TODO Auto-generated method stub
		Object object=getHibernateTemplate().get(class1, id);
		if(object==null){
			throw new ObjectRetrievalFailureException(class1, id);
		}
		return object;
	}

	public void saveObject(Object o) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(o);
	}

	public void removeObject(Class class1, Serializable id) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(getObject(class1, id));
	}

}
