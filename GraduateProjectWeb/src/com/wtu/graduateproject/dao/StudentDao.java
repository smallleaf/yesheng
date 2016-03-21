package com.wtu.graduateproject.dao;

import java.util.List;

import com.wtu.graduateproject.po.domain.Student;


public interface StudentDao extends Dao{
	public Student getStudent(String StudentId);
	public List getAllStudent();
	public void saveStudent(Student student);
	/**
	 * ¸üĞÂÑ¡¿Î×´Ì¬
	 */
	public void updateStudent(Student student);
}
