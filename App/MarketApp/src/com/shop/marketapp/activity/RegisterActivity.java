package com.shop.marketapp.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.marketapp.R;
import com.example.marketapp.R.layout;
import com.example.marketapp.R.menu;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.DialogUtil;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.widget.CustomProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity implements OnClickListener{

	private TextView backTextView;
	private TextView personNameTextView;
	private TextView passWordTextView;
	private TextView passWordAagainTextView;
	private Button registerButton;
	private CustomProgressDialog customProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		backTextView=(TextView)findViewById(R.id.common_tv_back);
		backTextView.setText("ע��");
		backTextView.setOnClickListener(this);
		personNameTextView=(TextView)findViewById(R.id.activity_register_et_phone);
		passWordTextView=(TextView)findViewById(R.id.activity_register_et_pass);
		passWordAagainTextView=(TextView)findViewById(R.id.activity_register_et_passagin);
		registerButton=(Button)findViewById(R.id.activity_register_bt_login);
		registerButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.activity_register_bt_login:
			String userName=personNameTextView.getText().toString();
			String passWord=passWordTextView.getText().toString();
			String passWordAgain=passWordAagainTextView.getText().toString();
			if(inputOK(userName, passWord, passWordAgain))
				new RegisterAsTask().execute(userName,passWord);
			break;

		default:
			break;
		}
	}
	/**
	 * �ж��Ƿ�������ȷ
	 * @return
	 */
	public boolean inputOK(String userName,String passWord,String passWordAgain){
		if(userName.equals("")){
			DialogUtil.showToast(RegisterActivity.this, "�û�������Ϊ��");
			return false;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); 
		Matcher m = p.matcher(userName); 
		if (!m.matches()) {
			DialogUtil.showToast(RegisterActivity.this, "��������ȷ���ֻ�����");
			return false;
		}
		else if(passWord.equals("")){
			DialogUtil.showToast(RegisterActivity.this, "���벻��Ϊ��");
			return false;
		}
		else if(!passWord.equals(passWordAgain)){
			DialogUtil.showToast(RegisterActivity.this, "�������벻һ��");
			return false;
		}
		return true;
	}
	public class RegisterAsTask extends AsyncTask<String, JSONObject, JSONObject>{
		protected void onPreExecute() {
			customProgressDialog = new CustomProgressDialog(
					RegisterActivity.this, "����ע����...", R.anim.rotate_loading);
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
			String url=HttpUtil.BASE_URL+"user!signup.action";
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
					String returnMsg=result.getString("user");
					DialogUtil.showToast(RegisterActivity.this, returnMsg);
					
					if(returnMsg.equals("��ϲע��ɹ�!"))
					{
						SharedPreferences sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
						Editor editor=sharedPreferences.edit();
						editor.putString(Constants.SP_LOGIN_NAME,personNameTextView.getText().toString());
						editor.commit();
						Intent intent=new Intent();
						intent.setClass(RegisterActivity.this, LoginActivity.class);
						startActivity(intent);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
	}

}
