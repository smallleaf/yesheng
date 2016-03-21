package com.wtu.university.slidingMenu.right.information;

import com.example.universityconnection.R;
import com.example.universityconnection.R.layout;
import com.example.universityconnection.R.menu;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.slidingMenu.right.PersonInrformationActivity;
import com.wtu.university.slidingMenu.right.information.adapter.IdeasExpandableListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class InformationMajorActivity extends BaseActivity implements OnClickListener{
	private ExpandableListView mListView;
	private IdeasExpandableListAdapter adapter;
	private Resources resources;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_university);
		((TextView)findViewById(R.id.title)).setText("选择专业");
		mListView=(ExpandableListView)findViewById(R.id.Bookbox_explistview);
		resources=getResources();
		init();
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {

		default:
			break;
		}
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
				editor.putString(Constants.SP_MAJOR, university[groupPosition][childPosition]);
				editor.commit();
				Intent intent=new Intent();
				intent.setClass(InformationMajorActivity.this, PersonInrformationActivity.class);
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
