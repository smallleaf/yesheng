package com.colleage.assistant.action;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.Hibernate;
import org.json.JSONObject;

import com.colleage.assistant.action.base.BaseAction;
import com.colleage.assistant.po.domain.NewsKind;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.utils.MD5;
import com.mysql.jdbc.log.Log;


public class GetNewsKindAction extends BaseAction implements ServletRequestAware,
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
		List<NewsKind> newsList=mgr.getNewsKind();
		List<String> newsLiStrings=new ArrayList<String>();
		for(int i=2;i<newsList.size();i++){
			newsLiStrings.add(newsList.get(i).getName());
		}
		response.setContentType("text/html; charset=GBK");
		JSONObject 	jsonObject;
		if(newsList.size()>0){
			jsonObject=new JSONObject().put("newsKind", newsLiStrings);
		}
		else{
			jsonObject=new JSONObject().put("newsKind", "null");
		}
		
		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
