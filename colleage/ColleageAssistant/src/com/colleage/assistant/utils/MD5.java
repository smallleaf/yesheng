package com.colleage.assistant.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String stringMd5(String str){
		MessageDigest md5=null;
		try {
			md5=MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		char[] charArray=str.toCharArray();
		byte[] byteArray=str.getBytes();
		
		for(int i=0;i<charArray.length;i++){
			byteArray[i]=(byte)charArray[i];
		}
		byte[] md5Bytes=md5.digest(byteArray);
		StringBuffer hexValue=new StringBuffer();
		for(int i=0;i<md5Bytes.length;i++){
			int val=((int)md5Bytes[i])&0xff;
			if(val<16)
			{
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	
	public static String convertMD5(String instr){
		char[] a=instr.toCharArray();
		for(int i=0;i<a.length;i++){
			a[i]=(char)(a[i]^'t');
		}
		String s=new String(a);
		return s;
	}

}
