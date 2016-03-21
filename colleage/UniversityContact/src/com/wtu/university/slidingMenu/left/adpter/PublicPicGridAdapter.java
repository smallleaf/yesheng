package com.wtu.university.slidingMenu.left.adpter;

import java.util.ArrayList;

import com.example.universityconnection.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PublicPicGridAdapter extends BaseAdapter{

	private ArrayList<Bitmap> arrayList;
	private Context mContext;
	public PublicPicGridAdapter(ArrayList<Bitmap> arrayList,Context mContext){
		this.arrayList=arrayList;
		this.mContext=mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {

			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_published_grida,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.image.setImageBitmap(arrayList.get(position));
		return convertView;
	}
	public class ViewHolder {
		public ImageView image;
	}

}
