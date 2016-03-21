package com.colleage.assistant.po.domain;

public class ReplyComment {
	private int id;
	private String username;
	private String content;
	private String reply_username;
	private int comment_id;
	private String reply_time;
	public String getReply_time() {
		return reply_time;
	}
	public void setReply_time(String reply_time) {
		this.reply_time = reply_time;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReply_username() {
		return reply_username;
	}
	public void setReply_username(String reply_username) {
		this.reply_username = reply_username;
	}
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
}
