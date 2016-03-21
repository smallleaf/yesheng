package com.wtu.university.slidingMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.universityconnection.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.wtu.university.MainInterfaceActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.left.PublicMessageActivity;
import com.wtu.university.slidingMenu.right.PersonInrformationActivity;
import com.wtu.university.slidingMenu.right.RegisterActivity;
import com.wtu.university.slidingMenu.right.SettingActivity;
import com.wtu.university.slidingMenu.right.server.GetUserInfo;
import com.wtu.university.view.CircleImageView;
import com.wtu.university.view.CustomProgressDialog;
import com.wtu.university.view.SwitchButton;
/** 
 * 自定义SlidingMenu 测拉菜单类
 * */
public class SlidingMenuContent implements OnClickListener{
	private final Activity activity;
	SlidingMenu localSlidingMenu;
	private SwitchButton night_mode_btn;
	private TextView night_mode_text;
	private RelativeLayout setting_btn;
	private SharedPreferences sharedPreferences;
	private Editor editor;
	//标题
	private ImageView top_user_pictureImageView;
	//注册
	private Button register;
	private Button loginButton;
	//左边界面
	private RelativeLayout publicMessageLayout;
	//登录
	private EditText loginUserName;
	private EditText loginPassWord;
	private CheckBox isPassword;
	private CheckBox isLoginBox;
	
	//设置界面
	private RelativeLayout settingLayout;
	//登录成功后的界面
	private RelativeLayout loginSuccessLayout;
	private CircleImageView userPicture;
	private TextView userNameTextView;
	private RelativeLayout infromationLayout;
	//正在登录界面
	CustomProgressDialog loginingCustomProgressDialog;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			loginingCustomProgressDialog.cancel();
			autuLogin();
		}
	};
	public SlidingMenuContent(Activity activity) {
		this.activity = activity;
	}

	public SlidingMenu initSlidingMenu() {
		sharedPreferences=activity.getSharedPreferences(Constants.SP_NAME, activity.MODE_PRIVATE);
		localSlidingMenu = new SlidingMenu(activity);
		localSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//设置左右滑菜单
		localSlidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW);//设置要使菜单滑动，触碰屏幕的范围
//		localSlidingMenu.setTouchModeBehind(SlidingMenu.SLIDING_CONTENT);//设置了这个会获取不到菜单里面的焦点，所以先注释掉
		localSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);//设置阴影图片的宽度
		localSlidingMenu.setShadowDrawable(R.drawable.shadow);//设置阴影图片
		localSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//SlidingMenu划出时主页面显示的剩余宽度
		localSlidingMenu.setFadeDegree(0.35F);//SlidingMenu滑动时的渐变程度
		localSlidingMenu.attachToActivity(activity, SlidingMenu.RIGHT);//使SlidingMenu附加在Activity右边
//		localSlidingMenu.setBehindWidthRes(R.dimen.left_drawer_avatar_size);//设置SlidingMenu菜单的宽度
		localSlidingMenu.setMenu(R.layout.left_drawer_fragment);//设置menu的布局文件
