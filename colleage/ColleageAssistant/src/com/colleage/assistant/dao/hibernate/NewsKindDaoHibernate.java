package com.colleage.assistant.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import com.colleage.assistant.dao.NewsHotDao;
import com.colleage.assistant.dao.NewsKindDao;
import com.colleage.assistant.po.domain.NewsHot;

public class NewsKindDaoHibernate extends BaseDaoHibernate implements NewsKindDao{

	@Override
	public List getNewsKindList() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from NewsKind");
	}

	


}
