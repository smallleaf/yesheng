package com.example.marketapp;

import com.shop.marketapp.activity.BuyCarActivity;
import com.shop.marketapp.activity.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class AllOrderActivity extends TabActivity {

	TabHost tabHost;
	RadioGroup radioGroup;
	private TextView backTextView;
	
	public static final String TAB_ORDER_MAIN = "MAIN_ORDER_ACTIVITY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_all_order);
		((TextView)findViewById(R.id.common_title_tv_titletext)).setText("我的订单");
		initView();
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		radioGroup = (RadioGroup)findViewById(R.id.Order_radioGroud_tableGroup);
		
		tabHost=this.getTabHost();
		
		//tablehost页面选择
		Intent i_main=new Intent(this, OrderRadioActivity.class);
		
		tabHost.addTab(tabHost.newTabSpec(TAB_ORDER_MAIN).setIndicator(TAB_ORDER_MAIN).setContent(i_main));
		
		tabHost.setCurrentTabByTag(TAB_ORDER_MAIN);
	}
}
