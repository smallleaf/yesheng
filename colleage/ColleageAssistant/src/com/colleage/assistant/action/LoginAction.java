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
import com.mysql.jdbc.log.Log;


public class LoginAction extends BaseAction implements ServletRequestAware,
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
		System.out.println("ÕýÔÚµÇÂ¼");
		String username=request.getParameter("loginusername");
		String password=request.getParameter("loginpassword");
		System.out.println("pass+"+password);
		System.out.println("paloginusernamess+"+username);
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		response.setContentType("text/html; charset=GBK");
		JSONObject jsonObject;
		if(mgr.login(user)){
			jsonObject=new JSONObject().put("login", true);
			System.out.println("µÇÂ¼³É¹¦");
		}
		else {
			System.out.println("µÇÂ¼Ê§°Ü");
			jsonObject=new JSONObject().put("login", false);
			
		}
		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
