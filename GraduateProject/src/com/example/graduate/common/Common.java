package com.example.graduate.common;

import com.example.graduateproject.R;

import android.R.raw;
import android.text.StaticLayout;

public class Common {
	
	public static  String[] group = {"�������","Ƕ��ʽ","����ý��","���繤��","������"};
	public static String[][] childen = {
			{"������","����Ƽ","������","����","κ ��"},
			{"���庺","������","����"},
			{"��С��","��ҵ��"},
			{"����","������","����"},
			{"�Ķ���","����ǿ"}
	};
	
	
	/**
	 * ѧ������
	 */
	public static String[] student_function_name=
		{
			"����ѡ��",
			"������Ϣ",
			"ϵͳ����"
		};
	
	public static int[] student_fuction_image=
		{
			R.drawable.cource,
			R.drawable.person,
			R.drawable.system
		};
	
	/**
	 * ��ʦ����
	 */
	public static String[] teacher_function_name=
		{
			"����״��",
			"������Ϣ",
			"ϵͳ����"
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
