package com.wtu.university.slidingMenu.bean;

import android.graphics.Bitmap;

public class NewsEntity {
	private int id;
	private String title;
	private String pulishTime;
	private int commentCounts;
	private String colleage;
	private String userPicture;
	private String rightPicture;
	private String picture1;
	private String picture2;
	private String picture3;
	private String content;
	private Bitmap picture1Bitmap;
	private Bitmap picture2Bitmap;
	private Bitmap picture3Bitmap;
	private String news_web;
	private int public_user_kind;//0代表系统推送
	
	
	public String getNews_web() {
		return news_web;
	}
	public void setNews_web(String news_web) {
		this.news_web = news_web;
	}
	public String getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}
	public int getPublic_user_kind() {
		return public_user_kind;
	}
	public void setPublic_user_kind(int public_user_kind) {
		this.public_user_kind = public_user_kind;
	}
	public Bitmap getPicture1Bitmap() {
		return picture1Bitmap;
	}
	public void setPicture1Bitmap(Bitmap picture1Bitmap) {
		this.picture1Bitmap = picture1Bitmap;
	}
	public Bitmap getPicture2Bitmap() {
		return picture2Bitmap;
	}
	public void setPicture2Bitmap(Bitmap picture2Bitmap) {
		this.picture2Bitmap = picture2Bitmap;
	}
	public Bitmap getPicture3Bitmap() {
		return picture3Bitmap;
	}
	public void setPicture3Bitmap(Bitmap picture3Bitmap) {
		this.picture3Bitmap = picture3Bitmap;
	}
	public NewsEntity(){}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private int  mutiPicture;//0表示没有图片，1表示一张，3表示3张


	public String getRightPicture() {
		return rightPicture;
	}

	public void setRightPicture(String rightPicture) {
		this.rightPicture = rightPicture;
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

	public int getMutiPicture() {
		return mutiPicture;
	}

	/**
	 * 
	 * @param true表示存的是三张图片
	 */
	public void setMutiPicture(int mutiPicture) {
		this.mutiPicture = mutiPicture;
	}

	public String getColleage() {
		return colleage;
	}

	public void setColleage(String colleage) {
		this.colleage = colleage;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPulishTime() {
		return pulishTime;
	}

	public void setPulishTime(String pulishTime) {
		this.pulishTime = pulishTime;
	}

	public int getCommentCounts() {
		return commentCounts;
	}

	public void setCommentCounts(int commentCounts) {
		this.commentCounts = commentCounts;
	}

	public NewsEntity(String title, String pulishTime, int commentCounts,
			String colleage, String rightPicture, String picture1,
			String picture2, String picture3, int mutiPicture) {
		super();
		this.title = title;
		this.pulishTime = pulishTime;
		this.commentCounts = commentCounts;
		this.colleage = colleage;
		this.rightPicture = rightPicture;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.picture3 = picture3;
		this.mutiPicture = mutiPicture;
	}

}
