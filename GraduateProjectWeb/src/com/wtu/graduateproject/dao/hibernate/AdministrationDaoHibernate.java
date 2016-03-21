package com.wtu.graduateproject.dao.hibernate;



import com.wtu.graduateproject.dao.AdministrationDao;
import com.wtu.graduateproject.dao.TeacherDao;
import com.wtu.graduateproject.po.domain.Administration;
import com.wtu.graduateproject.po.domain.Teacher;

public class AdministrationDaoHibernate extends BaseDaoHibernate implements AdministrationDao{

	@Override
	public Administration getAdministration(String id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(Administration.class, id);
	}






}
