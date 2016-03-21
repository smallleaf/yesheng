package com.shop.marketapp.adapter;

import java.util.ArrayList;

import com.example.marketapp.R;
import com.shop.marketapp.adapter.MerchantAdapter.ViewHoder;
import com.shop.marketapp.bean.Goods;
import com.shop.marketapp.constant.Tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MerchantFoodAdapter extends BaseAdapter{

	private ArrayList<Goods> vegatableArrayList;
	private Context mContext;
	public MerchantFoodAdapter(Context mContext,ArrayList<Goods> vegatableArrayList){
		this.mContext=mContext;
		this.vegatableArrayList=vegatableArrayList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vegatableArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return vegatableArrayList.get(arg0);
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
			convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_details_merchant_goods_list, null);
			viewHolder.imageView=(ImageView)convertView.findViewById(R.id.activity_details_iv_goods);
			viewHolder.goodsName=(TextView)convertView.findViewById(R.id.activity_detils_merchant_tv_goodsname);
			viewHolder.goodsPrice=(TextView)convertView.findViewById(R.id.activity_detils_merchant_tv_goods_price);
			viewHolder.buyMoney=(TextView)convertView.findViewById(R.id.activity_detils_merchant_tv_goods_money);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Goods goods=vegatableArrayList.get(position);
		viewHolder.imageView.setImageBitmap(Tools.base64toBitmap(goods.getPic()));
		viewHolder.goodsName.setText(goods.getName());
		viewHolder.goodsPrice.setText("µ¥¼Û:"+goods.getPrice()+"/½ï");
		if(goods.getGoodsale()>0){
			ColorStateList color=mContext.getResources().getColorStateList(R.color.red);
			viewHolder.buyMoney.setTextColor(color);
		}
		viewHolder.buyMoney.setText(goods.getGoodsale()+"Ôª");
		
		return convertView;
	}
	
	public class ViewHolder{
		public ImageView imageView;
		private TextView goodsName;
		private TextView goodsPrice;
		private TextView buyMoney;
	}

}
