package com.colleage.assistant.action;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.utils.MD5;
import com.colleage.assistant.utils.Utils;
import com.mysql.jdbc.Util;
import com.mysql.jdbc.log.Log;


public class GetNewsHotAction extends BaseAction implements ServletRequestAware,
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
		
		String newKindId=request.getParameter("id");
		String newsNum=request.getParameter("newsNum");
		System.out.println("选择地几个+"+newsNum);
		System.out.println("选择哪个项目+"+newKindId);
		List<Map> list=new ArrayList<Map>();
		List<NewsHot> newsHots=mgr.getNewsHotList(Integer.valueOf(newKindId)-1);
		System.out.println(newKindId+"有多少条数据"+newsHots.size());
		System.out.println(newsHots.size());
		for(int i=0;i<newsHots.size()&&i<Integer.valueOf(newsNum);i++){
			Map<String, String> map=new HashMap<String, String>();
			map.put("id", String.valueOf(newsHots.get(i).getId()));
			map.put("title", newsHots.get(i).getTitle());
			System.out.println(newsHots.get(i).getTitle());
			map.put("colleage", newsHots.get(i).getColleage());
			if (newsHots.get(i).getPublic_user_kind()==1&&!mgr.getUser(newsHots.get(i).getColleage()).getPicture().equals("null")) {
				map.put("userPicture", Utils.blobToString(mgr.getUser(newsHots.get(i).getColleage()).getPicture()));
			}
			else {
				map.put("userPicture","null");
			}
			map.put("content",newsHots.get(i).getContent());
			if(newsHots.get(i).getNews_web()!=null){
				map.put("news_web", newsHots.get(i).getNews_web());
			}
			else {
				map.put("news_web", "null");
			}
			map.put("commentCounts", String.valueOf(newsHots.get(i).getCommentCounts()));
			map.put("publicTime", newsHots.get(i).getPublicTime());
			map.put("public_user_kind", String.valueOf(newsHots.get(i).getPublic_user_kind()));
			map.put("rightPicture", Utils.blobToString(newsHots.get(i).getRightpicture()));
			map.put("picture1", Utils.blobToString(newsHots.get(i).getPicture1()));
			map.put("picture2", Utils.blobToString(newsHots.get(i).getPicture2()));
			map.put("picture3", Utils.blobToString(newsHots.get(i).getPicture3()));
			list.add(map);
		}
		
		response.setContentType("text/html; charset=GBK");
		
		JSONObject jsonObject=new JSONObject();
		if(list.size()==0)
		{
			jsonObject.put("newHotExist", false);
		}
		else {
			jsonObject.put("newsHot", list);
			jsonObject.put("newHotExist", true);
		}
		
		response.getWriter().println(jsonObject.toString());
		return SUCCESS;
	}
}
