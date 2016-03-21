package com.wtu.graduateproject.dao;

import java.util.List;

import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.StudentClass;


public interface StudentClassDao extends Dao{
	public StudentClass getStudentClass(int id);
	public List getAllStudentClass();
}
