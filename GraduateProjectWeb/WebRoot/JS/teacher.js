var cource;
var storage;
var teacherId;
var courceList;
var flagId=0;//������ĸ�������
var flagInt;
var detailsTeacher;
var courceId;
var sendTuisong=0;

var courceName;

var list_length;//��¼�洢���ݵĳ���
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

function loading()
{
	flag=0;
	
	createHttp();
	storage=window.sessionStorage;
	teacherId=storage.getItem("teacherId");
	xmlHttp.open("POST","details_teacher.action",true);
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
	xmlHttp.onreadystatechange=returnResponse;
	xmlHttp.send("teacherId="+teacherId);
	
	
}
function allCourceResponse()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);
			if(returnJson.state)
				{
					var list=returnJson.courceList;
					var ul=document.getElementById("cource_list");
					courceList=new Array();
					while(ul.hasChildNodes())
						{
							ul.removeChild(ul.firstChild);
						}
					for(var i=0;i<list.length;i++)
					{
						var cource=list[i];
						courceList[i]=cource;
						
						var li=document.createElement("li");
						li.innerHTML=cource.name;
						li.id=cource.id;
							
						ul.insertBefore(li, ul.firstChild);
					}
					
					var ul_list=document.getElementsByTagName("li");
					for(var i=0;i<ul_list.length;i++)
					{
						(function(x)
								{
									ul_list[x].onclick=function(){
										document.getElementById("new_gra").style.display="none";
										document.getElementById("create_gra").style.display="block";
										var cource=courceList[courceList.length-1-x];
										flagInt=courceList.length-1-x;
										flagId=cource.id;
										document.getElementById("gra_name").value=cource.name;
										document.getElementById("gra_subject").value=cource.demand;
										document.getElementById("gra_ability").value=cource.ability;
										document.getElementById("gra_limit").value=cource.count_limit;
										document.getElementById("but_sub").value="�޸�";
									}
								}
						)(i);
					}
						
					if(sendTuisong==1)
						{
					//����ƽ̨��������
					xmlHttp.open("POST","http://localhost:8080/notification.do?action=send",true);
					xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
					xmlHttp.send("teacher="+detailsTeacher.name+"&courceName="+courceName+"&courceId="+courceId+"&teacherId="+detailsTeacher.teacherId);
					sendTuisong=0;
						}
				}
		}
	}
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
					detailsTeacher=returnJson.detailsTeacher;
					document.getElementById("teacher_lab").innerHTML="��ӭ����"+detailsTeacher.name+"��ʦ";
					xmlHttp.open("POST","findAllCource.action",true);
					xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
					xmlHttp.onreadystatechange=allCourceResponse;
					xmlHttp.send("teacherId="+teacherId);
				}
		}
	}
}

function send()
{
	createHttp();
	//����json����
	cource=new Object();
	
	if(flagId!=0)
		{
		cource.id=flagId;
		xmlHttp.open("POST","updateCource.action",true);
		xmlHttp.onreadystatechange=returnUpadateCource;
		}
	else
	{//Ϊ0��ʾ�½�
		xmlHttp.open("POST","saveCource.action",true);
		xmlHttp.onreadystatechange=returnSaveCource;
	}
	cource.name=document.getElementById("gra_name").value;
	courceName=cource.name;
	cource.demand=document.getElementById("gra_subject").value;
	cource.ability=document.getElementById("gra_ability").value;
	cource.count_limit=document.getElementById("gra_limit").value;
	cource.teacherId=teacherId;
	var courceJson=JSON.stringify(cource);
	
	
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
	
	xmlHttp.send("cource="+courceJson);
}
function returnSaveCource()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);
			if(returnJson.state)
				{
					document.getElementById("new_gra").style.display="block";
					document.getElementById("create_gra").style.display="none";
					courceId=returnJson.courceId;
					//ˢ��ҳ��
					xmlHttp.open("POST","findAllCource.action",true);
					xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
					xmlHttp.onreadystatechange=allCourceResponse;
					xmlHttp.send("teacherId="+teacherId);
					
					sendTuisong=1;
					
				}
			else
				{
				alert("�ύʧ��!");
				}
		}
	}
}
function returnUpadateCource()
{
	if(xmlHttp.readyState==4)
	{
		if(xmlHttp.status==200)
		{
			var text=xmlHttp.responseText;
			var returnJson=JSON.parse(text);
			if(returnJson.state)
			{
				alert("�޸ĳɹ�!");
				document.getElementById("new_gra").style.display="block";
				document.getElementById("create_gra").style.display="none";
				courceList[flagInt]=cource;
				document.getElementById(flagId).innerHTML=cource.name;
			}
			else
			{
				alert("�ύʧ��!");
			}
		}
	}
}

function details_teacher()
{
	
}
function return_index()
{

	window.location.href="index.html";
}
function new_gra()
{

	document.getElementById("new_gra").style.display="none";
	document.getElementById("create_gra").style.display="block";
	
	flagId=0;
	
	document.getElementById("gra_name").value="";
	document.getElementById("gra_subject").value="";
	document.getElementById("gra_ability").value="";
	document.getElementById("gra_limit").value="";
	document.getElementById("but_sub").value="�ύ";
}

function clear()
{
	document.getElementById("gra_name").value="";
	document.getElementById("gra_subject").value="";
	document.getElementById("gra_ability").value="";
	document.getElementById("gra_limit").value="";
}

