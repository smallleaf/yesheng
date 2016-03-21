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
 * @author Tau.Chen 陈涛
 * 
 * @email tauchen1990@gmail.com,1076559197@qq.com
 * 
 * @date 2013�?�?2�?
 * 
 * @version V_1.0.0
 * 
 * @description
 * 
 */
public class BaseApplication extends Application {

	public BMapManager mBMapManager = null;//BMapManager��ͼ���������
    public Context mContext;
    public static Application application;
    public static Activity activity;
    /** 
     * ����İٶȵ�ͼAPI Key��Կ 
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
        	Log.v("��ʼ����ͼʧ��", "��ʼ����ͼʧ��");
            Toast.makeText(getApplicationContext(), 
                    "��ʼ����ͼ����ʧ��!", Toast.LENGTH_LONG).show();
        }
	}
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	//��������app���˳�֮ǰ����mapapi��destroy()�����������ظ���ʼ��������ʱ������
	public void onTerminate() {
		// TODO Auto-generated method stub
		 if (mBMapManager != null) {
	            mBMapManager.destroy();
	            mBMapManager = null;
	        }
		super.onTerminate();
	}
	
	 class MyGeneralListener implements MKGeneralListener {//�����¼���������������ͨ�������������Ȩ��֤�����
	    	//MKGeneralListener�ýӿڷ�������״̬����Ȩ��֤�Ƚ�����û���Ҫʵ�ָýӿ��Դ�����Ӧ�¼�
	        @Override
	        public void onGetNetworkState(int iError) {
	            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
	                Toast.makeText(getApplicationContext(), "����δ���ӣ�������������ԣ�",
	                    Toast.LENGTH_LONG).show();
	            }
	            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
	                Toast.makeText(getApplicationContext(), "������ȷ�ļ���������",
	                        Toast.LENGTH_LONG).show();
	            }
	            // ...
	        }

	        @Override
	        public void onGetPermissionState(int iError) {
	            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
	                //��ȨKey����
	                Toast.makeText(getApplicationContext(), 
	                        "���� MyApplication.java�ļ�������ȷ����ȨKey��", Toast.LENGTH_LONG).show();
	            }
	        }
	    }

}
