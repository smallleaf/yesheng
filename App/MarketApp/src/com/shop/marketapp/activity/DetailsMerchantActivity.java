package com.shop.marketapp.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.marketapp.R;
import com.example.marketapp.R.layout;
import com.example.marketapp.R.menu;
import com.google.gson.JsonArray;
import com.shop.marketapp.adapter.MerchantFoodAdapter;
import com.shop.marketapp.base.BaseActivity;
import com.shop.marketapp.bean.Goods;
import com.shop.marketapp.bean.MerChantBean;
import com.shop.marketapp.bean.Orders;
import com.shop.marketapp.bean.db.DataBaseManager;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.constant.Tools;
import com.shop.marketapp.image.ImageUtils;
import com.shop.marketapp.server.DetaileMerchantServer;
import com.shop.marketapp.widget.BadgeView;
import com.shop.marketapp.widget.CustomInitProgressDialog;
import com.shop.marketapp.widget.GridViewWithHeaderAndFooter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailsMerchantActivity extends BaseActivity implements
		OnClickListener {

	private SharedPreferences sharedPreferences;
	/* 商品展示gridview */
	private ListView merchantFoodGridView;
	private MerchantFoodAdapter merchantFoodAdapter;
	/** 下滑时选择种类消失，上滑时显示 */
	private View addKindsView;
	private LinearLayout linearLayout;
	// 标记是否滑动
	private boolean srollFlag = false;
	int flag = 0;
	/** 标记上次滑动位置 */
	private int lastvisibleItemPosition = 0;
	/** 增加尾部 */
	private View addFooterView;
	/** 增加头部 */
	private View addHeaderView;

	/** 商品分类 */
	private TextView goodsKindsTextView;
	/**加载动画*/
	private CustomInitProgressDialog customProgressDialog;
	/** 购物总价格那栏 */
	private TextView sumtTextView;
	private double sum=0.0;
	/**显示购物车右上角数量*/
	private BadgeView badgeView;
	/**订单保存服务器  同时传递到购物车时的数据*/
	private ArrayList<Orders> orderArrayList=new ArrayList<Orders>();
	
	private int buyCount=0;//提示购买了几件商品；
	//存储位置 防止在一个商品山显示多个数量
	private ArrayList<Integer> storePosition=new ArrayList<Integer>();
	private boolean flagStore=false;//防止返回时的重复提交操作
	// 购物车
	private RelativeLayout buyCarRelativeLayout;
	private ArrayList<Goods> goodsArrayList=new ArrayList<Goods>();
	/**商品类别的改变时 */
	private ArrayList<Goods> goodsTemp=new ArrayList<Goods>();
	private MerChantBean merChantBean;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			customProgressDialog.dismiss();
			initView();
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
		setNeedBackGesture(true);//设置需要手势监听
		setContentView(R.layout.activity_details_merchant);
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		customProgressDialog=CustomInitProgressDialog.createDialog(DetailsMerchantActivity.this);
		customProgressDialog.setMessage("正在加载...");
		customProgressDialog.show();
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		merChantBean=(MerChantBean)bundle.getSerializable("merchantBean");
		((TextView)findViewById(R.id.common_tv_back)).setText(merChantBean.getName());
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		DataBaseManager dataBaseManager=new DataBaseManager(DetailsMerchantActivity.this);
		ArrayList<Goods> goodsArrayList=new ArrayList<Goods>();
		goodsArrayList=dataBaseManager.findGoods(merChantBean.getId(),0);
		if(goodsArrayList.size()==0){
			DetaileMerchantServer detaileMerchantServer=new DetaileMerchantServer(DetailsMerchantActivity.this,handler, merChantBean.getId(), this.goodsArrayList);
			new Thread(detaileMerchantServer).start();
		}else
		{
			this.goodsArrayList=goodsArrayList;
			for(Goods goods:goodsArrayList){
				goodsTemp.add(goods);
			}
			if(goodsArrayList.size()==0){
				Log.v("daxiao xie0", "fds");
			}
			customProgressDialog.dismiss();
			initView();
		}
	}

	public void initView() {
		// TODO Auto-generated method stub
		merchantFoodGridView = (ListView) findViewById(R.id.details_merchant_lv_content);
		merchantFoodAdapter = new MerchantFoodAdapter(this, goodsTemp);
		merchantFoodGridView.setAdapter(merchantFoodAdapter);
		sumtTextView=(TextView)findViewById(R.id.common_goods_sun_tv_sum);
		goodsKindsTextView = (TextView) findViewById(R.id.details_merchant_tv_goodkinds);
		goodsKindsTextView.setOnClickListener(this);
		buyCarRelativeLayout = (RelativeLayout) findViewById(R.id.common_goods_sun_rl_goods);
		buyCarRelativeLayout.setOnClickListener(this);
		badgeView=new BadgeView(DetailsMerchantActivity.this,buyCarRelativeLayout);
		merchantFoodGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int poisition, long arg3) {
				// TODO Auto-generated method stub
				new GoodsBuyPopwindows(DetailsMerchantActivity.this,
						merchantFoodGridView,poisition);
				Log.v("位置", String.valueOf(poisition));
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.details_merchant_tv_goodkinds:
			System.out.println("sddff");
			new GoodsKindPopwindows(this, v);
			break;
		case R.id.common_goods_sun_rl_goods:
			Intent intent = new Intent();
			intent.setClass(DetailsMerchantActivity.this, BuyCarActivity.class);
			//疑问 如果 批量上传 当你跳转到购物车的时候提交数据 然后 在购物车界面你就会申请数据，但是你怎么知道你申请数据的时候
			//是在保存数据之前,
			//如果你将数据先不保存 ，传到购物车界面 但是你没有保存 不知道 你订单的id  如果你进行修改 进行更新操作的时候没有保存如何更新呢？
			//如果一条条数据提交呢？？
			//如果你提交后跳转界面，会出现卡顿的现象？？加个提交数据的对话框？？？有必要吗？？
			//还是跳转到购物车界面先 提交数据  再 获取数据  然后再进行操作 这是不是很浪费？？头很痛，过几天再想
			Bundle bundle=new Bundle();
			bundle.putSerializable("orderArrayList", orderArrayList);
			intent.putExtras(bundle);
			startActivity(intent);
			//如果跳转到购物车 就表示已经提交数据则不能再次提交数据了
			flagStore=true;
			break;
		default:
			break;
		}
	}

	public class GoodsKindPopwindows extends PopupWindow {
		public GoodsKindPopwindows(Context mContext, View parent) {
			View view = View.inflate(mContext,
					R.layout.common_goods_kind_popwindow, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_in));
			ListView goodsListView = (ListView) view
					.findViewById(R.id.common_pop_lv_goodskind);
			String[] strs = {"全部","蔬菜", "水果", "肉类", "水产" };
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
					R.layout.details_merchant_kinds_list, strs);
			goodsListView.setAdapter(adapter);
			setWidth(ImageUtils
					.screnWidthAndHeigthRace(DetailsMerchantActivity.this) / 4);
			setHeight(LayoutParams.WRAP_CONTENT);

			// popWindow背景变灰
			final WindowManager.LayoutParams params = DetailsMerchantActivity.this
					.getWindow().getAttributes();
			params.alpha = 0.5f;
			DetailsMerchantActivity.this.getWindow().setAttributes(params);

			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAsDropDown(parent, 0, 0);
			update();
			goodsListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					goodsTemp.clear();
					for(Goods goods:goodsArrayList){
						if(goods.getType()==position){
							goodsTemp.add(goods);
						}
					}
					if(position==0){
						for(Goods goods:goodsArrayList){
								goodsTemp.add(goods);
						}
					}
					merchantFoodAdapter.notifyDataSetChanged();
					dismiss();
				}
			});
			setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					// TODO Auto-generated method stub
					params.alpha = 1f;
					DetailsMerchantActivity.this.getWindow().setAttributes(
							params);
				}
			});

		}
	}

	public class GoodsSumPopwindows extends PopupWindow {
		@SuppressLint("NewApi")
		public GoodsSumPopwindows(Context mContext, View parent) {
			View view = View.inflate(mContext,
					R.layout.common_goods_sum_popwindow, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));
			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.MATCH_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();
		}
	}

	public class GoodsBuyPopwindows extends PopupWindow {
		@SuppressLint("NewApi")
		public GoodsBuyPopwindows(Context mContext, View parent,final int position) {
			View view = View.inflate(mContext, R.layout.common_buy_popwindow,
					null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_in));
			setWidth(LayoutParams.WRAP_CONTENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			TextView goodsName=(TextView)view.findViewById(R.id.common_buy_popwindow_tv_name);
			goodsName.setText(goodsArrayList.get(position).getName()+"  "+goodsArrayList.get(position).getPrice()+"/斤");
			final EditText money=(EditText)view.findViewById(R.id.common_buy_popwindow_et_money);
			Button confirm=(Button)view.findViewById(R.id.common_buy_popwindow_bt_confirm);
			Button cancel=(Button)view.findViewById(R.id.common_buy_popwindow_bt_cancel);
			// popWindow背景变灰
			final WindowManager.LayoutParams params = DetailsMerchantActivity.this
					.getWindow().getAttributes();
			params.alpha = 0.5f;
			DetailsMerchantActivity.this.getWindow().setAttributes(params);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.CENTER, 0, 0);
			update();
			confirm.setOnClickListener(new OnClickListener() {
				//商品分类 时的测试
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//该位置所代表的商品id
					int goodsId=goodsTemp.get(position).getId();
					Log.v("========", String.valueOf(goodsId));
					String sumString=money.getText().toString();
					if(sumString!=null&&!sumString.equals("")){
					//将商品的价格修改
					goodsTemp.get(position).setGoodsale(Double.valueOf(sumString));
					//将商品的价格修改
					goodsArrayList.get(goodsArrayList.indexOf(goodsTemp.get(position))).setGoodsale(Double.valueOf(sumString));
					merchantFoodAdapter.notifyDataSetChanged();
					}
					if(sumString.equals("")){
						goodsTemp.get(position).setGoodsale(0);
						goodsArrayList.get(goodsArrayList.indexOf(goodsTemp.get(position))).setGoodsale(0);
						merchantFoodAdapter.notifyDataSetChanged();
					}
					//如果价格类容不 为0  并且存储的位置不包含以前的位置
					if(!sumString.equals("")&&storePosition.indexOf(goodsId)==-1){
						buyCount++;
						badgeView.setText(String.valueOf(buyCount));
						badgeView.show();
						storePosition.add(goodsId);
					}
					/**添加订单*/
					for(int i=0;i<orderArrayList.size();i++){
						//表示是在同一商品下则将以前的该订单删除 重新加入这个订单
						if(orderArrayList.get(i).getGid()==goodsId){
							orderArrayList.remove(i);
						}
					}
					Orders orders=new Orders();
					orders.setGid(goodsId);
					orders.setUid(sharedPreferences.getString(Constants.SP_LOGIN_NAME, ""));
					if(sumString.equals(""))
						orders.setMount(0);
					else {
						orders.setMount(Double.valueOf(sumString));
					}
					orderArrayList.add(orders);
					sum=0;
					for(Orders orders2:orderArrayList){
						sum+=orders2.getMount();
					}
					sumtTextView.setText("总共"+sum+"元");
					//只要修改了 那么 你再进行页面跳转时就得能保存数据
					flagStore=false;
					Editor editor=sharedPreferences.edit();
					editor.putBoolean(Constants.SP_DATA_CHANGE, true);
					editor.commit();
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
					DetailsMerchantActivity.this.getWindow().setAttributes(
							params);
				}
			});
		}
	}
	
	//向服务器传订单信息
	public class Storeorders extends AsyncTask<String, String, String>{
		
		@Override
		protected String doInBackground(String... jsonStr) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			Log.v("正在保存", "details 正在保存数据");
			map.put("jsonStr", jsonStr[0]);
			String url=HttpUtil.BASE_URL+"order!ordering";
			try {
				HttpUtil.postRequest(url, map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
	public void onResume(){
		super.onResume();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(!flagStore)
		{
			Editor editor=sharedPreferences.edit();
		editor.putBoolean(Constants.SP_DATA_CHANGE, false);
		editor.commit();
			new Storeorders().execute(Tools.getJsonStringByList(orderArrayList));
		}
		super.onBackPressed();
//		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

}
