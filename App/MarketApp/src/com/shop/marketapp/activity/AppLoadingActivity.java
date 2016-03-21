package com.shop.marketapp.activity;

import com.example.marketapp.R;
import com.shop.marketapp.base.BaseActivity;
import com.shop.marketapp.constant.DialogUtil;
import com.shop.marketapp.constant.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 页面引导加载
 * 
 * @author small leaf
 * 
 */
public class AppLoadingActivity extends BaseActivity {

	private AlphaAnimation start_anima;
	View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.activity_app_loading, null);
		setContentView(view);
		initView();
		initData();
	}

	private void initData() {
		start_anima = new AlphaAnimation(0.3f, 1.0f);
		start_anima.setDuration(2000);
		view.startAnimation(start_anima);
		start_anima.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if(Utils.checkNetWorkIsAvailable(getApplicationContext())){
					redirectTo();
				}else {
					DialogUtil.showToast(getApplicationContext(), "联网失败，请检查网络");
				}
				
			}
		});
	}

	private void initView() {

	}

	private void redirectTo() {
		startActivity(new Intent(getApplicationContext(), IndexTableHostActivity.class));
		finish();
	}
	
}
