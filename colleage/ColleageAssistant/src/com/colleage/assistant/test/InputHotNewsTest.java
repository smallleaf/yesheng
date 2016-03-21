package com.colleage.assistant.test;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.colleage.assistant.po.domain.NewsHot;
import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.service.FacadeManager;

public class InputHotNewsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] configLocations={"WebContent/WEB-INF/applicationContext.xml","WebContent/WEB-INF/daoContext.xml"};
		ApplicationContext applicationContext=new FileSystemXmlApplicationContext(configLocations);
		FacadeManager facadeManager=(FacadeManager)applicationContext.getBean("facadeManager");
		NewsHot newsHot=new NewsHot();
		newsHot.setTitle("学校启动“青马工程”加强和改进大学生思想政治教育");
		newsHot.setColleage("武汉纺织大学");
		newsHot.setCommentCounts(150);
		newsHot.setContent("http://news.wtu.edu.cn/html/20150410/000000004bf3093f014ca103c0ba048e.html");
		
		Blob blob1=Hibernate.createBlob("http://news.wtu.edu.cn:8080/userfiles/image/2015041009230432.jpg".getBytes());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		newsHot.setPublicTime(sdf.format(new Date()));
		newsHot.setRightpicture(blob1);
//		newsHot.setPicture1(blob1);
		newsHot.setPublic_user_kind(0);
		newsHot.setNewsKindId(2);
		facadeManager.saveNewsHot(newsHot);
	}

}
