package com.wtu.university.slidingMenu.adapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.universityconnection.JobActivity;
import com.example.universityconnection.R;
import com.leslie.demo.AsyncImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wtu.university.common.Constants;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.PublicPersonInfoActivity;
import com.wtu.university.slidingMenu.bean.NewsEntity;
import com.wtu.university.slidingMenu.left.bean.UserInfoBean;
import com.wtu.university.slidingMenu.right.RegisterActivity;
import com.wtu.university.tools.DateTools;
import com.wtu.university.view.CustomProgressDialog;
import com.wtu.university.view.HeadListView;
import com.wtu.university.view.HeadListView.HeaderAdapter;

import android.R.raw;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class NewsAdapter extends BaseAdapter implements SectionIndexer, HeaderAdapter, OnScrollListener{
	private LayoutInflater inflater;
	private AsyncImageLoader imageLoader2;
	private List<NewsEntity> list=new ArrayList<NewsEntity>();
	private Context mContext;
	private Date d;
	private SimpleDateFormat sdf;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	//异步加载图片
	private ImageLoader imageLoader;
	DisplayImageOptions options;        // DisplayImageOptions是用于设置图片显示的类
	/** 弹出的更多选择框  */
	private PopupWindow popupWindow;
	public NewsAdapter(Context context){
		inflater = LayoutInflater.from(context);
		this.mContext=context;
		initPopWindow();
		imageLoader=ImageLoader.getInstance();
		
		 // 使用DisplayImageOptions.Builder()创建DisplayImageOptions  
        options = new DisplayImageOptions.Builder()  
            .showStubImage(R.drawable.app)          // 设置图片下载期间显示的图片  
            .showImageForEmptyUri(R.drawable.app)  // 设置图片Uri为空或是错误的时候显示的图片  
            .showImageOnFail(R.drawable.app)       // 设置图片加载或解码过程中发生错误显示的图片      
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中  
            .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中  
            .considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配置
				.considerExifParams(true)
				// 设置图片下载前的延迟
            .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 淡入
            .build();                                   // 创建配置过得DisplayImageOption对象  
		
		this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		initDateHead();
	}
	
	public void assignment(List<NewsEntity> list){
		this.list = list;
	}
	
	
	private List<Integer> mPositions;
	private List<String> mSections;
	
	private void initDateHead() {
		mSections = new ArrayList<String>();
		mPositions= new ArrayList<Integer>();
		for(int i = 0; i <list.size();i++){
			if(i == 0){
				mSections.add(list.get(i).getPulishTime());
				mPositions.add(i);
				continue;
			}
			if(i != list.size()){
				if(!(list.get(i).getPulishTime()==list.get(i - 1).getPulishTime())){
					mSections.add(list.get(i).getPulishTime());
					mPositions.add(i);
				}
			}
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public NewsEntity getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.news_list,
					null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.item_title);
			holder.unversity = (TextView) convertView.findViewById(R.id.item_source);
			holder.commentCounts = (TextView) convertView.findViewById(R.id.comment_count);
			holder.publicTime = (TextView) convertView.findViewById(R.id.publish_time);
			holder.pictureRight=(ImageView)convertView.findViewById(R.id.right_image);
			holder.picture1=(ImageView)convertView.findViewById(R.id.image1);
			holder.picture2=(ImageView)convertView.findViewById(R.id.image2);
			holder.picture3=(ImageView)convertView.findViewById(R.id.image3);
			holder.imageLayout=(LinearLayout)convertView.findViewById(R.id.image_layout);
			holder.popView=(ImageView)convertView.findViewById(R.id.popicon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//设置+按钮点击效果
		holder.popView.setOnClickListener(new popAction(position,holder.commentCounts));
		final NewsEntity newsEntity=list.get(position);
		holder.title.setText(newsEntity.getTitle());
		holder.unversity.setText(newsEntity.getColleage());
		holder.commentCounts.setText("赞 "+newsEntity.getCommentCounts());
		holder.publicTime.setText(newsEntity.getPulishTime());
		//多张图片
		if(newsEntity.getPublic_user_kind()==0)
		{//系统推送的图片，带链接的
		if(newsEntity.getRightPicture().equals("null")&&newsEntity.getPicture1().equals("null")){
			holder.imageLayout.setVisibility(View.GONE);
			holder.pictureRight.setVisibility(View.GONE);
		}
		else if (!newsEntity.getRightPicture().equals("null")&&newsEntity.getPicture1().equals("null")){
			String rightPicture=newsEntity.getRightPicture();
			holder.imageLayout.setVisibility(View.GONE);
			holder.pictureRight.setVisibility(View.VISIBLE);
			holder.pictureRight.setTag(rightPicture);
			imageLoader.displayImage(rightPicture, holder.pictureRight,options,animateFirstListener);
		}
		else if(newsEntity.getRightPicture().equals("null")&&!newsEntity.getPicture3().equals("null")){
			String picture1=newsEntity.getPicture1();
			String picture2=newsEntity.getPicture2();
			String picture3=newsEntity.getPicture3();
			//两个布局都得设置显示或者隐藏，如果不设置会出现图片错乱
			holder.pictureRight.setVisibility(View.GONE);
			holder.imageLayout.setVisibility(View.VISIBLE);
				imageLoader.displayImage(picture1, holder.picture1,options,animateFirstListener);
				imageLoader.displayImage(picture2, holder.picture2,options,animateFirstListener);
				imageLoader.displayImage(picture3, holder.picture3,options,animateFirstListener);
		}
		}
		else {
			
			if(newsEntity.getPicture1Bitmap()==null){
				holder.imageLayout.setVisibility(View.GONE);
				holder.pictureRight.setVisibility(View.GONE);
			}
			else if (newsEntity.getPicture1Bitmap()!=null&&newsEntity.getPicture3Bitmap()==null){
				holder.imageLayout.setVisibility(View.GONE);
				holder.pictureRight.setVisibility(View.VISIBLE);
				holder.pictureRight.setImageBitmap(newsEntity.getPicture1Bitmap());
			}
			else{
				//两个布局都得设置显示或者隐藏，如果不设置会出现图片错乱
				holder.pictureRight.setVisibility(View.GONE);
				holder.imageLayout.setVisibility(View.VISIBLE);
				holder.picture1.setImageBitmap(newsEntity.getPicture1Bitmap());
				holder.picture2.setImageBitmap(newsEntity.getPicture2Bitmap());
				holder.picture3.setImageBitmap(newsEntity.getPicture3Bitmap());
			}
		}
		if(newsEntity.getPublic_user_kind()==1){
		holder.unversity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(mContext, PublicPersonInfoActivity.class);
				intent.putExtra("userName", newsEntity.getColleage());
				mContext.startActivity(intent);
			}
		});
		}
		
		return convertView;
	}
	
	public final class ViewHolder {
		public TextView title;
		public TextView unversity;
		public TextView commentCounts;
		public TextView publicTime;
		public ImageView pictureRight;
		public ImageView picture2;
		public ImageView picture3;
		public ImageView picture1;
		public LinearLayout imageLayout;
		public ImageView popView;//点击+获得更多，点赞
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}
	//滑动监听
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (view instanceof HeadListView) {
			Log.d("first", "first:" + view.getFirstVisiblePosition());
//			if(isCityChannel){
//				if(view.getFirstVisiblePosition() == 0){
//					isfirst = true;
//				}else{
//					isfirst = false;
//				}
//				((HeadListView) view).configureHeaderView(firstVisibleItem - 1);
//			}else{
				((HeadListView) view).configureHeaderView(firstVisibleItem);
//			}
		}
	}
	
	@Override
	public int getHeaderState(int position) {
		// TODO Auto-generated method stub
		int realPosition = position;
//		if(isCityChannel){
//			if(isfirst){
//				return HEADER_GONE;
//			}
//		}
		if (realPosition < 0 || position >= getCount()) {
			return HEADER_GONE;
		}
		int section = getSectionForPosition(realPosition);
		int nextSectionPosition = getPositionForSection(section + 1);
		if (nextSectionPosition != -1
				&& realPosition == nextSectionPosition - 1) {
			return HEADER_PUSHED_UP;
		}
		return HEADER_VISIBLE;
	}

	@Override
	public void configureHeader(View header, int position, int alpha) {
		int realPosition = position;
		int section = getSectionForPosition(realPosition);
		String title = (String) getSections()[section];
//		((TextView) header.findViewById(R.id.section_text)).setText(title);
//		((TextView) header.findViewById(R.id.section_day)).setText("今天");
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return mSections.toArray();
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		if (sectionIndex < 0 || sectionIndex >= mPositions.size()) {
			return -1;
		}
		return mPositions.get(sectionIndex);
	}

	@Override
	public int getSectionForPosition(int position) {
		if (position < 0 || position >= getCount()) {
			return -1;
		}
		int index = Arrays.binarySearch(mPositions.toArray(), position);
		return index >= 0 ? index : -index - 2;
	}
	
	
	 /** 
     * 图片加载第一次显示监听器 
     * @author Administrator 
     * 
     */  
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {  
          
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());  
  
        @Override  
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {  
            if (loadedImage != null) {  
                ImageView imageView = (ImageView) view;  
                if(imageUri.equals(imageView.getTag())){
                // 是否第一次显示  
                boolean firstDisplay = !displayedImages.contains(imageUri);  
                if (firstDisplay) {  
                    // 图片淡入效果  
                    FadeInBitmapDisplayer.animate(imageView, 500);  
                    displayedImages.add(imageUri);  
                }  
                }
            }  
        }  
    }  
    
    /** popWindow 关闭按钮 */
	private ImageView btn_pop_close;
	/**popWindow点赞*/
	private LinearLayout btn_pop_dianzan;
	/**
	 * 初始化弹出的pop
	 * */
	private void initPopWindow() {
		View popView = inflater.inflate(R.layout.list_item_pop, null);
		popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0));
		//设置popwindow出现和消失动画
		popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
		btn_pop_close = (ImageView) popView.findViewById(R.id.btn_pop_close);
		btn_pop_dianzan=(LinearLayout)popView.findViewById(R.id.ll_pop_speech);
	}
    /** 
	 * 显示popWindow
	 * */
	public void showPop(View parent, int x, int y,final int postion,final TextView view) {
		//设置popwindow显示位置
		popupWindow.showAtLocation(parent, 0, x, y);
		//获取popwindow焦点
		popupWindow.setFocusable(true);
		//设置popwindow如果点击外面区域，便关闭。
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		if (popupWindow.isShowing()) {
			
		}
		btn_pop_close.setOnClickListener(new OnClickListener() {
			public void onClick(View paramView) {
				popupWindow.dismiss();
			}
		});
		btn_pop_dianzan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				view.setText("赞 "+(list.get(postion).getCommentCounts()+1));
				new PraiseDianAscaTask().execute(String.valueOf(list.get(postion).getId()),String.valueOf(list.get(postion).getCommentCounts()+1));
				popupWindow.dismiss();
			}
		});
	}
	/** 
	 * 每个ITEM中more按钮对应的点击动作
	 * */
	public class popAction implements OnClickListener{
		int position;
		TextView view;
		public popAction(int position,TextView view){
			this.position = position;
			this.view=view;
		}
		@Override
		public void onClick(View v) {
			int[] arrayOfInt = new int[2];
			//获取点击按钮的坐标
			v.getLocationOnScreen(arrayOfInt);
	        int x = arrayOfInt[0];
	        int y = arrayOfInt[1];
	        showPop(v, x , y, position,view);
		}
	}
	public class PraiseDianAscaTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			map.put("id", arg0[0]);
			map.put("commentCounts", arg0[1]);
			String url = HttpUtil.BASE_URL + "praiseDianAction.action";
			try {
				HttpUtil.postRequest(url, map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
}
