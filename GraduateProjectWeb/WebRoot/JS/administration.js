var insititutionList;//ѧԺ
var majors;//רҵ
var studentClass;

var detailsStudents;//����ѧ����Ϣ
function createHttp()
		{
			if(window.XMLHttpRequest){  
		          
		        //���FireFox,Mozillar,opera,safari,ie7,ie8  
		        xmlHttp = new XMLHttpRequest();  
		        //���ĳЩ�ض��汾��moziller�������BUG��������  
		        if(xmlHttp.overrideMimeType){  
		            xmlHttp.overrideMimeType("text/xml");  
		        }  
		    }else if(window.ActiveXObject){  
		        //���IE6,ie5.5 ie5  
		        //�����������ڴ���XMLHTTPREQUEST����ؼ����ƣ�������һ��JS������  
		        //����ǰ��İ汾����  
		        var activexName = new ActiveXObject["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];  
		        for(var i=0; i<activexName.length; i++){  
		            try{  
		                //ȡ��һ���ؼ������д�������������ɹ�����ֹѭ��  
		                //�������ʧ�ܣ����׳��쳣��Ȼ����Լ���ѭ�����������Դ���  
		                xmlHttp = new ActiveXObject(activexName[i]);  
		                break;  
		            }catch(e){  
		            	alert("����ʧ��");
		            }  
		        }  
		          
		    }  
			
		}



function student_manager()
{
	createHttp();
	xmlHttp.open("POST","ad_getAllStudent.action",true);
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
	xmlHttp.onreadystatechange=returnStudentResponse;
	xmlHttp.send(null);
	
}

function teacher_manager()
{
	load_teacher();
}
function load_teacher()
{
	createHttp();
	xmlHttp.open("POST","ad_getAllTeacher.action",true);
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
	xmlHttp.onreadystatechange=returnResponse;
	xmlHttp.send(null);
}
function returnResponse()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);
			if(returnJson.state)
				{
					var detailTeachers=returnJson.detailsTeachers;
					var table=document.getElementById("teacher_info");
					
					var rowSize=table.rows.length;
					//���
					for(var i=1;i<rowSize;rowSize--)
						{
						//��һ�в����
							table.deleteRow(i);
						}
					for(var i=0;i<detailTeachers.length;i++)
						{
						//ָ��λ��
							var rowIndex=table.rows.length;
							var row=table.insertRow(rowIndex);
							var c1=row.insertCell(0);
							c1.innerHTML="<input type='checkbox' name='teacherChoose'/>";
							var c2=row.insertCell(1);
							c2.innerHTML=detailTeachers[i].teacherId;
							var c3=row.insertCell(2);
							c3.innerHTML=detailTeachers[i].name;
							var c4=row.insertCell(3);
							c4.innerHTML=detailTeachers[i].institution;
							var c5=row.insertCell(4);
							c5.innerHTML=detailTeachers[i].major;
							var c6=row.insertCell(5);
							c6.innerHTML=detailTeachers[i].phone;
							var c7=row.insertCell(6);
							c7.innerHTML="<input type='button' value='�޸�'  /><input type='button' value='ɾ��' />";
						}
					document.getElementById("add_teacher").style.display="none";
					document.getElementById("add_student").style.display="none";
					document.getElementById("student_manager").style.display="none";
					document.getElementById("teacher_manager").style.display="block";
					
					xmlHttp.open("POST","ad_getAllInsAndMajor.action",true);
					xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
					xmlHttp.onreadystatechange=returnAllInsResponse;
					xmlHttp.send(null);
				}
		}
	}
	
}
function returnStudentResponse()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);
			if(returnJson.state)
			{
				var detailStudents=returnJson.detailsStudents;
				var table=document.getElementById("student_info");
				
				detailsStudents=new Array();
				var rowSize=table.rows.length;
				//���
				for(var i=1;i<rowSize;rowSize--)
				{
					//��һ�в����
					table.deleteRow(i);
				}
				for(var i=0;i<detailStudents.length;i++)
				{
					//��������
					detailsStudents[i]=detailStudents[i];
					//ָ��λ��
					var rowIndex=table.rows.length;
					var row=table.insertRow(rowIndex);
					var c1=row.insertCell(0);
					c1.innerHTML="<input type='checkbox' name='teacherChoose'/>";
					var c2=row.insertCell(1);
					c2.innerHTML=detailStudents[i].studentId;
					var c3=row.insertCell(2);
					c3.innerHTML=detailStudents[i].name;
					var c4=row.insertCell(3);
					c4.innerHTML=detailStudents[i].institution;
					var c5=row.insertCell(4);
					c5.innerHTML=detailStudents[i].major;
					var c5=row.insertCell(5);
					c5.innerHTML=detailStudents[i].class_name;
					var c6=row.insertCell(6);
					c6.innerHTML=detailStudents[i].tel;
					var c7=row.insertCell(7);
					c7.innerHTML="<input type='button' value='�޸�' onclick='st_change("+i+");'/><input type='button' value='ɾ��' />";
				}
				document.getElementById("add_teacher").style.display="none";
				document.getElementById("teacher_manager").style.display="none";
				document.getElementById("student_manager").style.display="block";
				document.getElementById("add_student").style.display="none";
				
				//��ȡѧУרҵ��Ϣ
						
						
						xmlHttp.open("POST","ad_getAllInsAndMajor.action",true);
						xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
						xmlHttp.onreadystatechange=returnAllStudentResponse;
						xmlHttp.send(null);
			}
		}
	}
	
}
function st_change(index)
{
	//�����޸�
	
	
	document.getElementById("studentId").value=detailsStudents[index].studentId;
	document.getElementById("st_name").value=detailsStudents[index].name;
	document.getElementById("st_password").value=detailsStudents[index].password;
	document.getElementById("st_tel").value=detailsStudents[index].tel;
	
	var institutionSelect=document.getElementById("st_institutionSelect");
	var majorSelect=document.getElementById("st_majorSelect");
	var studentClassSelect=document.getElementById("st_classSelect");
	
	var institutionSelectLength=institutionSelect.length;
	var majorSelectLength=majorSelect.length;
	var studentClassSelectLength=studentClassSelect.length;
	for(var i=0;i<institutionSelectLength;i++)
		{
			if(institutionSelect.options[i].innerHTML==detailsStudents[index].institution)
				{
				institutionSelect.options[i].selected=true;
				}
		}
	for(var i=0;i<majorSelectLength;i++)
	{
		if(majorSelect.options[i].innerHTML==detailsStudents[index].major)
		{
			majorSelect.options[i].selected=true;
		}
	}
	for(var i=0;i<studentClassSelectLength;i++)
	{
		if(studentClassSelect.options[i].innerHTML==detailsStudents[index].class_name)
		{
			studentClassSelect.options[i].selected=true;
		}
	}
	document.getElementById("add_teacher").style.display="none";
	document.getElementById("teacher_manager").style.display="none";
	document.getElementById("student_manager").style.display="none";
	document.getElementById("add_student").style.display="block";	
	document.getElementById("st_send_bt").value="�޸�";
}


