package com.example.graduate.activity.common;

import java.util.ArrayList;
import java.util.List;

import com.example.graduate.common.Common;
import com.example.graduate.student.activity.InstitutionActivity;
import com.example.graduate.student.activity.StudentInfoActivity;
import com.example.graduate.student.activity.SystemActivity;
import com.example.graduate.student.adapter.FuctionAdapter;
import com.example.graduate.student.bean.Institution;
import com.example.graduate.student.bean.Student;
import com.example.graduate.teacher.activity.TeacherCourceActivity;
import com.example.graduate.view.AdvertiseViewPager;
import com.example.graduate.view.AdvertiseViewPager.TransitionEffect;
import com.example.graduate.view.OutlineContainer;
import com.example.graduateproject.R;
import com.example.graduateproject.R.drawable;
import com.example.graduateproject.R.id;
import com.example.graduateproject.R.layout;
import com.nostra13.universalimageloader.core.ImageLoader;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {

	// ============== 广告切换 ===================
		private AdvertiseViewPager mViewPager = null;
		private LinearLayout mIndicator = null;
		private static final int MSG_CHANGE_PHOTO = 1;
		/** 图片自动切换时间 */
		private static final int PHOTO_CHANGE_TIME = 3000;
		/**
		 * 装ViewPager中ImageView的数组
		 */
		private ImageView[] mImageViews;
		private List<String> mImageUrls = new ArrayList<String>();
		private String mImageUrl = null;
		/**
		 * 装指引的ImageView数组
		 */
		private ImageView[] mIndicators;
	
	
		private Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case MSG_CHANGE_PHOTO:
					int index = mViewPager.getCurrentItem();
					if (index == mImageUrls.size() - 1) {
						index = -1;
					}
					mViewPager.setCurrentItem(index + 1);
					mHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO,
							PHOTO_CHANGE_TIME);
				}
			}

		};
		
		
	/**
	 * 功能列表
	 */
	private GridView functionGridView;
	private FuctionAdapter fuctionAdapter;
	private SharedPreferences sharedPreferences;
	private int role;
	private Student student;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		sharedPreferences=getSharedPreferences(Common.SP_NAME, MODE_PRIVATE);
		role=sharedPreferences.getInt(Common.USER_ROLE, 1);
		
		if(role==1)
		{
		Bundle bundle=this.getIntent().getExtras();
		student=(Student) bundle.getSerializable("student");
		Editor editor=sharedPreferences.edit();
		//保存学生第一二志愿信息
		if(student.getFirst_volunteer()==0)
		{
			editor.putInt(Common.FIRST_VOLUNTEER, 0);
		}
		else {
			editor.putInt(Common.FIRST_VOLUNTEER, student.getFirst_volunteer());
		}
		if(student.getSecond_volunteer()==0)
		{
			editor.putInt(Common.SECOND_VOLUNTEER, 0);
		}
		else {
			editor.putInt(Common.SECOND_VOLUNTEER, student.getSecond_volunteer());
		}
		editor.commit();
		}
		
		initData();
		findViewById();
	}
	private void findViewById() {
		// TODO Auto-generated method stub
		mViewPager=(AdvertiseViewPager)findViewById(R.id.main_viewpager_advertise);
		mIndicator = (LinearLayout) findViewById(R.id.main_index_product_images_indicator);
		functionGridView=(GridView)findViewById(R.id.main_activity_gv_function);
		
		fuctionAdapter=new FuctionAdapter(role, MainActivity.this);
		functionGridView.setAdapter(fuctionAdapter);
		
		
		functionGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position==0&&role==1)
				{
					Intent intent=new Intent();
					intent.setClass(MainActivity.this, InstitutionActivity.class);
					startActivity(intent);
				}
				//老师课题信息
				if(position==0&&role==2)
				{
					Intent intent=new Intent();
					intent.setClass(MainActivity.this, TeacherCourceActivity.class);
					startActivity(intent);
				}
				
				
				if(position==1&&role==1)
				{
					Intent intent=new Intent();
					intent.setClass(MainActivity.this, StudentInfoActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("studentId", student.getStudentId());
					intent.putExtras(bundle);
					startActivity(intent);
				}
				if(position==2)
				{
					Intent intent=new Intent();
					intent.setClass(MainActivity.this, SystemActivity.class);
					startActivity(intent);
					ScreenManager.getScreenManager().pushActivity(MainActivity.this);
				}
			}
		});
		
		initView();

	}
	/**初始化数据*/
	private void initData() {
		// TODO Auto-generated method stub
		mImageUrl = "drawable://" + R.drawable.image01;
		mImageUrls.add(mImageUrl);

		mImageUrl = "drawable://" + R.drawable.image02;
		mImageUrls.add(mImageUrl);

		mImageUrl = "drawable://" + R.drawable.image03;
		mImageUrls.add(mImageUrl);

		mImageUrl = "drawable://" + R.drawable.image04;
		mImageUrls.add(mImageUrl);

		mImageUrl = "drawable://" + R.drawable.image05;
		mImageUrls.add(mImageUrl);

		mImageUrl = "drawable://" + R.drawable.image06;
		mImageUrls.add(mImageUrl);

		mImageUrl = "drawable://" + R.drawable.image07;
		mImageUrls.add(mImageUrl);

		mImageUrl = "drawable://" + R.drawable.image08;
		mImageUrls.add(mImageUrl);
	}
	private void initView() {
		// TODO Auto-generated method stub
		// ======= 初始化ViewPager ========
		mIndicators = new ImageView[mImageUrls.size()];
		if (mImageUrls.size() <= 1) {
			mIndicator.setVisibility(View.GONE);
		}

		for (int i = 0; i < mIndicators.length; i++) {
			ImageView imageView = new ImageView(this);
			LayoutParams params = new LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 1.0f);
			if (i != 0) {
				params.leftMargin = 5;
			}
			imageView.setLayoutParams(params);
			mIndicators[i] = imageView;
			if (i == 0) {
				mIndicators[i]
						.setBackgroundResource(R.drawable.android_activities_cur);
			} else {
				mIndicators[i]
						.setBackgroundResource(R.drawable.android_activities_bg);
			}

			mIndicator.addView(imageView);
		}

		mImageViews = new ImageView[mImageUrls.size()];

		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			mImageViews[i] = imageView;
		}
		mViewPager.setTransitionEffect(TransitionEffect.CubeOut);
		mViewPager.setCurrentItem(0);
		mHandler.sendEmptyMessageDelayed(MSG_CHANGE_PHOTO, PHOTO_CHANGE_TIME);

		mViewPager.setAdapter(new MyAdapter());
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (mImageUrls.size() == 0 || mImageUrls.size() == 1)
					return true;
				else
					return false;
			}
		});
		
		
	}
	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			if (view instanceof OutlineContainer) {
				return ((OutlineContainer) view).getChildAt(0) == obj;
			} else {
				return view == obj;
			}
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mViewPager
					.findViewFromObject(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			ImageLoader.getInstance().displayImage(mImageUrls.get(position),
					mImageViews[position]);
			((ViewPager) container).addView(mImageViews[position], 0);
			mViewPager.setObjectForPosition(mImageViews[position], position);
			return mImageViews[position];
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		/**
		 * This method will be invoked when a new page becomes selected. positio
		 * n: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			setImageBackground(position);
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 
	 * 
	 * 0 x 设置0选0ds2r3 中的tip的背景 r2 3ZED2 * @param selectItemsIndex
	 */
	private void setImageBackground(int selectItemsIndex) {
		for (int i = 0; i < mIndicators.length; i++) {
			if (i == selectItemsIndex) {
				mIndicators[i]
						.setBackgroundResource(R.drawable.android_activities_cur);
			} else {
				mIndicators[i]
						.setBackgroundResource(R.drawable.android_activities_bg);
			}
		}
	}
}
