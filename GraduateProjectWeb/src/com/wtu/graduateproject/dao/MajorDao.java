package com.wtu.graduateproject.dao;

import java.util.List;

import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;


public interface MajorDao extends Dao{
	public Major getMajor(int MajorId);
	public List getAllMajor();
	
	public List getAllMajor(int institutionId);
}
