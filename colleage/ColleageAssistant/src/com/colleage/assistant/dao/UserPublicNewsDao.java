package com.colleage.assistant.dao;

import java.util.List;

import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.po.domain.UserPublicNews;


public interface UserPublicNewsDao extends Dao{
	public void saveUserPublic(UserPublicNews userPublicNews);
}
