package com.wtu.university.slidingMenu.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.wtu.university.common.HttpUtil;
import com.wtu.university.slidingMenu.left.bean.UserInfoBean;

public class GetUserInfoServer implements Runnable{

	private String userNameString;
	private UserInfoBean userInfoBean;
	private Handler handler;
	public GetUserInfoServer(String userNameString,UserInfoBean userInfoBean,Handler handler){
		this.userNameString=userNameString;
		this.userInfoBean=userInfoBean;
		this.handler=handler;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Map<String, String> map=new HashMap<String, String>();
		System.out.println("获得的数据为  "+userNameString);
		map.put("username", userNameString);
		String url=HttpUtil.BASE_URL+"getUserInfoAction.action";
		try {
			JSONObject jsonArray=new JSONObject(HttpUtil.postRequest(url, map));
			userInfoBean.setName(userNameString);
			userInfoBean.setAssociation(jsonArray.getString("associatition"));
			userInfoBean.setColleage(jsonArray.getString("university"));
			userInfoBean.setJob(jsonArray.getString("job"));
			userInfoBean.setLocation(jsonArray.getString("address"));
			userInfoBean.setHobby(jsonArray.getString("hobby1")+" "+jsonArray.getString("hobby2")+" "
					+jsonArray.getString("hobby3"));
			userInfoBean.setMajor(jsonArray.getString("major"));
			userInfoBean.setPicture(jsonArray.getString("picture"));
			Message msg=new Message();
			handler.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
