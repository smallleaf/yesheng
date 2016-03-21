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
import com.colleage.assistant.utils.Utils;
import com.mysql.jdbc.Util;


public class GetUserInfoAction extends BaseAction implements ServletRequestAware,
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
		System.out.println("正在获取信息");
		String username=request.getParameter("username");
		System.out.println(username);
		if(username!=null){
			User user=mgr.getUser(username);
			System.out.println(user.toString());
			response.setContentType("text/html; charset=GBK");
			JSONObject jsonObject;
			jsonObject=new JSONObject().put("picture", Utils.blobToString(user.getPicture()));
			jsonObject.put("university", user.getUniversity()).
						put("major", user.getMajor()).
						put("address", user.getAddress())
						.put("job", user.getJob())
						.put("hobby1", user.getHobby1())
						.put("hobby2", user.getHobby2())
						.put("hobby3", user.getHobby3())
						.put("associatition", user.getAssociatition());
			response.getWriter().println(jsonObject.toString());
		}
		return SUCCESS;
	}
}
