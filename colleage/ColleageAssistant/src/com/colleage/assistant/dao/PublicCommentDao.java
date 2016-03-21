package com.colleage.assistant.dao;

import java.util.List;

import com.colleage.assistant.po.domain.PublicComment;

public interface PublicCommentDao extends Dao{
	public void savePublicComment(PublicComment publicComment);
	public List publicComments(int news_id);
}
