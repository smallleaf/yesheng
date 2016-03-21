package com.wtu.university.slidingMenu.right.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wtu.university.common.Constants;
import com.wtu.university.common.HttpUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Space;

public class GetUserInfo implements Runnable{
	private Context mContext;
	private Handler handler;
	public GetUserInfo(Context mContext,Handler handler){
		this.mContext=mContext;
		this.handler=handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SharedPreferences sh=mContext.getSharedPreferences(Constants.SP_NAME, mContext.MODE_PRIVATE);
		Editor editor=sh.edit();
		Map<String, String> map=new HashMap<String, String>();
		map.put("username", sh.getString(Constants.SP_USER_NAME, ""));
		String url=HttpUtil.BASE_URL+"getUserInfoAction.action";
		try {
			JSONObject jsonArray=new JSONObject(HttpUtil.postRequest(url, map));
			editor.putString(Constants.SP_UNIVERSITY,jsonArray.getString("university"));
			editor.putString(Constants.SP_HOBBY1,jsonArray.getString("hobby1"));
			editor.putString(Constants.SP_HOBBY2,jsonArray.getString("hobby2"));
			editor.putString(Constants.SP_HOBBY3,jsonArray.getString("hobby3"));
			editor.putString(Constants.SP_JOB,jsonArray.getString("job"));
			editor.putString(Constants.SP_MAJOR,jsonArray.getString("major"));
			if(!jsonArray.getString("picture").equals("null"))
				editor.putString(Constants.SP_USER_PICTURE,jsonArray.getString("picture"));
			
			editor.putString(Constants.SP_ASSOCIATITION,jsonArray.getString("associatition"));
			editor.putString(Constants.SP_ADDRESS,jsonArray.getString("address"));
			editor.commit();
			Message message=new Message();
			handler.sendMessage(message);
			Log.v("地址", sh.getString(Constants.SP_ADDRESS, ""));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
		}
	}

}
