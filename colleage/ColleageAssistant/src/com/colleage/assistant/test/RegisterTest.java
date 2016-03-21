package com.colleage.assistant.test;

import java.sql.Blob;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.colleage.assistant.po.domain.User;
import com.colleage.assistant.service.FacadeManager;

public class RegisterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] configLocations={"WebContent/WEB-INF/applicationContext.xml","WebContent/WEB-INF/daoContext.xml"};
		ApplicationContext applicationContext=new FileSystemXmlApplicationContext(configLocations);
		FacadeManager facadeManager=(FacadeManager)applicationContext.getBean("facadeManager");
		User user=new User();
		user.setUsername("1");
			Blob blob=Hibernate.createBlob("fssdfsdfds".getBytes());
			user.setPassword("1");
			user.setPicture(blob);
			user.setUniversity("1");
			user.setMajor("");
			user.setAddress("");
			user.setJob("");
			user.setHobby1("");
			user.setHobby2("");
			user.setHobby3("sfsfd");
			user.setAssociatition("");
		facadeManager.updateUser(user);
//		if(facadeManager.registerUser(user)){
//			System.out.println("×¢²á³É¹¦");
//		}
	}

}
