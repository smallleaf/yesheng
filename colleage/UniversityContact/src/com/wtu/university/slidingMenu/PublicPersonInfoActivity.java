package com.wtu.university.slidingMenu;

import com.example.universityconnection.R;
import com.example.universityconnection.R.id;
import com.example.universityconnection.R.layout;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.left.bean.UserInfoBean;
import com.wtu.university.slidingMenu.server.GetUserInfoServer;
import com.wtu.university.view.CustomProgressDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PublicPersonInfoActivity extends Activity {
	private TextView backTextView;
	private ImageView pictureImageView;
	
	private CustomProgressDialog registerProgressDialog;
	private UserInfoBean userInfoBean;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			registerProgressDialog.cancel();
			((TextView)findViewById(R.id.title)).setText("亲，偷看  "+userInfoBean.getName());
			((TextView)findViewById(R.id.public_person_info_association)).setText(userInfoBean.getAssociation());
			((TextView)findViewById(R.id.public_person_info_colleage)).setText(userInfoBean.getColleage());
			((TextView)findViewById(R.id.public_person_info_hobby)).setText(userInfoBean.getHobby());
			((TextView)findViewById(R.id.public_person_info_job)).setText(userInfoBean.getJob());
			((TextView)findViewById(R.id.public_person_info_major)).setText(userInfoBean.getMajor());
			((TextView)findViewById(R.id.public_person_info_location)).setText(userInfoBean.getLocation());
			pictureImageView=(ImageView)findViewById(R.id.public_person_info_picture);
			pictureImageView.setImageBitmap(Utils.stringtoBitmap(userInfoBean.getPicture()));
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_public_person_info);
		
		userInfoBean=new UserInfoBean();
		
		registerProgressDialog = new CustomProgressDialog(
				PublicPersonInfoActivity.this, "正在偷看中...", R.anim.frame_meituan);
		registerProgressDialog.show();
		
		Intent intent=this.getIntent();
		
		System.out.println("获得的数据是多少呢     "+intent.getStringExtra("userName"));
		GetUserInfoServer getUserInfoServer=new GetUserInfoServer(intent.getStringExtra("userName"), userInfoBean, handler);
		Thread thread=new Thread(getUserInfoServer);
		thread.start();
		
		backTextView=(TextView)findViewById(R.id.back);
		backTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
