package com.wtu.university;


import java.lang.reflect.Field;
import java.util.ArrayList;

import com.example.universityconnection.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.wtu.university.common.Constants;
import com.wtu.university.slidingMenu.FixedSpeedScroller;
import com.wtu.university.slidingMenu.NewsFragment;
import com.wtu.university.slidingMenu.SlidingMenuContent;
import com.wtu.university.slidingMenu.adapter.MyPagerAdapter;
import com.wtu.university.slidingMenu.adapter.NewsFragmentPagerAdapter;
import com.wtu.university.slidingMenu.bean.ChannelItem;
import com.wtu.university.slidingMenu.bean.ChannelManage;
import com.wtu.university.view.ColumnHorizontalScrollView;
import com.wtu.university.view.PageTransformer;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainInterfaceActivity extends FragmentActivity {
	LocalActivityManager manager = null;
	/** 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;
	LinearLayout mRadioGroup_content;
	LinearLayout ll_more_columns;
	RelativeLayout rl_column;
	private ImageView button_more_columns;
	private SharedPreferences sharedPreferences;
	Editor editor;
	/** 用户选择的新闻分类列表*/
	private ArrayList<ChannelItem> userChannelList=new ArrayList<ChannelItem>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private ViewPager mViewPager;
	protected SlidingMenu side_drawer;
	/** 左阴影部分*/
	public ImageView shade_left;
	/** 右阴影部分 */
	public ImageView shade_right;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	
	/** head 头部 的左侧菜单 按钮*/
	private ImageView top_head;
	/** head 头部 的右侧菜单 按钮*/
	private ImageView top_more;
	
	/** 当前选中的栏目*/
	private int columnSelectIndex = 0;
	
	/** 请求CODE */
	public final static int CHANNELREQUEST = 1;
	/** 注销请求CODE */
	public final static int LOGOUTCHANNELREQUEST = 2;
	/** 高校板块选择高校返回高校的界面  */
	public final static int UNIVERISYCHOOSE = 3;
	/** 调整返回的RESULTCODE */
	public final static int CHANNELRESULT = 10;
	/** 调整返回的RESULTCODE */
	public final static int LOGOUTRESULT = 11;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_interface);
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		manager = new LocalActivityManager(this , true);
		manager.dispatchCreate(savedInstanceState);
		DisplayMetrics dm = new DisplayMetrics();
		MainInterfaceActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
		
		initView();
		initSlidingMenu();
	}
	//获得侧滑默认界面
	protected void initSlidingMenu() {
		side_drawer = new SlidingMenuContent(this).initSlidingMenu();
		if(sharedPreferences.getBoolean(Constants.SP_LOGOUT, false)){
			editor=sharedPreferences.edit();
			editor.putBoolean(Constants.SP_LOGOUT, false);
			editor.commit();
			side_drawer.showSecondaryMenu();
		}
		if(sharedPreferences.getBoolean(Constants.SP_REGISTER, false)){
			editor=sharedPreferences.edit();
			editor.putBoolean(Constants.SP_REGISTER, false);
			editor.commit();
			side_drawer.showSecondaryMenu();
		}
		if(sharedPreferences.getBoolean(Constants.SP_BACK, false)){
			editor=sharedPreferences.edit();
			editor.putBoolean(Constants.SP_BACK, false);
			editor.commit();
			side_drawer.showSecondaryMenu();
		}
	}
	private void initView() {
		// TODO Auto-generated method stub
		mColumnHorizontalScrollView =  (ColumnHorizontalScrollView)findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);
		shade_left = (ImageView) findViewById(R.id.shade_left);
		shade_right = (ImageView) findViewById(R.id.shade_right);
		ll_more_columns = (LinearLayout) findViewById(R.id.ll_more_columns);
		rl_column = (RelativeLayout) findViewById(R.id.rl_column);
		button_more_columns = (ImageView) findViewById(R.id.button_more_columns);
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		top_head = (ImageView) findViewById(R.id.top_head);
		top_more = (ImageView) findViewById(R.id.top_more);
		
		
		
		
		button_more_columns.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent_channel = new  Intent(getApplicationContext(), ChannelActivity.class);
				startActivityForResult(intent_channel, CHANNELREQUEST);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
		
		top_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(side_drawer.isSecondaryMenuShowing()){
					side_drawer.showContent();
				}else{
					side_drawer.showSecondaryMenu();
				}
			}
		});
		top_head.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(side_drawer.isMenuShowing()){
						side_drawer.showContent();
					}else{
						side_drawer.showMenu();
					}
				}
		});

		setChangelView();
	}
	
	/** 
	 *  当栏目项发生变化时候调用
	 * */
	private void setChangelView() {
		// TODO Auto-generated method stub
		initColumnData();
		initTabColumn();
		initFragment();
	}
	
	
	@SuppressWarnings("deprecation")
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
	/** 
	 *  初始化Fragment
	 * */
	private void initFragment() {
		fragments.clear();//清空
		int count =  userChannelList.size();
		for(int i = 0; i< count;i++){
			Bundle data = new Bundle();
    		data.putString("text", userChannelList.get(i).getName());
    		data.putInt("id", userChannelList.get(i).getId());
			NewsFragment newfragment = new NewsFragment();
			newfragment.setArguments(data);
			fragments.add(newfragment);
		}
		//控制速度
//		Field field;
//		try {
//			field = MainInterfaceActivity.class.getDeclaredField("mScroller");
//			field.setAccessible(true);
//			FixedSpeedScroller scroller=new FixedSpeedScroller(mViewPager.getContext(),new AccelerateInterpolator());
//			field.set(mViewPager, scroller);
//			scroller.setmDuration(4000);
//		} catch (Exception  e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getSupportFragmentManager(), fragments);
		mViewPager.setOffscreenPageLimit(1);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.setPageTransformer(true, new PageTransformer());
		mViewPager.setOnPageChangeListener(pageListener);
	}
	/** 
	 *  ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener= new OnPageChangeListener(){

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(position);
			selectTab(position);
		}
	};
	/** 获取Column栏目 数据*/
	private void initColumnData() {
		userChannelList.clear();
		for(int i=0;i<ChannelManage.defaultUserChannels.size();i++)
		{
			userChannelList.add(ChannelManage.defaultUserChannels.get(i));
			Log.v("test", ChannelManage.defaultUserChannels.get(i).getName());
		}
	}

	/** 
	 *  初始化Column栏目项
	 * */
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		int count =  userChannelList.size();
		mColumnHorizontalScrollView.setParam(this, mScreenWidth, mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
		for(int i = 0; i< count; i++){
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth , LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
//			TextView localTextView = (TextView) mInflater.inflate(R.layout.column_radio_item, null);
			TextView columnTextView = new TextView(this);
			columnTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
//			localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
			columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
			columnTextView.setGravity(Gravity.CENTER);
			columnTextView.setPadding(5, 5, 5, 5);
			columnTextView.setId(i);
			columnTextView.setText(userChannelList.get(i).getName());
			columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
			if(columnSelectIndex == i){
				columnTextView.setSelected(true);
			}
			columnTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
			          for(int i = 0;i < mRadioGroup_content.getChildCount();i++){
				          View localView = mRadioGroup_content.getChildAt(i);
				          if (localView != v)
				        	  localView.setSelected(false);
				          else{
				        	  localView.setSelected(true);
				        	  mViewPager.setCurrentItem(i);
				          }
			          }
			          Toast.makeText(getApplicationContext(), userChannelList.get(v.getId()).getName(), Toast.LENGTH_SHORT).show();
				}
			});
			mRadioGroup_content.addView(columnTextView, i ,params);
		}
	}
	/** 
	 *  选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
			View checkView = mRadioGroup_content.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
		//判断是否选中
		for (int j = 0; j <  mRadioGroup_content.getChildCount(); j++) {
			View checkView = mRadioGroup_content.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView.setSelected(ischeck);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case CHANNELREQUEST:
			if(resultCode == CHANNELRESULT){
				
				setChangelView();
			}
			else if(resultCode == UNIVERISYCHOOSE){
				Log.v("返回", "反悔哦");
				setChangelView();
				mViewPager.setCurrentItem(2);
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private long mExitTime;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(side_drawer.isMenuShowing() ||side_drawer.isSecondaryMenuShowing()){
				side_drawer.showContent();
			}else {
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(this, "在按一次退出",
							Toast.LENGTH_SHORT).show();
					mExitTime = System.currentTimeMillis();
				} else {
					finish();
				}
			}
			return true;
		}
		//拦截MENU按钮点击事件，让他无任何操作
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
