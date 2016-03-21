package com.wtu.university.slidingMenu.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.universityconnection.R;
import com.wtu.university.app.AppApplication;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.NewsFragment;
import com.wtu.university.slidingMenu.bean.NewsEntity;
import com.wtu.university.view.CustomProgressDialog;

public class GetHotNewsServer implements Runnable{
	ArrayList<NewsEntity> newsEntities;
	private Handler handler;
	private CustomProgressDialog customProgressDialog;
	private Context mContext;
	//选择那个频道s
	private int id;
	//加载几个数据
	private int newsNum;
	public GetHotNewsServer(ArrayList<NewsEntity> newsEntities, Handler handler,int id,int newsNum){
		this.newsEntities=newsEntities;
		this.handler=handler;
		this.id=id;
		this.newsNum=newsNum;
	}
	@Override
	public  void run() {
		// TODO Auto-generated method stub
		String url=HttpUtil.BASE_URL+"getNewsHotAction.action";
		Map<String, String> map=new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		map.put("newsNum", String.valueOf(newsNum));
		try {
			JSONObject jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
			if(!jsonObject.getBoolean("newHotExist")){
				Log.v("数据不存在", "数据不存在");
			}
			else {
				JSONArray jsonArray=new JSONArray(jsonObject.getString("newsHot"));
				for(int i=0;i<jsonArray.length();i++){
					JSONObject newsJsonObject=jsonArray.optJSONObject(i);
					NewsEntity newsEntity=new NewsEntity();
					newsEntity.setId(Integer.valueOf(newsJsonObject.getString("id")));
					newsEntity.setTitle(newsJsonObject.getString("title"));
					newsEntity.setColleage(newsJsonObject.getString("colleage"));
					newsEntity.setUserPicture(newsJsonObject.getString("userPicture"));
					newsEntity.setPulishTime(newsJsonObject.getString("publicTime"));
					newsEntity.setCommentCounts(Integer.valueOf(newsJsonObject.getString("commentCounts")));
					newsEntity.setContent(newsJsonObject.getString("content"));
					newsEntity.setNews_web(newsJsonObject.getString("news_web"));
					if(newsJsonObject.getString("public_user_kind").equals("0")){
						//来自系统的消息
						System.out.println(newsJsonObject.getString("title")+" vvv"+newsJsonObject.getString("rightPicture"));
						newsEntity.setRightPicture(newsJsonObject.getString("rightPicture"));
						newsEntity.setPicture1(newsJsonObject.getString("picture1"));
						newsEntity.setPicture2(newsJsonObject.getString("picture2"));
						newsEntity.setPicture3(newsJsonObject.getString("picture3"));
					}
					else {
						if(!newsJsonObject.getString("picture1").equals("null"))
						{
							newsEntity.setPicture1Bitmap(Utils.stringtoBitmap(newsJsonObject.getString("picture1")));
						}
						else {
							newsEntity.setPicture1Bitmap(null);
						}
						if(!newsJsonObject.getString("picture2").equals("null"))
						{
							newsEntity.setPicture2Bitmap(Utils.stringtoBitmap(newsJsonObject.getString("picture2")));
						}
						else {
							newsEntity.setPicture2Bitmap(null);
						}
						if(!newsJsonObject.getString("picture3").equals("null"))
						{
							newsEntity.setPicture3Bitmap(Utils.stringtoBitmap(newsJsonObject.getString("picture3")));
						}
						else {
							newsEntity.setPicture3Bitmap(null);
						}
					}
					newsEntity.setPublic_user_kind(Integer.valueOf(newsJsonObject.getString("public_user_kind")));
					newsEntities.add(newsEntity);
				}
			}
			
			
			Message msg=new Message();
			handler.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v("fsdsff", "fsdfsfs");
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO: handle exception
		}
	}
	public  void showToastThread(final Context  content,final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(content, msg, Toast.LENGTH_SHORT).show();
			}

		});
	}
}
