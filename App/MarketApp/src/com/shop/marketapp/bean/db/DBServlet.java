package com.shop.marketapp.bean.db;

import java.util.ArrayList;

import com.shop.marketapp.bean.MerChantBean;

import android.content.Context;


public class DBServlet {
	private static final String TAG=DBServlet.class.getSimpleName();
	private static Context mContext=null;
	
	public DBServlet(Context mContext){
		this.mContext=mContext;
	}
//	public static boolean saveMerchantRecomment(ArrayList<MerChantBean> merChantBeans){
////		dbHandle dbH = dbHandle.getInstance();
////		SQLiteDatabase db = dbH.getWritableDatabase();
//	}
}
