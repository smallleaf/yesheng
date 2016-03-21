package com.shop.marketapp.activity;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.i;
import com.baidu.mapapi.BMapManager;
import com.example.marketapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shop.marketapp.adapter.MerchantAdapter;
import com.shop.marketapp.base.BaseActivity;
import com.shop.marketapp.base.BaseApplication;
import com.shop.marketapp.bean.MerChantBean;
import com.shop.marketapp.bean.db.DataBaseManager;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.image.ImageUtils;
import com.shop.marketapp.server.MainMsgServer;
import com.shop.marketapp.widget.AbScrollView;
import com.shop.marketapp.widget.AdvertiseViewPager;
import com.shop.marketapp.widget.AdvertiseViewPager.TransitionEffect;
import com.shop.marketapp.widget.CustomInitProgressDialog;
import com.shop.marketapp.widget.OutlineContainer;
import com.shop.marketapp.widget.ScrollLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends BaseActivity implements OnClickListener {
	// ============== ����л� ===================
	private AdvertiseViewPager mViewPager = null;
	private LinearLayout mIndicator = null;
	private static final int MSG_CHANGE_PHOTO = 1;
	/** ͼƬ�Զ��л�ʱ�� */
	private static final int PHOTO_CHANGE_TIME = 3000;
	/**
	 * װViewPager��ImageView������
	 */
	private ImageView[] mImageViews;
	private List<String> mImageUrls = new ArrayList<String>();
	private String mImageUrl = null;
	/**
	 * װָ����ImageView����
	 */
	private ImageView[] mIndicators;

	/** ��λλ�� */
	private TextView addressTextView;

	/**
	 * �Ƽ��̼Ҳ���
	 */
	/** �̼���ҳ��. */
	private int merchantPageCount;
	/** ��ҳ��. */
	private int recommentPageCount;
	/** ��ǰҳ��. */
	private int PageCurrent;
	/** ��ѡ�е�. */
	private int gridID = -1;
	private GridView merchantGridView;
	private ScrollLayout merchantLayout;

	// GPS��λ
	/**
	 * Application���������
	 */
	protected BaseApplication mApplication;

	/**
	 * ��λSDK�ĺ�����
	 */
	private LocationClient mLocationClient;// ��λSDK�ĺ�����
	private BDLocationListener mLocationListener;// ���ã���ȡ��λ�������ȡPOI��Ϣ

	// �����ؼ�
	private GridView recommentGridView;
	private ScrollLayout recommentLayout;
	
	private AbScrollView abScrollView;
	/**���ض���*/
	private CustomInitProgressDialog customProgressDialog;
	/**��ʼ������*/
	
	/**
	 * �̼�����
	 */
	private ArrayList<MerChantBean> merChantBeans=new ArrayList<MerChantBean>();
	/**
	 * 
	 */
	private Handler initMainDataHandler=new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.v("��С", String.valueOf(merChantBeans.size()));
			customProgressDialog.dismiss();
			abScrollView.setVisibility(View.VISIBLE);
			initView();
			setMerchantGrid();
			setTdRecommentGrid();
		}
	};
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mApplication = (BaseApplication) this.getApplication();
		if (mApplication.mBMapManager == null) {
			mApplication.mBMapManager = new BMapManager(mApplication.mContext);
			mApplication.initEngineManager();
		}
		// ע�⣺��������setContentViewǰ��ʼ��BMapManager���󣬷���ᱨ��
		setContentView(R.layout.activity_main);
		((ImageView) findViewById(R.id.title_iv)).setVisibility(View.VISIBLE);
		abScrollView=(AbScrollView)findViewById(R.id.activity_abscrollview);
		customProgressDialog=CustomInitProgressDialog.createDialog(MainActivity.this);
		customProgressDialog.setMessage("���ڼ���...");
		customProgressDialog.show();
		initData();
		initLocation();
	}
	/** ��ʼ����λϵͳ */
	private void initLocation() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);

		LocationClientOption locationOption = new LocationClientOption();
		locationOption.setOpenGps(true);
		locationOption.setCoorType("bd09ll");
		locationOption.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		locationOption.setPriority(LocationClientOption.GpsFirst);
		locationOption.setAddrType("all");
		locationOption.setProdName("ͨ��GPS��λ");
		mLocationClient.setLocOption(locationOption);

		mLocationClient.start();

	}
	/**��ʼ������*/
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
		customProgressDialog.show();
		abScrollView.setVisibility(View.GONE);
		findViewById();
		DataBaseManager dataBaseManager=new DataBaseManager(MainActivity.this);
		ArrayList<MerChantBean> merChantBeans=new ArrayList<MerChantBean>();
		merChantBeans=dataBaseManager.findMerchant();
		for(int i=0;i<merChantBeans.size();i++){
			System.out.println(merChantBeans.get(i).getName());
		}
		if(merChantBeans.size()==0){
			MainMsgServer mainMsgServer=new MainMsgServer(MainActivity.this,initMainDataHandler,this.merChantBeans);
			new Thread(mainMsgServer).start();
		}
		else {
			this.merChantBeans=merChantBeans;
			customProgressDialog.dismiss();
			abScrollView.setVisibility(View.VISIBLE);
			
			initView();
			setMerchantGrid();
			setTdRecommentGrid();
		}
	}

	/**
	 * ��� �̼�GridView
	 */
	private void setMerchantGrid() {
		if (merChantBeans.size() % 6 == 0) {
			merchantPageCount = merChantBeans.size() / 6;
		} else {
			merchantPageCount = merChantBeans.size() / 6 + 1;
		}
		if (merchantGridView != null) {
			merchantLayout.removeAllViews();
		}
		for (int i = 0; i < merchantPageCount; i++) {
			merchantGridView = new GridView(MainActivity.this);
			merchantGridView.setAdapter(new MerchantAdapter(this,
					merChantBeans, i));
			merchantGridView.setNumColumns(3);
			merchantGridView.setHorizontalSpacing(8);
			merchantGridView.setVerticalSpacing(8);
			// ȥ�����ʱ�Ļ�ɫ����
			// merchantGridView.setSelector(R.drawable.bg_grid_item);
			merchantGridView.setOnItemClickListener(gridListener);
			merchantLayout.addView(merchantGridView);
		}
	}
	/**
	 * ��� �����Ƽ�GridView
	 */
	private void setTdRecommentGrid() {

		if (merChantBeans.size() % 6 == 0) {
			recommentPageCount = merChantBeans.size() / 6;
		} else {
			recommentPageCount = merChantBeans.size() / 6 + 1;
		}
		if (recommentGridView != null) {
			recommentLayout.removeAllViews();
		}
		for (int i = 0; i < recommentPageCount; i++) {
			recommentGridView = new GridView(MainActivity.this);
			recommentGridView.setAdapter(new MerchantAdapter(this,
					merChantBeans, i));
			recommentGridView.setNumColumns(3);
			recommentGridView.setHorizontalSpacing(8);
			recommentGridView.setVerticalSpacing(8);
			// ȥ�����ʱ�Ļ�ɫ����
			// merchantGridView.setSelector(R.drawable.bg_grid_item);
			// recommentGridView.setOnItemClickListener(gridListener);
			recommentLayout.addView(recommentGridView);
		}
	}

	private void findViewById() {
		// TODO Auto-generated method stub
		mViewPager = (AdvertiseViewPager) findViewById(R.id.main_viewpager_advertise);
		mIndicator = (LinearLayout) findViewById(R.id.main_index_product_images_indicator);

		recommentLayout = (ScrollLayout) findViewById(R.id.main_gridview_today_recomment);
		addressTextView = (TextView) findViewById(R.id.title_location);
		addressTextView.setVisibility(View.VISIBLE);
		addressTextView.setOnClickListener(this);
		/* ����ͼƬ�ı���4:3ʹ�����ÿ��item */
		double width = ImageUtils.screnWidthAndHeigthRace(this);
		recommentLayout.getLayoutParams().height = (int) ((width / 4) * 2) + 20;

		recommentLayout.setPageListener(new ScrollLayout.PageListener() {
			@Override
			public void page(int page) {
				// setCurPage(page);
			}
		});
		merchantLayout = (ScrollLayout) findViewById(R.id.main_gridview_shop);
		// ���������̼�ͼƬ�ĸ߶�ʹ���̶���
		merchantLayout.getLayoutParams().height = (int) ((width / 4) * 2) + 20;

		merchantLayout.setPageListener(new ScrollLayout.PageListener() {

			@Override
			public void page(int page) {
				// setCurPage(page);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		// ======= ��ʼ��ViewPager ========
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

	/**
	 * GridView�ļ����¼�
	 */
	public OnItemClickListener gridListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
				long arg3) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, DetailsMerchantActivity.class);
			Bundle bundle=new Bundle();
			System.out.println("λ��+"+postion);
			bundle.putSerializable("merchantBean", merChantBeans.get(postion));
			intent.putExtras(bundle);
			MainActivity.this.startActivity(intent);
		}
	};

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
	 * ��ViewPager��ҳ���״̬�����ı�ʱ����
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
	 * 0 x ����0ѡ0ds2r3 �е�tip�ı��� r2 3ZED2 * @param selectItemsIndex
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

	
	class MyLocationListener implements BDLocationListener {// BDLocationListener��ȡ��λ�������ȡPOI��Ϣ

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			addressTextView.setText(location.getAddrStr());
		}

		@Override
		public void onReceivePoi(BDLocation location) {
			// TODO Auto-generated method stub
		}

	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.title_location:
			Intent intent = new Intent();
			intent.setClass(this, DeliveryAddressActivity.class);
			Bundle bundle=new Bundle();
			bundle.putString("loaction", addressTextView.getText().toString());
			intent.putExtras(bundle);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	 @Override
	    protected void onResume() {
	    	Log.v("--onResume ---Current Activity ==","ff");
	        super.onResume();
	    }
}
