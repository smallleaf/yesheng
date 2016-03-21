package com.example.graduate.student.activity;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.activity.common.LoginActivity;
import com.example.graduate.activity.common.ScreenManager;
import com.example.graduate.common.Common;
import com.example.graduateproject.R;
import com.example.graduateproject.R.layout;
import com.example.graduateproject.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SystemActivity extends BaseActivity implements OnClickListener{

	private Button loginOut;
	private Button exit;
	SharedPreferences sharedPreferences;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setNeedBackGesture(true);
		setContentView(R.layout.activity_system);
		((TextView)findViewById(R.id.common_tv_back)).setText("œµÕ≥…Ë÷√");
		sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		editor=sharedPreferences.edit();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		loginOut=(Button)findViewById(R.id.activity_system_bt_login_out);
		exit=(Button)findViewById(R.id.activity_system_bt_exit);
		loginOut.setOnClickListener(this);
		exit.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_system_bt_login_out:
			editor.putBoolean(Common.AUTO_LOGIN, false);
			editor.commit();
			Intent intent=new Intent(SystemActivity.this,LoginActivity.class);
			ScreenManager.getScreenManager().popActivity();
			startActivity(intent);
			finish();
			break;

		case R.id.activity_system_bt_exit:
			ScreenManager.getScreenManager().popActivity();
			finish();
			break;
		default:
			break;
		}
	}
}
