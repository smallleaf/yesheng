package com.example.graduate.activity.common;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.androidpn.client.ServiceManager;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.common.Common;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.student.activity.TeacherActivity;
import com.example.graduate.student.bean.Student;
import com.example.graduate.student.bean.Teacher;
import com.example.graduate.view.CustomProgressDialog;
import com.example.graduateproject.R;
import com.google.gson.Gson;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.os.Build;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText userNameEt;
	private EditText passWordEt;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private RadioGroup radioGroup;
	private int role=1;//角色判断1--学生 2---老师
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		editor=sharedPreferences.edit();
		userNameEt= (EditText) findViewById(R.id.activity_main_et_username);
		
		
		userNameEt.setText(sharedPreferences.getString(Common.LOGIN_USERID, ""));
		passWordEt=(EditText)findViewById(R.id.activity_main_et_password);
		Button login = (Button) findViewById(R.id.activity_main_bt_login);
		
		radioGroup=(RadioGroup)findViewById(R.id.activity_login_rg_user);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==R.id.activity_login_rb_student)
				{
					role=1;
				}
				else {
					role=2;
				}
				
			}
		});
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		String userName=userNameEt.getText().toString();
		String passWord=passWordEt.getText().toString();
		
		editor.putString(Common.LOGIN_USERID, userName);
		editor.putString(Common.LOGIN_PASSWORD, passWord);
		editor.putInt(Common.USER_ROLE, role);
		editor.commit();
		System.out.println("role+"+role);
		new Login().execute(userName,passWord);
		
	}
	
	private void cereatTuiSong() {
		// TODO Auto-generated method stub
		 // Start the service
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.ic_launcher);
        serviceManager.startService();
	}

	public class Login extends AsyncTask<String, String, JSONObject>
	{
		CustomProgressDialog customProgressDialog;
		protected void onPreExecute() {
			customProgressDialog=new CustomProgressDialog(LoginActivity.this, "正在登陆...", R.anim.rotate_loading);
			customProgressDialog.show();
			super.onPreExecute();
		}
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
				DialogUtil.showToastThread(handler, LoginActivity.this, "服务器连接异常");
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
						Intent it = new Intent(LoginActivity.this,MainActivity.class);
						Bundle bundle=new Bundle();
						bundle.putSerializable("student", student);
						it.putExtras(bundle);
						startActivity(it);
						//开启推送服务
						cereatTuiSong();
					}
					else
					{
//						Teacher teacher=gson.fromJson(result.getString("teacher"), Teacher.class);
						Intent it = new Intent(LoginActivity.this,MainActivity.class);
						startActivity(it);
					}
					
					
					customProgressDialog.cancel();
					
					editor.putBoolean(Common.AUTO_LOGIN, true);
					editor.commit();
					
					finish();
					
					
				}
				else {
					customProgressDialog.cancel();
					DialogUtil.showToast(LoginActivity.this, "用户名或密码错误！");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
