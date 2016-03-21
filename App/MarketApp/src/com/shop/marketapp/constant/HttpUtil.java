package com.shop.marketapp.constant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;


public class HttpUtil {
	public static HttpClient httpClient=new DefaultHttpClient();
	public static final String BASE_URL="http://192.168.191.1:8080/app/";
	
	public static String getRequst(String url) throws Exception
	{
		HttpGet get=new HttpGet(url);
		HttpResponse httpResponse=httpClient.execute(get);
		if(httpResponse.getStatusLine().getStatusCode()==200){
			String result=EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}
	
	public synchronized static String  postRequest(String url,Map<String,String> rawParas) throws IOException
	{
		HttpPost httpPost=new HttpPost(url);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		for(String key:rawParas.keySet())
		{
			params.add(new BasicNameValuePair(key, rawParas.get(key)));
		}
		 // 请求超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        // 读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse httpResponse=httpClient.execute(httpPost);
		if(httpResponse.getStatusLine().getStatusCode()==200)
		{
			String result=EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}
}
