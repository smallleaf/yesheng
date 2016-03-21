package com.wtu.graduateproject.dao;

import java.util.List;

import com.wtu.graduateproject.po.domain.ChooseCource;

public interface ChooseCourceDao extends Dao {
	public void saveChooseCource(ChooseCource chooseCource);
	
	public List getChooseCource(String studenId);
	public List getChooseCource(int courceId);
	
	public void deleteCource(int chooseCourceId);
	
	public ChooseCource getChooseCourceFromId(int id);
}
