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
import com.colleage.assistant.po.domain.ReplyComment;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.po.domain.UserPublicNews;
import com.colleage.assistant.utils.MD5;
import com.colleage.assistant.utils.Utils;
import com.mysql.jdbc.Util;


public class SaveReplyCommentAction extends BaseAction implements ServletRequestAware,
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
		String reply_username=request.getParameter("reply_username");
		String username=request.getParameter("username");
		String content=request.getParameter("content");
		String reply_time=request.getParameter("reply_time");
		int comment_id=Integer.valueOf(request.getParameter("comment_id"));
		ReplyComment replyComment=new ReplyComment();
		replyComment.setContent(content);
		replyComment.setReply_username(reply_username);
		replyComment.setUsername(username);
		replyComment.setComment_id(comment_id);
		replyComment.setReply_time(reply_time);
		mgr.saveReplyComment(replyComment);
//		response.setContentType("text/html; charset=GBK");
//		JSONObject jsonObject=new JSONObject().put("saveReplyComment", true);
//		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
