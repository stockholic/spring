package kr.zchat.web.admin.mail.vo;

public class EmailFolder {

	private String name; 
	private int messageCount; 
	private int unreadMessageCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	public int getUnreadMessageCount() {
		return unreadMessageCount;
	}
	public void setUnreadMessageCount(int unreadMessageCount) {
		this.unreadMessageCount = unreadMessageCount;
	} 
 	
}
