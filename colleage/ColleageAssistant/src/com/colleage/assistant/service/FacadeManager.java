package com.colleage.assistant.service;


import java.util.Date;
import java.util.List;

import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.PublicComment;
import com.colleage.assistant.po.domain.ReplyComment;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.po.domain.UserPublicNews;



public interface FacadeManager {
	public boolean registerUser(User user);
	public boolean login(User user);
	public void updateUser(User user);
	public User getUser(String ueseName);
	public List getNewsHotList(int newsKind);
	public void saveNewsHot(NewsHot newsHot);
	public List getNewsKind();
	public void saveUserPublicNews(UserPublicNews userPublicNews);
	public void updateNewsHot(int id,int commentCounts);
	public void savePublicComment(PublicComment publicComment);
	public List publicComments(int news_id);
	public void saveReplyComment(ReplyComment replyComment);
	public List getRelpyComments(int comment_id);
}
