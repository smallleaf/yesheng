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


public class PraiseDianAction extends BaseAction implements ServletRequestAware,
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
		String id=request.getParameter("id");
		String commentCounts=request.getParameter("commentCounts");
		mgr.updateNewsHot(Integer.valueOf(id), Integer.valueOf(commentCounts));
		return SUCCESS;
	}
}
