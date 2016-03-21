package com.wtu.graduateproject.aciton;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


import com.google.gson.Gson;
import com.wtu.graduateproject.aciton.base.BaseAction;
import com.wtu.graduateproject.dao.hibernate.ChooseCourceDaoHibernate;
import com.wtu.graduateproject.dao.hibernate.StudentDaoHibernate;
import com.wtu.graduateproject.po.domain.Administration;
import com.wtu.graduateproject.po.domain.ChooseCource;
import com.wtu.graduateproject.po.domain.Cource;
import com.wtu.graduateproject.po.domain.DetailsStudent;
import com.wtu.graduateproject.po.domain.DetailsTeacher;
import com.wtu.graduateproject.po.domain.Institution;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.Teacher;


public class StudentAction extends BaseAction implements ServletRequestAware,
ServletResponseAware{
	
	private String studentId;
	private String passWord;
	
	
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

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
	
	
	public String login(){
		Student student=wtumgr.getStudent(studentId);
		if(student!=null&&student.getPassword().equals(passWord))
		{
			setDatas(true, "ok", "student", student);
			return SUCCESS;
		}
		else {
			setDatas(false, "ok", "", "");
		}
		return SUCCESS;
	}
	
	public String login_wtu(){
		System.out.println("==���ڵ�½");
		String value=request.getParameter("user_role");
		System.out.println(studentId+"===="+passWord+"==="+value);
		if(value.equals("0"))
		{
			Administration administration=new Administration();
			administration.setId(studentId);
			administration.setPassword(passWord);
			if(wtumgr.isValiable(administration,1))
			{
				setDatas(true, "administration", "administration", administration);
				return SUCCESS;
			}
		}
		else
		{
			Teacher teacher=new Teacher();
			teacher.setTeacherId(studentId);
			teacher.setPassword(passWord);
			if(wtumgr.isValiable(teacher,2))
			{
				setDatas(true, "teacher", "teacher", teacher);
				return SUCCESS;
			}
		}
		System.out.println("false");
		setDatas(false, "teacher", "", "");
		return SUCCESS;
		
	}
	public String modify()
	{
		System.out.println("==�����޸�");
		return SUCCESS;
	}
	
	/**
	 * ���ѧԺרҵ
	 */
	public String getInsAndMajor()
	{
		List<Institution> institutions=wtumgr.getAllInstitution();
		List<Major> majors=wtumgr.getAllMajor();
		setDatas(true, "ok", "institutions", institutions);
		data.put("institutions", majors);
		return SUCCESS;
	}
	
	/**
	 * ���������ʦ
	 */
	
	public String getAllTeacher()
	{
		int majorId=Integer.valueOf(request.getParameter("majorId"));
		List<Teacher> teachers=wtumgr.getAllTeacher(majorId);
		setDatas(true, "ok", "teachers", teachers);
		return SUCCESS;
	}
	
	/**
	 * ���ָ��ѧԺ����ʦ
	 */
	public String getInsTeacher()
	{
		int institutionId=Integer.valueOf(request.getParameter("institutionId"));
		System.out.println("===="+institutionId);
		List<Major> majors=wtumgr.getAllMajor(institutionId);
		List<Teacher> teachers=wtumgr.getAllInTeacher(institutionId);
		setDatas(true, "ok", "majors", majors);
		data.put("teachers", teachers);
		return SUCCESS;
	}
	
	/**
	 * ����ѡ��״̬ �Լ������û���Ϣ
	 */
	public String chooseCource()
	{
		Gson gson=new Gson();
		ChooseCource chooseCource=gson.fromJson(request.getParameter("chooseCource"), ChooseCource.class);
		wtumgr.saveChooseCource(chooseCource);
		//�����û���ѡ��״̬ 1��ʾ��һ־Ը 2���� �ڶ�־Ը
		String whichVolunteer=request.getParameter("volunteer");
		Student student=wtumgr.getStudent(chooseCource.getStudentId());
		if(whichVolunteer.equals("1"))
		{
			student.setFirst_volunteer(chooseCource.getCourceId());
		}
		else {
			student.setSecond_volunteer(chooseCource.getCourceId());
		}
		wtumgr.updateStudent(student);
		setDatas(true, "ok", "", "");
		return SUCCESS;
	}
	
	/**\
	 * 
	 * @returnsfsd
	 */
	public String getCourceAndTeacher()
	{
		String teacherId=request.getParameter("teacherId");
		String courceId=request.getParameter("courceId");
		
		DetailsTeacher detailsTeacher=wtumgr.getDetailsTeacher(teacherId);
		Cource cource=wtumgr.findCource(Integer.valueOf(courceId));
		setDatas(true, "ok", "detailsTeacher", detailsTeacher);
		data.put("cource", cource);
		return SUCCESS;
	}
	
	/**
	 * ���ָ��ѧ����ϸ��Ϣ
	 */
	
	public String getStudentInfo()
	{
		DetailsStudent detailsStudent=wtumgr.getDetailsStudent(studentId);
		setDatas(true, "ok", "detailsStudent", detailsStudent);
		System.out.println(detailsStudent.toString());
		return SUCCESS;
	}
	/**
	 * ɾ��ѧ��ѡ�β������û���ѡ��
	 */
	public String deleteCourcAndUpdateStudent()
	{
		int chooseCourceId=Integer.valueOf(request.getParameter("chooseCourceId"));
		int whichVo=Integer.valueOf(request.getParameter("whichVo"));
		Student student=wtumgr.getStudent(studentId);
		if(whichVo==1)
		{
			student.setFirst_volunteer(0);
		}
		else
		{
			student.setSecond_volunteer(0);
		}
		wtumgr.deleteCource(chooseCourceId,student);
		setDatas(true, "ok", "", "");
		return SUCCESS;
	}
	
	public String updateStudent()
	{
		String phone=request.getParameter("phone");
		Student student=wtumgr.getStudent(studentId);
		student.setTel(phone);
		wtumgr.updateStudent(student);
		setDatas(true, "ok", "", "");
		return SUCCESS;
	}
}
