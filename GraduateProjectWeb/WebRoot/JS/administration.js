var insititutionList;//学院
var majors;//专业
var studentClass;

var detailsStudents;//保存学生信息
function createHttp()
		{
			if(window.XMLHttpRequest){  
		          
		        //针对FireFox,Mozillar,opera,safari,ie7,ie8  
		        xmlHttp = new XMLHttpRequest();  
		        //针对某些特定版本的moziller浏览器的BUG进行修正  
		        if(xmlHttp.overrideMimeType){  
		            xmlHttp.overrideMimeType("text/xml");  
		        }  
		    }else if(window.ActiveXObject){  
		        //针对IE6,ie5.5 ie5  
		        //两个可以用于创建XMLHTTPREQUEST对象控件名称，保存在一个JS数组中  
		        //排在前面的版本较新  
		        var activexName = new ActiveXObject["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];  
		        for(var i=0; i<activexName.length; i++){  
		            try{  
		                //取出一个控件名进行创建，如果创建成功就终止循环  
		                //如果创建失败，会抛出异常，然后可以继续循环，继续尝试创建  
		                xmlHttp = new ActiveXObject(activexName[i]);  
		                break;  
		            }catch(e){  
		            	alert("创建失败");
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
					//清除
					for(var i=1;i<rowSize;rowSize--)
						{
						//第一行不清除
							table.deleteRow(i);
						}
					for(var i=0;i<detailTeachers.length;i++)
						{
						//指定位置
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
							c7.innerHTML="<input type='button' value='修改'  /><input type='button' value='删除' />";
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
				//清除
				for(var i=1;i<rowSize;rowSize--)
				{
					//第一行不清除
					table.deleteRow(i);
				}
				for(var i=0;i<detailStudents.length;i++)
				{
					//加入数据
					detailsStudents[i]=detailStudents[i];
					//指定位置
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
					c7.innerHTML="<input type='button' value='修改' onclick='st_change("+i+");'/><input type='button' value='删除' />";
				}
				document.getElementById("add_teacher").style.display="none";
				document.getElementById("teacher_manager").style.display="none";
				document.getElementById("student_manager").style.display="block";
				document.getElementById("add_student").style.display="none";
				
				//获取学校专业信息
						
						
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
	//数据修改
	
	
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
	document.getElementById("st_send_bt").value="修改";
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
			
				//删除以前的数据
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
				
				
				//删除以前的数据
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
				alert("新建用户成功!");
				
				load_teacher();
			}
			else
				{
				alert("新建用户失败!");
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
				alert("提交成功!");
				
				student_manager();
			}
			else
			{
				alert("新建用户失败!");
			}
		}
	}
	
}
function add_Student()
{
	document.getElementById("st_send_bt").value="提交";
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
 * 新建学生
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
	//清除 
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