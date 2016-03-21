package com.colleage.assistant.dao;

import java.util.List;

import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.User;


public interface NewsKindDao extends Dao{
	public List getNewsKindList();
}
