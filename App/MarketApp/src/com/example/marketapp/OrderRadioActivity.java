package com.example.marketapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shop.marketapp.activity.BuyCarActivity;
import com.shop.marketapp.activity.IndexTableHostActivity;
import com.shop.marketapp.activity.LoginActivity;
import com.shop.marketapp.activity.BuyCarActivity.GetOrder;
import com.shop.marketapp.activity.BuyCarActivity.GoodsBuyPopwindows;
import com.shop.marketapp.activity.BuyCarActivity.Storeorders;
import com.shop.marketapp.activity.BuyCarActivity.UpdateOrders;
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
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

public class OrderRadioActivity extends Activity {

	public  final static  String TAG = "===BuyCarActivity===";
	private RefreshSupportListView refreshSupportListView;
	private BuyCarAdapter buyCarAdapter;
	private Activity activity;
	private ArrayList<Orders> orderArr;
	private ArrayList<Goods> goodsArr;
	private ArrayList<String> merChantName;
	private String userName = "null";
	private SharedPreferences sharedPreferences;
	/**
	 * onResume时如果导一次数据则不在重新加载数据
	 */
	private boolean onResumeture = true;
	/** 加载动画 */
	/** 标记商品是否改变 */
	private boolean changed = false;
	/**
	 * 显示加载动画
	 */
	LinearLayout loadingLayout;
	/**
	 * 购物车勾选商品价格改变
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			buyCarAdapter.notifyDataSetChanged();
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_radio);
		sharedPreferences = getSharedPreferences(Constants.SP_NAME,
				MODE_PRIVATE);
	}

	public void initView() {
		userName = sharedPreferences.getString(Constants.SP_LOGIN_NAME, "");
			onResumeture = false;
			activity = this;
			loadingLayout=(LinearLayout)findViewById(R.id.loading_data);
			loadingLayout.setVisibility(View.VISIBLE);
			refreshSupportListView = (RefreshSupportListView) findViewById(R.id.activity_buy_car_rslv);
		
			refreshSupportListView
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							new GoodsBuyPopwindows(OrderRadioActivity.this,
									refreshSupportListView, position);
						}
					});
			new GetOrder().execute(userName);
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

				// 从商品界面获得的数据
				buyCarAdapter = new BuyCarAdapter(handler, activity, orderArr,
						goodsArr, merChantName);
				refreshSupportListView.setAdapter(buyCarAdapter);
				refreshSupportListView.setVisibility(View.VISIBLE);
				
				loadingLayout.setVisibility(View.GONE);
			} else {
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
			OrderRadioActivity.this.getWindow().setAttributes(params);
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
//					sum = 0;
//					for (int i = 0; i < orderArr.size(); i++) {
//						if (orderArr.get(i).isChecked())
//							sum += orderArr.get(i).getMount();
//					}
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
		//加载数据
			initView();
		super.onResume();
	}

	protected void onPause() {
		if (changed) {
			new UpdateOrders().execute();
		}
		super.onPause();
	}

}
