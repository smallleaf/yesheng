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
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.po.domain.UserPublicNews;
import com.colleage.assistant.utils.MD5;
import com.colleage.assistant.utils.Utils;
import com.mysql.jdbc.Util;


public class SaveUserPublicNewsAction extends BaseAction implements ServletRequestAware,
ServletResponseAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7110664032673587111L;
	HttpServletRequest request;
	HttpServletResponse response;
	private File file;
	private String fileContentType;
	private String fileFileName;
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public String execute() throws Exception{
		System.out.println(request.getParameter("file"));
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String userName=request.getParameter("publicName");
		int news_kind_id=Integer.valueOf(request.getParameter("news_kind_id"));
		String picture1=request.getParameter("picture1");
		String picture2=request.getParameter("picture2");
		String picture3=request.getParameter("picture3");
		
		NewsHot newsHot=new NewsHot();
		newsHot.setContent(content);
		newsHot.setNewsKindId(news_kind_id);
		newsHot.setTitle(title);
		newsHot.setPublic_user_kind(1);
		newsHot.setColleage(userName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		newsHot.setPublicTime(sdf.format(new Date()));
		System.out.println("正在发表");
		if(picture1!=null){
			System.out.println("图片不为空");
			Blob blob=Hibernate.createBlob(picture1.getBytes());
			newsHot.setPicture1(blob);
		}
		else {
			Blob blob=Hibernate.createBlob("null".getBytes());
			newsHot.setPicture1(blob);
		}
		if(picture2!=null){
			Blob blob=Hibernate.createBlob(picture2.getBytes());
			newsHot.setPicture2(blob);
		}
		else {
			Blob blob=Hibernate.createBlob("null".getBytes());
			newsHot.setPicture2(blob);
		}
		if(picture3!=null){
			Blob blob=Hibernate.createBlob(picture3.getBytes());
			newsHot.setPicture3(blob);
		}
		else {
			Blob blob=Hibernate.createBlob("null".getBytes());
			newsHot.setPicture3(blob);
		}
		mgr.saveNewsHot(newsHot);
		JSONObject jsonObject=new JSONObject().put("saveUserPublicNews", true);
			response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
