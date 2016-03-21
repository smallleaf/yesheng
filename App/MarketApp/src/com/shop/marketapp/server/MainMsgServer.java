package com.shop.marketapp.server;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shop.marketapp.activity.MainActivity;
import com.shop.marketapp.bean.MerChantBean;
import com.shop.marketapp.bean.db.DataBaseManager;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.constant.Tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 
 * @author Administrator
 *获得主页显示的数据
 */
public class MainMsgServer implements Runnable{

	private Context mContext;
	private Handler handler;
	private ArrayList<MerChantBean> merChantBeans;
	public MainMsgServer(Context mContext,Handler handler,ArrayList<MerChantBean> merChantBeans)
	{
		this.handler=handler;
		this.merChantBeans=merChantBeans;
		this.mContext=mContext;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.e("正在获取商家", "正在获取商家");
		String url=HttpUtil.BASE_URL+"main!getStores.action";
		JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.getRequst(url));
				JSONArray jsonArray=new JSONArray(jsonObject.getString("stores"));
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject2=jsonArray.optJSONObject(i);
					MerChantBean merChantBean=new MerChantBean();
					String userName=jsonObject2.getString("sname");
					String pic=jsonObject2.getString("pic");
					merChantBean.setId(jsonObject2.getInt("id"));
					merChantBean.setName(userName);
					merChantBean.setBitmapstr(pic);
					Tools.decoderBase64File(pic, userName+".jpg");
					merChantBeans.add(merChantBean);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		DataBaseManager dataBaseManager=new DataBaseManager(mContext);
		dataBaseManager.insertMerchantRecomment(merChantBeans, 1);
		Message msgMessage=new Message();
		handler.sendMessage(msgMessage);
	}

}
