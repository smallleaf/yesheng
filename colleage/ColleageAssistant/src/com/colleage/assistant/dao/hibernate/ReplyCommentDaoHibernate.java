package com.colleage.assistant.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import com.colleage.assistant.dao.NewsHotDao;
import com.colleage.assistant.dao.PublicCommentDao;
import com.colleage.assistant.dao.ReplyCommentDao;
import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.PublicComment;
import com.colleage.assistant.po.domain.ReplyComment;

public class ReplyCommentDaoHibernate extends BaseDaoHibernate implements ReplyCommentDao{

	@Override
	public void saveReplyComment(ReplyComment replyComment) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(replyComment);
	}

	@Override
	public List getReplyComments(int comment_id) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from ReplyComment rc where rc.comment_id=?",comment_id);
	}

}
