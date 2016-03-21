package com.wtu.university.slidingMenu.right.information;


import com.example.universityconnection.R;
import com.example.universityconnection.R.array;
import com.example.universityconnection.R.id;
import com.example.universityconnection.R.layout;
import com.wtu.university.MainInterfaceActivity;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.slidingMenu.right.PersonInrformationActivity;
import com.wtu.university.slidingMenu.right.PersonInrformationActivity.ChangeUserInfoTask;
import com.wtu.university.slidingMenu.right.information.adapter.IdeasExpandableListAdapter;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UniversityActivity extends BaseActivity {
	
	private ExpandableListView mListView;
	private IdeasExpandableListAdapter adapter;
	private RelativeLayout titlebaRelativeLayout;
	private ImageView searchImageView;
	private TextView titleTextView;
	private Resources resources;
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_university);
		titlebaRelativeLayout=(RelativeLayout)findViewById(R.id.title_university);
		titleTextView=(TextView)findViewById(R.id.title);
		titleTextView.setText("选择学校");
		mListView=(ExpandableListView)findViewById(R.id.Bookbox_explistview);
		resources=getResources();
		init();
//		new IdeasExpandableListAdapter(UniversityActivity.this);
//		setListAdapter(new IdeasExpandableListAdapter(this));
		
	}
	private void init() {
		// TODO Auto-generated method stub
		String province[]=resources.getStringArray(R.array.university_province_item);
		final String university[][]={
				resources.getStringArray(R.array.beijin_university_province_item),
				resources.getStringArray(R.array.university_tianjin_province_item)
		};
		adapter=new IdeasExpandableListAdapter(this, province, university);
		mListView.setAdapter(adapter);
		mListView.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int groupPosition , int childPosition , long arg4) {
				// TODO Auto-generated method stub
				SharedPreferences sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
				Editor editor=sharedPreferences.edit();
				editor.putString(Constants.SP_UNIVERSITY, university[groupPosition][childPosition]);
				editor.commit();
				Intent intent=new Intent();
				intent.setClass(UniversityActivity.this, PersonInrformationActivity.class);
				startActivity(intent);
				finish();
				return true;
			}
			
		});
		
				
		
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
