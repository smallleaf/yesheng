package com.shop.marketapp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.shop.marketapp.bean.Goods;
import com.shop.marketapp.bean.MerChantBean;
import com.shop.marketapp.bean.db.DataBaseManager;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.constant.Tools;

public class DetaileMerchantServer implements Runnable{
	private Handler handler;
	private int sid;
	private ArrayList<Goods> goodsaArrayList;
	private Context mContext;
	public DetaileMerchantServer(Context mContext,Handler handler,int sid,ArrayList<Goods> goods){
		this.sid=sid;
		this.goodsaArrayList=goods;
		this.mContext=mContext;
		this.handler=handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Map<String, String> map=new HashMap<String, String>();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("fdf+"+sid);
		map.put("sid", String.valueOf(sid));
		String url=HttpUtil.BASE_URL+"store!getGoods.action";
		JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
				JSONArray jsonArray=new JSONArray(jsonObject.getString("goods"));
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject2=jsonArray.optJSONObject(i);
					Goods goods=new Goods();
					goods.setId(jsonObject2.getInt("id"));
					String name=jsonObject2.getString("name");
					goods.setName(name);
					Log.v("znemg", name);
					goods.setCamount(jsonObject2.getInt("camount"));
					String picString=jsonObject2.getString("pic");
					Tools.decoderBase64File(picString, name);
					goods.setPic(picString);
					goods.setPrice(jsonObject2.getDouble("price"));
					goods.setSales(jsonObject2.getDouble("sales"));
					goods.setSid(jsonObject2.getInt("sid"));
					goods.setStocks(jsonObject2.getDouble("stocks"));
					goods.setType(jsonObject2.getInt("type"));
					goods.setGoodsale(0);
					goodsaArrayList.add(goods);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		DataBaseManager dataBaseManager=new DataBaseManager(mContext);
		dataBaseManager.insertDetailsGoods(goodsaArrayList);
		Message msgMessage=new Message();
		handler.sendMessage(msgMessage);
		Log.v("正咋执行", "真该制定法");
	}

}
