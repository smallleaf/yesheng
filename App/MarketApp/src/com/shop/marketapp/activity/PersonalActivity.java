package com.shop.marketapp.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.marketapp.AllOrderActivity;
import com.example.marketapp.R;
import com.example.marketapp.R.layout;
import com.example.marketapp.R.menu;
import com.shop.marketapp.constant.Constants;
import com.shop.marketapp.constant.DialogUtil;
import com.shop.marketapp.constant.HttpUtil;
import com.shop.marketapp.constant.Tools;
import com.shop.marketapp.image.ImageUtils;
import com.shop.marketapp.person.ui.PersonDetailsActivity;
import com.shop.marketapp.widget.CircleImageView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonalActivity extends Activity implements OnClickListener{
	//注册按钮
	private Button registerOrExit;
	//用户名
	private TextView userName;
	/**用户图片*/
	private CircleImageView userCircleImageView;
	
	/** 请求CODE */
	public final static int REQUEST = 1;
	/** 登陆成功请求CODE */
	public final static int LOGINSUCCESSREQUEST = 2;
	private SharedPreferences sharedPreferences;
	/**
	 * 所有订单
	 */
	private RelativeLayout allBillrLayout;
	/**
	 * 系统设置
	 */
	private ImageView systemImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal);
		((TextView)findViewById(R.id.common_title_tv_titletext)).setText("我的信息");
		systemImageView=(ImageView)findViewById(R.id.title_setting);
		systemImageView.setImageResource(R.drawable.system);
		systemImageView.setOnClickListener(this);
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		initView();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		registerOrExit=(Button)findViewById(R.id.activity_person_bt_login);
		registerOrExit.setOnClickListener(this);
		userCircleImageView=(CircleImageView)findViewById(R.id.activity_person_civ_user_pic);
		userCircleImageView.setOnClickListener(this);
		userName=(TextView)findViewById(R.id.activity_person_tv_username);
		userName.setOnClickListener(this);
		//如果已经登陆 就显示用户名 否则显示登陆按钮
		if(sharedPreferences.getBoolean(Constants.SP_ISLOGIN, false)){
			userName.setText(sharedPreferences.getString(Constants.SP_LOGIN_NAME, "")+",您好");
			userName.setVisibility(View.VISIBLE);
			registerOrExit.setVisibility(View.GONE);
		}else {
			registerOrExit.setVisibility(View.VISIBLE);
			userName.setVisibility(View.GONE);
		}
		
		allBillrLayout=(RelativeLayout)findViewById(R.id.personal_rl_allbill);
		allBillrLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.activity_person_bt_login:
			Intent intent=new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivityForResult(intent, REQUEST);
			break;
		case R.id.activity_person_tv_username:
			Intent intent2=new Intent();
			intent2.setClass(this, PersonDetailsActivity.class);
			startActivityForResult(intent2, REQUEST);
			break;
		case R.id.title_setting:
			Intent intent4=new Intent();
			intent4.setClass(this, PersonDetailsActivity.class);
			startActivityForResult(intent4, REQUEST);
			break;

		case R.id.activity_person_civ_user_pic:
			if(sharedPreferences.getBoolean(Constants.SP_ISLOGIN, false))
				new PopupWindows(PersonalActivity.this,view);
			else {
				DialogUtil.showToast(PersonalActivity.this, "亲，还未登陆哦！！！");
			}
			break;
		case R.id.personal_rl_allbill:
			Intent intent3=new Intent();
			intent3.setClass(PersonalActivity.this, AllOrderActivity.class);
			startActivity(intent3);
			break;
		default:
			break;
		}
	}
	
	
	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View.inflate(mContext,
					R.layout.choose_picture_popwindow, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					ImageUtils.imageUriFromCamera = ImageUtils
							.createImagePathUri(PersonalActivity.this);
					ImageUtils.openCameraImage(PersonalActivity.this,
							ImageUtils.imageUriFromCamera);
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					ImageUtils.openLocalImage(PersonalActivity.this);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					dismiss();
				}
			});
		}
	}
	
	// 回调函数
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_CANCELED) {
				return;
			}
			switch (requestCode) {
			// 拍照获取图片
			case ImageUtils.GET_IMAGE_BY_CAMERA:
				// uri传入与否影响图片获取方式,以下二选一
				// 方式一,ImageUtils.openCameraImage传入自定义uri时(可用initImagePathUri创建)
				// 系统会将拍摄的照片保存至自定义uri中
				if (ImageUtils.imageUriFromCamera != null) {
					ImageUtils.cropImage(this, ImageUtils.imageUriFromCamera);
				}
				break;
				// 手机相册获取图片
			case ImageUtils.GET_IMAGE_FROM_PHONE:
				if (data != null && data.getData() != null) {
					ImageUtils.cropImage(this, data.getData());
				}
				break;
				// 裁剪图片后结果
			case ImageUtils.CROP_IMAGE:
				Bitmap bitmap = (Bitmap) data.getParcelableExtra("data");
				if(bitmap != null) {
//					upLoadPicture=true;
					System.out.println(bitmap.getWidth() + ":" + bitmap.getHeight());
					userCircleImageView.setImageBitmap(bitmap);
					new SaveUserPicATsk().execute(sharedPreferences.getString(Constants.SP_LOGIN_NAME, ""),Tools.bitmapToBase64(bitmap));
//					getBitmap=bitmap;
				}
				break;
			case REQUEST:
				if(resultCode==LOGINSUCCESSREQUEST){
					initView();
				}
				break;
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
		
		public class SaveUserPicATsk extends AsyncTask<String, String, String>{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				Map<String, String> map=new HashMap<String, String>();
				map.put("phone", params[0]);
				map.put("picture", params[1]);
				String url=HttpUtil.BASE_URL+"user!update.action";
				try {
					HttpUtil.postRequest(url, map);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}
		protected void onResume() {
			Log.v("PersonalActivity======", "===onResume()");
			if(sharedPreferences.getBoolean(Constants.SP_ISLOGIN, false))
				initView();
			super.onResume();
		}
}
