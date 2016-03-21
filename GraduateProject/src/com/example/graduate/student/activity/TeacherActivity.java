package com.example.graduate.student.activity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.graduate.activity.common.BaseActivity;
import com.example.graduate.common.Common;
import com.example.graduate.common.DialogUtil;
import com.example.graduate.common.HttpUtil;
import com.example.graduate.student.adapter.MyExpandableListAdapter;
import com.example.graduate.student.bean.DetailsTeacher;
import com.example.graduate.student.bean.Institution;
import com.example.graduate.student.bean.Major;
import com.example.graduate.student.bean.Teacher;
import com.example.graduateproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class TeacherActivity extends BaseActivity {

	private ExpandableListView elistview = null;	// �����������
	private ExpandableListAdapter adapter = null;	// ��������������
	private SharedPreferences sharedPreferences;
	List<Major> majors;
	List<Teacher> teachers;
	Institution institution;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setNeedBackGesture(true);
		setContentView(R.layout.expandablelistview);
		this.elistview = (ExpandableListView) super.findViewById(R.id.elistview); 	// ȡ�����

		
		sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		Bundle bundle=this.getIntent().getExtras();
		institution=(Institution) bundle.getSerializable("institution");
		
		((TextView)findViewById(R.id.common_tv_back)).setText(institution.getName());
		
		elistview.setGroupIndicator(null);
		super.registerForContextMenu(this.elistview); 	// ע�������Ĳ˵�
		
		this.elistview.setOnChildClickListener(new OnChildClickListenerImpl());	// ����������¼�
		this.elistview.setOnGroupClickListener(new OnGroupClickListenerImpl());	// ����������¼�
		this.elistview.setOnGroupCollapseListener(new OnGroupCollapseListenerImpl()); // �رշ����¼�
		this.elistview.setOnGroupExpandListener(new OnGroupExpandListenerImpl()); 	// չ�������¼�

		new GetTeacher().execute(String.valueOf(institution.getInstitutionId()));
		}


	
	class GetTeacher extends AsyncTask<String, String, JSONObject>
	{
		protected void onPreExecute() {
			super.onPreExecute();
			((LinearLayout)findViewById(R.id.common_loading)).setVisibility(View.VISIBLE);
			elistview.setVisibility(View.GONE);
		}
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			map.put("institutionId", params[0]);
			String url=HttpUtil.BASE_URL+"student_getInsTeacher";
			JSONObject jsonObject=null;
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

					Gson gson=new Gson();
					majors=gson.fromJson(result.getString("majors"), new TypeToken<ArrayList<Major>>(){}.getType());
					teachers=gson.fromJson(result.getString("teachers"), new TypeToken<ArrayList<Teacher>>(){}.getType());

					adapter = new MyExpandableListAdapter(TeacherActivity.this,majors,teachers);	// ʵ����������
					elistview.setAdapter(adapter);	// ����������
					((LinearLayout)findViewById(R.id.common_loading)).setVisibility(View.GONE);
					elistview.setVisibility(View.VISIBLE);
				}
				else {
					((LinearLayout)findViewById(R.id.common_loading)).setVisibility(View.GONE);
					DialogUtil.showToast(TeacherActivity.this, "���ݼ���ʧ��");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private class OnChildClickListenerImpl implements OnChildClickListener
	{

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			Intent it2 = new Intent();
			it2.setClass(TeacherActivity.this, CourceActivity.class);
			
			
			DetailsTeacher detailsTeacher=new DetailsTeacher();
			ArrayList<Teacher> teachersTemp=new ArrayList<Teacher>();
			Teacher teachertemp = null;
			for(Teacher teacher:teachers)
			{
				if(majors.get(groupPosition).getMajorId()==teacher.getMajorId())
				{
					teachersTemp.add(teacher);
				}
			}
			if(teachersTemp.size()>0)
			{
				teachertemp=teachersTemp.get(childPosition);
			}
			if(teachertemp!=null)
			{
				detailsTeacher.setId(teachertemp.getId());
				detailsTeacher.setInstitution(institution.getName());
				detailsTeacher.setMajor(majors.get(groupPosition).getName());
				detailsTeacher.setName(teachertemp.getName());
				detailsTeacher.setTeacherId(teachertemp.getTeacherId());
				detailsTeacher.setPhone(teachertemp.getPhone());
				Bundle bundle2=new Bundle();
				bundle2.putSerializable("teacher", detailsTeacher);
				it2.putExtras(bundle2);
			}
			startActivity(it2);
			return false;
		}


		
		
	}
	
	private class OnGroupClickListenerImpl implements OnGroupClickListener {

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
//			Toast.makeText(TreeListShowTeacher.this,
//					"���鱻ѡ�У�groupPosition = " + groupPosition,
//					Toast.LENGTH_SHORT).show();	// ��ʾ��ʾ��
			return false;
		}

	
	}
	
	private class OnGroupCollapseListenerImpl implements
	OnGroupCollapseListener {
		@Override
		public void onGroupCollapse(int groupPosition) {
//			Toast.makeText(TreeListShowTeacher.this,
//			"�رշ��飬groupPosition = " + groupPosition, Toast.LENGTH_SHORT)
//			.show();// ��ʾ��ʾ��
}	
		}

	private class OnGroupExpandListenerImpl implements OnGroupExpandListener {
		@Override
		public void onGroupExpand(int groupPosition) {
//			Toast.makeText(TreeListShowTeacher.this,
//					"�򿪷��飬groupPosition = " + groupPosition, Toast.LENGTH_SHORT)
//					.show();// ��ʾ��ʾ��
		}

	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		ExpandableListView.ExpandableListContextMenuInfo info = 
			(ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
		int type = ExpandableListView
				.getPackedPositionType(info.packedPosition); // ȡ�ò����Ĳ˵���
		int group = ExpandableListView
				.getPackedPositionGroup(info.packedPosition); // ȡ�ò˵������ڵĲ˵���
		int child = ExpandableListView
				.getPackedPositionChild(info.packedPosition); // ȡ���Ӳ˵��������
		Toast.makeText(TeacherActivity.this,
				"type = " + type + "��group = " + group + "��child = " + child,
				Toast.LENGTH_SHORT).show();					// ��ʾ��ʾ��
	}

	public void onBackPressed() {
		super.onBackPressed();
	}
}
