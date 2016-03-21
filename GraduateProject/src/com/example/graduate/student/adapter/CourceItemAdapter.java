package com.example.graduate.student.adapter;

import java.util.List;

import com.example.graduate.student.bean.Cource;
import com.example.graduateproject.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CourceItemAdapter extends BaseAdapter{

	private List<Cource> courceList;
	private Context mContext;
	public CourceItemAdapter(Context mContext,List<Cource> courceList)
	{
		this.mContext=mContext;
		this.courceList=courceList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return courceList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return courceList.get(position);
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
			convertView=LayoutInflater.from(mContext).inflate(R.layout.cource_item, null);
			viewHolder.courceTitle=(TextView)convertView.findViewById(R.id.cource_item_courceName);
			viewHolder.courceChoose=(TextView)convertView.findViewById(R.id.cource_item_courcechoos);
			viewHolder.courceRemain=(TextView)convertView.findViewById(R.id.cource_item_courceRemain);
			convertView.setTag(viewHolder);
			
		}
		else {
			viewHolder=(ViewHolder)convertView.getTag();
		}
		viewHolder.courceTitle.setText(courceList.get(position).getName());
		viewHolder.courceChoose.setText("“——°‘Ò"+courceList.get(position).getCount_choose());
		viewHolder.courceRemain.setText(" £”‡"+(courceList.get(position).getCount_limit()-courceList.get(position).getCount_choose()));
		return convertView;
	}

	public class ViewHolder
	{
		public TextView courceTitle;
		public TextView courceChoose;
		public TextView courceRemain;
	}
}
