package com.example.graduate.teacher.adapter;

import java.util.List;

import com.example.graduate.student.bean.ChooseCourceState;
import com.example.graduateproject.R;

import android.content.Context;
import android.graphics.Color;
import android.renderscript.Element;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CourceStateAdapter extends BaseAdapter{
	List<ChooseCourceState> chooseCourceStates;
	public Context mContext;
	public CourceStateAdapter(Context mContext,List<ChooseCourceState> chooseCourceStates)
	{
		this.mContext=mContext;
		this.chooseCourceStates=chooseCourceStates;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chooseCourceStates.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chooseCourceStates.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if(convertView==null)
		{
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.teacher_cource_state_list, null);
			viewHolder.checkBox=(CheckBox)convertView.findViewById(R.id.teacher_cource_state_cb_choose);
			viewHolder.name=(TextView)convertView.findViewById(R.id.teacher_cource_state_tv_name);
			viewHolder.className=(TextView)convertView.findViewById(R.id.teacher_cource_state_tv_class);
			viewHolder.state=(TextView)convertView.findViewById(R.id.teacher_cource_state_tv_state);
			viewHolder.volunteer=(TextView)convertView.findViewById(R.id.teacher_cource_state_tv_vol);
			
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder=(ViewHolder)convertView.getTag();
		}
		final ChooseCourceState chooseCourceState=chooseCourceStates.get(position);
		viewHolder.name.setText("姓名:"+chooseCourceState.getStudentName());
		viewHolder.className.setText("班级:"+chooseCourceState.getClass_name());
		if(chooseCourceState.getVolunnteerKind()==1)
		{
			viewHolder.volunteer.setText("志愿:第一志愿");
		}
		else
		{
			viewHolder.volunteer.setText("志愿:第二志愿");
		}
		if(chooseCourceState.getState()==0)
		{
			String content="状态:"+"待审核";
			SpannableStringBuilder stringBuilder=new SpannableStringBuilder(content);
			stringBuilder.setSpan(new ForegroundColorSpan(Color.RED), "状态:".length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			viewHolder.state.setText(stringBuilder);
		}
		else {
			String content="状态:"+"已通过";
			SpannableStringBuilder stringBuilder=new SpannableStringBuilder(content);
			stringBuilder.setSpan(new ForegroundColorSpan(Color.RED), "状态:".length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			viewHolder.state.setText(stringBuilder);
		}
		if(chooseCourceState.isVerify())
		{
			viewHolder.checkBox.setChecked(true);
		}
		else
		{
			viewHolder.checkBox.setChecked(false);
		}
		viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					chooseCourceState.setVerify(true);
				}
				else {
					chooseCourceState.setVerify(false);
				}
			}
		});
		return convertView;
	}

	public class ViewHolder
	{
		public CheckBox checkBox;
		public TextView name;
		public TextView className;
		public TextView volunteer;
		public TextView state;
	}
}
