package com.colleage.assistant.utils;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

import sun.misc.BASE64Encoder;

public class Utils {

	/**
	 * blob 转化成Base64string
	 * @throws SQLException 
	 */
	public static String blobToBase64String(Blob blob) throws Exception{
		InputStream inputStream=blob.getBinaryStream();
		 byte[] buffer = new byte[(int) blob.length()];
		 inputStream.read(buffer);
		 inputStream.close();
		  return new BASE64Encoder().encode(buffer);
	}
	/**
	 * blob 转化成string
	 * @throws SQLException 
	 */
	public static String blobToString(Blob blob) throws Exception{
		InputStream inputStream=blob.getBinaryStream();
		byte[] buffer = new byte[(int) blob.length()];
		inputStream.read(buffer);
		inputStream.close();
		return new String(buffer);
	}
}
