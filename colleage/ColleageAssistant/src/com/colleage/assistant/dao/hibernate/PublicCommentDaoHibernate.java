package com.colleage.assistant.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import com.colleage.assistant.dao.NewsHotDao;
import com.colleage.assistant.dao.PublicCommentDao;
import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.PublicComment;

public class PublicCommentDaoHibernate extends BaseDaoHibernate implements PublicCommentDao{

	@Override
	public void savePublicComment(PublicComment publicComment) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(publicComment);
		}

	@Override
	public List publicComments(int news_id) {
		// TODO Auto-generated method stubnew_id
		System.out.println("µ½µ×ÊÇÊ²Ã´´íÎóÄØ");
		return getHibernateTemplate().find("from PublicComment pc where pc.new_id=?",news_id);
	}
}
