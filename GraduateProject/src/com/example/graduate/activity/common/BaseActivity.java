package com.example.graduate.activity.common;




import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

/**
 * 
 * @author small leaf
 *抽象基类
 */
public abstract class BaseActivity extends Activity{
	
	/** 手势监听 */
	GestureDetector mGestureDetector;
	/** 是否需要监听手势关闭功能 */
	private boolean mNeedBackGesture = false;
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        initGestureDetector();
	    }
	    private void initGestureDetector() {
			if (mGestureDetector == null) {
				mGestureDetector = new GestureDetector(getApplicationContext(),
						new BackGestureListener(this));
			}
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			// TODO Auto-generated method stub
			if(mNeedBackGesture){
				return mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
			}
			return super.dispatchTouchEvent(ev);
		}
		
		/*
		 * 设置是否进行手势监听
		 */
		public void setNeedBackGesture(boolean mNeedBackGesture){
			this.mNeedBackGesture = mNeedBackGesture;
		}
		
	    @Override
	    protected void onStart() {
	    	Log.v("--onStart ---Current Activity ==","");
	        super.onStart();
	    }
	 
	    @Override
	    protected void onResume() {
	    	Log.v("--onResume ---Current Activity ==","");
	        super.onResume();
	    }
	 
	    @Override
	    protected void onStop() {
	    	Log.v("--onStop ---Current Activity ==","");
	        super.onResume();
	    }
	 
	    @Override
	    protected void onPause() {
	    	Log.v("--onPause ---Current Activity ==","");
	        super.onPause();
	    }
	 
	    @Override
	    protected void onRestart() {
	    	Log.v("--onRestart ---Current Activity ==","");
	        super.onRestart();
	    }
	 
	    @Override
	    protected void onDestroy() {
	    	Log.v("--onDestroy ---Current Activity ==","");
	        super.onDestroy();
	    }
	    
	    
	    /*
		 * 返回
		 */
		public void doBack(View view) {
			onBackPressed();
		}

}
