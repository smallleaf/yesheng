package com.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.omg.CORBA.BAD_CONTEXT;
import org.springframework.stereotype.Service;

import com.app.dao.BaseDAO;
import com.app.entity.User;
import com.app.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	private BaseDAO<User> baseDao;
	
	
	@Override
	public boolean signup(User user){
		String hql="from User where phone = ?";
		if(baseDao.get(hql,new Object[]{user.getPhone()})==null){
			baseDao.save(user);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public User signin(User user){
		String hql="from User where phone =? and pwd = ?";
		User t_user=baseDao.get(hql, new Object[]{user.getPhone(),user.getPwd()});
		if(t_user!=null){
			return t_user;
		}
		return null;
	}
	
	@Override
	public void saveUser(User user) {
		baseDao.saveOrUpdate(user);
	//	baseDao.save(user);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		baseDao.update(user);
	}

	@Override
	public List<User> getUserByPhone(String phone) {
		String hql="from User where phone =?";
		List<User> list=baseDao.find(hql, new Object[]{phone});
		return list;
	}
	
	/**
	 * 判断用户输入的号码是否已经注册
	 */
	@Override
	public boolean isPhoneExist(String phone){
		List<User> list=getUserByPhone(phone);
		if(list==null || list.size()==0){
			return false;
		}else{
			return true;
		}
	}
	
	/*
	 * 号码是否激活
	 */
	@Override
	public boolean isPhoneActivated(String phone){
		List<User> list=getUserByPhone(phone);
		if(list==null || list.size()==0){
			return false;
		}else{
			if(list.get(0).getActivate()==0)
				return false;
			return true;
		}
	}
	
	@Override
	public boolean verify(String phone,String authcode){
		List<User> list=getUserByPhone(phone);
		if(list==null || list.size()==0){
			return false;
		}else{
			if(authcode.equals(list.get(0).getAuthcode()))
				return true;
			return false;
		}
	}
}
