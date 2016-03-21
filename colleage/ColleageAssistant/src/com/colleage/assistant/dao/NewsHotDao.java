package com.colleage.assistant.dao;

import java.util.List;

import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.User;


public interface NewsHotDao extends Dao{
	public List getNewsHotList(int kindId);
	public void saveNewsHot(NewsHot newsHot);
	public void updateNewsHot(int id,int commentCounts);
}
