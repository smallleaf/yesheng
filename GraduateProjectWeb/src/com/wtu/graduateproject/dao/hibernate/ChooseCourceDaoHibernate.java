package com.wtu.graduateproject.dao.hibernate;



import java.util.List;

import com.wtu.graduateproject.dao.ChooseCourceDao;
import com.wtu.graduateproject.dao.CourceDao;
import com.wtu.graduateproject.dao.MajorDao;
import com.wtu.graduateproject.dao.StudentDao;
import com.wtu.graduateproject.po.domain.ChooseCource;
import com.wtu.graduateproject.po.domain.Cource;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;

public class ChooseCourceDaoHibernate extends BaseDaoHibernate implements ChooseCourceDao{

	@Override
	public void saveChooseCource(ChooseCource chooseCource) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(chooseCource);
	}

	@Override
	public List getChooseCource(String studenId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from ChooseCource where studentId=?",studenId);
	}

	@Override
	public void deleteCource(int chooseCourceId) {
		// TODO Auto-generated method stub
		ChooseCource chooseCource=new ChooseCource();
		chooseCource.setId(chooseCourceId);
		getHibernateTemplate().delete(chooseCource);
	}

	@Override
	public List getChooseCource(int courceId) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from ChooseCource where courceId=?",courceId);
	}

	@Override
	public ChooseCource getChooseCourceFromId(int id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(ChooseCource.class, id);
	}




}
