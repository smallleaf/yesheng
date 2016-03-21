package com.wtu.university;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.universityconnection.R;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.slidingMenu.right.server.GetUserInfo;
import com.wtu.university.view.CustomProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class AppLoadingActivity extends Activity implements OnClickListener {
	private AlphaAnimation start_animAlphaAnimation;
	private View view;
	private TextView skipAppLoading;
	SharedPreferences sharedPreferences;
	private boolean pictureFinish=false;
	private boolean autoLogin=true;//无自动登陆
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(pictureFinish&&autoLogin)
				redirectTo();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		view = View.inflate(this, R.layout.welcome, null);
		Log.v("test", "test");
		setContentView(view);
		sharedPreferences = getSharedPreferences(Constants.SP_NAME,
				MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(Constants.conncectServer, false);
		editor.commit();
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		start_animAlphaAnimation = new AlphaAnimation(0.3f, 1.0f);
		start_animAlphaAnimation.setDuration(3000);
		view.startAnimation(start_animAlphaAnimation);
		start_animAlphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				pictureFinish=true;
				Message msg=new Message();
				handler.sendMessage(msg);
			
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		skipAppLoading = (TextView) findViewById(R.id.skipAppLoading);
		skipAppLoading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						MainInterfaceActivity.class));
				finish();
			}
		});

		if (sharedPreferences.getBoolean(Constants.SP_ATUON_LOGIN, false)) {
			autoLogin=false;
			new AutoLoginTask().execute();
		}else {
			Editor editor = sharedPreferences.edit();
			editor.putBoolean(Constants.SP_LOGIN_STATE, false);
			editor.commit();
		}
	}

	private void redirectTo() {
		startActivity(new Intent(getApplicationContext(),
				MainInterfaceActivity.class));
		finish();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	public class AutoLoginTask extends AsyncTask<String, String, JSONObject> {
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Message msg=new Message();
			String url = HttpUtil.BASE_URL + "loginAction.action";
			Map<String, String> map = new HashMap<String, String>();
			map.put("loginusername",
					sharedPreferences.getString(Constants.SP_USER_NAME, ""));
			map.put("loginpassword",
					sharedPreferences.getString(Constants.SP_PASSWORD, ""));
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(HttpUtil.postRequest(url, map));
				autoLogin=true;
				handler.sendMessage(msg);
				return jsonObject;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				autoLogin=true;
				handler.sendMessage(msg);
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				DialogUtil.showToastThread(handler, AppLoadingActivity.this,
						"连接服务器失败，请检查网络");
				e.printStackTrace();
				autoLogin=true;
				handler.sendMessage(msg);
				return null;
			}

		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.v("test", "onPostExecute");
			if (result != null) {
				try {
					boolean loginSuccess = result.getBoolean("login");
					if (loginSuccess) {
						Editor editor = sharedPreferences.edit();
						editor.putBoolean(Constants.conncectServer, true);
						editor.putBoolean(Constants.SP_LOGIN_STATE, true);
						editor.commit();

					} else {
						Editor editor = sharedPreferences.edit();
						editor.putBoolean(Constants.SP_LOGIN_STATE, false);
						editor.commit();
						DialogUtil.showToast(AppLoadingActivity.this,
								"自动登录失败，请检查用户名和密码！！！");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

}
