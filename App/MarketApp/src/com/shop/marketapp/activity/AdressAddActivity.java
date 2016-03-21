package com.shop.marketapp.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.marketapp.R;
import com.google.gson.JsonObject;
import com.shop.marketapp.base.BaseActivity;
import com.shop.marketapp.bean.Address;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.DialogUtil;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.constant.Tools;
import com.shop.marketapp.view.wheelview.ArrayWheelAdapter;
import com.shop.marketapp.view.wheelview.OnWheelChangedListener;
import com.shop.marketapp.view.wheelview.WheelView;
import com.shop.marketapp.widget.CustomInitProgressDialog;
import com.shop.marketapp.widget.CustomProgressDialog;

public class AdressAddActivity extends BaseActivity {
	
	private TextView confirmTextView;
	private Resources resources;
	private String province[];
	private String cities[][];
	private RelativeLayout cityLayout;
	private TextView cityTextView;
	private SharedPreferences sharedPreferences;
	private CustomProgressDialog customInitProgressDialog;
	
	//添加收货地址
	private EditText userName;
	private EditText userPhone;
	private EditText addressEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setNeedBackGesture(true);//设置需要手势监听
		setContentView(R.layout.activity_adress_add);
		
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		
		((TextView)findViewById(R.id.common_tv_back)).setText("添加送货地址");
		
		confirmTextView=(TextView)findViewById(R.id.common_tv_right_text);
		confirmTextView.setText("确定");
		confirmTextView.setVisibility(View.VISIBLE);
		confirmTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("=========", "==============");
				String userNameSTr=userName.getText().toString();
				String phone=userPhone.getText().toString();
				String addressString=addressEt.getText().toString();
				if(inputOk(userNameSTr, phone, addressString)){
					Address address=new Address();
					address.setUid(sharedPreferences.getString(Constants.SP_LOGIN_NAME, ""));
					address.setName(userNameSTr);
					address.setPhone(phone);
					address.setAddress(addressString);
					new AddAdressAsn().execute(address);
				}
				
			}
		});
		cityTextView=(TextView)findViewById(R.id.address_tv_person_city);
		cityLayout=(RelativeLayout)findViewById(R.id.address_rl_person_city);
		cityLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Tools.hideInput(arg0);
				new PopupWindows(AdressAddActivity.this,cityLayout);
			}
		});
		
		
		resources = getResources();
		province = resources.getStringArray(R.array.province_item);
		cities = new String[][] { resources.getStringArray(Constants.city[0]),
				resources.getStringArray(Constants.city[1]),
				resources.getStringArray(Constants.city[2]),
				resources.getStringArray(Constants.city[3]),
				resources.getStringArray(Constants.city[4]),
				resources.getStringArray(Constants.city[5]),
				resources.getStringArray(Constants.city[6]),
				resources.getStringArray(Constants.city[7]),
				resources.getStringArray(Constants.city[8]),
				resources.getStringArray(Constants.city[9]),
				resources.getStringArray(Constants.city[10]),
				resources.getStringArray(Constants.city[11]),
				resources.getStringArray(Constants.city[12]),
				resources.getStringArray(Constants.city[13]),
				resources.getStringArray(Constants.city[14]),
				resources.getStringArray(Constants.city[15]),
				resources.getStringArray(Constants.city[16]),
				resources.getStringArray(Constants.city[17]),
				resources.getStringArray(Constants.city[18]),
				resources.getStringArray(Constants.city[19]),
				resources.getStringArray(Constants.city[20]),
				resources.getStringArray(Constants.city[21]),
				resources.getStringArray(Constants.city[22]),
				resources.getStringArray(Constants.city[23]),
				resources.getStringArray(Constants.city[24]),
				resources.getStringArray(Constants.city[25]),
				resources.getStringArray(Constants.city[26]),
				resources.getStringArray(Constants.city[27]),
				resources.getStringArray(Constants.city[28]),
				resources.getStringArray(Constants.city[29]),
				resources.getStringArray(Constants.city[30]),
				resources.getStringArray(Constants.city[31]),
				resources.getStringArray(Constants.city[32]),
				resources.getStringArray(Constants.city[33]) };
		
		userName=(EditText)findViewById(R.id.address_et_person_name);
		userPhone=(EditText)findViewById(R.id.address_et_person_phone);
		addressEt=(EditText)findViewById(R.id.address_et_person_address);
		
		//获得从地址传来的数据
		Bundle bundle=this.getIntent().getExtras();
		if(bundle!=null){
			Address address=(Address)bundle.getSerializable("address");
			userName.setText(address.getName());
			userPhone.setText(address.getPhone());
			addressEt.setText(address.getAddress());
		}
		
		
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View.inflate(mContext, R.layout.choose_city, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));
			setContentView(view);

			final WheelView country = (WheelView) view
					.findViewById(R.id.province);
			country.setVisibleItems(5);
			country.setCyclic(true);//
			country.setAdapter(new ArrayWheelAdapter<String>(province));

			final WheelView city = (WheelView) view.findViewById(R.id.city);
			city.setVisibleItems(5);
			country.addChangingListener(new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue,
						int newValue) {
					System.out.println("id" + oldValue + "be+" + newValue);
					city.setAdapter(new ArrayWheelAdapter<String>(
							cities[newValue]));
					city.setCurrentItem(cities[newValue].length / 2);
				}
			});

			country.setCurrentItem(16);

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);

			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button cacle = (Button) view.findViewById(R.id.choosecity_cancel);
			Button confin = (Button) view.findViewById(R.id.choosecity_confin);
			cacle.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});
			confin.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					cityTextView.setText(province[country.getCurrentItem()]
									+ " "
									+ cities[country.getCurrentItem()][city
											.getCurrentItem()]);
					dismiss();
				}
			});

		}
	}
	public class AddAdressAsn extends AsyncTask<Address, String, JSONObject>{

		protected void onPreExecute() {
			customInitProgressDialog = new CustomProgressDialog(
					AdressAddActivity.this, "正在保存中...", R.anim.rotate_loading);
			customInitProgressDialog.show();
			Log.v("test", "onPreExecute");
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(Address... params) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			map.put("jsonStr", Tools.getJsonStringByEntity(params[0]));
			String url=HttpUtil.BASE_URL+"address!addAddress.action";
					
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			if(result!=null){
				customInitProgressDialog.cancel();
				finish();
			}
		}
		
	}
	//判断输入的内容是否为空
	
	public boolean inputOk(String userName,String phone,String address){
		if(userName.equals("")){
			DialogUtil.showToast(AdressAddActivity.this, "收货人姓名不能为空");
			return false;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); 
		Matcher m = p.matcher(phone); 
		if (!m.matches()) {
			DialogUtil.showToast(AdressAddActivity.this, "请输入正确的手机号码");
			return false;
		}
		if(address.equals("")){
			DialogUtil.showToast(AdressAddActivity.this, "收货地址不能为空");
			return false;
		}
		return true;
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
//		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
