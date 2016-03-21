package com.colleage.assistant.po.domain;

import java.sql.Blob;
import java.util.Date;


public class NewsHot {
	private int id;
	private String title;
	private String colleage ;
	private String publicTime;
	private Blob rightpicture ;
	private Blob picture1 ;
	private Blob picture2 ;
	private Blob picture3 ;
	private int commentCounts;
	private int newsKindId;
	private int public_user_kind;
	private String content;
	private String news_web;
	public String getNews_web() {
		return news_web;
	}
	public void setNews_web(String news_web) {
		this.news_web = news_web;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPublic_user_kind() {
		return public_user_kind;
	}
	public void setPublic_user_kind(int public_user_kind) {
		this.public_user_kind = public_user_kind;
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
	public String getColleage() {
		return colleage;
	}
	public void setColleage(String colleage) {
		this.colleage = colleage;
	}
	public String getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(String publicTime) {
		this.publicTime = publicTime;
	}
	public Blob getRightpicture() {
		return rightpicture;
	}
	public void setRightpicture(Blob rightpicture) {
		this.rightpicture = rightpicture;
	}
	public Blob getPicture1() {
		return picture1;
	}
	public void setPicture1(Blob picture1) {
		this.picture1 = picture1;
	}
	public Blob getPicture2() {
		return picture2;
	}
	public void setPicture2(Blob picture2) {
		this.picture2 = picture2;
	}
	public Blob getPicture3() {
		return picture3;
	}
	public void setPicture3(Blob picture3) {
		this.picture3 = picture3;
	}
	public int getCommentCounts() {
		return commentCounts;
	}
	public void setCommentCounts(int commentCounts) {
		this.commentCounts = commentCounts;
	}
	public int getNewsKindId() {
		return newsKindId;
	}
	public void setNewsKindId(int newsKindId) {
		this.newsKindId = newsKindId;
	}
	
}
