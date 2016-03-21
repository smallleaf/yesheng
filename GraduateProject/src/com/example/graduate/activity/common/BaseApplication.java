package com.example.graduate.activity.common;



import java.io.File;

import com.example.graduate.image.ImageLoaderConfig;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


/**
 * @author Tau.Chen é™ˆæ¶›
 * 
 * @email tauchen1990@gmail.com,1076559197@qq.com
 * 
 * @date 2013å¹?æœ?2æ—?
 * 
 * @version V_1.0.0
 * 
 * @description
 * 
 */
public class BaseApplication extends Application {

    public Context mContext;
    public static Application application;
    public static Activity activity;
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 mContext = getApplicationContext();
		 application=this;
	    
		ImageLoaderConfig.initImageLoader(this, "graduateProject/image");
	}

	public static Activity getActivity(){
		return activity;
	}
	public static Application getApplication(){
		return application;
	}

}
