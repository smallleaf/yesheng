package com.app.util;
import java.security.MessageDigest;


public class MD5Util {
	private final static String[] hexDigits={"0","1","2","3","4","5","6","7","8",
		"9","a","b","c","d","e","f"};
	
	public static String encrypt(String cleartext){
		return encryptByMD5(cleartext);
	}
	
	public static boolean validate(String cleartext,String ciphertext){
		if(ciphertext.endsWith(encryptByMD5(cleartext))){
			return true;
		}
		else{
			return false;
		}
	}
	
	private static String encryptByMD5(String cleartext){
		if(cleartext!=null){
			try{
				MessageDigest md=MessageDigest.getInstance("MD5");
				byte[] results=md.digest(cleartext.getBytes());
				String ciphertext=byteArrayToHexString(results);
				return ciphertext.toUpperCase();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private static String byteArrayToHexString(byte[] b){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<b.length;i++){
			sb.append(byteToHexString(b[i]));
		}
		return sb.toString();
	}
	
	private static String byteToHexString(byte b){
		int n=b;
		if(n<0){
			n+=256;
		}
		int d1=n/16;
		int d2=n%16;
		return hexDigits[d1]+hexDigits[d2];
	}
}
