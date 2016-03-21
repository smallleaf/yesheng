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
	 * 1������map����
	 * 2��map����������map��ֵ��һ�������ۣ���һ�������۵Ļظ�
	 * 3��Ϊʲô���۵Ļظ�Ҫ��map�أ�
	 * 		�����list����ô���ǾͲ�����ͬʱ���������ݴ����һ��map����
	 * 4�����۵Ļظ�map���溬��list���ظ���
	 * map---map---����
	 * 	  ---map---�ظ�(list)--map(ÿһ��)
	 */
	public String execute() throws Exception{
		String news_id=request.getParameter("news_id");
		System.out.println("����Ǹ�"+news_id);
		List<Map> sendList=new ArrayList<Map>();
		if(news_id!=null){
			
			List<PublicComment> publicComments=mgr.publicComments(Integer.valueOf(news_id));
			for(int i=0;i<publicComments.size();i++){
				Map<String, Map> map=new HashMap<String, Map>();
				Map<String, String> comments=new HashMap<String, String>();
				//ÿ�����۶�Ӧ�����ظ�
				Map<String, List> storeComment=new HashMap<String, List>();
				List<Map> storeComments=new ArrayList<Map>();
				comments.put("id", String.valueOf(publicComments.get(i).getId()));
				
				comments.put("username", publicComments.get(i).getUsername());
				comments.put("comment_content", publicComments.get(i).getComment_content());
				comments.put("comment_time", publicComments.get(i).getComment_time());
				System.out.println("�ĸ�++++++++"+publicComments.get(i).getUsername());
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
					//���������۵�����ȡ����
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
				//������ȡ����
				map.put("comment", comments);
				//�����۵Ļظ�ȡ����
				map.put("reply_commets", storeComment);
				//һ�𱣴��ڶ�Ӧ��������
				sendList.add(map);
			}
		}
		response.setContentType("text/html; charset=GBK");
		JSONObject jsonObject=new JSONObject().put("comments", sendList);
		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
