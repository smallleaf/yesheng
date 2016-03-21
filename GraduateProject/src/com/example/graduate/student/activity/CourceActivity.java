package com.example.graduate.student.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.student.adapter.CourceItemAdapter;
import com.example.graduate.student.bean.Cource;
import com.example.graduate.student.bean.DetailsTeacher;
import com.example.graduateproject.R;
import com.example.graduateproject.R.id;
import com.example.graduateproject.R.layout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CourceActivity extends BaseActivity {

	private DetailsTeacher detailsTeacher;
	private ListView listView;
	private List<Cource> couceList;
	private CourceItemAdapter courceItemAdapter;
	private LinearLayout searchLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setNeedBackGesture(true);
		setContentView(R.layout.activity_cource);
		
		Bundle bundle=this.getIntent().getExtras();
		if(bundle!=null)
		{
			detailsTeacher=(DetailsTeacher)bundle.getSerializable("teacher");
		}
		((TextView)findViewById(R.id.common_tv_back)).setText(detailsTeacher.getName());
		listView=(ListView)findViewById(R.id.activity_cource_lv_courceItem);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(CourceActivity.this, DetailCourceActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("cource", couceList.get(position));
				bundle.putSerializable("teacher", detailsTeacher);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		searchLayout=(LinearLayout)findViewById(R.id.common_ll_search);
		searchLayout.setVisibility(View.VISIBLE);
		new CourceTask().execute(detailsTeacher.getTeacherId());
	}
	
	class CourceTask extends AsyncTask<String, String, JSONObject>
	{
		protected void onPreExecute() {
			super.onPreExecute();
			((LinearLayout)findViewById(R.id.common_loading)).setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=HttpUtil.BASE_URL+"findAllCource.action?teacherId="+params[0];
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
					couceList=gson.fromJson(result.getString("courceList"), new TypeToken<ArrayList<Cource>>(){}.getType());
					
					courceItemAdapter=new CourceItemAdapter(CourceActivity.this, couceList);
					listView.setAdapter(courceItemAdapter);
					
					((LinearLayout)findViewById(R.id.common_loading)).setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}
				else {
					((LinearLayout)findViewById(R.id.common_loading)).setVisibility(View.GONE);
					DialogUtil.showToast(CourceActivity.this, "服务器连接失败!");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
