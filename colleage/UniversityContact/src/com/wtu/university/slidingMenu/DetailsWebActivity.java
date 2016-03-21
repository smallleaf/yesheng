package com.wtu.university.slidingMenu;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.universityconnection.R;
import com.example.universityconnection.R.layout;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.DetaisNewsActivity.GetPublicCommentAtast;
import com.wtu.university.slidingMenu.DetaisNewsActivity.SendPublicCommentAstask;
import com.wtu.university.slidingMenu.adapter.DetailsUserNewsAdapter;
import com.wtu.university.slidingMenu.bean.DetaisUserNewsBean;
import com.wtu.university.slidingMenu.bean.ReplyCommentBean;
import com.wtu.university.slidingMenu.server.NewsDetailsService;
import com.wtu.university.view.CustomProgressDialog;
import com.wtu.university.view.ListHeightUtils;
import com.wtu.university.view.RefreshSupportListView;
import com.wtu.university.view.ReplyListView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class DetailsWebActivity extends BaseActivity implements OnClickListener {
	private DetaisUserNewsBean detaisUserNewsBean;
	private TextView backTextView;
	private ArrayList<DetaisUserNewsBean> detaisUserNewsBeans;
	private CustomProgressDialog uploadProgressDialog;
	private WebView webView;
	private int lastItem;
	private ReplyListView mListView;
	private DetailsUserNewsAdapter detailsUserNewsAdapter;
	private SharedPreferences sharedPreferences;
	private EditText inputMsg;
	private int i=0;
	private Button sendMsg;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_details_web);
		setNeedBackGesture(true);//设置需要手势监听
		sharedPreferences = getSharedPreferences(Constants.SP_NAME,
				MODE_PRIVATE);
		detaisUserNewsBeans = new ArrayList<DetaisUserNewsBean>();
		Intent intent = this.getIntent();
		detaisUserNewsBean = (DetaisUserNewsBean) intent
				.getSerializableExtra("publicUser");
		detaisUserNewsBeans.add(detaisUserNewsBean);
		((TextView) findViewById(R.id.left_text)).setText(detaisUserNewsBean
				.getContent());
		((TextView) findViewById(R.id.left_text)).setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.title)).setVisibility(View.GONE);

		uploadProgressDialog = new CustomProgressDialog(
				DetailsWebActivity.this, "正在偷看中...", R.anim.frame_meituan);
		uploadProgressDialog.show();
		inputMsg = (EditText) findViewById(R.id.web_details_answer_publicPeople);
		backTextView = (TextView) findViewById(R.id.back);
		backTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		initWebView();
		
	}

	/** 初始化数据 */
	private void initData() {
		// TODO Auto-generated method stub

		new GetPublicCommentAtast().execute(String.valueOf(detaisUserNewsBean
				.getId()));

	}

	/** 初始化组件 */
	private void init() {
		// TODO Auto-generated method stub
		mListView = (ReplyListView) findViewById(R.id.web_listview);
	
		mListView.setDivider(this.getResources().getDrawable(
				R.drawable.cell_seperator));

		mListView.setOnScrollListener(new OnScrollListener() {

			/**
			 * 滚动状态改变时调用
			 */
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 不滚动时保存当前滚动到的位置
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					lastItem = mListView.getFirstVisiblePosition();
				}
			}

			/**
			 * 滚动时调用
			 */
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		inputMsg.setOnClickListener(this);
		 sendMsg=(Button)findViewById(R.id.web_details_news_sendBtu);
		 sendMsg.setOnClickListener(this);
		 sendMsg.setVisibility(View.VISIBLE);

	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void initWebView() {
		webView = (WebView) findViewById(R.id.webView);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// if (!TextUtils.isEmpty(news_url)) {
		Log.v("fsfsd", "fsfsd");
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);// 设置可以运行JS脚本
		// settings.setTextZoom(120);//Sets the text zoom of the page in
		// percent. The default is 100.
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// settings.setUseWideViewPort(true); //打开页面时， 自适应屏幕
		// settings.setLoadWithOverviewMode(true);//打开页面时， 自适应屏幕
		settings.setSupportZoom(false);// 用于设置webview放大
		settings.setBuiltInZoomControls(false);
		webView.setBackgroundResource(R.color.white);
		// 添加js交互接口类，并起别名 imagelistner
		webView.addJavascriptInterface(new JavascriptInterface(
				getApplicationContext()), "imagelistner");
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.setWebViewClient(new MyWebViewClient());
		new MyAsnycTask().execute(detaisUserNewsBean.getContent(),
				detaisUserNewsBean.getTitle(), detaisUserNewsBean.getName()
						+ " " + detaisUserNewsBean.getPublic_time());
	}

	private class MyAsnycTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... urls) {
			String data = NewsDetailsService.getNewsDetails(urls[0], urls[1],
					urls[2]);
			return data;
		}

		@Override
		protected void onPostExecute(String data) {
			webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
		}
	}

	// 注入js函数监听
	private void addImageClickListner() {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
		webView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\");"
				+ "var imgurl=''; " + "for(var i=0;i<objs.length;i++)  " + "{"
				+ "imgurl+=objs[i].src+',';"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.openImage(imgurl);  "
				+ "    }  " + "}" + "})()");
	}

	// js通信接口
	public class JavascriptInterface {

		private Context context;

		public JavascriptInterface(Context context) {
			this.context = context;
		}

		public void openImage(String img) {

			//
			String[] imgs = img.split(",");
			ArrayList<String> imgsUrl = new ArrayList<String>();
			for (String s : imgs) {
				imgsUrl.add(s);
				Log.i("图片的URL>>>>>>>>>>>>>>>>>>>>>>>", s);
			}
			Intent intent = new Intent();
			intent.putStringArrayListExtra("infos", imgsUrl);
			intent.setClass(context, ImageShowActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

	// 监听
	@SuppressLint("SetJavaScriptEnabled")
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageFinished(view, url);
			// html加载完成之后，添加监听图片的点击js函数
			addImageClickListner();
			// progressBar.setVisibility(View.GONE);
			// webView.setVisibility(View.VISIBLE);
			 if(i==0){
				 i++;
			 }
			 if(i==1){
				 
				 
				 handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						init();
						initData();
					}
				});
			 }
				
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// progressBar.setVisibility(View.GONE);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}

	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			// TODO Auto-generated method stub
			if (newProgress != 100) {
				// progressBar.setProgress(newProgress);
			}
			if (newProgress == 100) {
			}
			super.onProgressChanged(view, newProgress);
		}
	}

	public class SendPublicCommentAstask extends
			AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", arg0[0]);
			map.put("comment_time", arg0[1]);
			map.put("comment_content", arg0[2]);
			map.put("news_id", arg0[3]);
			String url = HttpUtil.BASE_URL + "savePublicCommentAction.action";
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(HttpUtil.postRequest(url, map));

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
			super.onPostExecute(result);
			try {
				if (result.getString("savePublicComment").equals("true")) {
					inputMsg.setText("");
					Utils.hideInput(getCurrentFocus());
					DialogUtil.showToast(DetailsWebActivity.this, "评论成功!");
					new GetPublicCommentAtast().execute(String
							.valueOf(detaisUserNewsBean.getId()));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 服务器传递数据 1，传递map对象 2、map对象还有两个map键值，一个是评论，另一个是评论的回复 3、为什么评论的回复要用map呢？
	 * 如果用list，那么我们就不方便同时将两组数据存入第一个map对象 4、评论的回复map里面含有list即回复数 map---map---评论
	 * ---map---回复(list)--map(每一条)
	 */
	public class GetPublicCommentAtast extends
			AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			map.put("news_id", arg0[0]);
			String url = HttpUtil.BASE_URL + "getPublicCommentAction.action";
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(HttpUtil.postRequest(url, map));
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
			super.onPostExecute(result);
			JSONArray jsonArray;
			try {
				// 获得评论的List
				jsonArray = result.getJSONArray("comments");
				if (jsonArray != null) {
					detaisUserNewsBeans.clear();
					for (int i = 0; i < jsonArray.length(); i++) {
						// 获得list中的Map（map有两个键值）
						JSONObject jsonObject = jsonArray.optJSONObject(i);
						// 获取评论的键值，即评论的map对象
						JSONObject jsonObject_comments = jsonObject
								.getJSONObject("comment");
						DetaisUserNewsBean detaisUserNewsBean = new DetaisUserNewsBean();
						detaisUserNewsBean.setId(Integer
								.valueOf(jsonObject_comments.getString("id")));
						detaisUserNewsBean.setContent(jsonObject_comments
								.getString("comment_content"));
						detaisUserNewsBean.setName(jsonObject_comments
								.getString("username"));
						detaisUserNewsBean.setPublic_time(jsonObject_comments
								.getString("comment_time"));
						detaisUserNewsBean.setPicture(jsonObject_comments
								.getString("picture"));
						if (jsonObject_comments.getString("reply_counts")
								.equals("true")) {// 效率太低
							// 获取回复的键值，即回复的map对象
							JSONObject jsonObject_reply = jsonObject
									.getJSONObject("reply_commets");
							// 获得map对象中的List对象
							JSONArray jsonArray_replyArray = jsonObject_reply
									.getJSONArray("reply");
							ArrayList<ReplyCommentBean> replyCommentBeans = new ArrayList<ReplyCommentBean>();
							// 获得每一条ID的对应评论(List里面就是map对象了)
							for (int j = 0; j < jsonArray_replyArray.length(); j++) {
								// 获得Map对象
								JSONObject jsonObject_reply_each = jsonArray_replyArray
										.optJSONObject(j);
								ReplyCommentBean replyCommentBean = new ReplyCommentBean();
								replyCommentBean
										.setContent(jsonObject_reply_each
												.getString("content"));
								System.out.println("zhegeweing"
										+ jsonObject_reply_each
												.getString("content"));
								System.out.println(jsonObject_reply_each
										.getString("content"));
								replyCommentBean
										.setReply_usernaem(jsonObject_reply_each
												.getString("reply_username"));
								replyCommentBean
										.setReplyTime(jsonObject_reply_each
												.getString("reply_time"));
								replyCommentBean
										.setUsername(jsonObject_reply_each
												.getString("username"));
								replyCommentBeans.add(replyCommentBean);
							}
							detaisUserNewsBean
									.setReplyCommentBeans(replyCommentBeans);
						} else {
							detaisUserNewsBean.setReplyCommentBeans(null);
						}
						detaisUserNewsBeans.add(detaisUserNewsBean);
					}
					ArrayList<DetaisUserNewsBean> detaisUserNewsBeanstemp = new ArrayList<DetaisUserNewsBean>();
					for (int i = 0; i < detaisUserNewsBeans.size(); i++) {
						detaisUserNewsBeanstemp.add(detaisUserNewsBeans.get(i));
					}
					detailsUserNewsAdapter = new DetailsUserNewsAdapter(
							DetailsWebActivity.this, detaisUserNewsBeanstemp,detaisUserNewsBean.getName());
					// 保持位置不变
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mListView.setSelection(lastItem);
						}
					});
					mListView.setAdapter(detailsUserNewsAdapter);
					uploadProgressDialog.cancel();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.web_details_news_sendBtu:
			String comment = inputMsg.getText().toString();
			if (comment != null && !comment.equals("")) {
				String news_id = String.valueOf(detaisUserNewsBean.getId());
				String comment_conent = comment;
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String comment_time = simpleDateFormat.format(new Date());
				String username = sharedPreferences.getString(
						Constants.SP_USER_NAME, "");
				new SendPublicCommentAstask().execute(username, comment_time,
						comment_conent, news_id);
			}
			break;

		default:
			break;
		}
	}
}
