package com.wtu.university.slidingMenu.right;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.universityconnection.JobActivity;
import com.example.universityconnection.R;
import com.example.universityconnection.R.layout;
import com.example.universityconnection.R.menu;
import com.wtu.university.MainInterfaceActivity;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.right.RegisterActivity.PopupWindows;
import com.wtu.university.slidingMenu.right.information.HobbyActivity;
import com.wtu.university.slidingMenu.right.information.InformationMajorActivity;
import com.wtu.university.slidingMenu.right.information.UniversityActivity;
import com.wtu.university.tools.ImageUtils;
import com.wtu.university.view.wheelview.ArrayWheelAdapter;
import com.wtu.university.view.wheelview.OnWheelChangedListener;
import com.wtu.university.view.wheelview.WheelView;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonInrformationActivity extends BaseActivity implements
		OnClickListener {
	Resources resources;
	String province[];
	String cities[][];
	private Bitmap getBitmap;
	private RelativeLayout informationLocationLayout;
	private RelativeLayout informationPictureLayout;
	private RelativeLayout informationUniversityLayout;
	private RelativeLayout informationMajorLayout;
	private RelativeLayout informationJobLayout;
	private RelativeLayout informarionAssociatitionLayout;
	private RelativeLayout informartionHoobyLayout;
	private SharedPreferences sharedPreferences;

	// 个人信息显示
	private TextView informationLocationTextView;
	private TextView informationUniversityTextView;
	private TextView informationJobTextView;
	private TextView informationMajorTextView;
	private TextView informationHoobyTextView;
	private TextView informationAssociatitionTextView;
	private ImageView informationPictureImageView;
	Editor editor;

	
	
	/** 请求CODE */
	public final static int PERONINFROMATION_UNIVERSITY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_person_inrformation);
		((TextView) findViewById(R.id.title)).setText("我的资料");
		
		resources = getResources();
		sharedPreferences = getSharedPreferences(Constants.SP_NAME,
				MODE_PRIVATE);
		editor = sharedPreferences.edit();
		informationLocationLayout = (RelativeLayout) findViewById(R.id.information_laction);
		informationLocationLayout.setOnClickListener(this);
		informationPictureLayout = (RelativeLayout) findViewById(R.id.information_picture);
		informationPictureLayout.setOnClickListener(this);
		informationLocationTextView = (TextView) findViewById(R.id.information_laction_text);
		informationUniversityLayout = (RelativeLayout) findViewById(R.id.information_colleage);
		informationUniversityLayout.setOnClickListener(this);
		informationMajorLayout = (RelativeLayout) findViewById(R.id.information_major);
		informationMajorLayout.setOnClickListener(this);
		informationJobLayout = (RelativeLayout) findViewById(R.id.information_job);
		informationJobLayout.setOnClickListener(this);
		informarionAssociatitionLayout = (RelativeLayout) findViewById(R.id.information_association);
		informarionAssociatitionLayout.setOnClickListener(this);
		informartionHoobyLayout = (RelativeLayout) findViewById(R.id.information_hobby);
		informartionHoobyLayout.setOnClickListener(this);

		informationAssociatitionTextView = (TextView) findViewById(R.id.information_association_text);
		informationHoobyTextView = (TextView) findViewById(R.id.information_hobby_text);
		informationJobTextView = (TextView) findViewById(R.id.information_job_text);
		informationMajorTextView = (TextView) findViewById(R.id.information_major_text);
		informationUniversityTextView = (TextView) findViewById(R.id.information_colleage_text);

		informationPictureImageView = (ImageView) findViewById(R.id.information_personpicture);
		getBitmap = Utils.stringtoBitmap(sharedPreferences.getString(
				Constants.SP_USER_PICTURE, ""));
		informationPictureImageView.setImageBitmap(Utils
				.stringtoBitmap(sharedPreferences.getString(
						Constants.SP_USER_PICTURE, "")));

		informationPictureImageView.setImageBitmap(Utils
				.stringtoBitmap(sharedPreferences.getString(
						Constants.SP_USER_PICTURE, "")));
		informationAssociatitionTextView.setText(sharedPreferences.getString(
				Constants.SP_UNIVERSITY, "")
				+ sharedPreferences.getString(Constants.SP_ASSOCIATITION, ""));
		informationJobTextView.setText(sharedPreferences.getString(
				Constants.SP_JOB, ""));
		informationLocationTextView.setText(sharedPreferences.getString(
				Constants.SP_ADDRESS, ""));
		informationMajorTextView.setText(sharedPreferences.getString(
				Constants.SP_MAJOR, ""));

		Log.v("daxue 的名字", "daxie de oimgzo ");
		Log.v("daxue 的名字",
				"daxie de oimgzo "
						+ sharedPreferences.getString(Constants.SP_UNIVERSITY,
								""));
		informationUniversityTextView.setText(sharedPreferences.getString(
				Constants.SP_UNIVERSITY, ""));
		informationHoobyTextView.setText(sharedPreferences.getString(
				Constants.SP_HOBBY1, "")
				+ " "
				+ sharedPreferences.getString(Constants.SP_HOBBY2, "")
				+ " "
				+ sharedPreferences.getString(Constants.SP_HOBBY3, ""));

		province = resources.getStringArray(R.array.province_item);
		cities = new String[][] { resources.getStringArray(Constants.city[0]),
				resources.getStringArray(Constants.city[1]),
				resources.getStringArray(Constants.city[2]),
				resources.getStringArray(Constants.city[3]),
				resources.getStringArray(Constants.city[4]),
				resources.getStringArray(Constants.city[5]),
				resources.getStringArray(Constants.city[6]),
				resources.getStringArray(Constants.city[7]),
				resources.getStringArray(Constants.city[8]),
				resources.getStringArray(Constants.city[9]),
				resources.getStringArray(Constants.city[10]),
				resources.getStringArray(Constants.city[11]),
				resources.getStringArray(Constants.city[12]),
				resources.getStringArray(Constants.city[13]),
				resources.getStringArray(Constants.city[14]),
				resources.getStringArray(Constants.city[15]),
				resources.getStringArray(Constants.city[16]),
				resources.getStringArray(Constants.city[17]),
				resources.getStringArray(Constants.city[18]),
				resources.getStringArray(Constants.city[19]),
				resources.getStringArray(Constants.city[20]),
				resources.getStringArray(Constants.city[21]),
				resources.getStringArray(Constants.city[22]),
				resources.getStringArray(Constants.city[23]),
				resources.getStringArray(Constants.city[24]),
				resources.getStringArray(Constants.city[25]),
				resources.getStringArray(Constants.city[26]),
				resources.getStringArray(Constants.city[27]),
				resources.getStringArray(Constants.city[28]),
				resources.getStringArray(Constants.city[29]),
				resources.getStringArray(Constants.city[30]),
				resources.getStringArray(Constants.city[31]),
				resources.getStringArray(Constants.city[32]),
				resources.getStringArray(Constants.city[33]) };

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.information_laction:
			new PopupWindows(this, informationLocationLayout);
			break;
		case R.id.information_picture:
			new PopupWindowsPicture(this, informationPictureLayout);
			break;
		case R.id.information_colleage:
			Intent intent = new Intent();
			intent.setClass(this, UniversityActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.information_major:
			Intent intent2 = new Intent();
			intent2.setClass(this, InformationMajorActivity.class);
			startActivity(intent2);
			finish();
			break;
		case R.id.information_hobby:
			Intent intent3 = new Intent();
			intent3.setClass(this, HobbyActivity.class);
			startActivity(intent3);
			finish();
			break;
		case R.id.information_job:
			new PopupWindowsJobOrAssociatition(this, arg0, true);
			break;
		case R.id.information_association:
			new PopupWindowsJobOrAssociatition(this, arg0, false);
			break;
		default:
			break;
		}
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View.inflate(mContext, R.layout.choose_city, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			// RelativeLayout ll_popup = (RelativeLayout) view
			// .findViewById(R.id.choosecity);
			// ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
			// R.anim.push_bottom_in_2));
			setContentView(view);

			final WheelView country = (WheelView) view
					.findViewById(R.id.province);
			country.setVisibleItems(5);
			country.setCyclic(true);//
			country.setAdapter(new ArrayWheelAdapter<String>(province));

			final WheelView city = (WheelView) view.findViewById(R.id.city);
			city.setVisibleItems(5);
			country.addChangingListener(new OnWheelChangedListener() {
				public void onChanged(WheelView wheel, int oldValue,
						int newValue) {
					System.out.println("id" + oldValue + "be+" + newValue);
					city.setAdapter(new ArrayWheelAdapter<String>(
							cities[newValue]));
					city.setCurrentItem(cities[newValue].length / 2);
				}
			});

			country.setCurrentItem(16);

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);

			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			Button cacle = (Button) view.findViewById(R.id.choosecity_cancel);
			Button confin = (Button) view.findViewById(R.id.choosecity_confin);
			cacle.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});
			confin.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					editor.putString(
							Constants.SP_ADDRESS,
							province[country.getCurrentItem()]
									+ " "
									+ cities[country.getCurrentItem()][city
											.getCurrentItem()]);
					editor.commit();
					informationLocationTextView.setText(province[country
							.getCurrentItem()]
							+ " "
							+ cities[country.getCurrentItem()][city
									.getCurrentItem()]);
					dismiss();
				}
			});

		}
	}

	public class PopupWindowsPicture extends PopupWindow {

		public PopupWindowsPicture(Context mContext, View parent) {

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
							.createImagePathUri(PersonInrformationActivity.this);
					ImageUtils.openCameraImage(PersonInrformationActivity.this,
							ImageUtils.imageUriFromCamera);
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					ImageUtils.openLocalImage(PersonInrformationActivity.this);
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

	public class PopupWindowsJobOrAssociatition extends PopupWindow {

		public PopupWindowsJobOrAssociatition(Context mContext, View parent,
				final boolean flag) {

			View view = View.inflate(mContext, R.layout.job_input_popwindow,
					null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			// LinearLayout ll_popup = (LinearLayout) view
			// .findViewById(R.id.ll_popup);
			// ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
			// R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			final EditText editText = (EditText) view
					.findViewById(R.id.job_input_popwindow);
			if (flag) {
				editText.setHint("请输入职业");
			} else {
				editText.setHint("请输入社团");
			}
			Button bt1 = (Button) view.findViewById(R.id.job_input_cancel);
			Button bt2 = (Button) view.findViewById(R.id.job_input_confin);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (flag) {
						editor.putString(Constants.SP_JOB, editText.getText()
								.toString());
						editor.commit();
						informationJobTextView.setText(editText.getText()
								.toString());
					} else {
						editor.putString(Constants.SP_ASSOCIATITION, editText
								.getText().toString());
						editor.commit();
						informationAssociatitionTextView
								.setText(sharedPreferences.getString(
										Constants.SP_UNIVERSITY, "")
										+ editText.getText().toString());
					}
					dismiss();
				}
			});

		}
	}

	@Override
	public void onBackPressed() {
		new ChangeUserInfoTask().execute();
		editor = sharedPreferences.edit();
		editor.putBoolean(Constants.SP_BACK, true);
		editor.commit();
		Intent intent = new Intent(getApplicationContext(),
				MainInterfaceActivity.class);
		startActivity(intent);
		this.finish();
	}

	public class ChangeUserInfoTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String username = sharedPreferences.getString(
					Constants.SP_USER_NAME, "");
			String university = informationUniversityTextView.getText()
					.toString();

			String major = informationMajorTextView.getText().toString();
			String address = informationLocationTextView.getText().toString();
			String job = informationJobTextView.getText().toString();
			String password = sharedPreferences.getString(
					Constants.SP_PASSWORD, "");

			Log.v("sdfafafdf", "dasfdsfaf" + address);

			// System.out.println("兴趣"+informationHoobyTextView.getText().toString());
			// String
			// hooby[]=Utils.departHooby(informationHoobyTextView.getText().toString());
			// String hobby1=hooby[0];
			// String hobby2=hooby[1];
			// String hobby3=hooby[2];
			// System.out.println("兴趣"+hobby1);
			// System.out.println("兴趣"+hobby2);
			// System.out.println("兴趣"+hobby3);

			editor = sharedPreferences.edit();
			editor.putString(Constants.SP_UNIVERSITY, university);
			Log.v("大修额", sharedPreferences.getString(Constants.SP_UNIVERSITY, ""));
			// editor.putString(Constants.SP_HOBBY1,hobby1);
			// editor.putString(Constants.SP_HOBBY2,hobby2);
			// editor.putString(Constants.SP_HOBBY3,hobby3);
			editor.putString(Constants.SP_JOB, job);
			editor.putString(Constants.SP_MAJOR, major);
			// editor.putString(Constants.SP_ASSOCIATITION,associatition);
			editor.putString(Constants.SP_ADDRESS, address);

			editor.commit();
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", username);
			map.put("password", password);
			if (getBitmap != null)
				map.put("picture", sharedPreferences.getString(
						Constants.SP_USER_PICTURE, ""));
			System.out.println("pcirie+"
					+ sharedPreferences
							.getString(Constants.SP_USER_PICTURE, ""));
			map.put("university", university);
			map.put("major", major);
			map.put("address", address);
			map.put("job", job);
			map.put("hobby1",
					sharedPreferences.getString(Constants.SP_HOBBY1, ""));
			map.put("hobby2",
					sharedPreferences.getString(Constants.SP_HOBBY2, ""));
			map.put("hobby3",
					sharedPreferences.getString(Constants.SP_HOBBY3, ""));
			map.put("associatition",
					sharedPreferences.getString(Constants.SP_ASSOCIATITION, ""));

			String url = HttpUtil.BASE_URL + "updateUserAction.action";
			try {
				HttpUtil.postRequest(url, map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
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
					System.out.println(bitmap.getWidth() + ":" + bitmap.getHeight());
					editor.putString(Constants.SP_USER_PICTURE,
							Utils.bitmapToString(bitmap));
					editor.commit();
					informationPictureImageView.setImageBitmap(bitmap);
				}
				break;
			}
			super.onActivityResult(requestCode, resultCode, data);
	}
}
