package com.colleage.assistant.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import com.colleage.assistant.dao.NewsHotDao;
import com.colleage.assistant.po.domain.NewsHot;

public class NewsHotDaoHibernate extends BaseDaoHibernate implements NewsHotDao{

	@Override
	public List getNewsHotList(int newsKind) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from NewsHot newsHot where newsHot.newsKindId=?",newsKind);
	}

	@Override
	public void saveNewsHot(NewsHot newsHot) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(newsHot);
	}

	@Override
	public void updateNewsHot(int id, int commentCounts) {
		// TODO Auto-generated method stub
		NewsHot newsHot=getHibernateTemplate().get(NewsHot.class,id);
		newsHot.setCommentCounts(commentCounts);
		getHibernateTemplate().update(newsHot);
	}



}
