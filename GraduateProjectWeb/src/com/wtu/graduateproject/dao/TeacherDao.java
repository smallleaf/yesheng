package com.wtu.graduateproject.dao;

import java.util.List;

import com.wtu.graduateproject.po.domain.DetailsTeacher;
import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.Teacher;


public interface TeacherDao extends Dao{
	public Teacher getTeacher(String teacherId);
	
	/**
	 * ���ָ��ѧԺ��ȫ����ʦ
	 */
	public List getAllTeacher(int majorId);
	
	/**
	 * ���ȫ����ʦ
	 */
	public List getAllTeacher();
	
	/**
	 * �½���ʦ
	 */
	public void saveTeacher(Teacher teacher);
}
