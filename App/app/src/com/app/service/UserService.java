package com.app.service;

import java.util.List;

import com.app.entity.User;

public interface UserService {
	public boolean signup(User user);
	public User signin(User user);
	
	public void saveUser(User user);
	public void updateUser(User user);
	public List<User> getUserByPhone(String phone);
	
	public boolean isPhoneExist(String phone);
	public boolean isPhoneActivated(String phone);
	public boolean verify(String phone,String authcode);
}
