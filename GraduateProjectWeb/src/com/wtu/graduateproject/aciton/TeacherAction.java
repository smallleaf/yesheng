package com.wtu.graduateproject.aciton;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wtu.graduateproject.aciton.base.BaseAction;
import com.wtu.graduateproject.po.domain.Administration;
import com.wtu.graduateproject.po.domain.ChooseCourceState;
import com.wtu.graduateproject.po.domain.Cource;
import com.wtu.graduateproject.po.domain.DetailsTeacher;
import com.wtu.graduateproject.po.domain.Institution;
import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.Teacher;


public class TeacherAction extends BaseAction implements ServletRequestAware,
ServletResponseAware{

	private String teacherId;
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
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
	
	/**
	 * 获得老师详细信息
	 */
	public String getdetailsTeacher()
	{
		DetailsTeacher detailsTeacher=wtumgr.getDetailsTeacher(teacherId);
		System.out.println(detailsTeacher.toString());
		setDatas(true, "ok", "detailsTeacher", detailsTeacher);
		return SUCCESS;
	}
	
	public String saveCource()
	{
		Gson gson=new Gson();
		String courceJson=request.getParameter("cource");
		if(courceJson!=null)
		{
			Cource cource=gson.fromJson(courceJson, Cource.class);
			int courceId=wtumgr.saveCource(cource);
			setDatas(true, "ok", "courceId", courceId);
			return SUCCESS;
		}
		setDatas(false, "ok", "", "");
		return SUCCESS;
	}
	
	public String findAllCource()
	{
		System.out.println("=====+"+teacherId);
		List<Cource> list=wtumgr.findAllCource(teacherId);
		setDatas(true, "ok", "courceList", list);
		return SUCCESS;
	}
	
	public String updateCource()
	{
		Gson gson=new Gson();
		String courceJson=request.getParameter("cource");
		if(courceJson!=null)
		{
			Cource cource=gson.fromJson(courceJson, Cource.class);
			wtumgr.upadateCource(cource);
			setDatas(true, "ok", "courceId", "");
			return SUCCESS;
		}
		setDatas(false, "ok", "", "");
		return SUCCESS;
	}
	public String getAllInstitution()
	{
		List<Institution> institutions=wtumgr.getAllInstitution();
		if(institutions!=null)
		{
			setDatas(true, "ok", "institutions", institutions);
		}
		else {
			setDatas(false, "ok", "", "");
		}
		return SUCCESS;
	}
	/**
	 * 登陆
	 */
	public String te_login()
	{
		Teacher teacher=new Teacher();
		String studentId=request.getParameter("studentId");
		String passWord=request.getParameter("passWord");
		teacher.setTeacherId(studentId);
		teacher.setPassword(passWord);
		if(wtumgr.isValiable(teacher,2))
		{
			setDatas(true, "teacher", "teacher", teacher);
			return SUCCESS;
		}
		else {
			setDatas(false, "teacher", "", "");
		}
		return SUCCESS;
	}
	/**
	 * 获得指定课题的选课信息
	 */
	public String getChooseCoureState()
	{
		int courceId=Integer.valueOf(request.getParameter("courceId"));
		List<ChooseCourceState> chooseCourceStates=wtumgr.getChooseCourceState(courceId);
		setDatas(true, "ok", "chooseCourceStates", chooseCourceStates);
		return SUCCESS;
	}
	
	public String verifySuccess()
	{
		System.out.println("===");
		String success=request.getParameter("success");
		System.out.println("=="+success);
		Gson gson=new Gson();
		List<Integer> chooseCourceIds=gson.fromJson(success, new  TypeToken<ArrayList<Integer>>(){}.getType());
		wtumgr.verifySuccess(chooseCourceIds);
		setDatas(true, "ok", "", "");
		return SUCCESS;
	}
}
