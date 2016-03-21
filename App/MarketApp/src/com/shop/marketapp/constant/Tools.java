package com.shop.marketapp.constant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.shop.marketapp.base.BaseApplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Tools {

	/**
	 * 
	 * @author small leaf ViewHolder ������
	 */
	public static class ViewHolder {
		public static <T extends View> T get(View view, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
			if (viewHolder == null) {
				viewHolder = new SparseArray<View>();
				view.setTag(viewHolder);
			}
			View childView = viewHolder.get(id);
			if (childView == null) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
			return (T) childView;
		}
	}

	/**
	 * ��bitmapת����Base64
	 */
	public static String bitmapToBase64(Bitmap bm) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	/**
	 * ��Base64ת����bitmap
	 */
	public static Bitmap base64toBitmap(String string) {
		// ���ַ���ת����Bitmap����
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	/**
	 * 
	 * @param base64Code
	 * @param name
	 * @throws Exception
	 * base64ͼƬת�����ļ�
	 */
	 public static void decoderBase64File(String base64Code, String name)
			   throws Exception {
//		 //����ͼƬλ��
	     File destDir=new File(Constants.PIC_PATH);
			if(!destDir.exists()){
				Log.v("����զ", "����զ");
				destDir.mkdir();
			}
			byte[] buffer =Base64.decode(base64Code,Base64.DEFAULT);
			FileOutputStream out = new FileOutputStream(Constants.PIC_PATH+name);
			 out.write(buffer);
			 out.close();
	}
	 /**
	  * �ļ�ת����base64λ
	  */
	 public static String encodeBase64File(String path) {
		 File  file = new File(path);
		 FileInputStream inputFile;
		 byte[] buffer=null;
		try {
			inputFile = new FileInputStream(file);
			buffer = new byte[(int)file.length()];
			 inputFile.read(buffer);
			inputFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		         return Base64.encodeToString(buffer,Base64.DEFAULT);
		 }
	 /* ��ʵ����ת����json�ַ���
      * 
      * @param <T>
      * @param o ʵ������
      * @return
      */
     public static String getJsonStringByEntity(Object o) {
             String strJson = "";
             Gson gson = new Gson();
             strJson = gson.toJson(o);
             return strJson;
     }
     
	 /**
      * ��ʵ�����Listת����json�ַ���
      * @param <T>
      * @param list  ��Ҫת����ʵ����List
      * @return
      * @throws JSONException
      */
     public static <E> String getJsonStringByList(List<E> list) {
             StringBuilder strJson = new StringBuilder("[");
             Gson gson = new Gson();
             for (int i = 0; i < list.size(); i++) {
                     if (i != list.size() - 1) {
                             strJson.append(gson.toJson(list.get(i)) + ",");
                     } else {
                             strJson.append(gson.toJson(list.get(i)));
                     }
             }
             strJson = strJson.append("]");
             return strJson.toString();
     }
     
     /**
 	 * �������뷨
 	 */
 	public static void hideInput(View view){
 		// �������뷨
 		InputMethodManager imm = (InputMethodManager) BaseApplication.application.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
 		//ǿ������
 		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
// 		// ��ʾ�����������뷨
// 		imm.toggleSoftInput(0, InputMethodManager.RESULT_HIDDEN);
 	}
 	/**
 	 * �����뷨
 	 */
 	public static void OpenInput(View view){
 		// �������뷨
 		InputMethodManager imm = (InputMethodManager) BaseApplication.application.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
 		//��ʾ
 		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
 	}
}
