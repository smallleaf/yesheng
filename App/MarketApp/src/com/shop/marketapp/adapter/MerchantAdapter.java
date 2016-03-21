package com.shop.marketapp.adapter;

import java.util.ArrayList;

import com.example.marketapp.R;
import com.shop.marketapp.bean.MerChantBean;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.Tools;
import com.shop.marketapp.image.ImageUtils;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MerchantAdapter extends BaseAdapter{
	private ArrayList<String> picBitmaps;
	private Context mContext;
	public MerchantAdapter(Context mContext,ArrayList<MerChantBean> merChantBeans,int page){
		this.mContext=mContext;
		picBitmaps=new ArrayList<String>();
		int i = page * 6;
		int iEnd = i + 6;
		while ((i < merChantBeans.size())&& (i < iEnd)) {
			picBitmaps.add(merChantBeans.get(i).getBitmapstr());
			i++;
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return picBitmaps.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return picBitmaps.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHoder viewHoder=null;
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_main_shopgridview_adater, null);
			viewHoder=new ViewHoder();
			viewHoder.imageView=(ImageView)convertView.findViewById(R.id.main_shopgridview_iv_picture);
			int width=(int)ImageUtils.screnWidthAndHeigthRace(mContext);
			viewHoder.imageView.setLayoutParams(new LinearLayout.LayoutParams(width/3, width/4));
			convertView.setTag(viewHoder);
		} else {
			viewHoder = (ViewHoder) convertView.getTag();
		}
		viewHoder.imageView.setImageBitmap(Tools.base64toBitmap(picBitmaps.get(position)));
		return convertView;
	}

	
	public class ViewHoder{
		private ImageView imageView;
	}
}
