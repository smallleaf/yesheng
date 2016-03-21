package com.shop.marketapp.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.platform.comapi.map.p;
import com.example.marketapp.R;
import com.example.marketapp.R.layout;
import com.shop.marketapp.base.BaseActivity;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.DialogUtil;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.widget.CustomProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener{

	private TextView registerTextView;
	private TextView backTextView;
	private EditText userNameEditText;
	private EditText passWordEditText;
	private Button loginButton;
	private SharedPreferences sharedPreferences;
	private CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		Log.v("username", "fsdf");
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		initView();
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		backTextView=(TextView)findViewById(R.id.common_tv_back);
		backTextView.setText("  登陆");
		
		registerTextView=(TextView)findViewById(R.id.common_tv_right_text);
		registerTextView.setText("注册");
		registerTextView.setVisibility(View.VISIBLE);

		registerTextView.setOnClickListener(this);
		loginButton=(Button)findViewById(R.id.activity_login_bt_login);
		loginButton.setOnClickListener(this);
		
		userNameEditText=(EditText)findViewById(R.id.activity_login_user);
		passWordEditText=(EditText)findViewById(R.id.activity_login_pass);
		String userName=sharedPreferences.getString(Constants.SP_LOGIN_NAME, "");
		if(userName!=null){
			userNameEditText.setText(userName);
		}
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.common_tv_right_text:
			Intent intent=new Intent();
			intent.setClass(this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.activity_login_bt_login:
			String userName=userNameEditText.getText().toString();
			String passWord=passWordEditText.getText().toString();
			if(userName.equals("")){
				DialogUtil.showToast(LoginActivity.this, "用户名不能为空！");
			}
			else if(passWord.equals("")){
				DialogUtil.showToast(LoginActivity.this, "密码不能为空！");
			}
			else {
				new LoginAsTask().execute(userName,passWord);
			}
			break;

		default:
			break;
		}
	}

	public class LoginAsTask extends AsyncTask<String, JSONObject, JSONObject>{
		protected void onPreExecute() {
			customProgressDialog = new CustomProgressDialog(
					LoginActivity.this, "正在登陆中...", R.anim.rotate_loading);
			customProgressDialog.show();
			Log.v("test", "onPreExecute");
			super.onPreExecute();
		}
		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			map.put("phone", arg0[0]);
			map.put("pwd", arg0[1]);
			String url=HttpUtil.BASE_URL+"user!signin.action";
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null){
				try {
					customProgressDialog.cancel();
					if(result.getString("user").equals("ok"))
					{
						DialogUtil.showToast(LoginActivity.this, "登陆成功");
						Editor editor=sharedPreferences.edit();
						editor.putString(Constants.SP_LOGIN_NAME, userNameEditText.getText().toString().trim());
						editor.putBoolean(Constants.SP_ISLOGIN, true);
						editor.commit();
						setResult(PersonalActivity.LOGINSUCCESSREQUEST);
						LoginActivity.this.finish();
					}
					else
					{
						DialogUtil.showToast(LoginActivity.this, "登陆失败，请检查用户名和密码！！！");
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		else{
			DialogUtil.showToast(LoginActivity.this, "登陆失败，请检查用户名和密码！！！");
		}
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
