package com.wtu.graduateproject.dao.hibernate;



import java.util.List;

import com.wtu.graduateproject.dao.CourceDao;
import com.wtu.graduateproject.dao.MajorDao;
import com.wtu.graduateproject.dao.StudentDao;
import com.wtu.graduateproject.po.domain.Cource;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;

public class CourceDaoHibernate extends BaseDaoHibernate implements CourceDao{

	@Override
	public void saveCource(Cource cource) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(cource);
	}

	@Override
	public Cource findCource(int courceId) {
		// TODO Auto-generated method stub
		return (Cource) getHibernateTemplate().find("from Cource where id=?",courceId).get(0);
	}

	@Override
	public List findAllCource(String teacherId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from Cource co where co.teacherId=?",teacherId);
	}

	@Override
	public void upadateCource(Cource cource) {
		// TODO Auto-generated method stub
		System.out.println("======");
		getHibernateTemplate().update(cource);
	}






}
