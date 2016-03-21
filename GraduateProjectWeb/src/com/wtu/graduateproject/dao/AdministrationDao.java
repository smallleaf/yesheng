package com.wtu.graduateproject.dao;

import java.util.List;

import com.wtu.graduateproject.po.domain.Administration;
import com.wtu.graduateproject.po.domain.Student;


public interface AdministrationDao extends Dao{
	public Administration getAdministration(String id);
}
