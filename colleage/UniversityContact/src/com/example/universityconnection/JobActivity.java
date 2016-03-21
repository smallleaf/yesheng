package com.example.universityconnection;

import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.slidingMenu.right.PersonInrformationActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class JobActivity extends BaseActivity {
	private EditText editText;
	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_job);
		editText=(EditText)findViewById(R.id.job_input);
		textView=(TextView)findViewById(R.id.right_text);
		textView.setText("确定");
		textView.setVisibility(View.VISIBLE);
		textView.setOnClickListener(new TextView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPreferences= getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
				Editor editor=sharedPreferences.edit();
				editor.putString(Constants.SP_JOB, editText.getText().toString());
				editor.commit();
				Intent intent=new Intent();
				intent.setClass(JobActivity.this, PersonInrformationActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
