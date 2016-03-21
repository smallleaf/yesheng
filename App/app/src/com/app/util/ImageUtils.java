package com.app.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class ImageUtils {
	//将byte数组进行base64编码后输出
	public static String encode(byte[] bytes){
		return new BASE64Encoder().encode(bytes);
	}
	//将String进行base64解码后输出
	public static byte[] decode(String encodeStr) throws IOException{
		byte[] buf=null;
		BASE64Decoder decoder=new BASE64Decoder();
		buf=decoder.decodeBuffer(encodeStr);
		return buf;
	}
	
	//
	public static byte[] connectBytes(byte[] front,byte[] after){
		byte[] result=new byte[front.length+after.length];
		System.arraycopy(front,0,result,0,after.length);
		System.arraycopy(after,0,result,front.length,after.length);
		return result;
	}
	
	public static String encodeImage(String url) throws IOException{
		FileInputStream fis=new FileInputStream(url);
		byte[] buf=new byte[fis.available()];
		fis.read(buf);
		fis.close();
		return encode(buf);
	}
	
	public static void main(String[] args) throws Exception{
		String str=encodeImage("D:\\psb.jpg");
		System.out.println(str.length());
		byte[] buf=GZipUtil.compress(str.getBytes());
		System.out.println(buf.length);
		File file=new File("D:\\psb1.jpg");
		OutputStream out=new FileOutputStream(file);
		out.write(decode(new String(GZipUtil.decompress(buf))));
		out.close();
	}
	/**接受的base64转化成图片
	 * 
	 */
	 public static void decoderBase64File(String base64Code, String targetPath)
			   throws Exception {
			  byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
			  FileOutputStream out = new FileOutputStream(targetPath);
			  out.write(buffer);
			  out.close();
	 }
	 /**
	  * 读取图片文件并转化成base64位
	  */
	 public static String fileTobase64(String filePath){
		 File file=new File(filePath);
		 byte[] buffer=null;
		 try {
			FileInputStream fileInputStream=new FileInputStream(file);
			buffer=new byte[(int)file.length()];
			fileInputStream.read(buffer);
			fileInputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new BASE64Encoder().encode(buffer);
		 
	 }
}
