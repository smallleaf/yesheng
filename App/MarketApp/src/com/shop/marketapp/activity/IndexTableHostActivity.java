package com.shop.marketapp.activity;


import com.example.marketapp.AllOrderActivity;
import com.example.marketapp.R;
import com.example.marketapp.R.layout;
import com.example.marketapp.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

/**
 * 主页 tablehost
 */
public class IndexTableHostActivity extends TabActivity {

	public static Activity activity;
	private RadioGroup mTabButtonGroup;
	private TabHost mTabHost;
	
	public static final String TAB_MAIN = "MAIN_ACTIVITY";
	public static final String TAB_BUYCAR = "BUYCAR_ACTIVITY";
	public static final String TAB_COLLECTION = "COLLECTION_ACTIVITY";
	public static final String TAB_PERSONAL = "PERSONAL_ACTIVITY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_home);
		activity=this;
		mTabButtonGroup = (RadioGroup) findViewById(R.id.main_radioGroud_tableGroup);
		
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		mTabHost=getTabHost();
		
		//tablehost页面选择
		Intent i_main=new Intent(this, MainActivity.class);
		Intent i_butcar=new Intent(this,AllOrderActivity.class);
		Intent i_collection=new Intent(this,CollectionActivity.class);
		Intent i_personal=new Intent(this,PersonalActivity.class);
		
		
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN).setContent(i_main));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_BUYCAR).setIndicator(TAB_BUYCAR).setContent(i_butcar));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_COLLECTION).setIndicator(TAB_COLLECTION).setContent(i_collection));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL).setIndicator(TAB_PERSONAL).setContent(i_personal));
		
		//通过设置的标签选择哪个界面
		mTabHost.setCurrentTabByTag(TAB_MAIN);
		
		mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_rb_buycar:
					mTabHost.setCurrentTabByTag(TAB_BUYCAR);
					break;
					
				case R.id.main_rb_collect:
					mTabHost.setCurrentTabByTag(TAB_COLLECTION);
					break;
					
				case R.id.main_rb_main:
					mTabHost.setCurrentTabByTag(TAB_MAIN);
					break;
					
				case R.id.main_rb_person:
					mTabHost.setCurrentTabByTag(TAB_PERSONAL);
					break;
					
				default:
					break;
				}
			}
		});
	}
}
