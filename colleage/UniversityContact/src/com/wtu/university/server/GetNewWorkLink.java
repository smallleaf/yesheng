//package com.wtu.university.server;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Map;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//
//import android.R.integer;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.wtu.university.bean.NewsEntity;
//import com.wtu.university.common.Constants;
//import com.wtu.university.common.Utils;
//
///**
// * 
// * @author 根据武汉纺织大学网址获得图片，连接，文章连接
// *
// */
//public class GetNewWorkLink{
//	private Document document;
//	private int num;
//	private ArrayList<NewsEntity> newsEntities;
//	private ArrayList<String> urls;
//	public GetNewWorkLink(ArrayList<String> urls,int num,ArrayList<NewsEntity> newsEntities){
//		this.urls=urls;
//		this.num=num;
//		this.newsEntities=newsEntities;
//	}
//	public ArrayList<NewsEntity> getNetWork() {
//		// TODO Auto-generated method stub
//		for(int i=0;i<num;i++){
////			NewsEntity newsEntity=new NewsEntity();
//			ArrayList<String> storeUrlTemp=new ArrayList<String>();
//			try {
//				document= Jsoup.connect(urls.get(i))
//						  .data("query", "Java")
//						  .userAgent("Mozilla")
//						  .cookie("auth", "token")
//						  .timeout(3000)
//						  .post();
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Element element=document.getElementsByClass("newtext").get(0);
//			newsEntity.setContent(element.select("p").text());
//			newsEntity.setPulishTime(Utils.getDate(document.getElementsByClass("date").get(0).text()));
//			newsEntity.setTitle(document.getElementsByClass("details").get(0).select("h1").text());
//			//获得图片的头连接
//			String urlHead=document.getElementsByTag("head").select("base").attr("href");
//			
//			//将获得的图片链接存储起来
//			for(int j=0;j<element.children().size();j++){
//				Element cElement=element.child(j);
//				if(cElement.select("img").size()>0){
//					Elements imgs=cElement.getElementsByTag("img");
//					for(Element img:imgs){
//						if(!img.attr("src").equals("")){
//							String temp=img.attr("src");
//							if(temp.charAt(0)=='h')
//							{
//								storeUrlTemp.add(temp);
//							}
//							else {
//								storeUrlTemp.add(urlHead+temp.substring(1, temp.length()));
//							}
//							
//						}
//					}
//				
//				}
//			}
//			if(storeUrlTemp.size()>=3){
//				newsEntity.setMutiPicture(3);
//				newsEntity.setPicture1(storeUrlTemp.get(0));
//				newsEntity.setPicture2(storeUrlTemp.get(1));
//				newsEntity.setPicture3(storeUrlTemp.get(2));
//			}
//			else if(storeUrlTemp.size()==0){
//				newsEntity.setMutiPicture(0);
//			}
//			else{
//				System.out.println(storeUrlTemp.get(0));
//				newsEntity.setMutiPicture(1);
//				newsEntity.setRightPicture(storeUrlTemp.get(0));
//			}
//			newsEntity.setColleage("武汉纺织大学");
//			newsEntities.add(newsEntity);
//		}
//			return newsEntities;
//	}
//
//}
