package com.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.BaseDAO;
import com.app.entity.Ad;
import com.app.service.AdService;

@Service("adService")
public class AdServiceImpl implements AdService{
	@Resource
	private BaseDAO<Ad> baseDao;

	@Override
	public List<Ad> getAd() {
		//返回广告的id和图片地址
		List<Ad> list=null;
		String hql="from Ad ";
		list=baseDao.find(hql);
		return list;
	}
}
