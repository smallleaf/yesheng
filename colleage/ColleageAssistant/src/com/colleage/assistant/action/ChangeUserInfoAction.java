package com.colleage.assistant.action;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.Hibernate;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;

import com.colleage.assistant.action.base.BaseAction;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.utils.MD5;


public class ChangeUserInfoAction extends BaseAction implements ServletRequestAware,
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
	
	public String execute() throws Exception{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String userpicture=request.getParameter("picture");
		String university=request.getParameter("university");
		System.out.println("大学"+university);
		String major=request.getParameter("major");
		String address=request.getParameter("address");
		String job=request.getParameter("job");
		String hobby1=request.getParameter("hobby1");
		String hobby2=request.getParameter("hobby2");
		String hobby3=request.getParameter("hobby3");
		String associatition=request.getParameter("associatition");
		User user=new User();
		user.setUsername(username);
			Blob blob=Hibernate.createBlob(userpicture.getBytes());
			user.setPassword(MD5.stringMd5(password));
			user.setPicture(blob);
			user.setUniversity(university);
			user.setMajor(major);
			user.setAddress(address);
			user.setJob(job);
			user.setHobby1(hobby1);
			user.setHobby2(hobby2);
			user.setHobby3(hobby3);
			user.setAssociatition(associatition);
			System.out.println("正在修改。。。。。");
			System.out.println(user.toString());
			
		mgr.updateUser(user);
		return SUCCESS;
	}
}
