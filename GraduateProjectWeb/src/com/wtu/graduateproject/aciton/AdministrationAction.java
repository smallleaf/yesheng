package com.wtu.graduateproject.aciton;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


import com.google.gson.Gson;
import com.wtu.graduateproject.aciton.base.BaseAction;
import com.wtu.graduateproject.po.domain.Administration;
import com.wtu.graduateproject.po.domain.Cource;
import com.wtu.graduateproject.po.domain.DetailsStudent;
import com.wtu.graduateproject.po.domain.DetailsTeacher;
import com.wtu.graduateproject.po.domain.Institution;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.StudentClass;
import com.wtu.graduateproject.po.domain.Teacher;


public class AdministrationAction extends BaseAction implements ServletRequestAware,
ServletResponseAware{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7110664032673587111L;
	HttpServletRequest request;
	HttpServletResponse response;

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	/**
	 * ���������ʦ��Ϣ
	 */
	@SuppressWarnings("unchecked")
	public String getAllTeacher()
	{
		List<DetailsTeacher> detailsTeachers=wtumgr.getAllTeacher();
		if(detailsTeachers==null)
		{
			setDatas(false, "��������ʦ", "", "");
		}
		else
		{
			setDatas(true, "ok", "detailsTeachers", detailsTeachers);
		}
		return SUCCESS;
	}
	/**
	 * ������е�ѧԺרҵ�༶
	 */
	public String getAllInsAndMajor()
	{
		List<Institution> institutions=wtumgr.getAllInstitution();
		List<Major> majors=wtumgr.getAllMajor();
		List<StudentClass> studentClasses=wtumgr.getAllClass();
		setDatas(true, "ok", "institutions", institutions);
		data.put("majors", majors);
		data.put("studentClasses", studentClasses);
		return SUCCESS;
	}
	
	public String saveTeacher()
	{
		Gson gson=new Gson();
		String teacherJson=request.getParameter("teacherJson");
		Teacher teacher=gson.fromJson(teacherJson, Teacher.class);
		wtumgr.saveTeacher(teacher);
		setDatas(true, "ok", "", "");
		return SUCCESS;
	}
	public String saveStudent()
	{
		Gson gson=new Gson();
		String studentJson=request.getParameter("studentJson");
		System.out.println("===+"+studentJson);
		Student student=gson.fromJson(studentJson, Student.class);
		wtumgr.saveStudent(student);
		setDatas(true, "ok", "", "");
		return SUCCESS;
	}
	
	/**
	 * �������ѧ����Ϣ
	 */
	public String getAllStudent()
	{
		List<DetailsStudent> detailsStudents=wtumgr.getAllDetailsStudent();
		if(detailsStudents!=null)
		{
			setDatas(true, "ok", "detailsStudents", detailsStudents);
		}
		else {
			setDatas(false, "ok", "", "");
		}
		return SUCCESS;
	}
	
}
