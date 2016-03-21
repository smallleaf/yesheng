package com.wtu.university.slidingMenu.right;

import com.example.universityconnection.R;
import com.wtu.university.MainInterfaceActivity;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout logout;
	SharedPreferences sharedPreferences;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		((TextView) findViewById(R.id.title)).setText("我的设置");

		logout = (RelativeLayout) findViewById(R.id.logout);
		logout.setOnClickListener(this);

		sharedPreferences = getSharedPreferences(Constants.SP_NAME,
				MODE_PRIVATE);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.logout:
			editor = sharedPreferences.edit();
			editor.putBoolean(Constants.SP_IS_PASSWORD, false);
			editor.putBoolean(Constants.SP_ATUON_LOGIN, false);
			editor.putBoolean(Constants.SP_LOGOUT, true);
			editor.putString(Constants.SP_PASSWORD, "");
			editor.putString(Constants.SP_USER_PICTURE, "null");
			editor.putBoolean(Constants.SP_LOGIN_STATE, false);
			editor.commit();
			Intent intent = new Intent(getApplicationContext(),
					MainInterfaceActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		editor = sharedPreferences.edit();
		editor.putBoolean(Constants.SP_BACK, true);
		editor.commit();
		Intent intent = new Intent(getApplicationContext(),
				MainInterfaceActivity.class);
		startActivity(intent);
		finish();
	}
}
