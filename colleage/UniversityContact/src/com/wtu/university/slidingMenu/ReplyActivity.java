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
import com.example.universityconnection.R.id;
import com.example.universityconnection.R.layout;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.DetaisNewsActivity.GetPublicCommentAtast;
import com.wtu.university.slidingMenu.adapter.ReplyCommentAdater;
import com.wtu.university.slidingMenu.bean.DetaisUserNewsBean;
import com.wtu.university.slidingMenu.bean.ReplyCommentBean;
import com.wtu.university.tools.ImageUtils;
import com.wtu.university.view.CustomProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ReplyActivity extends BaseActivity {
	private TextView usernameTextView;
	private TextView publicTimeTextView;
	private TextView contenTextView;
	private ImageView imageView;
	private EditText reply;
	private Button send;
	private TextView back;
	private ListView mListView;
	DetaisUserNewsBean detaisUserNewsBean;
	private ReplyCommentAdater replyCommentAdater;
	private SharedPreferences sharedPreferences;
	private String answer_man;
	private ArrayList<ReplyCommentBean> replyCommentBeans = new ArrayList<ReplyCommentBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reply);
		
		setNeedBackGesture(true);//设置需要手势监听
		((TextView) findViewById(R.id.title)).setText("");
		usernameTextView = (TextView) findViewById(R.id.reply_details_user_news_list_name);
		publicTimeTextView = (TextView) findViewById(R.id.reply_details_user_news_list_time);
		contenTextView = (TextView) findViewById(R.id.reply_details_user_news_list_content);
		imageView = (ImageView) findViewById(R.id.reply_details_user_news_list_picture);
		reply = (EditText) findViewById(R.id.reply_details_answer_publicPeople);
		send = (Button) findViewById(R.id.reply_details_news_sendBtu);
		mListView = (ListView) findViewById(R.id.reply_details_user_news_list_comments);
		send.setVisibility(View.VISIBLE);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		answer_man=bundle.getString("answer_man");
		detaisUserNewsBean = (DetaisUserNewsBean) intent
				.getSerializableExtra("detaisUserNewsBean");
		sharedPreferences = getSharedPreferences(Constants.SP_NAME,
				MODE_PRIVATE);
		((TextView) findViewById(R.id.back)).setText("第"
				+ bundle.getInt("position") + "楼");
		back = (TextView) findViewById(R.id.back);
		replyCommentBeans=detaisUserNewsBean.getReplyCommentBeans();
		if(replyCommentBeans==null){
			replyCommentBeans=new ArrayList<ReplyCommentBean>();
		}
		replyCommentAdater = new ReplyCommentAdater(ReplyActivity.this,
				replyCommentBeans);
		mListView.setAdapter(replyCommentAdater);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ReplyCommentBean replyCommentBean = new ReplyCommentBean();
				replyCommentBean.setUsername(sharedPreferences.getString(
						Constants.SP_USER_NAME, ""));
				replyCommentBean.setReply_usernaem(answer_man);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String comment_time = simpleDateFormat.format(new Date());
				replyCommentBean.setReplyTime(comment_time);
				replyCommentBean.setContent(reply.getText().toString());
				replyCommentBeans.add(replyCommentBean);
				replyCommentAdater = new ReplyCommentAdater(ReplyActivity.this,
						replyCommentBeans);
				mListView.setAdapter(replyCommentAdater);

				new ReplyCommentAstask().execute(sharedPreferences.getString(
						Constants.SP_USER_NAME, ""), answer_man, reply.getText().toString(), String
						.valueOf(detaisUserNewsBean.getId()), comment_time);
				reply.setText("");
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				answer_man=replyCommentBeans.get(arg2).getUsername();
				reply.setHint("回复  " + answer_man + ": ");
				Utils.OpenInput(getCurrentFocus());
			}
		});
		usernameTextView.setText(detaisUserNewsBean.getName());
		publicTimeTextView.setText(detaisUserNewsBean.getPublic_time());
		contenTextView.setText(detaisUserNewsBean.getContent());
		imageView.setImageBitmap(ImageUtils.getRoundedCornerBitmap(
				Utils.stringtoBitmap(detaisUserNewsBean.getPicture()), 20f));
		reply.setHint("回复  " + answer_man + ": ");
		Utils.OpenInput(getCurrentFocus());
	}

	public class ReplyCommentAstask extends
			AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", arg0[0]);
			map.put("reply_username", arg0[1]);
			map.put("content", arg0[2]);
			map.put("comment_id", arg0[3]);
			map.put("reply_time", arg0[4]);
			String url = HttpUtil.BASE_URL + "saveReplyCommentAction.action";
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
			Utils.hideInput(getCurrentFocus());
			return jsonObject;
		}
	}
}