function returnAllInsResponse()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);

			if(returnJson.state)
			{
				
				var insititutionListTemp=returnJson.institutions;
				var majorsTemp=returnJson.majors;
				
				insititutionList=new Array();
				majors=new Array();
				for(var i=0;i<insititutionListTemp.length;i++)
					{
					insititutionList[i]= insititutionListTemp[i];
					}
				for(var i=0;i<majorsTemp.length;i++)
				{
					majors[i]= majorsTemp[i];
				}
				var institutionSelect=document.getElementById("institutionSelect");
				var majorSelect=document.getElementById("majorSelect");
			
				//ɾ����ǰ������
				var institutionSelectLength=institutionSelect.length;
				var majorSelectLength=majorSelect.length;
				for(var i=0;i<majorSelectLength;majorSelectLength--)
					{
					institutionSelect.options.remove(i);
					}
				for(var i=0;i<majorSelectLength;majorSelectLength--)
				{
					majorSelect.options.remove(i);
				}
				
				
				
				for(var i=0;i<insititutionList.length;i++)
				{
					var item=new Option(insititutionList[i].name,insititutionList[i].institutionId);
					institutionSelect.options.add(item);
				}
				for(i=0;i<majors.length;i++)
					{
						if(majors[i].institutionId==1)
							{
							var item=new Option(majors[i].name,majors[i].majorId);
							majorSelect.options.add(item);
							}
					}
			}
		}
	}
	
}
function returnAllStudentResponse()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);
			if(returnJson.state)
			{	
				
				var insititutionListTemp=returnJson.institutions;
				var majorsTemp=returnJson.majors;
				var studentClassTemp=returnJson.studentClasses;
				studentClass =new Array();
				insititutionList=new Array();
				majors=new Array();
				for(var i=0;i<insititutionListTemp.length;i++)
					{
					insititutionList[i]= insititutionListTemp[i];
					}
				for(var i=0;i<majorsTemp.length;i++)
				{
					majors[i]= majorsTemp[i];
				}
				for(var i=0;i<studentClassTemp.length;i++)
				{
					studentClass[i]= studentClassTemp[i];
				}
				var institutionSelect=document.getElementById("st_institutionSelect");
				var majorSelect=document.getElementById("st_majorSelect");
				var studentClassSelect=document.getElementById("st_classSelect");
				
				
				//ɾ����ǰ������
				var institutionSelectLength=institutionSelect.length;
				var majorSelectLength=majorSelect.length;
				var studentClassSelectLength=studentClassSelect.length;
				for(var i=0;i<institutionSelectLength;institutionSelectLength--)
					{
					institutionSelect.options.remove(i);
					}
				for(var i=0;i<majorSelectLength;majorSelectLength--)
				{
					majorSelect.options.remove(i);
				}
				for(var i=0;i<studentClassSelectLength;studentClassSelectLength--)
				{
					studentClassSelect.options.remove(i);
				}
				
				
				for(var i=0;i<insititutionList.length;i++)
				{
					var item=new Option(insititutionList[i].name,insititutionList[i].institutionId);
					institutionSelect.options.add(item);
				}
				for(i=0;i<majors.length;i++)
				{
					if(majors[i].institutionId==1)
					{
						var item=new Option(majors[i].name,majors[i].majorId);
						majorSelect.options.add(item);
					}
				}
				for(i=0;i<studentClass.length;i++)
					{
						if(studentClass[i].majorId==1)
							{
								var item=new Option(studentClass[i].name,studentClass[i].id);
								studentClassSelect.options.add(item);
							}
					}
			}
		}
	}
	
}
function returnSaveTeacherResponse()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);
			if(returnJson.state)
			{
				alert("�½��û��ɹ�!");
				
				load_teacher();
			}
			else
				{
				alert("�½��û�ʧ��!");
				}
		}
	}
	
}
function returnSaveStudentResponse()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);
			if(returnJson.state)
			{
				alert("�ύ�ɹ�!");
				
				student_manager();
			}
			else
			{
				alert("�½��û�ʧ��!");
			}
		}
	}
	
}
function add_Student()
{
	document.getElementById("st_send_bt").value="�ύ";
	document.getElementById("teacher_manager").style.display="none";
	document.getElementById("student_manager").style.display="none";
	document.getElementById("add_student").style.display="block";
	document.getElementById("add_teacher").style.display="none";

}

