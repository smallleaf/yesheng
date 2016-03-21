package com.shop.marketapp.activity;

import com.example.marketapp.R;
import com.example.marketapp.R.layout;
import com.example.marketapp.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class CollectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_collection);
		((TextView)findViewById(R.id.common_title_tv_titletext)).setText("Œ“µƒ ’≤ÿ");
	}


}
