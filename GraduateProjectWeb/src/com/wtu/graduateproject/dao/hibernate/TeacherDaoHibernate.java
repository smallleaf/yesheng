package com.wtu.graduateproject.dao.hibernate;



import java.util.List;

import com.wtu.graduateproject.dao.TeacherDao;
import com.wtu.graduateproject.po.domain.DetailsTeacher;
import com.wtu.graduateproject.po.domain.Institution;
import com.wtu.graduateproject.po.domain.Teacher;

public class TeacherDaoHibernate extends BaseDaoHibernate implements TeacherDao{

	@Override
	public Teacher getTeacher(String teacherId) {
		// TODO Auto-generated method stub
		List<Teacher> list=(List<Teacher>) getHibernateTemplate().find("from Teacher where teacherId=?",teacherId);
		if(list.size()>0)
		{
			return list.get(0);
		}
		else
		{
			return null;
		}
	}

	@Override
	public List getAllTeacher(int majorId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from Teacher where majorId=?",majorId);
	}

	@Override
	public List getAllTeacher() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from Teacher");
	}

	@Override
	public void saveTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(teacher);
	}

}
