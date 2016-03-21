package com.shop.marketapp.bean.db;

import com.baidu.location.f.c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper{

	private static DbHelper mInstance=null;
	
	
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public synchronized static DbHelper getInstance(Context context, String name, CursorFactory factory,
			int version){
		if(mInstance==null){
			mInstance=new DbHelper(context,name,factory,version);
		}
		return mInstance;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.beginTransaction();
		try {
			Log.v("正在创建","正在创建");
			db.execSQL(merChantRecommend().toString());
			db.execSQL(detailsGoods().toString());
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 首页商家与推荐表
	 */
	public StringBuffer merChantRecommend(){
		StringBuffer sb=new StringBuffer();
		sb.append("create table if not exists merChantRecommend (");
		sb.append("id interger primary key,");
		sb.append("kind interger,");
		sb.append("name TEXT,");
		sb.append("pic TEXT )");
		return sb;
	}
	/**
	 *获得商家详细表
	 **/
	public StringBuffer detailsGoods(){
		StringBuffer sb=new StringBuffer();
		sb.append("create table if not exists detailsGoods (");
		sb.append("id interger primary key,");
		sb.append("goodsKind interger,");
		sb.append("merchantId interger,");
		sb.append("name TEXT,");
		sb.append("price REAL,");
		sb.append("buy_money REAL,");
		sb.append("picture TEXT )");
		return sb;
	}
	
	

}
