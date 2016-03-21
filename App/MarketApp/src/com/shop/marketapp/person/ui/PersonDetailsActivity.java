package com.shop.marketapp.person.ui;

import com.example.marketapp.R;
import com.example.marketapp.R.id;
import com.example.marketapp.R.layout;
import com.shop.marketapp.activity.LoginActivity;
import com.shop.marketapp.activity.PersonalActivity;
import com.shop.marketapp.constant.Constants;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonDetailsActivity extends Activity {

	//注销
	private TextView textView;
	private SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_person_details);
		((TextView)findViewById(R.id.common_tv_back)).setText("我的信息");
		textView=(TextView)findViewById(R.id.common_tv_right_text);
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		textView.setText("注销");
		textView.setVisibility(View.VISIBLE);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor editor=sharedPreferences.edit();
				editor.putBoolean(Constants.SP_ISLOGIN, false);
				editor.commit();
				setResult(PersonalActivity.LOGINSUCCESSREQUEST);
				PersonDetailsActivity.this.finish();
			}
		});
	}


}
