package com.wtu.university.slidingMenu.right;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.universityconnection.R;
import com.example.universityconnection.R.layout;
import com.example.universityconnection.R.menu;
import com.wtu.university.MainInterfaceActivity;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.tools.ImageUtils;
import com.wtu.university.view.CircleImageView;
import com.wtu.university.view.CustomProgressDialog;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	ListView choosePictureWayListView;
	Adapter adapter;
	CircleImageView registerPicture;
	private Button registerButton;
	private EditText userNameEditText;
	private EditText passWordEditText;
	private EditText passWordAgainEditText;
	private CustomProgressDialog registerProgressDialog;
	private boolean upLoadPicture = false;// 是否上传图片
	private Bitmap getBitmap = null;// 图库或者相机返回的图片
	String items[] = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		((TextView) findViewById(R.id.title)).setText("注册新用户");
		registerPicture = (CircleImageView) findViewById(R.id.rigister_picture);
		Resources resources = getResources();
		userNameEditText = (EditText) findViewById(R.id.register_name);
		passWordEditText = (EditText) findViewById(R.id.register_pass);
		passWordAgainEditText = (EditText) findViewById(R.id.register_pass_again);
		registerPicture.setImageBitmap(BitmapFactory.decodeResource(resources,
				R.drawable.login));
		registerPicture.setOnClickListener(this);
		registerButton = (Button) findViewById(R.id.register_but);
		registerButton.setOnClickListener(this);
		// choosePictureWayListView=(ListView)findViewById(R.id.chooseopnePicture);

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(),
				MainInterfaceActivity.class);
		setResult(MainInterfaceActivity.CHANNELRESULT, intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub i
		switch (arg0.getId()) {
		case R.id.rigister_picture:
			new PopupWindows(this, registerPicture);
			break;
		case R.id.register_but:
			if (isCanRegister())
				new RegisterTask().execute(items);
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
							.createImagePathUri(RegisterActivity.this);
					ImageUtils.openCameraImage(RegisterActivity.this,
							ImageUtils.imageUriFromCamera);
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					ImageUtils.openLocalImage(RegisterActivity.this);
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
				upLoadPicture=true;
				System.out.println(bitmap.getWidth() + ":" + bitmap.getHeight());
				registerPicture.setImageBitmap(bitmap);
				getBitmap=bitmap;
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public class RegisterTask extends AsyncTask<String, String, JSONObject> {
		protected void onPreExecute() {
			registerProgressDialog = new CustomProgressDialog(
					RegisterActivity.this, "正在注册中...", R.anim.frame_meituan);
			registerProgressDialog.show();
			Log.v("test", "onPreExecute");
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String username = userNameEditText.getText().toString();
			String password = passWordEditText.getText().toString();
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", username);
			map.put("password", password);
			SharedPreferences sp = getSharedPreferences(Constants.SP_NAME,
					MODE_PRIVATE);
			Editor editor = sp.edit();
			if (upLoadPicture) {
				Log.v("正在保存图片", "正在保存图片");
				upLoadPicture = false;
				map.put("picture", Utils.bitmapToString(getBitmap));
				editor.putString(Constants.SP_USER_PICTURE,
						Utils.bitmapToString(getBitmap));
			} else {
				map.put("picture", "null");
			}
			editor.putString(Constants.SP_USER_NAME, username);
			editor.commit();
			map.put("university", " ");
			map.put("major", " ");
			map.put("address", " ");
			map.put("job", " ");
			map.put("hobby1", " ");
			map.put("hobby2", " ");
			map.put("hobby3", " ");
			map.put("associatition", " ");
			JSONObject jsonObject = null;
			try {
				jsonObject = registerJsonObject(map);
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
			registerProgressDialog.cancel();
			super.onPostExecute(result);
			Log.v("test", "onPostExecute");
			try {
				boolean registerSuccess = result.getBoolean("register");
				if (registerSuccess) {
					SharedPreferences sharedPreferences = getSharedPreferences(
							Constants.SP_NAME, MODE_PRIVATE);
					Editor editor;
					editor = sharedPreferences.edit();
					editor.putBoolean(Constants.SP_REGISTER, true);
					editor.commit();
					Intent intent = new Intent(getApplicationContext(),
							MainInterfaceActivity.class);
					startActivity(intent);
					finish();
					DialogUtil.showToast(RegisterActivity.this, "注册成功");
				} else {
					DialogUtil.showToast(RegisterActivity.this, "注册失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private JSONObject registerJsonObject(Map<String, String> map)
			throws JSONException, IOException {
		String url = HttpUtil.BASE_URL + "registerAction.action";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}

	private boolean isCanRegister() {
		String username = userNameEditText.getText().toString();
		String password = passWordEditText.getText().toString();
		String passwordagain = passWordAgainEditText.getText().toString();
		if (username == null || username.equals("")) {
			DialogUtil.showToast(this, "用户名不能为空");
			return false;
		} else if (passwordagain == null || passwordagain.equals("")
				|| password == null || password.equals("")) {
			DialogUtil.showToast(this, "密码不能为空");
			return false;
		} else if (!passwordagain.equals(password)) {
			DialogUtil.showToast(this, "两次密码不一致");
			return false;
		}
		return true;
	}

}
