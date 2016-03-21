package com.wtu.university.slidingMenu.left;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.universityconnection.R;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.left.adpter.PublicPicGridAdapter;
import com.wtu.university.slidingMenu.left.view.KeyboardListenEdittext;
import com.wtu.university.slidingMenu.left.view.KeyboardListenEdittext.MOnKeyboardStateChangedListener;
import com.wtu.university.tools.ImageUtils;
import com.wtu.university.view.CustomProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PublicMessageActivity extends BaseActivity implements OnClickListener {

	private Context mCon;
	// 控制表情布局部分
	private ViewPager viewPager;
	private ArrayList<GridView> grids;
	private GridView noScrollgridview;
	private PublicPicGridAdapter picGridAdapter;
	private ArrayList<Bitmap> choosePicture=new ArrayList<Bitmap>();
	private TextView publicNews;
	private SharedPreferences sharedPreferences;
	// 发表新鲜事
	private KeyboardListenEdittext content_et;
	private EditText titleEditText;
	private TextView kindsEditText;
	private RelativeLayout kindsNewsLayout;
	private ArrayList<String> newsKindArrayList=new ArrayList<String>();
	private int newsKindPosition;
	private CustomProgressDialog pubicnewNewsCustomProgressDialog;
	private RelativeLayout bface_lay;
	private ImageButton face_btn;
	private ImageButton word_btn;
	private Boolean isfocus = false;
	private Boolean biaoqingstate = false;

	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			System.out.println("出生年改制"+choosePicture.size());
//			picGridAdapter=new PublicPicGridAdapter(choosePicture, mCon);
			picGridAdapter.notifyDataSetChanged();
			noScrollgridview.setAdapter(picGridAdapter);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_public_message);
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		((TextView) findViewById(R.id.title)).setText("发布动态");
		publicNews=(TextView)findViewById(R.id.right_text);
		publicNews.setText("发布");
		publicNews.setOnClickListener(this);
		publicNews.setVisibility(View.VISIBLE);
		titleEditText=(EditText)findViewById(R.id.publicNews_title);
		kindsEditText=(TextView)findViewById(R.id.publicNews_kind);
		kindsNewsLayout=(RelativeLayout)findViewById(R.id.publicNews_kind_layout);
		kindsNewsLayout.setOnClickListener(this);
		mCon = this;

		Init();
		new GetNewsKindAsTack().execute();
		content_et = (KeyboardListenEdittext) findViewById(R.id.content_et);

		content_et.setOnClickListener(this);
		content_et
				.setOnKeyboardStateChangedListener(new MOnKeyboardStateChangedListener() {

					@Override
					public void onKeyboardStateChanged(int state) {
						// TODO Auto-generated method stub

						switch (state) {
						case KeyboardListenEdittext.KEYBOARD_STATE_HIDE:

							isfocus = false;

							break;

						case KeyboardListenEdittext.KEYBOARD_STATE_SHOW:

							isfocus = true;

							break;
						default:
							break;
						}

					}
				});
		
	}

	

	public void Init() {
		
		choosePicture.add(BitmapFactory.decodeResource(getResources(), R.drawable.add_item_hover));
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		picGridAdapter=new PublicPicGridAdapter(choosePicture, mCon);
		noScrollgridview.setAdapter(picGridAdapter);
		
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pistion,
					long arg3) {
				// TODO Auto-generated method stub
				// 隐藏输入法
				Utils.hideInput(noScrollgridview);
				if(choosePicture.size()<=3){
					if(pistion==choosePicture.size()-1){
						new PopupWindows(mCon,noScrollgridview);
					}
				}
				else {
					DialogUtil.showToast(mCon, "最多3张图片");
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.publicNews_kind_layout:
			String[] items=newsKindArrayList.toArray(new String[]{});
			Dialog kindsDialog=new AlertDialog.Builder(this)
				.setTitle("请选择类型")
				.setIcon(R.color.blue)
				.setItems(items, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int position) {
						// TODO Auto-generated method stub
						kindsEditText.setText("新闻类型:"+newsKindArrayList.get(position));
						newsKindPosition=position;
					}
				}).create();
			kindsDialog.show();
			break;
		case R.id.right_text:
			if(sendJudge())
			new SendUSerNews().execute();
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
							.createImagePathUri(PublicMessageActivity.this);
					ImageUtils.openCameraImage(PublicMessageActivity.this,
							ImageUtils.imageUriFromCamera);
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					ImageUtils.openLocalImage(PublicMessageActivity.this);
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
			if (ImageUtils.imageUriFromCamera != null) {
				choosePicture.add(0,Utils.getSmallBitmap(Utils.getFilePath(mCon, ImageUtils.imageUriFromCamera)));
				Message msg=new Message();
				handler.sendMessage(msg);
			}
			break;
			// 手机相册获取图片
		case ImageUtils.GET_IMAGE_FROM_PHONE:
			choosePicture.add(0,Utils.getSmallBitmap(Utils.getFilePath(mCon, data.getData())));
			Message msg=new Message();
			handler.sendMessage(msg);
			break;
		
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
//		Intent intent = new Intent(getApplicationContext(),
//				MainInterfaceActivity.class);
//		setResult(MainInterfaceActivity.CHANNELRESULT, intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	
	public class GetNewsKindAsTack extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String url = HttpUtil.BASE_URL + "getNewsKindAction.action";
			Map<String, String> map=new HashMap<String, String>();
			try {
				JSONObject jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
				if(!jsonObject.getString("newsKind").equals("null")){
					JSONArray jsonArray=new JSONArray(jsonObject.getString("newsKind"));
					for(int i=0;i<jsonArray.length();i++){
						newsKindArrayList.add(jsonArray.getString(i));
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	public boolean sendJudge(){
		if(titleEditText.getText().toString().equals("")){
			DialogUtil.showToast(mCon, "标题不能为空");
			return false;
		}
		if(content_et.getText().toString().equals("")){
			DialogUtil.showToast(mCon, "内容不能为空");
			return false;
		}
		if(kindsEditText.getText().equals("")){
			DialogUtil.showToast(mCon, "类型不能为空");
			return false;
		}
		return true;
	}
	
	public class SendUSerNews extends AsyncTask<String, String, JSONObject>{
		protected void onPreExecute() {
			pubicnewNewsCustomProgressDialog = new CustomProgressDialog(
					mCon, "正在发表中...", R.anim.frame_meituan);
			pubicnewNewsCustomProgressDialog.show();
			Log.v("test", "onPreExecute");
			super.onPreExecute();
		}
		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String url = HttpUtil.BASE_URL + "saveUserPublicNewsAction.action";
			Map<String, String> map=new HashMap<String, String>();
			map.put("title", titleEditText.getText().toString());
			map.put("content", titleEditText.getText().toString());
			map.put("news_kind_id", String.valueOf(newsKindPosition+2));
			map.put("publicName", sharedPreferences.getString(Constants.SP_USER_NAME, ""));
			if(choosePicture.size()==2){
				map.put("picture1", Utils.bitmapToString(choosePicture.get(0)));
			}
			else if(choosePicture.size()==3) {
				map.put("picture1", Utils.bitmapToString(choosePicture.get(0)));
				map.put("picture2", Utils.bitmapToString(choosePicture.get(1)));
			}
			else if(choosePicture.size()==4) {
				map.put("picture1", Utils.bitmapToString(choosePicture.get(0)));
				map.put("picture2", Utils.bitmapToString(choosePicture.get(1)));
				map.put("picture3", Utils.bitmapToString(choosePicture.get(2)));
			}
			else {
				Log.v("没有选择图片", "没有选择图片");
				map.put("picture1", "null");
				map.put("picture2", "null");
				map.put("picture3", "null");
			}
			JSONObject jsonObject=null;
			try {
				jsonObject=new JSONObject(HttpUtil.postRequest(url, map));
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
			pubicnewNewsCustomProgressDialog.cancel();
			super.onPostExecute(result);
			if(result!=null){
				try {
					if(result.getBoolean("saveUserPublicNews")){
						DialogUtil.showToast(mCon, "发表成功");
						finish();
						overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
					}
					else {
						DialogUtil.showToast(mCon, "发表失败");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				DialogUtil.showToast(mCon, "发表失败");
			}
		}
	}
}