function add_teacher()
{
	document.getElementById("teacher_manager").style.display="none";
	document.getElementById("student_manager").style.display="none";
	document.getElementById("add_student").style.display="none";
	document.getElementById("add_teacher").style.display="block";
}
function teacher_Stroe()
{
	createHttp();
	var teacher=new Object();
	
	teacher.teacherId=document.getElementById("teacherId").value;
	teacher.password=document.getElementById("password").value;
	teacher.name=document.getElementById("name").value;
	teacher.phone=document.getElementById("phone").value;
	var majorSelect=document.getElementById("majorSelect");
	var majorId=majorSelect.options[majorSelect.options.selectedIndex].value;
	teacher.majorId=majorId;
	var teacherJson=JSON.stringify(teacher);
	xmlHttp.open("POST","ad_saveTeacher.action",true);
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
	xmlHttp.onreadystatechange=returnSaveTeacherResponse;
	xmlHttp.send("teacherJson="+teacherJson);
}
/**
 * �½�ѧ��
 */
function student_Stroe()
{
	createHttp();
	var student=new Object();
	
	student.studentId=document.getElementById("studentId").value;
	student.name=document.getElementById("st_name").value;
	student.password=document.getElementById("st_password").value;
	student.tel=document.getElementById("st_tel").value;
	var majorSelect=document.getElementById("st_majorSelect");
	var majorId=majorSelect.options[majorSelect.options.selectedIndex].value;
	var classSelect=document.getElementById("st_classSelect");
	var classId=classSelect.options[classSelect.options.selectedIndex].value;
	student.majorId=majorId;
	student.classId=classId;
	var studentJson=JSON.stringify(student);
	alert(studentJson);
	xmlHttp.open("POST","ad_saveStudent.action",true);
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
	xmlHttp.onreadystatechange=returnSaveStudentResponse;
	xmlHttp.send("studentJson="+studentJson);
}

function institutionChange()
{
	var institutionSelect=document.getElementById("institutionSelect");
	var institutionId=institutionSelect.options[institutionSelect.options.selectedIndex].value;
	var majorSelect=document.getElementById("majorSelect");
	var majorLength=majorSelect.options.length;
	//��� 
	for(var i=0;i<majorLength;majorLength--)
		{
		majorSelect.options.remove(i);
		}
	
	for(i=0;i<majors.length;i++)
		{
			if(majors[i].institutionId==institutionId)
				{
				var item=new Option(majors[i].name,majors[i].majorId);
				majorSelect.options.add(item);
				}
		}
	
}