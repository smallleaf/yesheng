package com.wtu.graduateproject.service.impl;



import java.util.ArrayList;
import java.util.List;

import com.wtu.graduateproject.dao.AdministrationDao;
import com.wtu.graduateproject.dao.ChooseCourceDao;
import com.wtu.graduateproject.dao.CourceDao;
import com.wtu.graduateproject.dao.InstitutionDao;
import com.wtu.graduateproject.dao.MajorDao;
import com.wtu.graduateproject.dao.StudentClassDao;
import com.wtu.graduateproject.dao.StudentDao;
import com.wtu.graduateproject.dao.TeacherDao;
import com.wtu.graduateproject.po.domain.Administration;
import com.wtu.graduateproject.po.domain.ChooseCource;
import com.wtu.graduateproject.po.domain.ChooseCourceState;
import com.wtu.graduateproject.po.domain.Cource;
import com.wtu.graduateproject.po.domain.DetailsStudent;
import com.wtu.graduateproject.po.domain.DetailsTeacher;
import com.wtu.graduateproject.po.domain.Institution;
import com.wtu.graduateproject.po.domain.Major;
import com.wtu.graduateproject.po.domain.Student;
import com.wtu.graduateproject.po.domain.StudentClass;
import com.wtu.graduateproject.po.domain.Teacher;
import com.wtu.graduateproject.service.BaseManager;
import com.wtu.graduateproject.service.FacadeManager;




public class FacadeManagerImpl extends BaseManager implements FacadeManager{

	private StudentDao studentDao;
	private AdministrationDao administrationDao;
	private TeacherDao teacherDao;
	private MajorDao majorDao;
	private InstitutionDao institutionDao;
	private CourceDao courceDao;
	private StudentClassDao studentClassDao;
	private ChooseCourceDao chooseCourceDao;
	
	
	
	
	public void setChooseCourceDao(ChooseCourceDao chooseCourceDao) {
		this.chooseCourceDao = chooseCourceDao;
	}



	public void setStudentClassDao(StudentClassDao studentClassDao) {
		this.studentClassDao = studentClassDao;
	}



	public void setCourceDao(CourceDao courceDao) {
		this.courceDao = courceDao;
	}



	public void setMajorDao(MajorDao majorDao) {
		this.majorDao = majorDao;
	}



	public void setInstitutionDao(InstitutionDao institutionDao) {
		this.institutionDao = institutionDao;
	}



