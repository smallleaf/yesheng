package com.shop.marketapp.adapter;

import java.util.ArrayList;

import com.example.marketapp.R;
import com.shop.marketapp.bean.Address;
import com.shop.marketapp.bean.AddressManager;
import com.shop.marketapp.constant.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressManagerAdapter extends BaseAdapter{

	private ArrayList<Address> addressManagers;
	private Context mContext;
	public AddressManagerAdapter(Context mContext,ArrayList<Address> addressManagers){
		this.mContext=mContext;
		this.addressManagers=addressManagers;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return addressManagers.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return addressManagers.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHodler viewHodler=null;
		if(convertView==null){
			convertView=LayoutInflater.from(mContext).inflate(R.layout.deliver_address_manager_list, null);
//			viewHodler=new ViewHodler();
//			viewHodler.personName=(TextView)convertView.findViewById(R.id.deliver_address_tv_person_name);
//			viewHodler.personPhone=(TextView)convertView.findViewById(R.id.deliver_address_tv_person_phone);
//			viewHodler.address=(TextView)convertView.findViewById(R.id.deliver_address_tv_add);
//			viewHodler.choose=(ImageView)convertView.findViewById(R.id.delive_iv_yes);
//			convertView.setTag(viewHodler);
//		}else {
//			viewHodler=(ViewHodler)convertView.getTag();
		}
		TextView personName=Tools.ViewHolder.get(convertView, R.id.deliver_address_tv_person_name);
		TextView personPhone=Tools.ViewHolder.get(convertView, R.id.deliver_address_tv_person_phone);
		TextView address=Tools.ViewHolder.get(convertView, R.id.deliver_address_tv_add);
		ImageView choose=Tools.ViewHolder.get(convertView, R.id.delive_iv_yes);
		Address addressManager=addressManagers.get(position);
		
		
		personName.setText(addressManager.getName());
		personPhone.setText(addressManager.getPhone());
		address.setText(addressManager.getAddress());
		if(addressManager.isChoosed()){
			choose.setVisibility(View.VISIBLE);
			choose.setImageResource(R.drawable.yes);
		}
		else {
			choose.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	class ViewHodler{
		public TextView personName;
		public TextView personPhone;
		public TextView address;
		public ImageView choose;
	}

}
