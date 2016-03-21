package com.example.graduate.student.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.graduate.student.bean.Major;
import com.example.graduate.student.bean.Teacher;
import com.example.graduateproject.R;
import com.example.graduateproject.R.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private List<Major> majors;
	private List<Teacher> teachers;
	private Context context =null;
	public MyExpandableListAdapter(Context context,List<Major> majors,List<Teacher> teachers)
	{
		this.context = context;
		this.majors=majors;
		this.teachers=teachers;
	}
	@Override
	public Teacher getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		ArrayList<Teacher> teachers=new ArrayList<Teacher>();
		for(Teacher teacher:this.teachers)
		{
			if(majors.get(groupPosition).getMajorId()==teacher.getMajorId())
			{
				teachers.add(teacher);
			}
		}
		if(teachers.size()>0)
		{
			return teachers.get(childPosition);
		}
		
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	
	public TextView buildTextView() {	// 自定义方法，建立文本
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 100);// 指定布局参数
		TextView textView = new TextView(this.context);// 创建TextView
		textView.setLayoutParams(param);	// 设置布局参数
		textView.setTextSize(20.0f);		// 设置文字大小
		textView.setGravity(Gravity.CENTER_VERTICAL);	// 左对齐
		textView.setPadding(40, 8, 3, 3);	// 间距
		return textView;// 返回组件
	}
	@SuppressLint("ResourceAsColor")
	public TextView buildChildTextView() {	// 自定义方法，建立文本
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 80);// 指定布局参数
		TextView textView = new TextView(this.context);// 创建TextView
		textView.setLayoutParams(param);	// 设置布局参数
		textView.setTextSize(18.0f);		// 设置文字大小
		textView.setBackgroundResource(R.color.gray2);
		textView.setGravity(Gravity.CENTER_VERTICAL);		// 左对齐
		textView.setPadding(60, 8, 3, 3);	// 间距
		return textView;// 返回组件
	}


	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		//返回子项组件
		// TODO Auto-generated method stub
		TextView textView = buildChildTextView();	// 创建TextView
		textView.setText(getChild(groupPosition, 
				childPosition).getName());		// 设置显示文字
		return textView;

	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		ArrayList<Teacher> teachers=new ArrayList<Teacher>();
		for(Teacher teacher:this.teachers)
		{
			if(majors.get(groupPosition).getMajorId()==teacher.getMajorId())
			{
				teachers.add(teacher);
			}
		}
		return teachers.size();
	}

	@Override
	public Major getGroup(int groupPosition) {// 取得组对象
		return majors.get(groupPosition);
	}


	@Override
	public int getGroupCount() {	// 取得组个数
		return majors.size();
	}


	@Override
	public long getGroupId(int groupPosition) {// 取得组ID
		return groupPosition;
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {	// 取得组显示组件
		TextView textView = buildTextView();	// 建立组件
		textView.setText(this.getGroup(groupPosition).getName());	// 设置文字
		return textView;
	}


	@Override
	public boolean hasStableIds() {
		return true; 
	}


	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}


}
