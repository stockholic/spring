package com.taxholic.core.socket;

import org.springframework.web.socket.WebSocketSession;

public class User {
	
	private Integer channelId;
	private String channelNm;
	private String userNick;
	private WebSocketSession session;
	
	
	public User(Integer channelId, String channelNm,  String userNick,WebSocketSession session) {
		this.channelId = channelId;
		this.channelNm = channelNm;
		this.userNick = userNick;
		this.setSession(session);
	}


	public Integer getChannelId() {
		return channelId;
	}


	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}


	public String getChannelNm() {
		return channelNm;
	}


	public void setChannelNm(String channelNm) {
		this.channelNm = channelNm;
	}


	public String getUserNick() {
		return userNick;
	}


	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}


	public WebSocketSession getSession() {
		return session;
	}


	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	
	
}
