package com.wtu.university.slidingMenu.adapter;

import java.util.ArrayList;

import com.example.universityconnection.R;
import com.wtu.university.slidingMenu.PublicPersonInfoActivity;
import com.wtu.university.slidingMenu.bean.ReplyCommentBean;
import com.wtu.university.view.TextViewFixTouchConsume;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReplyCommentAdater extends BaseAdapter {
	private ArrayList<ReplyCommentBean> replyCommentBeans;
	private Context mContext;
	ReplyCommentBean replyCommentBean ;

	public ReplyCommentAdater(Context mContext,
			ArrayList<ReplyCommentBean> replyCommentBeans) {
		this.mContext = mContext;
		this.replyCommentBeans = replyCommentBeans;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(replyCommentBeans==null){
			return 0;
		}
		else{
			return	replyCommentBeans.size();
		}
			
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return replyCommentBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.reply_comments, null);
			holder = new ViewHolder();
			holder.content = (TextView) convertView
					.findViewById(R.id.reply_content);
//			holder.itemLayout=(LinearLayout)convertView
//					.findViewById(R.id.reply_relate_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		replyCommentBean = replyCommentBeans
				.get(position);
		String tempString = ": 回复 ";
		String content = replyCommentBean.getUsername() + tempString
				+ replyCommentBean.getReply_usernaem() + ":"
				+ replyCommentBean.getContent() + "  "
				+ replyCommentBean.getReplyTime();

		ForegroundColorSpan changeColorSpan = new ForegroundColorSpan(
				Color.BLUE);
		ForegroundColorSpan changeColorSpan2 = new ForegroundColorSpan(
				Color.BLUE);
		SpannableStringBuilder builder = new SpannableStringBuilder(content);
		builder.setSpan(changeColorSpan, 0, replyCommentBean.getUsername()
				.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		builder.setSpan(changeColorSpan2,
				(replyCommentBean.getUsername() + tempString).length(),
				(replyCommentBean.getUsername() + tempString + replyCommentBean
						.getReply_usernaem()).length(),
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		builder.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, PublicPersonInfoActivity.class);
				intent.putExtra("userName", replyCommentBean.getUsername());
				mContext.startActivity(intent);
			}

		}, 0, replyCommentBean.getUsername().length(),
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		builder.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, PublicPersonInfoActivity.class);
				intent.putExtra("userName",
						replyCommentBean.getReply_usernaem());
				mContext.startActivity(intent);
			}

		}, (replyCommentBean.getUsername() + tempString).length(),
				(replyCommentBean.getUsername() + tempString + replyCommentBean
						.getReply_usernaem()).length(),
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		holder.content.setText(builder);
		holder.content.setMovementMethod(TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
		return convertView;
	}

	public class ViewHolder {
		public TextView content;
		public LinearLayout itemLayout;
	}
	
	
}
