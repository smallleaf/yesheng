package com.colleage.assistant.dao.hibernate;

import com.colleage.assistant.dao.UserDao;
import com.colleage.assistant.po.domain.User;

public class UserDaoHibernate extends BaseDaoHibernate implements UserDao{

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(user);
	}

	@Override
	public User getUser(String userName) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().get(User.class, userName);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(user);
		}

}
