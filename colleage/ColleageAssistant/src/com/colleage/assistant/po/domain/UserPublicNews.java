package com.colleage.assistant.po.domain;

import java.sql.Blob;

public class UserPublicNews {
	private int id;
	private String title;
	private String content;
	private int news_kind_id;
	private Blob picture1;
	private Blob picture2;
	private Blob picture3;
	private String public_time;
	public String getPublic_time() {
		return public_time;
	}
	public void setPublic_time(String public_time) {
		this.public_time = public_time;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getNews_kind_id() {
		return news_kind_id;
	}
	public void setNews_kind_id(int news_kind_id) {
		this.news_kind_id = news_kind_id;
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
}
