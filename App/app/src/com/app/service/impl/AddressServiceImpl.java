package com.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.BaseDAO;
import com.app.entity.Address;
import com.app.service.AddressService;

@Service("addressService")
public class AddressServiceImpl implements AddressService{
	@Resource
	private BaseDAO<Address> baseDao;
	
	@Override
	public Address addAddress(Address addr) {
		Address temp=null;
		if(addr!=null){
			baseDao.save(addr);
			//没有返回id
//			int id=(Integer) baseDao.save(addr);
//			temp=baseDao.get(Address.class, id);
		}
		return temp;
	}

	@Override
	public Address updateAddress(Address addr) {
		if(addr!=null){
			baseDao.update(addr);
			return baseDao.get(Address.class,addr.getId());
		}
		return null;
	}

	@Override
	public int deleteAddress(int id) {
		String hql="delete from Address where id=?";
		return baseDao.executeHql(hql, new Object[]{id});
	}

	@Override
	public List<Address> queryAddress(String uid) {
		String hql="from Address where uid = ?";
		List<Address> list=baseDao.find(hql, new Object[]{uid});
		return list;
	}
	
}
