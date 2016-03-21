package com.wtu.university.slidingMenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.R.raw;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.universityconnection.R;
import com.wtu.university.MainInterfaceActivity;
import com.wtu.university.app.AppApplication;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.adapter.NewsAdapter;
import com.wtu.university.slidingMenu.bean.ChannelManage;
import com.wtu.university.slidingMenu.bean.DetaisUserNewsBean;
import com.wtu.university.slidingMenu.bean.NewsEntity;
import com.wtu.university.slidingMenu.right.information.UniversityActivity;
import com.wtu.university.slidingMenu.server.GetHotNewsServer;
import com.wtu.university.slidingMenu.university.UniverisityChooseActivity;
import com.wtu.university.slidingMenu.university.UniversiyGridViewAdapter;
import com.wtu.university.tools.DateTools;
import com.wtu.university.view.RefreshSupportListView;
import com.wtu.university.view.RefreshSupportListView.OnLoadListener;

public class NewsFragment extends Fragment	 {
	private final static String TAG = "NewsFragment";
	Activity activity;
//	HeadListView mListView;
	RefreshSupportListView mListView;
	NewsAdapter mAdapter;
	String text;
	int channel_id;
	ImageView detail_loading;
	private SharedPreferences sharedPreferences=AppApplication.getApp().getSharedPreferences(Constants.SP_NAME, AppApplication.getApp().MODE_PRIVATE);
	Editor editor=sharedPreferences.edit();
	List<NewsEntity> newsEntitiesTemp;
	public final static int SET_NEWSLIST = 0;
	//Toast提示框
	private RelativeLayout notify_view;
	private TextView notify_view_text;
	//显示日期
	private TextView date;
	private View showDateView;
	
	private boolean flag=false;
	//加载显示最后一个
	int lastItem;
	//加载几个数据
	private int newsNum=5;
	/**
	 * 加载更多时的表示，防止重新加载
	 */
	 private boolean load=false;
	//加载时的动画
	
	private NewsEntity newsEntity=new NewsEntity();
	private ArrayList<NewsEntity> newsEntities=new ArrayList<NewsEntity>();
	private boolean closeLoad=false;
	
	
	//高校模块7
	private GridView gridView;
	private UniversiyGridViewAdapter universiyGridViewAdapter;
	private TextView university;
	private ImageView chooseUniversity;
	
	private Handler startThreadHander = new Handler(){
		public void handleMessage(Message msg) {
			super.sendMessage(msg);
		}
	};
	
	private Handler getHotNews=new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			
				