	public void setAdministrationDao(AdministrationDao administrationDao) {
		this.administrationDao = administrationDao;
	}



	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}



	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}



	@Override
	public boolean isValiable(Object object,int id) {
		// TODO Auto-generated method stub
		if(id==0)
		{
			Student studentTemp=((Student)object);
			Student student=studentDao.getStudent(studentTemp.getStudentId());
			if(student!=null)
			{
				if(student.getPassword().equals(studentTemp.getPassword()))
				{
					return true;
				}
			}
		}
		else if(id==1)
		{//管理员
			Administration administrationTemp=((Administration)object);
			Administration administration=administrationDao.getAdministration(administrationTemp.getId());
			if(administration!=null)
			{
				if(administration.getPassword().equals(administrationTemp.getPassword()))
				{
					return true;
				}
			}
		}
		else if(id==2)
		{
			Teacher teacherTemp=((Teacher)object);
			Teacher teacher=teacherDao.getTeacher(teacherTemp.getTeacherId());
			if(teacher!=null)
			{
				if(teacher.getPassword().equals(teacherTemp.getPassword()))
				{
					return true;
				}
			}
		}
//		Student student2=studentDao.getStudent(student.getStudentId());
//		if(student2!=null)
//		{
//			System.out.println("====+"+student2.getPassword());
//			if(student2.getPassword().equals(student.getPassword()))
//			{
//				return true;
//			}
//		}
		return false;
	}



	@Override
	public DetailsTeacher getDetailsTeacher(String teacherId) {
		// TODO Auto-generated method stub
		Teacher teacher=teacherDao.getTeacher(teacherId);
		Major major=majorDao.getMajor(teacher.getMajorId());
		Institution institution=institutionDao.getInstitution(major.getInstitutionId());
		DetailsTeacher detailsTeacher=new DetailsTeacher();
		detailsTeacher.setId(teacher.getId());
		detailsTeacher.setName(teacher.getName());
		detailsTeacher.setPassword(teacher.getPassword());
		detailsTeacher.setPhone(teacher.getPhone());
		detailsTeacher.setTeacherId(teacher.getTeacherId());
		detailsTeacher.setInstitution(institution.getName());
		detailsTeacher.setMajor(major.getName());
		return detailsTeacher;
	}


	@Override
	public int saveCource(Cource cource) {
		// TODO Auto-generated method stub
		courceDao.saveCource(cource);
		return cource.getId();
	}



	@Override
	public List<Cource> findAllCource(String teacherID) {
		// TODO Auto-generated method stub
		return  courceDao.findAllCource(teacherID);
	}



	@Override
	public void upadateCource(Cource cource) {
		// TODO Auto-generated method stub
		courceDao.upadateCource(cource);
	}



	@Override
	public List getAllInstitution() {
		// TODO Auto-generated method stub
		return institutionDao.getAllInstitution();
	}



	@Override
	public List getAllMajor() {
		// TODO Auto-generated method stub
		return majorDao.getAllMajor();
	}



	@Override
	public List getAllTeacher(int majorId) {
		// TODO Auto-generated method stub
		return teacherDao.getAllTeacher(majorId);
	}



	@Override
	public List getAllTeacher() {
		// TODO Auto-generated method stub
		List<DetailsTeacher> detailsTeachers=new ArrayList<DetailsTeacher>();
		List<Teacher> teachers=teacherDao.getAllTeacher();
		if(teachers!=null)
		{
			for(Teacher teacher:teachers)
			{
				Major major=majorDao.getMajor(teacher.getMajorId());
				Institution institution=institutionDao.getInstitution(major.getInstitutionId());
				DetailsTeacher detailsTeacher=new DetailsTeacher();
				detailsTeacher.setId(teacher.getId());
				detailsTeacher.setName(teacher.getName());
				detailsTeacher.setPassword(teacher.getPassword());
				detailsTeacher.setPhone(teacher.getPhone());
				detailsTeacher.setTeacherId(teacher.getTeacherId());
				detailsTeacher.setInstitution(institution.getName());
				detailsTeacher.setMajor(major.getName());
				
				detailsTeachers.add(detailsTeacher);
			}
			return detailsTeachers;
		}
		else
		{
			return null;
		}
		
	}



	@Override
	public void saveTeacher(Teacher teacher) {
		// TODO Auto-generated method stub
		teacherDao.saveTeacher(teacher);
	}



	@Override
	public List getAllDetailsStudent() {
		// TODO Auto-generated method stub
		List<Student> students=studentDao.getAllStudent();
		List<DetailsStudent> detailsStudents=new ArrayList<DetailsStudent>();
		if(students!=null)
		{
			for(Student student:students)
			{
				Major major=majorDao.getMajor(student.getMajorId());
				Institution institution=institutionDao.getInstitution(major.getInstitutionId());
				StudentClass studentClass=studentClassDao.getStudentClass(student.getClassId());
				DetailsStudent detailsStudent=new DetailsStudent();
				detailsStudent.setStudentId(student.getStudentId());
				detailsStudent.setPassword(student.getPassword());
				detailsStudent.setName(student.getName());
				detailsStudent.setTel(student.getTel());
				detailsStudent.setClass_name(studentClass.getName());
				detailsStudent.setInstitution(institution.getName());
				detailsStudent.setMajor(major.getName());
				detailsStudent.setFirst_volunteer(student.getFirst_volunteer());
				detailsStudent.setSecond_volunteer(student.getSecond_volunteer());
				detailsStudents.add(detailsStudent);
			}
			return detailsStudents;
		}
		return null;
	}



	@Override
	public List getAllClass() {
		// TODO Auto-generated method stub
		return studentClassDao.getAllStudentClass();
	}



	@Override
	public void saveStudent(Student student) {
		// TODO Auto-generated method stub
		studentDao.saveStudent(student);
	}



	@Override
	public List getAllInTeacher(int institutionId) {
		// TODO Auto-generated method stub
		List<Teacher> teachersList=new ArrayList<Teacher>();
		List<Major> majors=majorDao.getAllMajor(institutionId);
		for(Major major:majors)
		{
			List<Teacher> teachers=teacherDao.getAllTeacher(major.getMajorId());
			for(Teacher teacher:teachers)
			{
				teachersList.add(teacher);
			}
		}
		return teachersList;
	}



	@Override
	public List getAllMajor(int institutionId) {
		// TODO Auto-generated method stub
		return majorDao.getAllMajor(institutionId);
	}



	@Override
	public Student getStudent(String StudentId) {
		// TODO Auto-generated method stub
		return studentDao.getStudent(StudentId);
	}



	@Override
	public void saveChooseCource(ChooseCource chooseCource) {
		// TODO Auto-generated method stub
		chooseCourceDao.saveChooseCource(chooseCource);
	}



	@Override
	public void updateStudent(Student student) {
		// TODO Auto-generated method stub
		studentDao.updateStudent(student);
	}



	@Override
	public Cource findCource(int courceId) {
		// TODO Auto-generated method stub
		return courceDao.findCource(courceId);
	}



	@Override
	public DetailsStudent getDetailsStudent(String studentId) {
		// TODO Auto-generated method stub
		Student student=studentDao.getStudent(studentId);
		Major major=majorDao.getMajor(student.getMajorId());
		Institution institution=institutionDao.getInstitution(major.getInstitutionId());
		StudentClass studentClass=studentClassDao.getStudentClass(student.getClassId());
		DetailsStudent detailsStudent=new DetailsStudent();
		detailsStudent.setStudentId(student.getStudentId());
		detailsStudent.setPassword(student.getPassword());
		detailsStudent.setName(student.getName());
		detailsStudent.setTel(student.getTel());
		detailsStudent.setClass_name(studentClass.getName());
		detailsStudent.setInstitution(institution.getName());
		detailsStudent.setMajor(major.getName());
		detailsStudent.setFirst_volunteer(student.getFirst_volunteer());
		detailsStudent.setSecond_volunteer(student.getSecond_volunteer());
		
		//获得选课信息
		List<ChooseCource> chooseCources=chooseCourceDao.getChooseCource(studentId);
		for(ChooseCource chooseCource:chooseCources)
		{
			//第一志愿
			if(chooseCource.getVolunteerKind()==1)
			{
				Cource cource=courceDao.findCource(chooseCource.getCourceId());
				detailsStudent.setFirst_volunteer_name(cource.getName());
				detailsStudent.setFirst_volunteer_state(chooseCource.getState());
				detailsStudent.setFirst_volunteer_id(chooseCource.getId());
			}
			else if (chooseCource.getVolunteerKind()==2) {
				Cource cource=courceDao.findCource(chooseCource.getCourceId());
				detailsStudent.setSecond_volunteer_name(cource.getName());
				detailsStudent.setSecond_volunteer_state(chooseCource.getState());
				detailsStudent.setSecond_volunteer_id(chooseCource.getId());
			}
		}
		return detailsStudent;
	}



	@Override
	public void deleteCource(int chooseCourceId,Student student) {
		// TODO Auto-generated method stub
		chooseCourceDao.deleteCource(chooseCourceId);
		studentDao.updateStudent(student);
	}



	@Override
	public List getChooseCource(String studenId) {
		// TODO Auto-generated method stub
		return chooseCourceDao.getChooseCource(studenId);
	}



	@Override
	public List getChooseCourceState(int courceId) {
		// TODO Auto-generated method stub
		List<ChooseCourceState> chooseCourceStates=new ArrayList<ChooseCourceState>();
		List<ChooseCource> chooseCources=chooseCourceDao.getChooseCource(courceId);
		for(ChooseCource chooseCource:chooseCources)
		{
			ChooseCourceState chooseCourceState=new ChooseCourceState();
			chooseCourceState.setId(chooseCource.getId());
			Student student=studentDao.getStudent(chooseCource.getStudentId());
			StudentClass studentClass=studentClassDao.getStudentClass(student.getClassId());
			chooseCourceState.setClass_name(studentClass.getName());
			chooseCourceState.setStudentName(student.getName());
			chooseCourceState.setVolunnteerKind(chooseCource.getVolunteerKind());
			chooseCourceState.setState(chooseCource.getState());
			chooseCourceStates.add(chooseCourceState);
		}
		return chooseCourceStates;
	}



	@Override
	public void verifySuccess(List<Integer> chooseIds){
		// TODO Auto-generated method stub
		for(int i=0;i<chooseIds.size();i++)
		{
			ChooseCource chooseCource=chooseCourceDao.getChooseCourceFromId(chooseIds.get(i));
			chooseCource.setState(1);
			chooseCourceDao.saveChooseCource(chooseCource);
			Cource cource=courceDao.findCource(chooseCource.getCourceId());
			cource.setCount_choose(cource.getCount_choose()+1);
			courceDao.upadateCource(cource);
		}
	}




}
