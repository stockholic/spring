package kr.zchat.web.admin.mail.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

public class EmailMessage {

	private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy/MM/dd HH:mm"); 
	private static final FastDateFormat TIME_FORMAT = FastDateFormat.getInstance("a h:mm"); 
    
    // Instance Members 
    private final int messageNumber; 
    private final String uid; 
    private final String sender; 
    private final String subject;  // Passed in separately AntiSamy treatment  
    private final Date sentDate; 
    private boolean unread; 
    private final boolean answered; 
    private final boolean deleted; 
    private boolean flaged; 
    private boolean multipart; 
    private String contentType; 
    private final EmailMessageContent content;  // Optional;  passed in separately AntiSamy treatment 
    private final String toRecipients; 
    private final String ccRecipients; 
    private final String bccRecipients; 
 
 
 /*
  * Public API. 
  */ 
 
    /**
     * Creates a new {@link EmailMessage} based on the specified  
     * <code>Message</code> and {@link EmailMessageContent}. 
     */ 
    public EmailMessage(int messageNumber, String uid, String sender, String subject,Date sentDate, 
    		boolean unread, boolean answered, boolean deleted, boolean flaged, boolean multipart, 
    		String contentType, EmailMessageContent content,String toRecipients, String ccRecipients, String bccRecipients) { 
    	
     // Instance Members. 
        this.messageNumber = messageNumber; 
        this.uid = uid;  // NB:  may be null 
        this.sender = sender; 
        this.subject = subject; 
        this.sentDate = sentDate;  // NB:  possibly null in some circumstances 
        this.unread = unread; 
        this.answered = answered; 
        this.deleted = deleted; 
        this.flaged = flaged;
        this.multipart = multipart; 
        this.contentType = contentType;  // NB:  may be null 
        this.content = content; 
        this.toRecipients = toRecipients; 
        this.ccRecipients = ccRecipients; 
        this.bccRecipients = bccRecipients; 
    } 
 
    public EmailMessage(int messageNumber, String uid, String sender, String subject, Date sentDate, 
    					boolean unread, boolean answered, boolean deleted, boolean flaged, boolean multipart,
    					String contentType, EmailMessageContent content) { 
        this(messageNumber,uid,sender,subject,sentDate,unread,answered,deleted,flaged,multipart,contentType,content,null,null,null); 
    } 
 
    public int getMessageNumber() { 
        return messageNumber; 
    } 
 
	 /**
	  * Returns the UID of the message as set by the Folder or <code>null</code>  
	  * if the Folder does not implement UIDFolder.  
	  *  
	  * @return The UID provided by the Folder for this message or null 
	  */ 
	 public String getUid() { 
	     return uid; 
	 } 
	 
	    /**
     * Returns the UID of the message if it is present, else the message number. 
     * 
     * @return UID if present else message number 
     */ 
    public String getMessageId() { 
        return StringUtils.isNotBlank(uid) ? uid : new Integer(messageNumber).toString(); 
    } 
  
	/**
	  * Returns the date the email message was sent or <code>null</code> if the  
	  * server did not provide one. 
	  *  
	  * @return Date the email message was sent or <code>null</code> 
      * @throws MessagingException  
	  */ 
	 public Date getSentDate() { 
		 return sentDate != null ? new Date(sentDate.getTime()) : null; 
	 } 
	 
	 public String getSentDateString() { 
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		 String returnDate = sentDate != null ? DATE_FORMAT.format(sentDate) : "";
		 
		 String dateA = simpleDateFormat.format(new Date());
		 String dateB = simpleDateFormat.format(sentDate);

		 if(dateA.compareTo(dateB) == 0){		//날짜 비교
			 return TIME_FORMAT.format(sentDate);
		 }
	     return returnDate;
	 } 
	 
	 /**
	  * Returns the sender of this email message. 
	  * 
	  * @return The sender of the email message. 
	  */ 
	 public String getSender() { 
	     return sender; 
	 } 
 
    public String getSenderName() { 
        String senderName = getSender(); 
        if (getSender().contains("<")) { 
            senderName = getSender().split("\\s*<")[0]; 
        } else if (getSender().contains("<")) { 
            senderName = getSender().split("\\s*<")[0]; 
        } 
        return senderName; 
    } 
 
    /**
	 * Returns the email message subject. 
	 * 
	 * @return The email message subject. 
	 */ 
    public String getSubject() { 
    	return this.subject; 
    } 
 
    public boolean isUnread() { 
        return unread; 
    } 
     
    public void setUnread(boolean unread) { 
        this.unread = unread; 
    } 
 
    public boolean isAnswered() { 
        return answered; 
    } 
 
    public boolean isDeleted() { 
        return deleted; 
    } 

    public boolean isFlaged() { 
    	return flaged; 
    } 
     
    public boolean isMultipart() { 
        return multipart; 
    } 
     
    /**
     *  
     * @return The content type (e.g. "text/plain") of the message body or  
     * <code>null</code> if the content cannot be read 
     */ 
    public String getContentType() { 
        return contentType; 
    } 
 
    public EmailMessageContent getContent() { 
        return content; 
    } 
 
    public String getToRecipients() { 
    	return toRecipients; 
    } 
	 
	public String getCcRecipients() { 
		return ccRecipients; 
	} 
	 
	public String getBccRecipients() { 
		return bccRecipients; 
	} 
}
