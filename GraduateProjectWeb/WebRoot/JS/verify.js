
var xmlHttp;
var role_value=1;
var id;
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
	
		function valide()
		{
			
			createHttp();
			id=document.getElementsByName("studentId")[0].value;
			var password=document.getElementsByName("passWord")[0].value;
			var role=document.getElementsByName("user_role");
			
			for(var i=0;i<role.length;i++)
			{
				if(role[i].checked==true)
				{
					role_value=role[i].value;
					
				}
			}
			xmlHttp.open("POST","student_login_web.action",true);
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
			xmlHttp.onreadystatechange=returnResponse;
			xmlHttp.send("studentId="+id+"&passWord="+password+"&user_role="+role_value);
			
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
							if(role_value==1)
							{
								if(window.localStorage)
								{
									var storage=window.sessionStorage ;
									storage.setItem("teacherId",id);
								}
								else
									{
									alert("浏览器不支持本地存储");
									}
								window.location.href="teachear.html";
							}
							else{
								window.location.href="administration.html";
							}
						}
					else
						{
						alert("登陆失败!请验证用户名和密码!");
						}
				}
			}
		}