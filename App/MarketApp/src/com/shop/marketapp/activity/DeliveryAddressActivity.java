package com.shop.marketapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.i;
import com.baidu.location.i.b;
import com.baidu.mapapi.BMapManager;
import com.example.marketapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.shop.marketapp.activity.MainActivity.MyLocationListener;
import com.shop.marketapp.adapter.AddressManagerAdapter;
import com.shop.marketapp.base.BaseActivity;
import com.shop.marketapp.base.BaseApplication;
import com.shop.marketapp.bean.Address;
import com.shop.marketapp.bean.AddressManager;
import com.shop.marketapp.bean.Orders;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.DialogUtil;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.constant.Tools;
import com.shop.marketapp.widget.CustomInitProgressDialog;
import com.shop.marketapp.widget.CustomProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DeliveryAddressActivity extends BaseActivity {
	private final static String TAG="===DeliveryAddressActivity===";
	
	private RelativeLayout addAddressLayout;
	private ListView addressListView;
	private ArrayList<Address> addressManagers = new ArrayList<Address>();
	private AddressManagerAdapter adapter;
	private TextView dingweiAgain;
	private RelativeLayout dingweiLayout;
	// GPS定位
	/**
	 * Application对象的引用
	 */
	protected BaseApplication mApplication;

	/**
	 * 定位SDK的核心类
	 */
	private LocationClient mLocationClient;// 定位SDK的核心类
	private BDLocationListener mLocationListener;// 作用：获取定位结果，获取POI信息

	//请求数据时的对话框
	private CustomInitProgressDialog customInitProgressDialog;
	private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setNeedBackGesture(true);//设置需要手势监听
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mApplication = (BaseApplication) this.getApplication();
		if (mApplication.mBMapManager == null) {
			mApplication.mBMapManager = new BMapManager(mApplication.mContext);
			mApplication.initEngineManager();
		}
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_delivery_address);
		((TextView) findViewById(R.id.common_tv_back)).setText("添加配送地址");
		dingweiLayout = (RelativeLayout) findViewById(R.id.activity_delivery_rl_gpsagain);
		dingweiAgain = (TextView) findViewById(R.id.activity_delivery_tv_gpsagain);
		addressListView = (ListView) findViewById(R.id.activity_delivery_lv_address_manager);
		addAddressLayout = (RelativeLayout) findViewById(R.id.activity_delivery_rl_add);
		Bundle bundle=this.getIntent().getExtras();
		dingweiAgain.setText(bundle.getString("loaction"));
		
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		addAddressLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(DeliveryAddressActivity.this,
						AdressAddActivity.class);
				startActivity(intent);
			}
		});
		dingweiLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initLocation();
			}
		});
	
		addressManagers=new ArrayList<Address>();
		adapter = new AddressManagerAdapter(DeliveryAddressActivity.this,
				addressManagers);
		addressListView.setAdapter(adapter);

		addressListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				for (int i = 0; i < addressManagers.size(); i++) {
					// 点击时全部设置为未选中
					addressManagers.get(i).setChoosed(false);
				}
				// 将选中的选为true
				addressManagers.get(position).setChoosed(true);
				adapter.notifyDataSetChanged();
			}
		});

		addressListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int position, long arg3) {
						// TODO Auto-generated method stub
						new AddressManagerPopwindows(
								DeliveryAddressActivity.this, addressListView,
								position);
						return false;
					}
				});
	}

	private void initDate() {
		// TODO Auto-generated method stub
		new getAddressAs().execute(sharedPreferences.getString(Constants.SP_LOGIN_NAME, ""));
	}

	/** 初始化定位系统 */
	private void initLocation() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);

		LocationClientOption locationOption = new LocationClientOption();
		locationOption.setOpenGps(true);
		locationOption.setCoorType("bd09ll");
		locationOption.setAddrType("all");// 返回的定位结果包含地址信息
		locationOption.setPriority(LocationClientOption.GpsFirst);
		locationOption.setAddrType("all");
		locationOption.setProdName("通过GPS定位");
		mLocationClient.setLocOption(locationOption);

		mLocationClient.start();

	}

	public class AddressManagerPopwindows extends PopupWindow {
		@SuppressLint("NewApi")
		public AddressManagerPopwindows(Context mContext, View parent,
				final int position) {
			View view = View.inflate(mContext, R.layout.address_manager_pop,
					null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_in));
			setWidth(LayoutParams.WRAP_CONTENT);
			setHeight(LayoutParams.WRAP_CONTENT);

			RelativeLayout changeLayout = (RelativeLayout) view
					.findViewById(R.id.address_manager_pop_rl_change);
			RelativeLayout deleteLayout = (RelativeLayout) view
					.findViewById(R.id.address_manager_pop_rl_delete);
			// popWindow背景变灰
			final WindowManager.LayoutParams params = DeliveryAddressActivity.this
					.getWindow().getAttributes();
			params.alpha = 0.5f;
			DeliveryAddressActivity.this.getWindow().setAttributes(params);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.CENTER, 0, 0);
			update();

			setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					// TODO Auto-generated method stub
					params.alpha = 1f;
					DeliveryAddressActivity.this.getWindow().setAttributes(
							params);
				}
			});

			changeLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new deleteAddressAs(position).execute();
					Intent intent = new Intent();
					intent.setClass(DeliveryAddressActivity.this,
							AdressAddActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("address", addressManagers.get(position));
					intent.putExtras(bundle);
					startActivity(intent);
					dismiss();
				}
			});
			deleteLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dismiss();
					new deleteAddressAs(position).execute();
				}
			});
		}
	}

	class MyLocationListener implements BDLocationListener {// BDLocationListener获取定位结果，获取POI信息

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			dingweiAgain.setText(location.getAddrStr());
		}

		@Override
		public void onReceivePoi(BDLocation location) {
			// TODO Auto-generated method stub
		}

	}

	//删除地址
	class deleteAddressAs extends AsyncTask<Integer, String, JSONObject>{

		private int id;
		public deleteAddressAs(int id){
			this.id=id;
		}
		@Override
		protected JSONObject doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			String url=HttpUtil.BASE_URL+"address!deleteAddress?id="+addressManagers.get(id).getId();
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.getRequst(url));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;
		}
			@Override
			protected void onPostExecute(JSONObject result) {
				super.onPostExecute(result);
				if(result!=null){
					addressManagers.remove(id);
					adapter.notifyDataSetChanged();
				}
			}
		
	}
	class getAddressAs extends AsyncTask<String, String, JSONObject>{

		protected void onPreExecute() {
			customInitProgressDialog=CustomInitProgressDialog.createDialog(DeliveryAddressActivity.this);
			customInitProgressDialog.setMessage("正在加载地址...");
			customInitProgressDialog.show();
			Log.v("test", "onPreExecute");
			super.onPreExecute();
			
		}
		
		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url=HttpUtil.BASE_URL+"address!queryAddress.action?uid="+params[0];
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.getRequst(url));
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
				customInitProgressDialog.dismiss();
				Gson gson = new Gson();
				addressManagers.clear();
				ArrayList<Address> addresses;
				try {
					addresses = gson.fromJson(result.getString("addrs"),
							new TypeToken<ArrayList<Address>>() {
							}.getType());
					if(addresses!=null){
						for(Address address:addresses){
							addressManagers.add(address);
						}
					}
					adapter.notifyDataSetChanged();
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
	public void onResume(){
		Log.v(TAG, "==onResume====");
		initDate();
		super.onResume();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
