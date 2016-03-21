package com.shop.marketapp.bean.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.shop.marketapp.bean.Goods;
import com.shop.marketapp.bean.MerChantBean;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.Tools;


import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DataBaseManager {
	private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "loc.db"; //保存的数据库文件名
    public static final String PACKAGE_NAME = "com.shop.marketapp.constant";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME+"/"+DB_NAME;  //在手机里存放数据库的位置
    private Context context;
    private SQLiteDatabase writeDb;
    private SQLiteDatabase readDb;
    public DataBaseManager(Context context) {
        this.context = context;
        writeDb=DbHelper.getInstance(context, DB_NAME, null, 1).getWritableDatabase();
        readDb=DbHelper.getInstance(context, DB_NAME, null, 1).getReadableDatabase();
    }
	/**
	 * 商家表推荐表添加信息
	 * @param 
	 * id 商家是1 推荐是2
	 */
    public void insertMerchantRecomment(ArrayList<MerChantBean> merChantBeans,int id){
    	Log.v("正在插入", "正在插入");
    	for(int i=0;i<merChantBeans.size();i++){
    		MerChantBean merChantBean=merChantBeans.get(i);
    		ContentValues contentValues=new ContentValues();
    		contentValues.put("id", merChantBean.getId());
    		contentValues.put("kind", id);
    		contentValues.put("name", merChantBean.getName());
    		contentValues.put("pic", merChantBean.getName()+".jpg");
    		writeDb.insert("merChantRecommend", null, contentValues);
    	}
    }
    /**
     * 商家表推荐表读取信息
     */
    public ArrayList<MerChantBean> findMerchant(){
    	ArrayList<MerChantBean> merChantBeans=new ArrayList<MerChantBean>();
    	int i=1;
    	Cursor cursor=readDb.query("merChantRecommend", new String[]{"id","name","pic"} , "kind=1", null, null, null, null);
    	while(cursor.moveToNext()){
    		MerChantBean merChantBean=new MerChantBean();
    		
    		merChantBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
    		merChantBean.setName(cursor.getString(cursor.getColumnIndex("name")));
    		String pathpic=cursor.getString(cursor.getColumnIndex("pic"));
    		System.out.println("代销"+pathpic);
    		System.out.println("代销"+(i++));
    		merChantBean.setBitmapstr(Tools.encodeBase64File(Constants.PIC_PATH+pathpic));
    		merChantBeans.add(merChantBean);
    	}
    	return merChantBeans;
    }
    /**
     * 插入商品详细信息
     */
    public void insertDetailsGoods(ArrayList<Goods> goodsArrayList){
    	for(int i=0;i<goodsArrayList.size();i++){
    		Goods goods=goodsArrayList.get(i);
    		ContentValues contentValues=new ContentValues();
    		contentValues.put("id", goods.getId());
    		contentValues.put("goodsKind", goods.getType());
    		contentValues.put("name", goods.getName());
    		/**考率物品过多图片命名问题
    		 */
    		contentValues.put("picture", goods.getName());
    		contentValues.put("price", goods.getPrice());
    		contentValues.put("merchantId", goods.getSid());
    		writeDb.insert("detailsGoods", null, contentValues);
    	}
    }
    /**
     * 读取商品详细信息
     */
    public ArrayList<Goods> findGoods(int sid,int type){
    	ArrayList<Goods> goodsArrayList=new ArrayList<Goods>();
    	Log.v("类型", String.valueOf(type));
    	Cursor cursor=null;
    	if(type==0){
    		cursor=readDb.query("detailsGoods", new String[]{"id","name","picture","price","goodsKind"} , "merchantId="+sid, null, null, null, null);
    	}else {
    		cursor=readDb.query("detailsGoods", new String[]{"id","name","picture","price","goodsKind"} , "merchantId=? and goodsKind=?", new String[]{String.valueOf(sid),String.valueOf(type)}, null, null, null);
		}
    		while(cursor.moveToNext()){
    		Goods goods=new Goods();
    		goods.setId(cursor.getInt(cursor.getColumnIndex("id")));
    		goods.setType(cursor.getInt(cursor.getColumnIndex("goodsKind")));
    		goods.setName(cursor.getString(cursor.getColumnIndex("name")));
    		goods.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
    		String pathpic=cursor.getString(cursor.getColumnIndex("picture"));
    		goods.setPic(Tools.encodeBase64File(Constants.PIC_PATH+pathpic));
    		goodsArrayList.add(goods);
    	}
    	return goodsArrayList;
    }
}
