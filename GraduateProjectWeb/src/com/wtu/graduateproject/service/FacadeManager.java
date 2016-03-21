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
	 * ��֤�û���½
	 */
	public boolean isValiable(Object object,int id);
	
	/**
	 * �����ʦ��ϸ��Ϣ
	 */
	public DetailsTeacher getDetailsTeacher(String teacherId);
	/**
	 * �ύ��ҵ����
	 */
	public int saveCource(Cource cource);
	/**
	 * �ҵ�����ĳλ��ʦ�Ŀ���
	 */
	public List<Cource> findAllCource(String teacherID);
	
	public void upadateCource(Cource cource);
	/**
	 * ���ѧԺ
	 */
	public List getAllInstitution();
	/**
	 * ���רҵ
	 */
	public List getAllMajor();
	/**
	 * ���ָ��רҵ��ȫ����ʦ
	 */
	public List getAllTeacher(int majorId);
	public List getAllClass();
	
	/**
	 * ���ȫ����ʦ����ϸ��Ϣ
	 */
	public List getAllTeacher();
	/**
	 * �½���ʦ
	 */
	public void saveTeacher(Teacher teacher);
	/**
	 * �������ѧ����ϸ��Ϣ
	 */
	public List getAllDetailsStudent();
	public void saveStudent(Student student);
	
	/**
	 * ���ָ��ѧԺ��������ʦ��Ϣ
	 */
	public List getAllInTeacher(int institutionId);
	/**
	 * ���ָ��ѧԺ��רҵ
	 */
	public List getAllMajor(int institutionId);
	
	public Student getStudent(String StudentId);
	/**
	 * �ύѡ��״̬
	 */
	public void saveChooseCource(ChooseCource chooseCource);
	public void updateStudent(Student student);
	public Cource findCource(int courceId);
	/**
	 * ���ָ��ѧ������ϸ��Ϣ
	 */
	public DetailsStudent getDetailsStudent(String studentId);
	
	/**
	 * ɾ��ѡ�� ���� �û���
	 */
	
	public void deleteCource(int chooseCourceId,Student student);
	/**
	 * ����û���ѡ�α�
	 */
	public List getChooseCource(String studenId);
	
	public List getChooseCourceState(int courceId);
	
	/**
	 * �γ����ͨ��
	 */
	public void verifySuccess(List<Integer> chooseIds);
}
