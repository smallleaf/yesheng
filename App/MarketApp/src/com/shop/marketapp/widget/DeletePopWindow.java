package com.shop.marketapp.widget;

import com.example.marketapp.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.PopupWindow.OnDismissListener;

public class DeletePopWindow extends PopupWindow{
	public DeletePopWindow(final Activity activity, View parent,int position) {
		View view = View.inflate(activity, R.layout.address_manager_pop,
				null);
		view.startAnimation(AnimationUtils.loadAnimation(activity,
				R.anim.fade_in));
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);

		RelativeLayout changeLayout=(RelativeLayout)view.findViewById(R.id.address_manager_pop_rl_change);
		changeLayout.setVisibility(View.GONE);
		RelativeLayout deleteLayout=(RelativeLayout)view.findViewById(R.id.address_manager_pop_rl_delete);
		// popWindow±³¾°±ä»Ò
		final WindowManager.LayoutParams params = activity
				.getWindow().getAttributes();
		params.alpha = 0.5f;
		activity.getWindow().setAttributes(params);
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
				activity.getWindow().setAttributes(
						params);
			}
		});
		
		deleteLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}
}
