
var xmlHttp;
var role_value=1;
var id;
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
									alert("�������֧�ֱ��ش洢");
									}
								window.location.href="teachear.html";
							}
							else{
								window.location.href="administration.html";
							}
						}
					else
						{
						alert("��½ʧ��!����֤�û���������!");
						}
				}
			}
		}