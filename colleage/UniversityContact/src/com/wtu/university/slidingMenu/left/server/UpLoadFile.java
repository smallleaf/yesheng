package com.wtu.university.slidingMenu.left.server;

import java.io.File;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wtu.university.common.HttpUtil;

import android.util.Log;


public class UpLoadFile implements Runnable{
	private File file;
	public UpLoadFile(File file){
		this.file=file;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		AsyncHttpClient client=new AsyncHttpClient();
		RequestParams params=new RequestParams();
		try {
//			params.put("fileName",URLEncoder.encode(file.getName(), "utf-8"));
			params.put("file", file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url=HttpUtil.BASE_URL+"upLoadAnnouncementAndroid.action";
		client.post(url,params, new AsyncHttpResponseHandler()
		{
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.v("uploadefile", "上传成功");
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				Log.v("uploadefile", "上传失败");
			}
			
		});
	}

}
