package com.example.graduate.teacher.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.common.Common;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.student.bean.ChooseCourceState;
import com.example.graduate.student.bean.Cource;
import com.example.graduateproject.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TeacherCourceActivity extends BaseActivity {

	private ListView listView;
	private LinearLayout progressLayout;
	private SharedPreferences sharedPreferences;
	List<Cource> cources;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNeedBackGesture(true);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_teacher_cource);
		sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		progressLayout=(LinearLayout)findViewById(R.id.common_loading);
		((TextView)findViewById(R.id.common_tv_back)).setText("课题列表");
		listView=(ListView)findViewById(R.id.activity_teacher_cource_lv_list);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(TeacherCourceActivity.this,CourceStateActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("courceId",String.valueOf(cources.get(position).getId()));
				bundle.putString("courceName",cources.get(position).getName());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		new GetCource().execute(sharedPreferences.getString(Common.LOGIN_USERID, ""));
	}

	public class GetCource extends AsyncTask<String, String, JSONObject>
	{

		protected void onPreExecute() {
			super.onPreExecute();
			listView.setVisibility(View.GONE);
			progressLayout.setVisibility(View.VISIBLE);
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
				if(result!=null&&result.getBoolean("state"))
				{
					Gson gson=new Gson();
					cources=gson.fromJson(result.getString("courceList"), new  TypeToken<ArrayList<Cource>>(){}.getType());
					
					ArrayList<String> couceNameArrayList=new ArrayList<String>();
					for(Cource cource:cources)
					{
						couceNameArrayList.add(cource.getName());
					}
					ArrayAdapter<String> adapter=new ArrayAdapter<String>(TeacherCourceActivity.this, R.layout.institution_list,R.id.insitution_list_tv, couceNameArrayList);
					listView.setAdapter(adapter);
					progressLayout.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}
				else {
					progressLayout.setVisibility(View.GONE);
					DialogUtil.showToast(TeacherCourceActivity.this, "数据获取失败");
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
		
}
