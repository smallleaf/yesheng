package com.shop.marketapp.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.marketapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shop.marketapp.adapter.BuyCarAdapter;
import com.shop.marketapp.bean.Goods;
import com.shop.marketapp.bean.Orders;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.constant.Tools;
import com.shop.marketapp.widget.CustomInitProgressDialog;
import com.shop.marketapp.widget.RefreshSupportListView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BuyCarActivity extends Activity {

	public  final static  String TAG = "===BuyCarActivity===";
	private RefreshSupportListView refreshSupportListView;
	private BuyCarAdapter buyCarAdapter;
	private Activity activity;
	private ArrayList<Orders> orderArr;
	private ArrayList<Goods> goodsArr;
	private ArrayList<String> merChantName;
	private String userName = "null";
	private TextView sumMoney;
	private double sum = 0;
	private RelativeLayout loginRelativeLayout;
	private TextView backTextView;
	private SharedPreferences sharedPreferences;
	/**
	 * onResume时如果导一次数据则不在重新加载数据
	 */
	private boolean onResumeture = true;
	/** 加载动画 */
	private CustomInitProgressDialog customProgressDialog;
	/** 标记商品是否改变 */
	private boolean changed = false;
	/**
	 * 购物车勾选商品价格改变
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			sum = 0;
			for (int i = 0; i < orderArr.size(); i++) {
				if (orderArr.get(i).isChecked())
					sum += orderArr.get(i).getMount();
			}
			sumMoney.setText("总共" + sum + "元");
			buyCarAdapter.notifyDataSetChanged();
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_buy_car);
		((ImageView) findViewById(R.id.title_setting)).setVisibility(View.GONE);
		((TextView) findViewById(R.id.common_title_tv_titletext))
				.setText("购物车");
		loginRelativeLayout = (RelativeLayout) findViewById(R.id.acitivity_buy_car_rl_login);
		sharedPreferences = getSharedPreferences(Constants.SP_NAME,
				MODE_PRIVATE);
	}

	public void initView() {
		userName = sharedPreferences.getString(Constants.SP_LOGIN_NAME, "");
			backTextView = (TextView) findViewById(R.id.title_location);
			backTextView.setVisibility(View.VISIBLE);
			backTextView.setText("返回");
			backTextView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		if (sharedPreferences.getBoolean(Constants.SP_ISLOGIN, false)) {
			sumMoney = (TextView) findViewById(R.id.common_goods_sun_tv_sum);
			customProgressDialog = CustomInitProgressDialog
					.createDialog(BuyCarActivity.this);
			customProgressDialog.setMessage("正在加载...");
			customProgressDialog.show();
			//用户没有登陆时商品的界面消失，未登录界面出来
			loginRelativeLayout.setVisibility(View.GONE);
			onResumeture = false;
			activity = this;
			refreshSupportListView = (RefreshSupportListView) findViewById(R.id.activity_buy_car_rslv);
			refreshSupportListView.setVisibility(View.VISIBLE);
			refreshSupportListView
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							new GoodsBuyPopwindows(BuyCarActivity.this,
									refreshSupportListView, position);
						}
					});
			new GetOrder().execute(userName);
		} else {
			
			//用户没有登陆时商品的界面出来，未登录界面消失
			((LinearLayout) findViewById(R.id.detials_merchant_ll_bottom))
					.setVisibility(View.GONE);
			((RefreshSupportListView) findViewById(R.id.activity_buy_car_rslv)).setVisibility(View.GONE);
			loginRelativeLayout.setVisibility(View.VISIBLE);
			loginRelativeLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//点击登陆 后返回到购物车界面，此时要加载数据 于是设置恢复为true
					onResumeture=true;
					Intent intent = new Intent();
					intent.setClass(BuyCarActivity.this, LoginActivity.class);
					startActivity(intent);
				}
			});
		}
	}

	public class GetOrder extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.v("正在获取数据", "正在跟新");
			Map<String, String> map = new HashMap<String, String>();
			map.put("uid", params[0]);
			String url = HttpUtil.BASE_URL + "order!getOrder";
			String jsonString = null;
			try {
				jsonString = HttpUtil.postRequest(url, map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonString;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject(result);
					Gson gson = new Gson();
					orderArr = gson.fromJson(jsonObject.getString("orders"),
							new TypeToken<ArrayList<Orders>>() {
							}.getType());
					goodsArr = gson.fromJson(jsonObject.getString("goods"),
							new TypeToken<ArrayList<Goods>>() {
							}.getType());
					merChantName = gson.fromJson(
							jsonObject.getString("merChantName"),
							new TypeToken<ArrayList<String>>() {
							}.getType());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				customProgressDialog.cancel();

				// 从商品界面获得的数据
				buyCarAdapter = new BuyCarAdapter(handler, activity, orderArr,
						goodsArr, merChantName);
				refreshSupportListView.setAdapter(buyCarAdapter);
				sum = 0;
				for (int i = 0; i < orderArr.size(); i++) {
					if (orderArr.get(i).isChecked())
						sum += orderArr.get(i).getMount();
				}
				sumMoney.setText("总共" + sum + "元");
			} else {
				customProgressDialog.cancel();
				sumMoney.setText("总共0元");
			}
		}
	}

	public class GoodsBuyPopwindows extends PopupWindow {
		@SuppressLint("NewApi")
		public GoodsBuyPopwindows(Context mContext, View parent,
				final int position) {
			View view = View.inflate(mContext, R.layout.common_buy_popwindow,
					null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_in));
			setWidth(LayoutParams.WRAP_CONTENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			TextView goodsName = (TextView) view
					.findViewById(R.id.common_buy_popwindow_tv_name);
			goodsName.setText(goodsArr.get(position - 1).getName() + "  "
					+ goodsArr.get(position - 1).getPrice() + "/斤");
			final EditText money = (EditText) view
					.findViewById(R.id.common_buy_popwindow_et_money);
			money.setText(String.valueOf(orderArr.get(position - 1).getMount()));
			Button confirm = (Button) view
					.findViewById(R.id.common_buy_popwindow_bt_confirm);
			Button cancel = (Button) view
					.findViewById(R.id.common_buy_popwindow_bt_cancel);
			// popWindow背景变灰
			final WindowManager.LayoutParams params = IndexTableHostActivity.activity
					.getWindow().getAttributes();
			params.alpha = 0.5f;
			BuyCarActivity.this.getWindow().setAttributes(params);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.CENTER, 0, 0);
			update();
			confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 切换界面 提交信息到服务器
					orderArr.get(position - 1).setChanged(true);
					changed = true;
					String sumString = money.getText().toString();
					if (sumString != null) {
						if (sumString.equals("")) {
							orderArr.get(position - 1).setMount(0);
						} else {
							orderArr.get(position - 1).setMount(
									Double.valueOf(sumString));
						}
					}
					buyCarAdapter.notifyDataSetChanged();
					sum = 0;
					for (int i = 0; i < orderArr.size(); i++) {
						if (orderArr.get(i).isChecked())
							sum += orderArr.get(i).getMount();
					}
					sumMoney.setText("总共" + sum + "元");
					dismiss();
				}
			});
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismiss();
				}
			});
			setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					// TODO Auto-generated method stub
					params.alpha = 1f;
					IndexTableHostActivity.activity.getWindow().setAttributes(
							params);
				}
			});
		}
	}

	// 向服务器传订单信息
	public class Storeorders extends AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... jsonStr) {
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			Log.v(TAG, "details 正在保存数据");
			map.put("jsonStr", jsonStr[0]);
			String url = HttpUtil.BASE_URL + "order!ordering";
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(HttpUtil.postRequest(url, map));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;

		}

		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			try {
				if (result.getBoolean("state")) {
					// 保存到服务器成功 返回数据
					Log.v("=======butcar", "服务器返回成功");
					Editor editor = sharedPreferences.edit();
					editor.putBoolean(Constants.SP_DATA_CHANGE, false);
					editor.commit();
					initView();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class UpdateOrders extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<Orders> changeOrders = new ArrayList<Orders>();
			for (Orders orders : orderArr) {
				if (orders.isChanged()) {
					changeOrders.add(orders);
				}
			}
			String url = HttpUtil.BASE_URL + "order!updateOrders";
			Map<String, String> map = new HashMap<String, String>();
			map.put("jsonStr", Tools.getJsonStringByList(changeOrders));
			try {
				HttpUtil.postRequest(url, map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	@Override
	protected void onResume() {
		Log.v("---burcharactivity----", "onResume");
		// 如果数据是从上个页面传过来的
		if (sharedPreferences.getBoolean(Constants.SP_DATA_CHANGE, false)) {
			Log.v(TAG, "上个页面有数据发生改变");
			Bundle bundle = BuyCarActivity.this.getIntent().getExtras();
			ArrayList<Orders> tempOrders=null;
			if(bundle!=null)
			{
				tempOrders = (ArrayList<Orders>) bundle
						.getSerializable("orderArrayList");
			}			
//			//如果未登陆
//			if(!sharedPreferences.getBoolean(Constants.SP_ISLOGIN, false)){
//				Log.v(TAG, "用户未登陆");
//				Intent intent=new Intent();
//				intent.setClass(BuyCarActivity.this, LoginActivity.class);
//				startActivity(intent);
//			}
//			else {
				Log.v(TAG, "提交数据");
				String uid=sharedPreferences.getString(Constants.SP_LOGIN_NAME, "");
				for(int i=0;i<tempOrders.size();i++){
					tempOrders.get(i).setUid(uid);
				}
				new Storeorders().execute(Tools.getJsonStringByList(tempOrders));
//			}
		}
		super.onResume();
	}

	protected void onPause() {
		if (changed) {
			new UpdateOrders().execute();
		}
		super.onPause();
	}
}