				mListView.onLoadComplete();
				mListView.onRefreshComplete();
				load=false;
				editor.putBoolean(Constants.conncectServer, true);
				editor.commit();
				List<NewsEntity> newsEntitiesTemp=new ArrayList<NewsEntity>();
				for(int i=0;i<newsEntities.size();i++){
					newsEntitiesTemp.add(newsEntities.get(i));
				}
				if(mAdapter==null)
					mAdapter = new NewsAdapter(activity);
				mAdapter.assignment(newsEntitiesTemp);
			
			
			//第一个频道无法在newfragment可见时显示
			if(channel_id==1){
				handler.obtainMessage(SET_NEWSLIST).sendToTarget();
			}
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		channel_id = args != null ? args.getInt("id", 0) : 0;
		super.onCreate(savedInstanceState);
		//如果是高校板块
			initData();
		
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		this.activity = activity;
		super.onAttach(activity);
	}
	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			//fragment可见时加载数据
			System.out.println("fragment可见时加载数据"+channel_id);
			if(newsEntities !=null&&newsEntities.size()!=0){
				handler.obtainMessage(SET_NEWSLIST).sendToTarget();
			}else{
				GetHotNewsServer getHotNewsServer=new GetHotNewsServer(newsEntities,getHotNews,channel_id,newsNum);
				Thread thread=new Thread(getHotNewsServer);
				thread.start();
			}
		}else{
			//fragment不可见时不执行操作
			
				newsEntities.clear();
	    		GetHotNewsServer getHotNewsServer=new GetHotNewsServer(newsEntities,getHotNews,channel_id,newsNum);
				Thread thread=new Thread(getHotNewsServer);
				thread.start();
			
		}
		super.setUserVisibleHint(isVisibleToUser);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view ;
		if(channel_id==3){
			showDateView=LayoutInflater.from(getActivity()).inflate(R.layout.university_newsfragment, null);
			gridView=(GridView)showDateView.findViewById(R.id.university_newsfragment_gridview);
			university=(TextView)showDateView.findViewById(R.id.university_newsfragment_university_name);
			if(!sharedPreferences.getString(Constants.SP_UNIVERISTY_CHOOSE, "").equals("")){
				university.setText(sharedPreferences.getString(Constants.SP_UNIVERISTY_CHOOSE, ""));
			}
			chooseUniversity=(ImageView)showDateView.findViewById(R.id.university_newsfragment_setunivetsity);
			chooseUniversity.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					
					intent.setClass(activity, UniverisityChooseActivity.class);
					activity.startActivityForResult(intent, MainInterfaceActivity.CHANNELREQUEST);
					activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				}
			});
			universiyGridViewAdapter=new UniversiyGridViewAdapter(activity);
			gridView.setAdapter(universiyGridViewAdapter);
		}
		
		else{
			showDateView=LayoutInflater.from(getActivity()).inflate(R.layout.list_item_section, null);
			date=(TextView)showDateView.findViewById(R.id.section_text);
			date.setText(DateTools.getToday((new Date()).getTime()));
			((TextView)showDateView.findViewById(R.id.section_day)).setText("今天");
		}
		view = LayoutInflater.from(getActivity()).inflate(R.layout.news_fragment, null);
		mListView = (RefreshSupportListView) view.findViewById(R.id.mListView);
		TextView item_textview = (TextView)view.findViewById(R.id.item_textview);
		detail_loading = (ImageView)view.findViewById(R.id.detail_loading);
		
		mListView.addHeaderView(showDateView);
		//Toast提示框
		notify_view = (RelativeLayout)view.findViewById(R.id.notify_view);
		notify_view_text = (TextView)view.findViewById(R.id.notify_view_text);
		item_textview.setText(text);
		mListView.setDivider(this.getResources().getDrawable(R.drawable.cell_seperator));
		
		mListView.setonRefreshListener(new RefreshSupportListView.OnRefreshListener(){
        	public void onRefresh() {
        		if(sharedPreferences.getBoolean(Constants.conncectServer, false)){
	        		newsEntities.clear();
	        		GetHotNewsServer getHotNewsServer=new GetHotNewsServer(newsEntities,getHotNews,channel_id,newsNum);
					Thread thread=new Thread(getHotNewsServer);
					thread.start();
        		}
        		else {
        			mListView.onRefreshComplete();
				}
        	}
        });
		
		mListView.setonLoadListener(new OnLoadListener() {
			public void onLoad() {
				if(!load&&sharedPreferences.getBoolean(Constants.conncectServer, false)){//没有在加载
					load=true;
					ProgressBar moreProgressBar = (ProgressBar)mListView.findViewById(R.id.pull_to_refresh_progress);
					moreProgressBar.setVisibility(View.VISIBLE);
					TextView loadMoreView = (TextView) mListView.findViewById(R.id.load_more);
					loadMoreView.setText("正在加载");
					lastItem=newsEntities.size();
					newsNum=newsNum+5;
					newsEntities.clear();
					GetHotNewsServer getHotNewsServer=new GetHotNewsServer(newsEntities,getHotNews,channel_id,newsNum);
					Thread thread=new Thread(getHotNewsServer);
					thread.start();
				}
				else {
					mListView.onLoadComplete();
				}
				
			}
		});
		return view;
	}
	
	private void initData() {
		
		
		GetHotNewsServer getHotNewsServer=new GetHotNewsServer(newsEntities,getHotNews,channel_id,newsNum);
		Thread thread=new Thread(getHotNewsServer);
		thread.start();
	}
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SET_NEWSLIST:
				detail_loading.setVisibility(View.GONE);
				if(mAdapter==null)
				{
					
				}
				post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mListView.setSelection(lastItem-1);
					}
				});
				
				mListView.setAdapter(mAdapter);
				mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					
					//如果频道为0即热点，此时为系统发布，系统发布是用webview来显示的
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if(newsEntities.get(position-2).getPublic_user_kind()!=0){
						DetaisUserNewsBean detaisUserNewsBean=new DetaisUserNewsBean();
						detaisUserNewsBean.setId(newsEntities.get(position-2).getId());
						detaisUserNewsBean.setName(newsEntities.get(position-2).getColleage());
						detaisUserNewsBean.setPicture(newsEntities.get(position-2).getUserPicture());
						detaisUserNewsBean.setPublic_time(newsEntities.get(position-2).getPulishTime());
						detaisUserNewsBean.setContent(newsEntities.get(position-2).getContent());
						if(newsEntities.get(position-2).getPicture1Bitmap()!=null){
							detaisUserNewsBean.setPicture1(Utils.bitmapToString(newsEntities.get(position-2).getPicture1Bitmap()));
						}
						if(newsEntities.get(position-2).getPicture2Bitmap()!=null){
							detaisUserNewsBean.setPicture2(Utils.bitmapToString(newsEntities.get(position-2).getPicture2Bitmap()));
						}
						if(newsEntities.get(position-2).getPicture3Bitmap()!=null){
							detaisUserNewsBean.setPicture3(Utils.bitmapToString(newsEntities.get(position-2).getPicture3Bitmap()));
						}
						Intent intent=new Intent();
						intent.setClass(activity, DetaisNewsActivity.class);
						Bundle bundle=new Bundle();
						bundle.putSerializable("publicUser", detaisUserNewsBean);
						intent.putExtras(bundle);
						activity.startActivity(intent);
						}
						else
						{
							DetaisUserNewsBean detaisUserNewsBean=new DetaisUserNewsBean();
							detaisUserNewsBean.setId(newsEntities.get(position-2).getId());
							detaisUserNewsBean.setName(newsEntities.get(position-2).getColleage());
							detaisUserNewsBean.setPicture(newsEntities.get(position-2).getUserPicture());
							detaisUserNewsBean.setPublic_time(newsEntities.get(position-2).getPulishTime());
							detaisUserNewsBean.setContent(newsEntities.get(position-2).getContent());
							detaisUserNewsBean.setTitle(newsEntities.get(position-2).getTitle());
							if(newsEntities.get(position-2).getRightPicture()!=null){
								detaisUserNewsBean.setPicture(newsEntities.get(position-2).getRightPicture());
							}
							if(newsEntities.get(position-2).getPicture1()!=null){
								detaisUserNewsBean.setPicture1(newsEntities.get(position-2).getPicture1());
							}
							if(newsEntities.get(position-2).getPicture2()!=null){
								detaisUserNewsBean.setPicture2(newsEntities.get(position-2).getPicture2());
							}
							if(newsEntities.get(position-2).getPicture3()!=null){
								detaisUserNewsBean.setPicture3(newsEntities.get(position-2).getPicture3());
							}
							Intent intent=new Intent();
							intent.setClass(activity, DetailsWebActivity.class);
							Bundle bundle=new Bundle();
							bundle.putSerializable("publicUser", detaisUserNewsBean);
							intent.putExtras(bundle);
							activity.startActivity(intent);
						}
					}
				});
				if(channel_id == 1){
//					initNotify();
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	
//	/* 初始化选择城市的header*/
//	public void initCityChannel() {
//		View headview = LayoutInflater.from(activity).inflate(R.layout.city_category_list_tip, null);
//		TextView chose_city_tip = (TextView) headview.findViewById(R.id.chose_city_tip);
//		chose_city_tip.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(activity, CityListActivity.class);
//				startActivity(intent);
//			}
//		});
//		mListView.addHeaderView(headview);
//	}
	
//	/* 初始化通知栏目*/
//	private void initNotify() {
//		new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				notify_view_text.setText(String.format(getString(R.string.ss_pattern_update), 10));
//				notify_view.setVisibility(View.VISIBLE);
//				new Handler().postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						notify_view.setVisibility(View.GONE);
//					}
//				}, 2000);
//			}
//		}, 1000);
//	}
	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d("onDestroyView", "channel_id = " + channel_id);
		mAdapter = null;
	}
	/* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "channel_id = " + channel_id);
	}
		
}
