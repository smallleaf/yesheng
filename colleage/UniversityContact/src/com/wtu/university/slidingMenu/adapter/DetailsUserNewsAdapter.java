package com.wtu.university.slidingMenu.adapter;

import java.util.ArrayList;

import com.example.universityconnection.R;
import com.wtu.university.common.Constants;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.PublicPersonInfoActivity;
import com.wtu.university.slidingMenu.ReplyActivity;
import com.wtu.university.slidingMenu.adapter.NewsAdapter.PraiseDianAscaTask;
import com.wtu.university.slidingMenu.adapter.NewsAdapter.popAction;
import com.wtu.university.slidingMenu.bean.DetaisUserNewsBean;
import com.wtu.university.tools.ImageUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailsUserNewsAdapter extends BaseAdapter implements OnClickListener{
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<DetaisUserNewsBean> detaisUserList;
	private SharedPreferences sharedPreferences;
	private boolean userNews=true;
	private String webUser;
	
	/** 弹出的更多选择框  */
	private PopupWindow popupWindow;
	public DetailsUserNewsAdapter(Context mContext,ArrayList<DetaisUserNewsBean> detaisUserList){
		inflater = LayoutInflater.from(mContext);
		sharedPreferences=mContext.getSharedPreferences(Constants.SP_NAME, mContext.MODE_PRIVATE);
		this.mContext=mContext;
		this.detaisUserList=detaisUserList;
		initPopWindow();
	}
	public DetailsUserNewsAdapter(Context mContext,ArrayList<DetaisUserNewsBean> detaisUserList,String webUser){
		inflater = LayoutInflater.from(mContext);
		sharedPreferences=mContext.getSharedPreferences(Constants.SP_NAME, mContext.MODE_PRIVATE);
		this.mContext=mContext;
		this.detaisUserList=detaisUserList;
		this.userNews=false;
		this.webUser=webUser;
		initPopWindow();
	}
	
	public void assignment(ArrayList<DetaisUserNewsBean> detaisUserList){
		this.detaisUserList=detaisUserList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return detaisUserList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return detaisUserList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		
			
		return 0;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.details_user_news_list,
					null);
			holder = new ViewHolder();
			holder.pictureImageView=(ImageView)convertView.findViewById(R.id.details_user_news_list_picture);
			holder.userNameTextView=(TextView)convertView.findViewById(R.id.details_user_news_list_name);
			holder.publicOrCommentsDate=(TextView)convertView.findViewById(R.id.details_user_news_list_time);
			holder.content=(TextView)convertView.findViewById(R.id.details_user_news_list_content);
			holder.commentListView=(ListView)convertView.findViewById(R.id.details_user_news_list_comments);
			holder.pictuer1=(ImageView)convertView.findViewById(R.id.details_user_news_list_picture1);
			holder.pictuer2=(ImageView)convertView.findViewById(R.id.details_user_news_list_picture2);
			holder.pictuer3=(ImageView)convertView.findViewById(R.id.details_user_news_list_picture3);
			holder.parentLayout=(RelativeLayout)convertView.findViewById(R.id.details_user_parent);
			holder.reply=(ImageView)convertView.findViewById(R.id.details_user_news_list_reply);
			holder.replyListView=(ListView)convertView.findViewById(R.id.details_user_news_list_comments);
			convertView.setTag(holder);
		}
	 else {
		holder = (ViewHolder) convertView.getTag();
	}
		final DetaisUserNewsBean detailsUserNewsBean=detaisUserList.get(position);
		
		//设置+按钮点击效果
		holder.reply.setOnClickListener(new popAction(position));
		if (position==0&&userNews) {
			holder.reply.setVisibility(View.GONE);
			holder.replyListView.setVisibility(View.GONE);
			holder.parentLayout.setBackgroundResource(R.color.white);
			if(detailsUserNewsBean.equals("null")||detailsUserNewsBean.getPicture()==null){
				holder.pictureImageView.setImageResource(R.drawable.app);
			}
			else {
				holder.pictureImageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(Utils.stringtoBitmap(detailsUserNewsBean.getPicture()),20f));
			}
			
			if (detailsUserNewsBean.getPicture1()!=null&&detailsUserNewsBean.getPicture2()==null) {
				holder.pictuer1.setImageBitmap(Utils.stringtoBitmap(detailsUserNewsBean.getPicture1()));
				holder.pictuer1.setVisibility(View.VISIBLE);
				holder.pictuer2.setVisibility(View.GONE);
				holder.pictuer3.setVisibility(View.GONE);
			}
			else if(detailsUserNewsBean.getPicture1()==null){
				holder.pictuer1.setVisibility(View.GONE);
				holder.pictuer2.setVisibility(View.GONE);
				holder.pictuer3.setVisibility(View.GONE);
			}
			else{
				holder.pictuer1.setVisibility(View.VISIBLE);
				holder.pictuer2.setVisibility(View.VISIBLE);
				holder.pictuer3.setVisibility(View.VISIBLE);
				holder.pictuer1.setImageBitmap(Utils.stringtoBitmap(detailsUserNewsBean.getPicture1()));
				holder.pictuer2.setImageBitmap(Utils.stringtoBitmap(detailsUserNewsBean.getPicture2()));
				holder.pictuer3.setImageBitmap(Utils.stringtoBitmap(detailsUserNewsBean.getPicture3()));
				
			}
			holder.userNameTextView.setText(detailsUserNewsBean.getName());
			holder.publicOrCommentsDate.setText(detailsUserNewsBean.getPublic_time());
			holder.content.setText(detailsUserNewsBean.getContent());
		}
		else {
			System.out.println(position);
			System.out.println(detailsUserNewsBean.getContent());
			holder.reply.setVisibility(View.VISIBLE);
			if(detailsUserNewsBean.getPicture().equals("null")||detailsUserNewsBean.getPicture()==null){
				holder.pictureImageView.setImageResource(R.drawable.app);
			}
			else {
				holder.pictureImageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(Utils.stringtoBitmap(detailsUserNewsBean.getPicture()),20f));
			}
			holder.parentLayout.setBackgroundResource(R.drawable.university_child_bg);
			holder.pictuer1.setVisibility(View.GONE);
			holder.pictuer2.setVisibility(View.GONE);
			holder.pictuer3.setVisibility(View.GONE);
			holder.userNameTextView.setText(detailsUserNewsBean.getName());
			holder.publicOrCommentsDate.setText(position+"目  "+detailsUserNewsBean.getPublic_time());
			holder.content.setText(detailsUserNewsBean.getContent());
			
			if(detailsUserNewsBean.getReplyCommentBeans()!=null){
				holder.replyListView.setVisibility(View.VISIBLE);
				ReplyCommentAdater replyCommentAdater=new ReplyCommentAdater(mContext, detailsUserNewsBean.getReplyCommentBeans());
				holder.replyListView.setAdapter(replyCommentAdater);
				//重写listview
			}
			else {
				holder.replyListView.setVisibility(View.GONE);
			}
		}
	//设置图像名字点击事件
		holder.pictureImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(mContext, PublicPersonInfoActivity.class);
				intent.putExtra("userName",detailsUserNewsBean.getName());
				mContext.startActivity(intent);
			}
		});
		holder.userNameTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(mContext, PublicPersonInfoActivity.class);
				intent.putExtra("userName",detailsUserNewsBean.getName());
				mContext.startActivity(intent);
			}
		});
		
		holder.replyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(mContext, ReplyActivity.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("detaisUserNewsBean",detailsUserNewsBean);
				bundle.putString("answer_man", detailsUserNewsBean.getReplyCommentBeans().get(arg2).getUsername());
				bundle.putInt("position", position);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}
	public class ViewHolder
	{
		public ImageView pictureImageView;
		public TextView userNameTextView;
		public TextView publicOrCommentsDate;
		public TextView content;
		public ListView commentListView;
		public ImageView pictuer1;
		public ImageView pictuer2;
		public ImageView pictuer3;
		public ImageView reply;
		public RelativeLayout parentLayout;
		public ListView replyListView;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	public void setListViewHeightBasedOnChildren(ListView listView) {   
		 // 获取ListView对应的Adapter   
		 ListAdapter listAdapter = listView.getAdapter();   
		      if (listAdapter == null) {   
		          return;   
		      }   
		
		      int totalHeight = 0;   
		      for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
		          // listAdapter.getCount()返回数据项的数目   
		            View listItem = listAdapter.getView(i, null, listView);   
		           // 计算子项View 的宽高   
		            listItem.measure(0, 0);    
		             // 统计所有子项的总高度   
		             totalHeight += listItem.getMeasuredHeight();    
		         }   
		    
		        ViewGroup.LayoutParams params = listView.getLayoutParams();   
		        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
		         // listView.getDividerHeight()获取子项间分隔符占用的高度   
		         // params.height最后得到整个ListView完整显示需要的高度   
		        listView.setLayoutParams(params);   
		    } 
	
	 /** popWindow 关闭按钮 */
		private ImageView btn_pop_close;
		/**popWindow点赞*/
		private LinearLayout btn_pop_reply;
		/**popWindow删除*/
		private LinearLayout btn_pop_delete;
		/**
		 * 初始化弹出的pop
		 * */
		private void initPopWindow() {
			View popView = inflater.inflate(R.layout.reply_list_item_pop, null);
			popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			popupWindow.setBackgroundDrawable(new ColorDrawable(0));
			//设置popwindow出现和消失动画
			popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
			btn_pop_close = (ImageView) popView.findViewById(R.id.btn_pop_close);
			btn_pop_reply=(LinearLayout)popView.findViewById(R.id.ll_pop_reply);
			btn_pop_delete=(LinearLayout)popView.findViewById(R.id.ll_pop_delete);
			if(userNews){
				if(sharedPreferences.getString(Constants.SP_USER_NAME, "").equals(detaisUserList.get(0).getName())){
					btn_pop_delete.setVisibility(View.VISIBLE);
				}
			}else {
				System.out.println("dfdsfd"+sharedPreferences.getString(Constants.SP_USER_NAME, ""));
				System.out.println(webUser);
				if(sharedPreferences.getString(Constants.SP_USER_NAME, "").equals(webUser)){
					btn_pop_delete.setVisibility(View.VISIBLE);
				}
			}
				
		}
	    /** 
		 * 显示popWindow
		 * */
		public void showPop(View parent, int x, int y,final int position) {
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
			btn_pop_reply.setOnClickListener(new OnClickListener() {
				public void onClick(View paramView) {
					Intent intent=new Intent();
					intent.setClass(mContext, ReplyActivity.class);
					Bundle bundle=new Bundle();
					bundle.putSerializable("detaisUserNewsBean",detaisUserList.get(position));
					if(userNews)
					bundle.putString("answer_man", detaisUserList.get(position).getName());
					else {
						bundle.putString("answer_man", webUser);
					}
					bundle.putInt("position", position);
					intent.putExtras(bundle);
					mContext.startActivity(intent);
					popupWindow.dismiss();
				}
			});
		}
		/** 
		 * 每个ITEM中more按钮对应的点击动作
		 * */
		public class popAction implements OnClickListener{
			int position;
			public popAction(int position){
				this.position = position;
			}
			@Override
			public void onClick(View v) {
				int[] arrayOfInt = new int[2];
				//获取点击按钮的坐标
				v.getLocationOnScreen(arrayOfInt);
		        int x = arrayOfInt[0];
		        int y = arrayOfInt[1];
		        showPop(v, x , y, position);
			}
		}

}
