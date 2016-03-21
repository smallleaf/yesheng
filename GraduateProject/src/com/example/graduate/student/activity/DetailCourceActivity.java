package com.example.graduate.student.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.common.Common;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.common.Tools;
import com.example.graduate.student.bean.ChooseCource;
import com.example.graduate.student.bean.Cource;
import com.example.graduate.student.bean.DetailsTeacher;
import com.example.graduate.view.CustomProgressDialog;
import com.example.graduateproject.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class DetailCourceActivity extends BaseActivity {

	private Cource cource;
	private LinearLayout teacherLayout;
	private Button chooseBt;
	private DetailsTeacher detailsTeacher;
	SharedPreferences sharedPreferences;
	int whichVo;//志愿
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setNeedBackGesture(true);
		setContentView(R.layout.activity_detail_cource);
		((TextView)findViewById(R.id.common_tv_back)).setText("课题信息");
		Bundle bundle = this.getIntent().getExtras();
		cource = (Cource) bundle.getSerializable("cource");
	 sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		
		detailsTeacher = (DetailsTeacher) bundle.getSerializable("teacher");
		((TextView) findViewById(R.id.detail_cource_activity_name))
				.setText(cource.getName());
		((TextView) findViewById(R.id.detail_cource_activity_demand))
				.setText(cource.getDemand());
		((TextView) findViewById(R.id.detail_cource_activity_ability))
				.setText(cource.getAbility());
		((TextView) findViewById(R.id.detail_cource_activity_limit))
				.setText("总共" + cource.getCount_limit());
		((TextView) findViewById(R.id.detail_cource_activity_choose))
				.setText("已选" + cource.getCount_choose());
		((TextView) findViewById(R.id.detail_cource_activity_major))
				.setText(detailsTeacher.getMajor() + "    "
						+ detailsTeacher.getName() + "老师");
		teacherLayout = (LinearLayout) findViewById(R.id.details_activity_teacher_info);
		chooseBt = (Button) findViewById(R.id.details_activity_choose);
		teacherLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent itent = new Intent();
				itent.setClass(DetailCourceActivity.this,
						DetailTeacherActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("teacher", detailsTeacher);
				itent.putExtras(bundle);
				startActivity(itent);
			}
		});
		chooseBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sharedPreferences.getInt(Common.FIRST_VOLUNTEER, 0)==cource.getId()||sharedPreferences.getInt(Common.SECOND_VOLUNTEER, 0)==cource.getId())
				{
					DialogUtil.showToast(DetailCourceActivity.this, "您已选择此课题");
				}
				else
				{
					new VolunteerChoos(DetailCourceActivity.this, chooseBt);
				}
			}
		});
	}

	public class VolunteerChoos extends PopupWindow {
		@SuppressLint({ "NewApi", "ResourceAsColor" })
		public VolunteerChoos(Context mContext, View parent) {
			View view = View.inflate(mContext, R.layout.volunteer_choose, null);

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));
			TextView firstVolunteer = (TextView) view
					.findViewById(R.id.volunteer_choose_first);
			TextView secondVolunteer = (TextView) view
					.findViewById(R.id.volunteer_choose_second);
			// popWindow背景变灰
			final WindowManager.LayoutParams params = DetailCourceActivity.this
					.getWindow().getAttributes();
			params.alpha = 0.5f;
			DetailCourceActivity.this.getWindow().setAttributes(params);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();
			
			view.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					dismiss();
					return true;
				}
			});
			setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					// TODO Auto-generated method stub
					params.alpha = 1f;
					DetailCourceActivity.this.getWindow().setAttributes(
							params);
				}
			});
			
			final SharedPreferences sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
			final int first=sharedPreferences.getInt(Common.FIRST_VOLUNTEER, 0);
			final int second=sharedPreferences.getInt(Common.SECOND_VOLUNTEER, 0);
			if(first!=0)
			{
				firstVolunteer.setText("第一志愿(已选)");
				firstVolunteer.setTextColor(Color.RED);
				firstVolunteer.setBackgroundResource(R.color.gray2);
			}
			else {
				firstVolunteer.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(first==cource.getId()||second==cource.getId())
						{
							DialogUtil.showToast(DetailCourceActivity.this, "您已经选了此课题，请重新选择！！！");
						}
						else
						{
						whichVo=1;
						new ChooseCourceTask().execute("1");
						}
					}
				});
			}
			if(second!=0)
			{
				secondVolunteer.setText("第二志愿(已选)");
				secondVolunteer.setTextColor(Color.RED);
				secondVolunteer.setBackgroundResource(R.color.gray2);
			}
			else {
				secondVolunteer.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(first==cource.getId()||second==cource.getId())
						{
							DialogUtil.showToast(DetailCourceActivity.this, "您已经选了此课题，请重新选择！！！");
						}
						else {
							whichVo=2;
							new ChooseCourceTask().execute("2");
						}
					}
				});
			}
			
		}
	}
	
	class ChooseCourceTask extends AsyncTask<String, String, JSONObject>
	{
		CustomProgressDialog customProgressDialog;
		protected void onPreExecute() {
			super.onPreExecute();
			customProgressDialog=new CustomProgressDialog(DetailCourceActivity.this, "正在提交数据...", R.anim.rotate_loading);
			customProgressDialog.show();
		}
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			ChooseCource chooseCource=new ChooseCource();
		
			chooseCource.setStudentId(sharedPreferences.getString(Common.LOGIN_USERID, ""));
			chooseCource.setCourceId(cource.getId());
			chooseCource.setVolunteerKind(whichVo);
			map.put("chooseCource", Tools.getJsonStringByEntity(chooseCource));
			map.put("volunteer", params[0]);
			JSONObject jsonObject=null;
			String url=HttpUtil.BASE_URL+"st_chooseCource.action";
			try {
				jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
			} catch (JSONException e) {
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
					Editor editor=sharedPreferences.edit();
					if(whichVo==1)
					{
						editor.putInt(Common.FIRST_VOLUNTEER, cource.getId());
					}
					else {
						editor.putInt(Common.SECOND_VOLUNTEER, cource.getId());
					}
					editor.commit();
					customProgressDialog.cancel();
					DialogUtil.showToast(DetailCourceActivity.this, "提交成功，请等待老师审核！");
					finish();
				}
				else {
					customProgressDialog.cancel();
					DialogUtil.showToast(DetailCourceActivity.this, "选课失败,请重新选择！");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} 
}
