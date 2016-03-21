package com.colleage.assistant.po.domain;

public class PublicComment {
	private int id;
	private String username;
	private String comment_time;
	private String comment_content;
	private int new_id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getComment_time() {
		return comment_time;
	}
	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public int getNew_id() {
		return new_id;
	}
	public void setNew_id(int new_id) {
		this.new_id = new_id;
	}
	
	public PublicComment(){
		
	}
	public PublicComment(int id, String username, String comment_time,
			String comment_content, int new_id) {
		super();
		this.id = id;
		this.username = username;
		this.comment_time = comment_time;
		this.comment_content = comment_content;
		this.new_id = new_id;
	}
	public PublicComment(String username, String comment_time,
			String comment_content, int new_id) {
		super();
		this.username = username;
		this.comment_time = comment_time;
		this.comment_content = comment_content;
		this.new_id = new_id;
	}
	@Override
	public String toString() {
		return "PublicComment [username=" + username + ", comment_time="
				+ comment_time + ", comment_content=" + comment_content
				+ ", new_id=" + new_id + "]";
	}
	
	
}
