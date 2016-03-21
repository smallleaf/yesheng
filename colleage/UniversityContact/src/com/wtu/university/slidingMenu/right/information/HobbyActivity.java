package com.wtu.university.slidingMenu.right.information;

import com.example.universityconnection.R;
import com.example.universityconnection.R.id;
import com.example.universityconnection.R.layout;
import com.wtu.university.common.Constants;
import com.wtu.university.slidingMenu.right.PersonInrformationActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class HobbyActivity extends Activity {
	private TextView cofinTextView;
	private EditText hobby1;
	private EditText hobby2;
	private EditText hobby3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hobby);
		
		cofinTextView=(TextView)findViewById(R.id.right_text);
		cofinTextView.setVisibility(View.VISIBLE);
		cofinTextView.setText("确定");
		hobby1=(EditText)findViewById(R.id.hobby1);
		hobby2=(EditText)findViewById(R.id.hobby2);
		hobby3=(EditText)findViewById(R.id.hobby3);
		
		cofinTextView.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
				Editor editor=sharedPreferences.edit();
				editor.putString(Constants.SP_HOBBY1, hobby1.getText().toString());
				editor.putString(Constants.SP_HOBBY2, hobby2.getText().toString());
				editor.putString(Constants.SP_HOBBY3, hobby3.getText().toString());
				editor.commit();
				Intent intent=new Intent();
				intent.setClass(HobbyActivity.this, PersonInrformationActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
	}


}
