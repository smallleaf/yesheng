package com.colleage.assistant.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import com.colleage.assistant.dao.NewsHotDao;
import com.colleage.assistant.dao.NewsKindDao;
import com.colleage.assistant.dao.UserPublicNewsDao;
import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.UserPublicNews;

public class UserPublicNewsDaoHibernate extends BaseDaoHibernate implements UserPublicNewsDao{

	@Override
	public void saveUserPublic(UserPublicNews userPublicNews) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(userPublicNews);
	}


}
