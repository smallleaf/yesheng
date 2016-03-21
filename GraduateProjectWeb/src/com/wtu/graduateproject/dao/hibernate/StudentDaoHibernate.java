package com.wtu.graduateproject.dao.hibernate;



import java.util.List;

import com.wtu.graduateproject.dao.StudentDao;
import com.wtu.graduateproject.po.domain.Student;

public class StudentDaoHibernate extends BaseDaoHibernate implements StudentDao{

	@Override
	public Student getStudent(String StudentId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(Student.class, StudentId);
	}

	@Override
	public List getAllStudent() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from Student");
	}

	@Override
	public void saveStudent(Student student) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(student);
	}

	@Override
	public void updateStudent(Student student) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(student);
	}




}
