package com.wtu.graduateproject.dao.hibernate;



import java.util.List;

import com.wtu.graduateproject.dao.CourceDao;
import com.wtu.graduateproject.dao.MajorDao;
import com.wtu.graduateproject.dao.StudentClassDao;
import com.wtu.graduateproject.dao.StudentDao;
import com.wtu.graduateproject.po.domain.Cource;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.StudentClass;

public class StudentClassDaoHibernate extends BaseDaoHibernate implements StudentClassDao{

	@Override
	public StudentClass getStudentClass(int id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(StudentClass.class, id);
	}

	@Override
	public List getAllStudentClass() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from StudentClass");
	}





}
