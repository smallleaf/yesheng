package com.colleage.assistant.action;

import java.io.File;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.Hibernate;
import org.json.JSONObject;


import com.colleage.assistant.action.base.BaseAction;
import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.PublicComment;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.po.domain.UserPublicNews;
import com.colleage.assistant.utils.MD5;
import com.colleage.assistant.utils.Utils;
import com.mysql.jdbc.Util;


public class SavePublicCommentAction extends BaseAction implements ServletRequestAware,
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
		System.out.println("正在保存评论信息");
		String username=request.getParameter("username");
		String comment_time=request.getParameter("comment_time");
		String comment_content=request.getParameter("comment_content");
		int news_id=Integer.valueOf(request.getParameter("news_id"));
		PublicComment publicComment=new PublicComment(username,comment_time,comment_content,news_id);
		
		publicComment.toString();
		mgr.savePublicComment(publicComment);
		response.setContentType("text/html; charset=GBK");
		JSONObject jsonObject=new JSONObject().put("savePublicComment", "true");
		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
