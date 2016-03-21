package com.wtu.graduateproject.dao;

import java.util.List;

import com.wtu.graduateproject.po.domain.Institution;
import com.wtu.graduateproject.po.domain.Student;


public interface InstitutionDao extends Dao{
	public Institution getInstitution(int institutionId);
	public List getAllInstitution();
}
