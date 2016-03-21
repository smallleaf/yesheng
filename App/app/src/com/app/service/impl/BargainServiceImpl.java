package com.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.BaseDAO;
import com.app.entity.Bargain;
import com.app.service.BargainService;

@Service("bargainService")
public class BargainServiceImpl implements BargainService{
	@Resource
	private BaseDAO baseDAO;
	
	@Override
	public List<Bargain> getBargains() {
		String hql="from Bargain";
		List<Bargain> list=baseDAO.find(hql);
		return list;
	}
	
}
