package com.colleage.assistant.action;

import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.Hibernate;
import org.json.JSONObject;

import com.colleage.assistant.action.base.BaseAction;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.utils.MD5;


public class RegisterAction extends BaseAction implements ServletRequestAware,
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
		System.out.println("ÕýÔÚ×¢²á...");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String picture=request.getParameter("picture");
		User user=new User();
		user.setUsername(username);
		user.setPassword(MD5.stringMd5(password));
		if(picture!=null){
			System.out.println("Í¼Æ¬²¹Î»¿Õ");
			Blob blob=Hibernate.createBlob(picture.getBytes());
			user.setPicture(blob);
		}
		else {
			Blob blob=Hibernate.createBlob("null".getBytes());
			user.setPicture(blob);
		}
		user.setAddress(" ");
		user.setAssociatition(" ");
		user.setHobby1(" ");
		user.setHobby2(" ");
		user.setHobby3(" ");
		user.setJob(" ");
		user.setMajor(" ");
		user.setUniversity(" ");
		response.setContentType("text/html; charset=GBK");
		JSONObject jsonObject;
		if(mgr.registerUser(user)){
			
			jsonObject=new JSONObject().put("register", true);
		}
		else {
			jsonObject=new JSONObject().put("register", false);
		}
		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
		
		
	}
}
