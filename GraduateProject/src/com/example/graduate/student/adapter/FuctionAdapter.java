package com.example.graduate.student.adapter;

import com.example.graduate.common.Common;
import com.example.graduateproject.R;

import android.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FuctionAdapter extends BaseAdapter {

	private Context mContext;
	private String[] functionName;
	private int[] functionImage;
	public FuctionAdapter(int role,Context mContext)
	{
		this.mContext=mContext;
		if(role==1)
		{//Ñ§Éú
			functionName=Common.student_function_name;
			functionImage=Common.student_fuction_image;
		}
		else if(role==2){
			functionName=Common.teacher_function_name;
			functionImage=Common.teacher_function_iamge;
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return functionName.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return functionName[position];
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
			convertView=LayoutInflater.from(mContext).inflate(R.layout.function_list, null);
			viewHolder.imageView=(ImageView)convertView.findViewById(R.id.function_list_iv);
			viewHolder.textView=(TextView)convertView.findViewById(R.id.function_list_tv);
			
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder=(ViewHolder)convertView.getTag();
		}
		viewHolder.imageView.setImageResource(functionImage[position]);
		viewHolder.textView.setText(functionName[position]);
		return convertView;
	}
	
	class ViewHolder{
		public ImageView imageView;
		public TextView textView;
	}

}
