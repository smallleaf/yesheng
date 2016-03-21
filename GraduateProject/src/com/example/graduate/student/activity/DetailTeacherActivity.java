package com.example.graduate.student.activity;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.student.bean.DetailsTeacher;
import com.example.graduateproject.R;
import com.example.graduateproject.R.layout;
import com.example.graduateproject.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class DetailTeacherActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setNeedBackGesture(true);
		setContentView(R.layout.activity_detail_teacher);
		Bundle bundle=this.getIntent().getExtras();
		DetailsTeacher detailsTeacher=(DetailsTeacher) bundle.getSerializable("teacher");
		((TextView)findViewById(R.id.common_tv_back)).setText("老师信息");
		((TextView)findViewById(R.id.activity_details_teacher_name)).setText(detailsTeacher.getName());
		((TextView)findViewById(R.id.activity_details_teacher_institution)).setText(detailsTeacher.getInstitution());
		((TextView)findViewById(R.id.activity_details_teacher_major)).setText(detailsTeacher.getMajor());
		((TextView)findViewById(R.id.activity_details_teacher_phone)).setText(detailsTeacher.getPhone());
	}


}
