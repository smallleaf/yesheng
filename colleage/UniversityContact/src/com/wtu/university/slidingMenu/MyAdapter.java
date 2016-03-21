package com.wtu.university.slidingMenu;

import java.util.ArrayList;

import com.example.universityconnection.R;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	private ArrayList<String> tetArrayList;
	private Context mContext;
	public MyAdapter(Context mContext,ArrayList<String> tetArrayList){
		this.mContext=mContext;
		this.tetArrayList=tetArrayList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(tetArrayList==null){
			return 0;
		}
		return tetArrayList.size();
	}
	public void setlis(ArrayList<String> tetArrayList){
		this.tetArrayList=tetArrayList;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if(tetArrayList==null){
			return null;
		}
		return tetArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list,
					null);
			holder = new ViewHolder();
			holder.textView=(TextView)convertView.findViewById(R.id.tew);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(tetArrayList.get(position));
		return convertView;
	}
	
	public final class ViewHolder {
		public WebView webView;
		public TextView textView;
	}

}
