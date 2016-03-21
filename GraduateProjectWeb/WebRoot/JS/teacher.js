var cource;
var storage;
var teacherId;
var courceList;
var flagId=0;//标记是哪个被保存
var flagInt;
var detailsTeacher;
var courceId;
var sendTuisong=0;

var courceName;

var list_length;//记录存储数据的长度
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
										document.getElementById("but_sub").value="修改";
									}
								}
						)(i);
					}
						
					if(sendTuisong==1)
						{
					//推送平台推送数据
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
					document.getElementById("teacher_lab").innerHTML="欢迎您，"+detailsTeacher.name+"老师";
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
	//建立json数据
	cource=new Object();
	
	if(flagId!=0)
		{
		cource.id=flagId;
		xmlHttp.open("POST","updateCource.action",true);
		xmlHttp.onreadystatechange=returnUpadateCource;
		}
	else
	{//为0表示新建
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
					//刷新页面
					xmlHttp.open("POST","findAllCource.action",true);
					xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
					xmlHttp.onreadystatechange=allCourceResponse;
					xmlHttp.send("teacherId="+teacherId);
					
					sendTuisong=1;
					
				}
			else
				{
				alert("提交失败!");
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
				alert("修改成功!");
				document.getElementById("new_gra").style.display="block";
				document.getElementById("create_gra").style.display="none";
				courceList[flagInt]=cource;
				document.getElementById(flagId).innerHTML=cource.name;
			}
			else
			{
				alert("提交失败!");
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
	document.getElementById("but_sub").value="提交";
}

function clear()
{
	document.getElementById("gra_name").value="";
	document.getElementById("gra_subject").value="";
	document.getElementById("gra_ability").value="";
	document.getElementById("gra_limit").value="";
}

