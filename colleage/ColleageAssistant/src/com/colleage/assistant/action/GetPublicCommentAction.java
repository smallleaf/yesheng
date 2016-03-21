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


public class GetPublicCommentAction extends BaseAction implements ServletRequestAware,
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
	
	/**
	 * 1，传递map对象
	 * 2、map对象还有两个map键值，一个是评论，另一个是评论的回复
	 * 3、为什么评论的回复要用map呢？
	 * 		如果用list，那么我们就不方便同时将两组数据存入第一个map对象
	 * 4、评论的回复map里面含有list即回复数
	 * map---map---评论
	 * 	  ---map---回复(list)--map(每一条)
	 */
	public String execute() throws Exception{
		String news_id=request.getParameter("news_id");
		System.out.println("获得那个"+news_id);
		List<Map> sendList=new ArrayList<Map>();
		if(news_id!=null){
			
			List<PublicComment> publicComments=mgr.publicComments(Integer.valueOf(news_id));
			for(int i=0;i<publicComments.size();i++){
				Map<String, Map> map=new HashMap<String, Map>();
				Map<String, String> comments=new HashMap<String, String>();
				//每条评论对应多条回复
				Map<String, List> storeComment=new HashMap<String, List>();
				List<Map> storeComments=new ArrayList<Map>();
				comments.put("id", String.valueOf(publicComments.get(i).getId()));
				
				comments.put("username", publicComments.get(i).getUsername());
				comments.put("comment_content", publicComments.get(i).getComment_content());
				comments.put("comment_time", publicComments.get(i).getComment_time());
				System.out.println("哪个++++++++"+publicComments.get(i).getUsername());
				User user=mgr.getUser(publicComments.get(i).getUsername());
				if(user!=null)
					comments.put("picture", Utils.blobToString(user.getPicture()));
				else {
					comments.put("picture", "null");
				}
				if(mgr.getRelpyComments(publicComments.get(i).getId()).size()>0){
					comments.put("reply_counts", "true");
					List<ReplyComment> replyComments=mgr.getRelpyComments(publicComments.get(i).getId());
					System.out.println(String.valueOf(publicComments.get(i).getId())+"fdd"+replyComments.size());
					//讲带有评论的评论取出来
					for(int j=0;j<replyComments.size();j++){
						Map<String, String> mapReply=new HashMap<String, String>();
						mapReply.put("username", replyComments.get(j).getUsername());
						mapReply.put("content", replyComments.get(j).getContent());
						mapReply.put("reply_time", replyComments.get(j).getReply_time());
						mapReply.put("reply_username", replyComments.get(j).getReply_username());
						storeComments.add(mapReply);
					}
					storeComment.put("reply", storeComments);
				}
				else {
					comments.put("reply_counts", "false");
					storeComment.put("reply", null);
				}
				//将评论取出来
				map.put("comment", comments);
				//讲评论的回复取出来
				map.put("reply_commets", storeComment);
				//一起保存在对应的评论中
				sendList.add(map);
			}
		}
		response.setContentType("text/html; charset=GBK");
		JSONObject jsonObject=new JSONObject().put("comments", sendList);
		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
