package com.wtu.graduateproject.dao;

import java.util.List;

import com.wtu.graduateproject.po.domain.DetailsTeacher;
import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.Teacher;


public interface TeacherDao extends Dao{
	public Teacher getTeacher(String teacherId);
	
	/**
	 * 获得指定学院的全部老师
	 */
	public List getAllTeacher(int majorId);
	
	/**
	 * 获得全部老师
	 */
	public List getAllTeacher();
	
	/**
	 * 新建老师
	 */
	public void saveTeacher(Teacher teacher);
}
