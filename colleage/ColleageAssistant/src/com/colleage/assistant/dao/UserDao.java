package com.colleage.assistant.dao;

import com.colleage.assistant.po.domain.User;


public interface UserDao extends Dao{
	public void saveUser(User user);
	public User getUser(String userName);
	public void updateUser(User user);
}
