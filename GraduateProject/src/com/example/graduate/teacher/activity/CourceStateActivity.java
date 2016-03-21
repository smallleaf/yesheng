package com.example.graduate.teacher.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.activity.common.LoginActivity;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.common.Tools;
import com.example.graduate.student.bean.ChooseCourceState;
import com.example.graduate.student.bean.Cource;
import com.example.graduate.teacher.adapter.CourceStateAdapter;
import com.example.graduate.view.CustomProgressDialog;
import com.example.graduateproject.R;
import com.example.graduateproject.R.layout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CourceStateActivity extends BaseActivity implements OnClickListener{

	List<ChooseCourceState> chooseCourceStates;
	List<ChooseCourceState> chooseTemp=new ArrayList<ChooseCourceState>();
	private String courceId;
	private ListView listView;
	private LinearLayout progressLayout;
	private CourceStateAdapter courceStateAdapter;
	private TextView wait;
	private TextView fisrt_wait;
	private TextView second_wait;
	private TextView success;
	private Button verifySuccess;
	private Button verifyFail;
	/**
	 * 分类
	 */
	int which;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setNeedBackGesture(true);
		setContentView(R.layout.activity_cource_state);
		listView=(ListView)findViewById(R.id.activity_cource_state_lv_courceItem);
		progressLayout=(LinearLayout)findViewById(R.id.common_loading);
		Bundle bundle=this.getIntent().getExtras();
		courceId=bundle.getString("courceId");
		((TextView)findViewById(R.id.common_tv_back)).setText(bundle.getString("courceName"));
		
		wait=(TextView)findViewById(R.id.activity_cource_state_tv_verify);
		fisrt_wait=(TextView)findViewById(R.id.activity_cource_state_tv_frist_verify);
		second_wait=(TextView)findViewById(R.id.activity_cource_state_tv_second_verify);
		success=(TextView)findViewById(R.id.activity_cource_state_tv_verify_success);
		verifySuccess=(Button)findViewById(R.id.activity_cource_success);
		
		wait.setOnClickListener(this);
		fisrt_wait.setOnClickListener(this);
		second_wait.setOnClickListener(this);
		success.setOnClickListener(this);
		verifySuccess.setOnClickListener(this);
		
		new GetChooseCourceState().execute(courceId);
	}
	
	public void sendWhich(int which)
	{
		chooseTemp.clear();
		if(which==1)
		{
			for(ChooseCourceState chooseCourceState:chooseCourceStates)
			{
				if(chooseCourceState.getState()==0)
				{
					chooseCourceState.setVerify(false);
					chooseTemp.add(chooseCourceState);
				}
			}
		}
		else if(which==2){
			for(ChooseCourceState chooseCourceState:chooseCourceStates)
			{
				if(chooseCourceState.getVolunnteerKind()==1&&chooseCourceState.getState()==0)
				{
					chooseTemp.add(chooseCourceState);
				}
			}
		}
		else if(which==3)
		{
			for(ChooseCourceState chooseCourceState:chooseCourceStates)
			{
				if(chooseCourceState.getVolunnteerKind()==2&&chooseCourceState.getState()==0)
				{
					chooseTemp.add(chooseCourceState);
				}
			}
		}
		else if(which==4)
		{
			for(ChooseCourceState chooseCourceState:chooseCourceStates)
			{
				if(chooseCourceState.getState()!=0)
				{
					chooseTemp.add(chooseCourceState);
				}
			}
			
		}
		courceStateAdapter.notifyDataSetChanged();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_cource_state_tv_verify:
		
			which=1;
			sendWhich(which);
			break;
		case R.id.activity_cource_state_tv_frist_verify:
			which=2;
			sendWhich(which);
			break;
		case R.id.activity_cource_state_tv_second_verify:
			which=3;
			sendWhich(which);
			break;
		case R.id.activity_cource_state_tv_verify_success:
			which=4;
			sendWhich(which);
			break;
		case R.id.activity_cource_success:
			List<Integer> successCourceId=new ArrayList<Integer>();
			for(ChooseCourceState chooseCourceState:chooseTemp)
			{
				if(chooseCourceState.isVerify())
				{//审核通过
					successCourceId.add(chooseCourceState.getId());
				}
			}
			new VerifyCourceState().execute(Tools.getJsonStringByList(successCourceId));
			break;
		default:
			break;
		}
	}

	
	
	/**
	 * 审核通过或者失败
	 * @author Administrator
	 *
	 */
	public class VerifyCourceState extends AsyncTask<String, String, JSONObject>
	{
		CustomProgressDialog customProgressDialog;
		protected void onPreExecute() {
			super.onPreExecute();
			customProgressDialog=new CustomProgressDialog(CourceStateActivity.this, "正在提交...", R.anim.rotate_loading);
			customProgressDialog.show();
		}
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String url=HttpUtil.BASE_URL+"te_verifySuccess.action?success="+params[0];
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
					ArrayList<Integer> removeId=new ArrayList<Integer>();
					//数据更改
					for(int i=0;i<chooseTemp.size();i++)
					{
						ChooseCourceState chooseCourceState=chooseTemp.get(i);
						if(chooseCourceState.isVerify())
						{//审核通过
							chooseCourceStates.get(chooseCourceStates.indexOf(chooseCourceState)).setState(1);
							removeId.add(i);
						}
					}
//					for(int i=0;i<removeId.size();i++)
//					{
//						chooseTemp.remove(removeId.get(i));
//					}
					sendWhich(1);
					courceStateAdapter.notifyDataSetChanged();
					customProgressDialog.cancel();
				}
				else {
					customProgressDialog.cancel();
					DialogUtil.showToast(CourceStateActivity.this, "提交失败");
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
	/**
	 * 获取选课情况
	 * @author Administrator
	 *
	 */
	public class GetChooseCourceState extends AsyncTask<String, String, JSONObject>
	{

		protected void onPreExecute() {
			super.onPreExecute();
			listView.setVisibility(View.GONE);
			progressLayout.setVisibility(View.VISIBLE);
		}
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=HttpUtil.BASE_URL+"te_getChooseCoureState.action?courceId="+params[0];
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
					System.out.println(result);
					Gson gson=new Gson();
					chooseCourceStates=gson.fromJson(result.getString("chooseCourceStates"), new  TypeToken<ArrayList<ChooseCourceState>>(){}.getType());
					for(ChooseCourceState chooseCourceState:chooseCourceStates)
					{
						if(chooseCourceState.getState()==0)
						{
							chooseTemp.add(chooseCourceState);
						}
					}
					courceStateAdapter=new CourceStateAdapter(CourceStateActivity.this, chooseTemp);
					listView.setAdapter(courceStateAdapter);
					progressLayout.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				}
				else {
					progressLayout.setVisibility(View.GONE);
					DialogUtil.showToast(CourceStateActivity.this, "数据获取失败");
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
	public void onResume()
	{
		super.onResume();
	}
}
