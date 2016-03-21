package com.colleage.assistant.service.impl;


import java.util.List;

import com.colleage.assistant.dao.NewsHotDao;
import com.colleage.assistant.dao.NewsKindDao;
import com.colleage.assistant.dao.PublicCommentDao;
import com.colleage.assistant.dao.ReplyCommentDao;
import com.colleage.assistant.dao.UserDao;
import com.colleage.assistant.dao.UserPublicNewsDao;
import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.PublicComment;
import com.colleage.assistant.po.domain.ReplyComment;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.po.domain.UserPublicNews;
import com.colleage.assistant.service.BaseManager;
import com.colleage.assistant.service.FacadeManager;
import com.colleage.assistant.utils.MD5;



public class FacadeManagerImpl extends BaseManager implements FacadeManager{
	private UserDao userDao;
	private NewsHotDao newsHotDao;
	private NewsKindDao newsKindDao;
	private UserPublicNewsDao userPublicNewsDao;
	private PublicCommentDao publicCommentDao;
	private ReplyCommentDao replyCommentDao;





	public void setReplyCommentDao(ReplyCommentDao replyCommentDao) {
		this.replyCommentDao = replyCommentDao;
	}




	public void setPublicCommentDao(PublicCommentDao publicCommentDao) {
		this.publicCommentDao = publicCommentDao;
	}




	public void setUserPublicNewsDao(UserPublicNewsDao userPublicNewsDao) {
		this.userPublicNewsDao = userPublicNewsDao;
	}




	public void setNewsKindDao(NewsKindDao newsKindDao) {
		this.newsKindDao = newsKindDao;
	}




	public void setNewsHotDao(NewsHotDao newsHotDao) {
		this.newsHotDao = newsHotDao;
	}




	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}




	@Override
	public boolean registerUser(User user) {
		// TODO Auto-generated method stub
		User user2=userDao.getUser(user.getUsername());
		if(user2==null){
			userDao.saveUser(user);
			return true;
		}
		else {
			return false;
		}
	}




	@Override
	public boolean login(User user) {
		// TODO Auto-generated method stub
		User user2=userDao.getUser(user.getUsername());
		if(user2!=null&&user2.getPassword().equals(MD5.stringMd5(user.getPassword())))
		{
			return true;
		}
		return false;
	}




	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao.updateUser(user);
	}




	@Override
	public User getUser(String userName) {
		// TODO Auto-generated method stub
		return userDao.getUser(userName);
	}




	@Override
	public List getNewsHotList(int newsKind) {
		// TODO Auto-generated method stub
		return newsHotDao.getNewsHotList(newsKind);
	}




	@Override
	public void saveNewsHot(NewsHot newsHot) {
		// TODO Auto-generated method stub
		newsHotDao.saveNewsHot(newsHot);
	}




	@Override
	public List getNewsKind() {
		// TODO Auto-generated method stub
		return newsKindDao.getNewsKindList();
	}




	@Override
	public void saveUserPublicNews(UserPublicNews userPublicNews) {
		// TODO Auto-generated method stub
		userPublicNewsDao.saveUserPublic(userPublicNews);
	}




	@Override
	public void updateNewsHot(int id, int commentCounts) {
		// TODO Auto-generated method stub
		newsHotDao.updateNewsHot(id, commentCounts);
	}




	@Override
	public void savePublicComment(PublicComment publicComment) {
		// TODO Auto-generated method stub
		publicCommentDao.savePublicComment(publicComment);
	}




	@Override
	public List publicComments(int news_id) {
		// TODO Auto-generated method stub
		return publicCommentDao.publicComments(news_id);
	}




	@Override
	public void saveReplyComment(ReplyComment replyComment) {
		// TODO Auto-generated method stub
		replyCommentDao.saveReplyComment(replyComment);
	}




	@Override
	public List getRelpyComments(int comment_id) {
		// TODO Auto-generated method stub
		return replyCommentDao.getReplyComments(comment_id);
	}


}
