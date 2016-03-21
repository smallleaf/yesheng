package com.wtu.graduateproject.dao.hibernate;



import java.util.List;

import com.wtu.graduateproject.dao.InstitutionDao;
import com.wtu.graduateproject.dao.MajorDao;
import com.wtu.graduateproject.dao.StudentDao;
import com.wtu.graduateproject.po.domain.Institution;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;

public class InstitutionDaoHibernate extends BaseDaoHibernate implements InstitutionDao{

	@Override
	public Institution getInstitution(int institutionId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(Institution.class, institutionId);
	}

	@Override
	public List getAllInstitution() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from Institution");
	}
}
