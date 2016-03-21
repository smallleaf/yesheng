package com.example.graduate.common;

import com.example.graduateproject.R;

import android.R.raw;
import android.text.StaticLayout;

public class Common {
	
	public static  String[] group = {"软件工程","嵌入式","数字媒体","网络工程","物联网"};
	public static String[][] childen = {
			{"丁益祥","吴宛萍","崔树芹","彭涛","魏 雄"},
			{"何儒汉","高晓清","李明"},
			{"杜小勤","尹业安"},
			{"朱勇","胡新荣","胡鸣"},
			{"夏定纯","陈永强"}
	};
	
	
	/**
	 * 学生功能
	 */
	public static String[] student_function_name=
		{
			"课题选择",
			"个人信息",
			"系统设置"
		};
	
	public static int[] student_fuction_image=
		{
			R.drawable.cource,
			R.drawable.person,
			R.drawable.system
		};
	
	/**
	 * 老师功能
	 */
	public static String[] teacher_function_name=
		{
			"课题状况",
			"个人信息",
			"系统设置"
		};
	public static int[] teacher_function_iamge={
		R.drawable.cource,
		R.drawable.person,
		R.drawable.system
	};
	
	//
	public static String SP_NAME="graduate";
	public static String LOGIN_USERID="username";
	public static String LOGIN_PASSWORD="password";
	public static String USER_ROLE="role";
	public static String FIRST_VOLUNTEER="first_volunteer";
	public static String SECOND_VOLUNTEER="second_volunteer";
	
	public static String AUTO_LOGIN="auto_login";
}