//		localSlidingMenu.toggle();//动态判断自动关闭或开启SlidingMenu
		localSlidingMenu.setSecondaryMenu(R.layout.profile_drawer_right);
		localSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
		localSlidingMenu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
					public void onOpened() {
						
					}
				});
		localSlidingMenu.setOnClosedListener(new OnClosedListener() {
			
			@Override
			public void onClosed() {
				// TODO Auto-generated method stub
				
			}
		});
		//判断是否自动登录
		if(sharedPreferences.getBoolean(Constants.conncectServer, false)&&sharedPreferences.getBoolean(Constants.SP_ATUON_LOGIN, false)){
			Log.v("自动登录", "自动登录");
			autuLogin();
		}
		else
			initView();
		return localSlidingMenu;
	}

	private void autuLogin() {
		// TODO Auto-generated method stub
		((RelativeLayout)localSlidingMenu.findViewById(R.id.login_relative)).setVisibility(View.GONE);
		loginSuccessLayout=(RelativeLayout)localSlidingMenu.findViewById(R.id.login_success_relative);
		userPicture=(CircleImageView)localSlidingMenu.findViewById(R.id.login_success_picture);
		userPicture.setImageBitmap(Utils.stringtoBitmap(sharedPreferences.getString(Constants.SP_USER_PICTURE, "")));
//		top_user_pictureImageView=(ImageView)localSlidingMenu.findViewById(R.id.top_head);
//		top_user_pictureImageView.setImageBitmap(Utils.stringtoBitmap(sharedPreferences.getString(Constants.SP_USER_PICTURE, "")));
		loginSuccessLayout.setVisibility(View.VISIBLE);
		userNameTextView=(TextView)localSlidingMenu.findViewById(R.id.login_success_name);
		userNameTextView.setText(sharedPreferences.getString(Constants.SP_USER_NAME, ""));
		
		settingLayout=(RelativeLayout)localSlidingMenu.findViewById(R.id.login_success_settting);
		settingLayout.setOnClickListener(this);
		infromationLayout=(RelativeLayout)localSlidingMenu.findViewById(R.id.login_success_userInfo);
		infromationLayout.setOnClickListener(this);
		
		
		//左边的界面
		publicMessageLayout=(RelativeLayout)localSlidingMenu.findViewById(R.id.public_message_btn);
		publicMessageLayout.setOnClickListener(this);
		
	}

	private void initView() {
		
		
		register=(Button)localSlidingMenu.findViewById(R.id.register);
		register.setOnClickListener(this);
		loginButton=(Button)localSlidingMenu.findViewById(R.id.login_btn);
		loginButton.setOnClickListener(this);
		loginUserName=(EditText)localSlidingMenu.findViewById(R.id.login_name);
		userNameTextView=(TextView)localSlidingMenu.findViewById(R.id.login_success_name);
		isPassword=(CheckBox)localSlidingMenu.findViewById(R.id.remember_password);
		isLoginBox=(CheckBox)localSlidingMenu.findViewById(R.id.auto_login);
		String userNameflag=sharedPreferences.getString(Constants.SP_USER_NAME, "");
		loginUserName.setText(userNameflag);
		loginPassWord=(EditText)localSlidingMenu.findViewById(R.id.login_password);
		if(sharedPreferences.getBoolean(Constants.SP_IS_PASSWORD, false)){
			loginPassWord.setText(sharedPreferences.getString(Constants.SP_PASSWORD, ""));
			isPassword.setChecked(true);
		}
		
		if(sharedPreferences.getBoolean(Constants.SP_BACK, false)){
			autuLogin();
		}
		//左边的界面
		publicMessageLayout=(RelativeLayout)localSlidingMenu.findViewById(R.id.public_message_btn);
		publicMessageLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			activity.startActivity(new Intent(activity,RegisterActivity.class));
			activity.finish();
			activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;
		case R.id.login_btn:
			if(canLogin()){
				new LoginTask().execute();
			}
			break;
		case R.id.login_success_settting:
			Intent intent=new Intent();
			intent.setClass(activity, SettingActivity.class);
			activity.startActivity(intent);
			activity.finish();
			break;
		case R.id.login_success_userInfo:
			
			Intent intent2=new Intent();
			intent2.setClass(activity, PersonInrformationActivity.class);
			activity.startActivity(intent2);
			activity.finish();
			break;
		case R.id.public_message_btn:
			System.out.println("发布消息");
			if(!sharedPreferences.getBoolean(Constants.SP_LOGIN_STATE, false)){
				DialogUtil.showToast(activity, "请先登陆");
			}
			else {
				Intent intent4=new Intent();
				intent4.setClass(activity, PublicMessageActivity.class);
				activity.startActivity(intent4);
			}
			break;
		default:
			break;
		}
	}
	
	
	public class LoginTask extends AsyncTask<String, String, JSONObject>{
		protected void onPreExecute() {
			loginingCustomProgressDialog=new CustomProgressDialog(activity, "正在登录中",R.anim.frame_meituan);
			loginingCustomProgressDialog.show();
			Log.v("test", "onPreExecute");
			super.onPreExecute();
		}
		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String username=loginUserName.getText().toString();
			String password=loginPassWord.getText().toString();
			Map<String, String> map=new HashMap<String, String>();
			map.put("loginusername", username);
			map.put("loginpassword", password);
			JSONObject jsonObject=null;
			try {
				jsonObject=loginJsonObject(map);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonObject;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.v("test", "onPostExecute");
			
			try {
				boolean loginSuccess=result.getBoolean("login");
				if(loginSuccess){
					Thread thread=new Thread(new GetUserInfo(activity,handler));
					thread.start();
					editor=sharedPreferences.edit();
					if(isPassword.isChecked()){
						
						editor.putBoolean(Constants.SP_IS_PASSWORD, true);
						editor.putString(Constants.SP_USER_NAME, loginUserName.getText().toString());
						editor.putString(Constants.SP_PASSWORD, loginPassWord.getText().toString());
					}
					else {
						editor.putBoolean(Constants.SP_IS_PASSWORD, false);
						editor.putString(Constants.SP_USER_NAME, loginUserName.getText().toString());
						editor.putString(Constants.SP_PASSWORD, loginPassWord.getText().toString());
					}
					if(isLoginBox.isChecked()){
						editor.putBoolean(Constants.SP_ATUON_LOGIN, true);
					}
					else {
						editor.putBoolean(Constants.SP_ATUON_LOGIN, false);
					}
					editor.putBoolean(Constants.SP_LOGIN_STATE, true);
					editor.commit();
				}
				else {
					initView();
					DialogUtil.showToast(activity, "登录失败，请检查用户名和密码！！！");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	private JSONObject loginJsonObject(Map<String, String> map) throws JSONException, IOException
	{
		String url=HttpUtil.BASE_URL+"loginAction.action";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
	public boolean canLogin(){
		String username=loginUserName.getText().toString();
		String password=loginPassWord.getText().toString();
		if(username==null||username.equals("")){
			DialogUtil.showToast(activity, "用户名不能为空");
			return false;
		}
		if(password==null||password.equals("")){
			DialogUtil.showToast(activity, "密码不能为空");
			return false;
		}
		return true;
	}
	
}
