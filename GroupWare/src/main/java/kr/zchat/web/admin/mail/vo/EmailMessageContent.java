package kr.zchat.web.admin.mail.vo;

import java.util.List;

public class EmailMessageContent {
	
	 	private String content; 
	 	
	 	private boolean isHtml;
	 	
	 	private List<EmailAttachment> attachment;
	 
	    public EmailMessageContent() { 
	    } 
	 
	    public EmailMessageContent(String contentString, boolean isHtml) { 
	        this.content = contentString; 
	        this.isHtml = isHtml; 
	    } 
	 
	    public String getContentString() { 
	        return content; 
	    } 
	 
	    public void setContentString(String content) { 
	        this.content = content; 
	    } 
	 
	    public boolean isHtml() { 
	        return isHtml; 
	    } 
	 
	    public void setHtml(boolean isHtml) { 
	        this.isHtml = isHtml; 
	    }

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public List<EmailAttachment> getAttachment() {
			return attachment;
		}

		public void setAttachment(List<EmailAttachment> attachment) {
			this.attachment = attachment;
		}

		
}
