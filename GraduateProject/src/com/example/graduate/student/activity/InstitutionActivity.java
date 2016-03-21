package com.example.graduate.student.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.student.adapter.FuctionAdapter;
import com.example.graduate.student.bean.Institution;
import com.example.graduateproject.R;
import com.example.graduateproject.R.id;
import com.example.graduateproject.R.layout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.nineoldandroids.view.ViewHelper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class InstitutionActivity extends BaseActivity {

	private ListView listView;
	private LinearLayout progressLayout;
	List<Institution> institutions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNeedBackGesture(true);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_institution);
		((TextView)findViewById(R.id.common_tv_back)).setText("选择学院");
		
		listView=(ListView)findViewById(R.id.institution_activity_lv_institution);
		progressLayout=(LinearLayout)findViewById(R.id.common_loading);
		
	
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(InstitutionActivity.this, TeacherActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("institution", institutions.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});
		
		
		new GetInstitutionAs().execute();
	}
	class GetInstitutionAs extends AsyncTask<String, String, JSONObject>
	{
		protected void onPreExecute() {
			super.onPreExecute();
			listView.setVisibility(View.GONE);
			progressLayout.setVisibility(View.VISIBLE);
		}
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=HttpUtil.BASE_URL+"te_getAllInstitution.action";
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
				if(result!=null&&result.getBoolean("state"))
				{
					Gson gson=new Gson();
					institutions=gson.fromJson(result.getString("institutions"), new  TypeToken<ArrayList<Institution>>(){}.getType());
					ArrayList<String> institution_name=new ArrayList<String>();
					for(Institution institution:institutions)
					{
						institution_name.add(institution.getName());
					}
					ArrayAdapter<String> adapter=new ArrayAdapter<String>(InstitutionActivity.this, R.layout.institution_list,R.id.insitution_list_tv, institution_name);
					listView.setAdapter(adapter);
					progressLayout.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}
				else {
					progressLayout.setVisibility(View.GONE);
					DialogUtil.showToast(InstitutionActivity.this, "数据获取失败");
				}
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void onBackPressed() {
		super.onBackPressed();
	}

}
