package com.example.graduate.student.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.common.Common;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.student.bean.DetailsStudent;
import com.example.graduate.view.CustomProgressDialog;
import com.example.graduateproject.R;
import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StudentInfoActivity extends BaseActivity implements OnClickListener{

	private EditText phoneEditText;
	private LinearLayout firstVolunteer;
	private LinearLayout secondeVolunteer;
	private LinearLayout confirmLayout;
	private DetailsStudent detailsStudent;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	String studentId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setNeedBackGesture(true);
		setContentView(R.layout.activity_student_info);
		((TextView)findViewById(R.id.common_tv_back)).setText("个人信息");
		Bundle bundle=this.getIntent().getExtras();
		studentId=bundle.getString("studentId");
		sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		editor=sharedPreferences.edit();
		initView();
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		phoneEditText=(EditText)findViewById(R.id.student_info_activity_phone);
		firstVolunteer=(LinearLayout)findViewById(R.id.student_info_ll_first_volunteer);
		secondeVolunteer=(LinearLayout)findViewById(R.id.student_info_ll_second_volunteer);
		confirmLayout=(LinearLayout)findViewById(R.id.common_tv_right);
		confirmLayout.setVisibility(View.VISIBLE);
		firstVolunteer.setOnClickListener(this);
		secondeVolunteer.setOnClickListener(this);
		confirmLayout.setOnClickListener(this);
	}

	public class StudentInfoTask extends AsyncTask<String, String, JSONObject>
	{

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=HttpUtil.BASE_URL+"st_getStudentInfo.action?studentId="+params[0];
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.getRequst(url));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;
		}
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			try {
				if(result.getBoolean("state"))
				{
					Gson gson=new Gson();
					detailsStudent=gson.fromJson(result.getString("detailsStudent"), DetailsStudent.class);
					
					
					((TextView)findViewById(R.id.student_info_activity_id)).setText(detailsStudent.getStudentId());
					((TextView)findViewById(R.id.student_info_activity_name)).setText(detailsStudent.getName());
					((TextView)findViewById(R.id.student_info_activity_institution)).setText(detailsStudent.getInstitution());
					((TextView)findViewById(R.id.student_info_activity_major)).setText(detailsStudent.getMajor());
					((TextView)findViewById(R.id.student_info_activity_class)).setText(detailsStudent.getClass_name());
					
					if(detailsStudent.getFirst_volunteer()!=0)
					{
						if(detailsStudent.getFirst_volunteer_state()==0)
						{
							String content=detailsStudent.getFirst_volunteer_name()+"(正在审核)";
							SpannableStringBuilder stringBuilder=new SpannableStringBuilder(content);
							stringBuilder.setSpan(new ForegroundColorSpan(Color.RED), detailsStudent.getFirst_volunteer_name().length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							((TextView)findViewById(R.id.student_info_tv_first_volunteer)).setText(stringBuilder);
						}
						else {
							String content=detailsStudent.getFirst_volunteer_name()+"(已通过)";
							SpannableStringBuilder stringBuilder=new SpannableStringBuilder(content);
							stringBuilder.setSpan(new ForegroundColorSpan(Color.RED), detailsStudent.getFirst_volunteer_name().length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							((TextView)findViewById(R.id.student_info_tv_first_volunteer)).setText(stringBuilder);
						}
					}
					else {
						((TextView)findViewById(R.id.student_info_tv_first_volunteer)).setText("未选择");
					}
					if (detailsStudent.getSecond_volunteer()!=0) {
						if(detailsStudent.getSecond_volunteer_state()==0)
						{
						String content=detailsStudent.getSecond_volunteer_name()+"(正在审核)";
						Log.v("=======", content);
						SpannableStringBuilder stringBuilder=new SpannableStringBuilder(content);
						stringBuilder.setSpan(new ForegroundColorSpan(Color.RED), detailsStudent.getSecond_volunteer_name().length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						((TextView)findViewById(R.id.student_info_tv_second_volunteer)).setText(stringBuilder);
				
						}
						else {
							String content=detailsStudent.getSecond_volunteer_name()+"(已通过)";
							SpannableStringBuilder stringBuilder=new SpannableStringBuilder(content);
							stringBuilder.setSpan(new ForegroundColorSpan(Color.RED), detailsStudent.getSecond_volunteer_name().length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							((TextView)findViewById(R.id.student_info_tv_second_volunteer)).setText(stringBuilder);
						}
					}
					else {
						((TextView)findViewById(R.id.student_info_tv_second_volunteer)).setText("未选择");
					}
					
					phoneEditText.setText(detailsStudent.getTel());
					
				}
				else {
					DialogUtil.showToast(StudentInfoActivity.this, "数据获取失败!");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.student_info_ll_first_volunteer:
			AlertDialog.Builder builder=new Builder(StudentInfoActivity.this);
			builder.setTitle("提示");
			if(detailsStudent.getFirst_volunteer()!=0)
			{
				if(detailsStudent.getFirst_volunteer_state()==0)
				{
					builder.setMessage("第一志愿正在审核，是否确定取消？");
					builder.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new DeteCourceTask("1").execute(String.valueOf(detailsStudent.getFirst_volunteer_id()));
						}
						
					});
					builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					builder.show();
				}
				else {
					DialogUtil.showToast(StudentInfoActivity.this, "选课成功，不能更改");
				}
			}else {
				builder.setMessage("第一志愿未选择,去选择？");
				builder.setPositiveButton("去选择", new android.content.DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(StudentInfoActivity.this,InstitutionActivity.class);
						startActivity(intent);
					}
					
				});
				builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
			}
			break;
		case R.id.student_info_ll_second_volunteer:
			AlertDialog.Builder builder2=new Builder(StudentInfoActivity.this);
			builder2.setTitle("提示");
			if(detailsStudent.getSecond_volunteer()!=0)
			{
				if(detailsStudent.getSecond_volunteer_state()==0)
				{
					builder2.setMessage("第二志愿正在审核，是否确定取消？");
					builder2.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						
							new DeteCourceTask("2").execute(String.valueOf(detailsStudent.getSecond_volunteer_id()));
						}
						
					});
					builder2.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					builder2.show();
				}
				else {
					
					DialogUtil.showToast(StudentInfoActivity.this, "选课成功，不能更改");
				}
			}else {
				builder2.setMessage("第二志愿未选择,去选择？");
				builder2.setPositiveButton("去选择", new android.content.DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(StudentInfoActivity.this,InstitutionActivity.class);
						startActivity(intent);
					}
					
				});
				builder2.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
			}
		
			break;
		case R.id.common_tv_right:
			if(phoneEditText.getText().toString().equals(detailsStudent.getTel()))
			{
				finish();
				
			}else
			{
				new UpdateStudentInfo().execute(detailsStudent.getStudentId(),phoneEditText.getText().toString());
				finish();
			}
			break;

		default:
			break;
		}
	}
	
	public class DeteCourceTask extends AsyncTask<String, String, JSONObject>
	{

		
		CustomProgressDialog customProgressDialog;
		String whichVo;
		public DeteCourceTask(String whichVo)
		{
			this.whichVo=whichVo;
		}
		protected void onPreExecute() {
			customProgressDialog=new CustomProgressDialog(StudentInfoActivity.this, "正在取消...", R.anim.rotate_loading);
			customProgressDialog.show();
			super.onPreExecute();
		}
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			map.put("chooseCourceId", params[0]);
			map.put("whichVo", whichVo);
			map.put("studentId", detailsStudent.getStudentId());
			String url=HttpUtil.BASE_URL+"st_deleteCourcAndUpdateStudent.action";
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;
		}
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			
			try {
				if(result!=null&&result.getBoolean("state"))
				{
					customProgressDialog.cancel();
					if(whichVo.equals("1"))
					{
						detailsStudent.setFirst_volunteer(0);
						editor.putInt(Common.FIRST_VOLUNTEER, 0);
						((TextView)findViewById(R.id.student_info_tv_first_volunteer)).setText("未选择");
					}
					else
					{
						detailsStudent.setSecond_volunteer(0);
						editor.putInt(Common.SECOND_VOLUNTEER, 0);
						((TextView)findViewById(R.id.student_info_tv_second_volunteer)).setText("未选择");
					}
					
					editor.commit();
				}
				else {
					customProgressDialog.cancel();
					DialogUtil.showToast(StudentInfoActivity.this, "删除失败！");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	class UpdateStudentInfo extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			map.put("studentId", params[0]);
			map.put("phone", params[1]);
			String url=HttpUtil.BASE_URL+"st_updateStudent.action";
			HttpUtil.postRequest(url, map);
			return null;
		}
		
	}
	
	public void onResume()
	{
		super.onResume();
		new StudentInfoTask().execute(studentId);

	}

}
