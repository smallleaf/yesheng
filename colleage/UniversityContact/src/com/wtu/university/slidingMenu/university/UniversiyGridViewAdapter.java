package com.wtu.university.slidingMenu.university;

import com.example.universityconnection.R;
import com.wtu.university.common.Constants;
import com.wtu.university.slidingMenu.adapter.NewsAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class UniversiyGridViewAdapter extends BaseAdapter{

	private Context mContext;
	public UniversiyGridViewAdapter(Context mContext)
	{
		this.mContext=mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Constants.universitys_textStrings.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return Constants.universitys_textStrings[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.university_newsfragment_gridview, null);
			viewHolder.pictureImageView=(ImageView)convertView.findViewById(R.id.university_gridview_image);
			viewHolder.textView=(TextView)convertView.findViewById(R.id.university_gridview_text);
			convertView.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.pictureImageView.setImageResource(Constants.universitys_picture[position])	;
		viewHolder.textView.setText(Constants.universitys_textStrings[position]);
		return convertView;
	}
	public  class ViewHolder {
		private ImageView pictureImageView;
		private TextView textView;
	}
}
