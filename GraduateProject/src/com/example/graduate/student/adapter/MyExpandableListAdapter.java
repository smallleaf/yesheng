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
	
	public TextView buildTextView() {	// �Զ��巽���������ı�
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 100);// ָ�����ֲ���
		TextView textView = new TextView(this.context);// ����TextView
		textView.setLayoutParams(param);	// ���ò��ֲ���
		textView.setTextSize(20.0f);		// �������ִ�С
		textView.setGravity(Gravity.CENTER_VERTICAL);	// �����
		textView.setPadding(40, 8, 3, 3);	// ���
		return textView;// �������
	}
	@SuppressLint("ResourceAsColor")
	public TextView buildChildTextView() {	// �Զ��巽���������ı�
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 80);// ָ�����ֲ���
		TextView textView = new TextView(this.context);// ����TextView
		textView.setLayoutParams(param);	// ���ò��ֲ���
		textView.setTextSize(18.0f);		// �������ִ�С
		textView.setBackgroundResource(R.color.gray2);
		textView.setGravity(Gravity.CENTER_VERTICAL);		// �����
		textView.setPadding(60, 8, 3, 3);	// ���
		return textView;// �������
	}


	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		//�����������
		// TODO Auto-generated method stub
		TextView textView = buildChildTextView();	// ����TextView
		textView.setText(getChild(groupPosition, 
				childPosition).getName());		// ������ʾ����
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
	public Major getGroup(int groupPosition) {// ȡ�������
		return majors.get(groupPosition);
	}


	@Override
	public int getGroupCount() {	// ȡ�������
		return majors.size();
	}


	@Override
	public long getGroupId(int groupPosition) {// ȡ����ID
		return groupPosition;
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {	// ȡ������ʾ���
		TextView textView = buildTextView();	// �������
		textView.setText(this.getGroup(groupPosition).getName());	// ��������
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
