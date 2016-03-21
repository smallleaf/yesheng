package com.example.graduate.activity.common;


import java.util.HashMap;
import java.util.Map;

import org.androidpn.client.ServiceManager;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.common.Common;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.student.bean.Student;
import com.example.graduate.student.bean.Teacher;
import com.example.graduate.view.CustomProgressDialog;
import com.example.graduateproject.R;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class StartActivity extends Activity{

	
	private AlphaAnimation start_anima;
	View view;
	SharedPreferences sharedPreferences;
	int role;
	int dataInit=0;//1表示登陆成功 2-表示异常
	private Handler handler=new Handler()
	{
		public void handleMessage(Message message){
			super.handleMessage(message);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = View.inflate(this, R.layout.splash, null);
		setContentView(view);
		sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		role=sharedPreferences.getInt(Common.USER_ROLE, 1);
		initData();
	}

	private void initData() {
		start_anima = new AlphaAnimation(0.3f, 1.0f);
		start_anima.setDuration(5000);
		view.startAnimation(start_anima);
		start_anima.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				if(sharedPreferences.getBoolean(Common.AUTO_LOGIN, false))
				{
					String userId=sharedPreferences.getString(Common.LOGIN_USERID, "");
					String passWord=sharedPreferences.getString(Common.LOGIN_PASSWORD, "");
					new Login().execute(userId,passWord);
				}
				else {
					dataInit=2;
				}

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
//				if(Utils.checkNetWorkIsAvailable(getApplicationContext())){
//					redirectTo();
//				}else {
//					DialogUtil.showToast(getApplicationContext(), "联网失败，请检查网络");
//				}
				Intent intent=new Intent();
				if(dataInit==1)
				{
					intent.setClass(StartActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
				if(dataInit==2)
				{
					intent.setClass(StartActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				}
//				
			}
		});
	}

	public class Login extends AsyncTask<String, String, JSONObject>
	{
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			Map<String, String> map=new HashMap<String, String>();
			map.put("studentId", params[0]);
			map.put("passWord", params[1]);
			String url;
			if(role==1)
			{
				url=HttpUtil.BASE_URL+"studentAction_login.action";
			}
			else {
				url=HttpUtil.BASE_URL+"te_login.action";
			}
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				DialogUtil.showToastThread(handler, StartActivity.this, "服务器连接异常");
				dataInit=2;
				e.printStackTrace();
			}
			return jsonObject;
		}
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			
			try {
				if(result!=null&&result.getBoolean("state"))
				{
					Gson gson=new Gson();
					if(role==1)
					{
						Student student=gson.fromJson(result.getString("student"), Student.class);
						Intent it = new Intent(StartActivity.this,MainActivity.class);
						Bundle bundle=new Bundle();
						bundle.putSerializable("student", student);
						it.putExtras(bundle);
						startActivity(it);
						//开启推送服务
						cereatTuiSong();
					}
					else
					{
						Teacher teacher=gson.fromJson(result.getString("teacher"), Teacher.class);
						Intent it = new Intent(StartActivity.this,MainActivity.class);
						startActivity(it);
					}
					
					finish();
					dataInit=1;
					
				}
				else {
					dataInit=2;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void cereatTuiSong() {
		// TODO Auto-generated method stub
		 // Start the service
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.ic_launcher);
        serviceManager.startService();
	}

}
