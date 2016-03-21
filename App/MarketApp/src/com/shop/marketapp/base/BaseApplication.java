package com.shop.marketapp.base;



import java.io.File;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.image.ImageLoaderConfig;

/**
 * @author Tau.Chen 闄堟稕
 * 
 * @email tauchen1990@gmail.com,1076559197@qq.com
 * 
 * @date 2013骞?鏈?2鏃?
 * 
 * @version V_1.0.0
 * 
 * @description
 * 
 */
public class BaseApplication extends Application {

	public BMapManager mBMapManager = null;//BMapManager地图引擎管理类
    public Context mContext;
    public static Application application;
    public static Activity activity;
    /** 
     * 申请的百度地图API Key密钥 
     */  
    private static final String BAIDU_MAP_KEY = "8BB7F0E5C9C77BD6B9B655DB928B74B6E2D838FD";  
    
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
		 activity=this.activity;
	     mBMapManager = new BMapManager(getApplicationContext());
	    
		initEngineManager();
		ImageLoaderConfig.initImageLoader(this, "tetst/test");
	}

	public static Activity getActivity(){
		return activity;
	}
	public static Application getApplication(){
		return application;
	}
	public void initEngineManager() {
        if (!mBMapManager.init(BAIDU_MAP_KEY,new MyGeneralListener())) {
        	Log.v("初始化地图失败", "初始化地图失败");
            Toast.makeText(getApplicationContext(), 
                    "初始化地图引擎失败!", Toast.LENGTH_LONG).show();
        }
	}
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	//建议在您app的退出之前调用mapapi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		 if (mBMapManager != null) {
	            mBMapManager.destroy();
	            mBMapManager = null;
	        }
		super.onTerminate();
	}
	
	 class MyGeneralListener implements MKGeneralListener {//常用事件监听，用来处理通常的网络错误，授权验证错误等
	    	//MKGeneralListener该接口返回网络状态，授权验证等结果，用户需要实现该接口以处理相应事件
	        @Override
	        public void onGetNetworkState(int iError) {
	            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
	                Toast.makeText(getApplicationContext(), "网络未连接，请检查网络后重试！",
	                    Toast.LENGTH_LONG).show();
	            }
	            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
	                Toast.makeText(getApplicationContext(), "输入正确的检索条件！",
	                        Toast.LENGTH_LONG).show();
	            }
	            // ...
	        }

	        @Override
	        public void onGetPermissionState(int iError) {
	            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
	                //授权Key错误：
	                Toast.makeText(getApplicationContext(), 
	                        "请在 MyApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
	            }
	        }
	    }

}
