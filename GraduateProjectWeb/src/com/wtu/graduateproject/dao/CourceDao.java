package com.wtu.graduateproject.dao;


import java.util.List;

import com.wtu.graduateproject.po.domain.Cource;


public interface CourceDao extends Dao{
	public void saveCource(Cource cource);
	public Cource findCource(int courceId);
	public List findAllCource(String teacherID);
	public void upadateCource(Cource cource);
}
