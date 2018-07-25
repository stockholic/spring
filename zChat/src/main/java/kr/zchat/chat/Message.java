package kr.zchat.chat;

import java.util.List;


public class Message {
	
	private String cmd;
	private String msg;
	private Integer channelId;
	private String channelNm;
	private String userId;
	private String userNick;
	private String changeNick;
	private String oldNick;
	private String pwd;
	private List<String> userList;
	
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public List<String> getUserList() {
		return userList;
	}
	public void setUserList(List<String> userList) {
		this.userList = userList;
	}
	public String getChangeNick() {
		return changeNick;
	}
	public void setChangeNick(String changeNick) {
		this.changeNick = changeNick;
	}
	public String getOldNick() {
		return oldNick;
	}
	public void setOldNick(String oldNick) {
		this.oldNick = oldNick;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "Message [cmd=" + cmd + ", msg=" + msg + ", channelId=" + channelId + ", channelNm=" + channelNm
				+ ", userId=" + userId + ", userNick=" + userNick + ", changeNick=" + changeNick + ", oldNick="
				+ oldNick + ", pwd=" + pwd + ", userList=" + userList + "]";
	}
	
}
