package com.app.util;

import java.util.Random;

public class AuthcodeUtils {
	public static final int AUTHCODE_LENGTH=5;
	public static String createAuthcode(){
		Random random=new Random();
		StringBuffer authcode=new StringBuffer();
		for(int i=0;i<AUTHCODE_LENGTH;i++){
			authcode.append(random.nextInt(10));
		}
		return authcode.toString();
	}
}
