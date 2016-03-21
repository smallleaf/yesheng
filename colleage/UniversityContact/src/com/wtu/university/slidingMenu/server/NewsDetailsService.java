package com.wtu.university.slidingMenu.server;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.text.TextUtils;
import android.util.Log;

public class NewsDetailsService {

	public static String getNewsDetails(String url, String news_title,
			String news_date) {
		ArrayList<String> imgArrayList=new ArrayList<String>();
		Document document = null;
		
		//web浏览器系那是标题
		String data = "<body>" +
				"<center><h2 style='font-size:16px;'>" + news_title + "</h2></center>";
		data = data + "<p align='left' style='margin-left:10px'>" 
				+ "<span style='font-size:10px;'>" 
				+ news_date
				+ "</span>" 
				+ "</p>";
		data = data + "<hr size='1' />";
		try {
			document=Jsoup.connect(url).timeout(9000).get();
			Element element=document.getElementsByClass("newtext").get(0);
			Log.v("titlefsdddf","内容"+document.getElementsByTag("head").select("base").attr("href"));
			String head=document.getElementsByTag("head").select("base").attr("href");
			for(int j=0;j<element.children().size();j++){
				Element cElement=element.child(j);
				if(cElement.select("img").size()>0){
					Elements imgs=cElement.getElementsByTag("img");
					for(Element img:imgs){
						if(!img.attr("src").equals("")){
							imgArrayList.add(img.attr("src"));
						}
					}
				}
			}
			data+=element.toString();
			data = data + "</body>";
			for(int i=0;i<imgArrayList.size();i++){
				data=data.replace(imgArrayList.get(i), head.substring(0, head.length()-1)+imgArrayList.get(i));
			}
//			Log.v("内容","内容"+element.text());
//			Log.v("title","内容"+document.getElementsByClass("details").get(0).select("h1").text());
//			
//			
//			Log.v("titlefsdddf","内容"+document.getElementsByTag("head").select("base").attr("href"));
//			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			document = Jsoup.connect(url).timeout(9000).get();
//			Element element = null;
//			if (TextUtils.isEmpty(url)) {
//				data = "";
//				element = document.getElementById("memberArea");
//			} else {
//				element = document.getElementById("artibody");
//			}
//			if (element != null) {
//				data = data + element.toString();
//			}
			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return data;
	}
}
