package com.wtu.university.slidingMenu.bean;

import java.io.Serializable;

public class ReplyCommentBean implements Serializable{
	private String username;
	private String content;
	private String replyTime;
	private String reply_usernaem;
	public String getReply_usernaem() {
		return reply_usernaem;
	}
	public void setReply_usernaem(String reply_usernaem) {
		this.reply_usernaem = reply_usernaem;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
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
	
}
