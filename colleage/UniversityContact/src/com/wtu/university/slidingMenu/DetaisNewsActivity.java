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
import com.example.universityconnection.R.menu;
import com.wtu.university.base.BaseActivity;
import com.wtu.university.common.Constants;
import com.wtu.university.common.DialogUtil;
import com.wtu.university.common.HttpUtil;
import com.wtu.university.common.Utils;
import com.wtu.university.slidingMenu.adapter.DetailsUserNewsAdapter;
import com.wtu.university.slidingMenu.bean.DetaisUserNewsBean;
import com.wtu.university.slidingMenu.bean.ReplyCommentBean;
import com.wtu.university.view.CustomProgressDialog;
import com.wtu.university.view.RefreshSupportListView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetaisNewsActivity extends BaseActivity implements OnClickListener{
	private RefreshSupportListView refreshSupportListView;
	private ArrayList<DetaisUserNewsBean> detaisUserNewsBeans;
	private CustomProgressDialog registerProgressDialog;
	private DetailsUserNewsAdapter detailsUserNewsAdapter;
	private EditText inputMsg;
	DetaisUserNewsBean detaisUserNewsBean;
	private TextView back;
	private int lastItem;
	private SharedPreferences sharedPreferences;
	private Button sendMsg;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detais_news);
		((TextView)findViewById(R.id.title)).setText("高校帮");
		sharedPreferences=getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		detaisUserNewsBeans=new ArrayList<DetaisUserNewsBean>();
		setNeedBackGesture(true);//设置需要手势监听
		back=(TextView)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		init();
		initData();
		
		
		
	}
	/**初始化数据*/
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent=this.getIntent();
	detaisUserNewsBean=(DetaisUserNewsBean)intent.getSerializableExtra("publicUser");
		detaisUserNewsBeans.add(detaisUserNewsBean);
		registerProgressDialog = new CustomProgressDialog(
				DetaisNewsActivity.this, "正在偷看中...", R.anim.frame_meituan);
		registerProgressDialog.show();
		
		new GetPublicCommentAtast().execute(String.valueOf(detaisUserNewsBean.getId()));
		
	}
	/**初始化组件*/
	private void init() {
		// TODO Auto-generated method stub
		refreshSupportListView=(RefreshSupportListView)findViewById(R.id.details_ListView);
		refreshSupportListView.setDivider(this.getResources().getDrawable(R.drawable.cell_seperator));
		
		refreshSupportListView.setOnScrollListener(new OnScrollListener() {   
			  
		    /**  
		     * 滚动状态改变时调用  
		     */  
		    @Override  
		    public void onScrollStateChanged(AbsListView view, int scrollState) {   
		        // 不滚动时保存当前滚动到的位置  
		        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {   
		                lastItem = refreshSupportListView.getFirstVisiblePosition();   
		        }   
		    }   
		    /**  
		     * 滚动时调用  
		     */  
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {   
		    }   
		});  
		inputMsg=(EditText)findViewById(R.id.details_answer_publicPeople);
		inputMsg.setOnClickListener(this);
		sendMsg=(Button)findViewById(R.id.details_news_sendBtu);
		sendMsg.setOnClickListener(this);
		sendMsg.setVisibility(View.VISIBLE);
	
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.details_news_sendBtu:
			System.out.println("没有反应？");
			String comment=inputMsg.getText().toString();
			if(comment!=null&&!comment.equals("")){
				String news_id=String.valueOf(detaisUserNewsBean.getId());
				String comment_conent=comment;
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String comment_time=simpleDateFormat.format(new Date());
				String username=sharedPreferences.getString(Constants.SP_USER_NAME, "");
				new SendPublicCommentAstask().execute(username,comment_time,comment_conent,news_id);
			}
			break;

		default:
			break;
		}
	}
	
	public class SendPublicCommentAstask extends AsyncTask<String, String, JSONObject>{

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			map.put("username",arg0[0]);
			map.put("comment_time",arg0[1]);
			map.put("comment_content",arg0[2]);
			map.put("news_id",arg0[3]);
			String url=HttpUtil.BASE_URL+"savePublicCommentAction.action";
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
			super.onPostExecute(result);
			try {
				if(result.getString("savePublicComment").equals("true")){
					inputMsg.setText("");
					Utils.hideInput(getCurrentFocus());
					DialogUtil.showToast(DetaisNewsActivity.this, "评论成功!");
					new GetPublicCommentAtast().execute(String.valueOf(detaisUserNewsBean.getId()));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**服务器传递数据
	 * 1，传递map对象
	 * 2、map对象还有两个map键值，一个是评论，另一个是评论的回复
	 * 3、为什么评论的回复要用map呢？
	 * 		如果用list，那么我们就不方便同时将两组数据存入第一个map对象
	 * 4、评论的回复map里面含有list即回复数
	 * map---map---评论
	 * 	  ---map---回复(list)--map(每一条)
	 */
	public class GetPublicCommentAtast extends AsyncTask<String, String, JSONObject>{

		@Override
		protected JSONObject doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Map<String, String> map=new HashMap<String, String>();
			map.put("news_id",arg0[0]);
			String url=HttpUtil.BASE_URL+"getPublicCommentAction.action";
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
			super.onPostExecute(result);
			JSONArray jsonArray;
			try {
				//获得评论的List
				jsonArray = result.getJSONArray("comments");
				if(jsonArray!=null){
					detaisUserNewsBeans.clear();
					detaisUserNewsBeans.add(detaisUserNewsBean);
				for(int i=0;i<jsonArray.length();i++){
					//获得list中的Map（map有两个键值）
					JSONObject jsonObject=jsonArray.optJSONObject(i);
					//获取评论的键值，即评论的map对象
					JSONObject jsonObject_comments=jsonObject.getJSONObject("comment");
					DetaisUserNewsBean detaisUserNewsBean=new DetaisUserNewsBean();
					detaisUserNewsBean.setId(Integer.valueOf(jsonObject_comments.getString("id")));
					detaisUserNewsBean.setContent(jsonObject_comments.getString("comment_content"));
					detaisUserNewsBean.setName(jsonObject_comments.getString("username"));
					detaisUserNewsBean.setPublic_time(jsonObject_comments.getString("comment_time"));
					detaisUserNewsBean.setPicture(jsonObject_comments.getString("picture"));
					if(jsonObject_comments.getString("reply_counts").equals("true")){//效率太低
						//获取回复的键值，即回复的map对象
						JSONObject jsonObject_reply=jsonObject.getJSONObject("reply_commets");
						//获得map对象中的List对象
						JSONArray jsonArray_replyArray=jsonObject_reply.getJSONArray("reply");
						ArrayList<ReplyCommentBean> replyCommentBeans=new ArrayList<ReplyCommentBean>();
						//获得每一条ID的对应评论(List里面就是map对象了)
						for(int j=0;j<jsonArray_replyArray.length();j++){
							//获得Map对象
							JSONObject jsonObject_reply_each=jsonArray_replyArray.optJSONObject(j);
							ReplyCommentBean replyCommentBean=new ReplyCommentBean();
							replyCommentBean.setContent(jsonObject_reply_each.getString("content"));
							System.out.println("zhegeweing"+jsonObject_reply_each.getString("content"));
							System.out.println(jsonObject_reply_each.getString("content"));
							replyCommentBean.setReply_usernaem(jsonObject_reply_each.getString("reply_username"));
							replyCommentBean.setReplyTime(jsonObject_reply_each.getString("reply_time"));
							replyCommentBean.setUsername(jsonObject_reply_each.getString("username"));
							replyCommentBeans.add(replyCommentBean);
						}
						detaisUserNewsBean.setReplyCommentBeans(replyCommentBeans);
					}
					else {
						detaisUserNewsBean.setReplyCommentBeans(null);
					}
					detaisUserNewsBeans.add(detaisUserNewsBean);
				}
				ArrayList<DetaisUserNewsBean> detaisUserNewsBeanstemp=new ArrayList<DetaisUserNewsBean>();
				for(int i=0;i<detaisUserNewsBeans.size();i++){
					detaisUserNewsBeanstemp.add(detaisUserNewsBeans.get(i));
				}
				detailsUserNewsAdapter=new DetailsUserNewsAdapter(DetaisNewsActivity.this,detaisUserNewsBeanstemp);
				//保持位置不变
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("位置"+lastItem);
						refreshSupportListView.setSelection(lastItem);
					}
				});
				refreshSupportListView.setAdapter(detailsUserNewsAdapter);
				registerProgressDialog.cancel();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
