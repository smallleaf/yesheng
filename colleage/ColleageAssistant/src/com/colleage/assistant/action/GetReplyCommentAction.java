package com.colleage.assistant.action;

import java.io.File;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


public class GetReplyCommentAction extends BaseAction implements ServletRequestAware,
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
		String comment_id=request.getParameter("comment_id");
		List<Map> sendList=new ArrayList<Map>();
		System.out.println("正在获取信息");
		if(comment_id!=null){
			List<ReplyComment> replyComments=mgr.getRelpyComments(Integer.valueOf(comment_id));
			for(int i=0;i<replyComments.size();i++){
				Map<String, String> map=new HashMap<String, String>();
				map.put("username", replyComments.get(i).getUsername());
				map.put("content", replyComments.get(i).getContent());
				map.put("reply_time", replyComments.get(i).getReply_time());
				map.put("reply_username", replyComments.get(i).getReply_username());
				sendList.add(map);
			}
		}
		response.setContentType("text/html; charset=GBK");
		JSONObject jsonObject=new JSONObject().put("comments", sendList);
		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
