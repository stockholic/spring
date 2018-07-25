package kr.zchat.web.admin.mail.vo;

public final class MailStoreConfiguration {
	
    private String protocol; 
    private String host; 
    private int port; 
    private String user;
    private String password;
    private String inboxFolderName; 
    private int timeout; 
    private int connectionTimeout;
    private boolean markMessagesAsRead;

    
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getInboxFolderName() {
		return inboxFolderName;
	}
	public void setInboxFolderName(String inboxFolderName) {
		this.inboxFolderName = inboxFolderName;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean getMarkMessagesAsRead() {
		return markMessagesAsRead;
	}
	public void setMarkMessagesAsRead(boolean markMessagesAsRead) {
		this.markMessagesAsRead = markMessagesAsRead;
	} 
    
}
