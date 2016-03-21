package com.wtu.graduateproject.dao.hibernate;



import java.util.List;

import com.wtu.graduateproject.dao.MajorDao;
import com.wtu.graduateproject.dao.StudentDao;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;

public class MajorDaoHibernate extends BaseDaoHibernate implements MajorDao{

	@Override
	public Major getMajor(int MajorId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(Major.class, MajorId);
	}

	@Override
	public List getAllMajor() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from Major");
	}

	@Override
	public List getAllMajor(int institutionId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from Major where institutionId=?",institutionId);
	}





}
