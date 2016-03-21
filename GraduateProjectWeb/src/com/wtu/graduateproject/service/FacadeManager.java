package com.wtu.graduateproject.service;


import java.util.Date;
import java.util.List;

import com.wtu.graduateproject.po.domain.ChooseCource;
import com.wtu.graduateproject.po.domain.Cource;
import com.wtu.graduateproject.po.domain.DetailsStudent;
import com.wtu.graduateproject.po.domain.DetailsTeacher;
import com.wtu.graduateproject.po.domain.Institution;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.Teacher;



public interface FacadeManager {
	/**
	 * 验证用户登陆
	 */
	public boolean isValiable(Object object,int id);
	
	/**
	 * 获得老师详细信息
	 */
	public DetailsTeacher getDetailsTeacher(String teacherId);
	/**
	 * 提交毕业课题
	 */
	public int saveCource(Cource cource);
	/**
	 * 找到所有某位老师的课题
	 */
	public List<Cource> findAllCource(String teacherID);
	
	public void upadateCource(Cource cource);
	/**
	 * 获得学院
	 */
	public List getAllInstitution();
	/**
	 * 获得专业
	 */
	public List getAllMajor();
	/**
	 * 获得指定专业的全部老师
	 */
	public List getAllTeacher(int majorId);
	public List getAllClass();
	
	/**
	 * 获得全部老师的详细信息
	 */
	public List getAllTeacher();
	/**
	 * 新建老师
	 */
	public void saveTeacher(Teacher teacher);
	/**
	 * 获得所有学生详细信息
	 */
	public List getAllDetailsStudent();
	public void saveStudent(Student student);
	
	/**
	 * 获得指定学院的所有老师信息
	 */
	public List getAllInTeacher(int institutionId);
	/**
	 * 获得指定学院的专业
	 */
	public List getAllMajor(int institutionId);
	
	public Student getStudent(String StudentId);
	/**
	 * 提交选课状态
	 */
	public void saveChooseCource(ChooseCource chooseCource);
	public void updateStudent(Student student);
	public Cource findCource(int courceId);
	/**
	 * 获得指定学生的详细信息
	 */
	public DetailsStudent getDetailsStudent(String studentId);
	
	/**
	 * 删除选课 跟新 用户更
	 */
	
	public void deleteCource(int chooseCourceId,Student student);
	/**
	 * 获得用户的选课表
	 */
	public List getChooseCource(String studenId);
	
	public List getChooseCourceState(int courceId);
	
	/**
	 * 课程审核通过
	 */
	public void verifySuccess(List<Integer> chooseIds);
}
