package com.wtu.university.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.Map;

import com.wtu.university.app.AppApplication;
import com.wtu.university.tools.ImageUtils;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

	/**
	 * 计算图片的缩放值
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
	}
	
	/**
	 * 根据uri获得文件路径
	 */
	
	public static String getFilePath(final Context context,Uri uri)
	{
		if ( null == uri ) return null;
	    final String scheme = uri.getScheme();
	    String data = null;
	    if ( scheme == null )
	        data = uri.getPath();
	    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
	        data = uri.getPath();
	    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
	        Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
	        if ( null != cursor ) {
	            if ( cursor.moveToFirst() ) {
	                int index = cursor.getColumnIndex( ImageColumns.DATA );
	                if ( index > -1 ) {
	                    data = cursor.getString( index );
	                }
	            }
	            cursor.close();
	        }
	    }
	    return data;
	}
	/**
	 * // 根据路径获得图片并压缩，返回bitmap用于显示
	 */
	public static Bitmap getSmallBitmap(String filePath) {
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);

	        // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, 400, 400);

	        // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;

	    return BitmapFactory.decodeFile(filePath, options);
	    }
	
	/**
	 * 把bitmap转换成String
	 */
	public static String bitmapToString(Bitmap bm) {

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
	        byte[] b = baos.toByteArray();
	        return Base64.encodeToString(b, Base64.DEFAULT);
	    }
	/**
	 * 把String转换成bitmap
	 */
	public static Bitmap stringtoBitmap(String string){
	    //将字符串转换成Bitmap类型
	    Bitmap bitmap=null;
	    try {
	    byte[]bitmapArray;
	    bitmapArray=Base64.decode(string, Base64.DEFAULT);
	bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
	} catch (Exception e) {
	e.printStackTrace();
	}
	   
	    return bitmap;
	    }
	/**
	 * 分离3个业余爱好
	 */
	public static String[] departHooby(String str){
		String hobby1="";
		String hobby2="";
		String hobby3="";
		int flag=0;
		for (int i = 0; i < str.length(); i++) {
			String temp="";
			if(str.charAt(i)==' '){
				flag++;
			}
			if(flag==1){
				hobby1=temp;
				temp="";
			}
			if(flag==2){
				hobby2=temp;
				temp="";
			}
			if(i==str.length()-1){
				hobby3=temp;
			}
		}
		return new String[]{hobby1,hobby2,hobby3};
		
	}
	/**
	 * 
	 * @param 获得发布日期
	 * @return
	 */
	public static String getDate(String containDate){
		String dateString="";
		for(int i=0;i<containDate.length();i++){
			if(String.valueOf(containDate.charAt(i)).equals("期")||String.valueOf(containDate.charAt(i)).equals(":")){
				dateString="";
			}
			else {
				dateString+=containDate.charAt(i);
			}
		}
		return dateString.substring(1, dateString.length());
	}
	
	/**
	 * 根据path得到file
	 */
	public static File getFileFromPath(String path){
		File file=new File(path);
		return file;
	}
	/*
//	 * 判断线程是否都执行完
//	 */
//	public static boolean threadfinish(Map<String, Boolean> threadFinishMap){
//		for(int i=0;i<threadFinishMap.size();i++){
//			if(threadFinishMap.)
//		}
//	}
	/**
	 * 隐藏输入法
	 */
	public static void hideInput(View view){
		// 隐藏输入法
		InputMethodManager imm = (InputMethodManager) AppApplication.getApp().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		//强制隐藏
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//		// 显示或者隐藏输入法
//		imm.toggleSoftInput(0, InputMethodManager.RESULT_HIDDEN);
	}
	/**
	 * 打开输入法
	 */
	public static void OpenInput(View view){
		// 隐藏输入法
		InputMethodManager imm = (InputMethodManager) AppApplication.getApp().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		//显示
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}
}
