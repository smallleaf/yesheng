package com.colleage.assistant.dao;

import java.util.List;

import com.colleage.assistant.po.domain.PublicComment;
import com.colleage.assistant.po.domain.ReplyComment;

public interface ReplyCommentDao extends Dao{
	public void saveReplyComment(ReplyComment replyComment);
	public List getReplyComments(int comment_id);
}
