package com.wtu.university.slidingMenu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;


/**
 * 
 * 用户发表的相信新闻带评论
 *
 */
public class DetaisUserNewsBean implements Serializable{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int id;
		private String name;
		private String title;
		private String content;
		private String public_time;
		private String picture;
		private String picture1;
		private String picture2;
		private String picture3;
		private String news_web;
		private int mutilPicture;//0--没有，1一张，3--3张
		/**每一个评论的回复*/
		private ArrayList<ReplyCommentBean> replyCommentBeans;
		
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getNews_web() {
			return news_web;
		}

		public void setNews_web(String news_web) {
			this.news_web = news_web;
		}

		public ArrayList<ReplyCommentBean> getReplyCommentBeans() {
			return replyCommentBeans;
		}

		public void setReplyCommentBeans(ArrayList<ReplyCommentBean> replyCommentBeans) {
			this.replyCommentBeans = replyCommentBeans;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getMutilPicture() {
			return mutilPicture;
		}

		public void setMutilPicture(int mutilPicture) {
			this.mutilPicture = mutilPicture;
		}

		public String getPicture1() {
			return picture1;
		}

		public void setPicture1(String picture1) {
			this.picture1 = picture1;
		}

		public String getPicture2() {
			return picture2;
		}

		public void setPicture2(String picture2) {
			this.picture2 = picture2;
		}

		public String getPicture3() {
			return picture3;
		}

		public void setPicture3(String picture3) {
			this.picture3 = picture3;
		}

		/**用户评论*/
		private ArrayList<DetaisUserNewsBean> commentArrayList;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getPublic_time() {
			return public_time;
		}

		public void setPublic_time(String public_time) {
			this.public_time = public_time;
		}

		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}

		public ArrayList<DetaisUserNewsBean> getCommentArrayList() {
			return commentArrayList;
		}

		public void setCommentArrayList(ArrayList<DetaisUserNewsBean> commentArrayList) {
			this.commentArrayList = commentArrayList;
		}
		
		
}
