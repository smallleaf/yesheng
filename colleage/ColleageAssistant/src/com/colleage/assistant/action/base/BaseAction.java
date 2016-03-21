package com.colleage.assistant.action.base;

import com.colleage.assistant.service.FacadeManager;
import com.opensymphony.xwork2.ActionSupport;


public class BaseAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected FacadeManager mgr;
	public void setMgr(FacadeManager mgr)
	{
		System.out.println("test");
		this.mgr=mgr;
	}
	
}
