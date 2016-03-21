package com.shop.marketapp.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.marketapp.R;
import com.shop.marketapp.activity.DetailsMerchantActivity;
import com.shop.marketapp.bean.Goods;
import com.shop.marketapp.bean.MerChantBean;
import com.shop.marketapp.bean.Orders;
import com.shop.marketapp.constant.DialogUtil;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.constant.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

public class BuyCarAdapter extends BaseAdapter{
	private ArrayList<Orders> buyGoods;
	private ArrayList<Goods> goodsArr;
	private ArrayList<String> merChantName;
	private Activity activity;
	private Handler handler;
	public BuyCarAdapter(Handler handler,Activity activity,ArrayList<Orders> buyGoods,ArrayList<Goods> goodsArr,ArrayList<String> merChantName){
		this.activity=activity;
		this.buyGoods=buyGoods;
		this.handler=handler;
		this.goodsArr=goodsArr;
		this.merChantName=merChantName;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return buyGoods.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return buyGoods.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=LayoutInflater.from(activity).inflate(R.layout.activity_buy_car_list, null);
		}
		CheckBox checkBox=Tools.ViewHolder.get(convertView, R.id.activity_buy_car_choose);
		TextView merchantName=Tools.ViewHolder.get(convertView, R.id.activity_buy_car_merchant);
		ImageView goodsPicture=Tools.ViewHolder.get(convertView, R.id.activity_details_iv_goods);
		TextView goodsName=Tools.ViewHolder.get(convertView, R.id.activity_detils_merchant_tv_goodsname);
		TextView goodsUnitPrice=Tools.ViewHolder.get(convertView, R.id.activity_detils_merchant_tv_goods_price);
		TextView goodsMoney=Tools.ViewHolder.get(convertView, R.id.activity_detils_buy_tv_goods_money);
		final ImageView goodsDelete=Tools.ViewHolder.get(convertView, R.id.activity_buy_car_delete);
		final Orders orders=buyGoods.get(position);
		final Goods goods=goodsArr.get(position);
		merchantName.setText(merChantName.get(position));
		goodsPicture.setImageBitmap(Tools.base64toBitmap(goods.getPic()));
		goodsName.setText(goods.getName());
		goodsUnitPrice.setText("单价 "+goods.getPrice()+"/斤");
		goodsMoney.setText(orders.getMount()+"元");
		//点击商家名进入商家页面
		merchantName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				MerChantBean merChantBean=new MerChantBean();
//				merChantBean.setId(goods.getSid());
//				merChantBean.setName(merChantName.get(position));
//				Intent intent=new Intent();
//				intent.setClass(activity, DetailsMerchantActivity.class);
//				Bundle bundle=new Bundle();
//				bundle.putSerializable("merchantBean", merChantBean);
//				intent.putExtras(bundle);
//				activity.startActivity(intent);
			}
		});
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					orders.setChecked(true);
				}else
				{
					orders.setChecked(false);
				}
				Message message =new Message();
				handler.sendMessage(message);
				
			}
		});
		goodsDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public  void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new AlertDialog.Builder(activity);
				builder.setMessage("是否删除？");
				builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						new DeleteOrders(position).execute(orders.getId(),position);
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				builder.show();
			}
		});
		return convertView;
	}

	public class DeleteOrders extends AsyncTask<Integer, String, JSONObject>{
		private int position;
		public DeleteOrders(int position){
			this.position=position;
		}
		@Override
		protected JSONObject doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			ArrayList<Integer> arrayList=new ArrayList<Integer>();
			arrayList.add(params[0]);
			System.out.println("weizhi+"+params[0]);
			String url=HttpUtil.BASE_URL+"order!deleteOrders";
			Map<String, String> map=new HashMap<String, String>();
			map.put("jsonStr", Tools.getJsonStringByList(arrayList));
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				if(result.getBoolean("state")){
					buyGoods.remove(position);
					Message message=new Message();
					handler.sendMessage(message);
				}else {
					DialogUtil.showToast(activity, "删除订单失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
