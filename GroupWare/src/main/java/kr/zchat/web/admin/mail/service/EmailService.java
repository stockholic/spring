package kr.zchat.web.admin.mail.service;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.SharedByteArrayInputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.james.mime4j.codec.DecoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.zchat.core.Exception.ErrorException;
import kr.zchat.web.admin.mail.vo.Email;
import kr.zchat.web.admin.mail.vo.EmailAttachment;
import kr.zchat.web.admin.mail.vo.EmailFolder;
import kr.zchat.web.admin.mail.vo.EmailMessage;
import kr.zchat.web.admin.mail.vo.EmailMessageContent;

@Service
public class EmailService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String CONTENT_TYPE_ATTACHMENTS_PATTERN = "multipart/mixed;"; 
    private static final String INTERNET_ADDRESS_TYPE = "rfc822"; 
    
    private @Value("${mail.store.protocol}") String protocol;
    private @Value("${mail.host}") String host;
    private @Value("${mail.port}") int port;
    
	/**
	 * 로그인
	 * @param config
	 * @return
	 */
	public Session login(String user, String password) { 
		
        // Initialize connection properties 
        Properties mailProperties = new Properties(); 
        mailProperties.put("mail.store.protocol", protocol); 
        mailProperties.put("mail.host", host); 
        mailProperties.put("mail.port", port); 
        mailProperties.put("mail.imaps.ssl.trust", "*");
        mailProperties.put("mail.smtp.ssl.trust", "*"); 
 
//        String protocolPropertyPrefix = "mail." + config.getProtocol() + "."; 
 
        // Set connection timeout property 
//        int connectionTimeout = config.getConnectionTimeout(); 
//        if (connectionTimeout >= 0) { 
//            mailProperties.put(protocolPropertyPrefix + 
//                    "connectiontimeout", connectionTimeout); 
//        } 
// 
//        // Set timeout property 
//        int timeout = config.getTimeout(); 
//        if (timeout >= 0) { 
//            mailProperties.put(protocolPropertyPrefix + "timeout", timeout); 
//        } 
        
        // Connect/authenticate to the configured store 
       return Session.getInstance(mailProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
	        
	 } 
	
	/**
	 * 접속종료
	 * @param inbox
	 */
	public void close(Folder inbox) { 
		if ( inbox != null ) { 
            try { 
                if(inbox.isOpen()) inbox.close(false);
            } catch ( Exception e ) { 
                logger.warn("Can't close correctly javamail inbox connection"); 
            } 
            try { 
               if(inbox.getStore().isConnected()) inbox.getStore().close(); 
            } catch ( Exception e ) { 
                logger.warn("Can't close correctly javamail store connection"); 
            } 
        } 
	}
	
	 
	/**
	 * 폴더 목록
	 * @param session
	 * @param folderName
	 * @return
	 * @throws ErrorException 
	 * @throws MessagingException
	 */
	public List<EmailFolder> getFolderList(Session session, String folderName) throws ErrorException { 
	 
        Folder inbox = null;
        List<EmailFolder> folderList = new LinkedList<EmailFolder>();
        try { 
            
        	inbox = getInbox(session, folderName);
            inbox.open(Folder.READ_ONLY); 
            
            EmailFolder rootFolder = new EmailFolder();
            rootFolder.setName(inbox.getFullName());
            rootFolder.setUnreadMessageCount(inbox.getUnreadMessageCount());
            rootFolder.setMessageCount(inbox.getMessageCount());
            folderList.add(rootFolder);
            
			for(Folder folder : inbox.list()){
				EmailFolder subFolder = new EmailFolder();
				subFolder.setName(folder.getFullName());
				subFolder.setUnreadMessageCount(folder.getUnreadMessageCount());
				subFolder.setMessageCount(folder.getMessageCount());
				folderList.add(subFolder);
			}
			
			 //이름순 정렬
	        Collections.sort(folderList, new Comparator<EmailFolder>(){
	            public int compare(EmailFolder obj1, EmailFolder obj2){
	            	return obj1.getName().compareToIgnoreCase(obj2.getName());
	            }
	        });
 
        } catch (Exception e) { 
        	throw new ErrorException(e);
        }finally{
			close(inbox);
		}
        
        return folderList; 
 
    } 
	
	/**
	 * 폴더별 정보
	 * @param session
	 * @param folderName
	 * @return
	 * @throws ErrorException 
	 * @throws MessagingException
	 */
	public EmailFolder getFolderInfo(Session session, String folderName) throws ErrorException{ 
	 
        Folder inbox = null;
        EmailFolder folder = new EmailFolder();
        try { 
        	
        	inbox = getInbox(session, folderName);
            inbox.open(Folder.READ_ONLY); 
            
            folder.setName(inbox.getFullName());
            folder.setUnreadMessageCount(inbox.getUnreadMessageCount());
            folder.setMessageCount(inbox.getMessageCount());
            
        } catch (Exception e) { 
        	throw new ErrorException(e);
        }finally{
			close(inbox);
		}
        
        return folder; 
 
    } 
	 
	/**
	 * 폴더별 메일 목록
	 * @param session
	 * @param email
	 * @return
	 * @throws ErrorException
	 */
	public List<EmailMessage> getEmailMessageList(Session session, Email email) throws ErrorException { 
		
	    Folder inbox = null;
	    List<EmailMessage> emails = new LinkedList<EmailMessage>(); 
	    
	    try {
	    	
            inbox = getInbox(session, email.getBox());
            inbox.open(Folder.READ_ONLY); 
	        
            email.setLimit(10);
            email.setTotalPage((int) Math.ceil( email.getTotal() / (double)email.getLimit()));
            int pageStart = (email.getPage() - 1) * email.getLimit();
            
	        int totalMessageCount = inbox.getMessageCount(); 
	        int start = Math.max(1, email.getTotal()  - pageStart - (email.getLimit() - 1)); 
	        int end = Math.max(email.getTotal() - pageStart, 1); 
	 
	        Message[] messages = totalMessageCount != 0 
	                                ? inbox.getMessages(start, end) 
	                                : new Message[0]; 
	                                
	        long startTime = System.currentTimeMillis(); 
        	
	        // Fetch only necessary headers for each message 
	        FetchProfile profile = new FetchProfile(); 
	        profile.add(FetchProfile.Item.ENVELOPE); 
	        profile.add(FetchProfile.Item.FLAGS); 
	        profile.add(FetchProfile.Item.CONTENT_INFO); 
	        if (inbox instanceof UIDFolder) { 
	            profile.add(UIDFolder.FetchProfileItem.UID); 
	        } 
	        inbox.fetch(messages, profile); 
	 
	        if (logger.isDebugEnabled()) { 
	            logger.debug("Time elapsed while fetching message headers; {}ms", System.currentTimeMillis() - startTime); 
	        } 
	 
	        for (Message currentMessage : messages) { 
	            EmailMessage emailMessage = wrapMessage(currentMessage, false, session); 
	            emails.add(emailMessage); 
	        } 
	 
	        Collections.reverse(emails); 
        
        } catch (Exception e) { 
        	throw new ErrorException(e);
        }finally{
			close(inbox);
		}
        
        return emails; 
	 }
	
	/**
	 * 메일 내용
	 * @param session
	 * @param folderName
	 * @param messageId
	 * @return
	 * @throws ErrorException 
	 * @throws MessagingException
	 * @throws IOException
	 */
	public EmailMessage getEmailMessage(Session session,String folderName, String messageId) throws ErrorException { 
		
		Folder inbox = null;
		
		try{
			
			inbox = getInbox(session, folderName);
	        inbox.open(Folder.READ_WRITE); 
	 
			Message message; 
			if (inbox instanceof UIDFolder) { 
				message = ((UIDFolder)inbox).getMessageByUID(Long.parseLong(messageId)); 
			} else { 
				message = inbox.getMessage(Integer.parseInt(messageId)); 
			} 
			message.setFlag(Flag.SEEN, true); 
			EmailMessage emailMessage = wrapMessage(message, true, session); 
			
			//첨부파일 
			if( emailMessage.isMultipart()){
				emailMessage.getContent().setAttachment(getAttachments(message));
			}

			return emailMessage; 
			
		} catch (Exception e) { 
			throw new ErrorException("메세지 조회 실패",e);
		}finally{
			close(inbox);
		}
	 
	} 
	
	/**
	 * 첨부파일 추출
	 * @param msg
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	private List<EmailAttachment> getAttachments(Message msg) throws IOException, MessagingException  { 
		
	    List<EmailAttachment> files = new LinkedList<EmailAttachment>(); 
	 
	    Multipart multipart = (Multipart) msg.getContent(); 
	    int cnt = multipart.getCount(); 

	    for (int i = cnt-1; i >= 0; i--) { 

	    	MimeBodyPart part = (MimeBodyPart )multipart.getBodyPart(i); 
              
	    	if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
	    		EmailAttachment emailAttachment = new EmailAttachment();
	    		emailAttachment.setFileName(DecoderUtil.decodeEncodedWords(part.getFileName(), null));
	    		emailAttachment.setFileSize(part.getSize());
//				part.saveFile("D:/99.tmp/" + part.getFileName());
            	files.add(emailAttachment);
			}
         }
	    
	    Collections.reverse(files);
          
          return files;
	} 
	
	/**
	 * 첨부파일 다운로드
	 * @param msg
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 * @throws ErrorException 
	 */
	public void attachmentDownLoad(Session session, HttpServletResponse response,String box, String messageId, int index) throws ErrorException  { 
		
		
		Folder inbox = null;
		
		try{
			
			InputStream inputStream = null;
			inbox = getInbox(session, box);
	        inbox.open(Folder.READ_ONLY); 
	        String fileName = "";
	 
			Message message; 
			if (inbox instanceof UIDFolder) { 
				message = ((UIDFolder)inbox).getMessageByUID(Long.parseLong(messageId)); 
			} else { 
				message = inbox.getMessage(Integer.parseInt(messageId)); 
			} 
			
		    Multipart multipart = (Multipart) message.getContent(); 
		    int cnt = multipart.getCount(); 

		    for (int i = cnt-1; i >= 0; i--) { 

		    	MimeBodyPart part = (MimeBodyPart )multipart.getBodyPart(i); 
	              
	    		if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) && i == index) {
	    			fileName = part.getFileName();
		    		inputStream = part.getInputStream();
				}
	         }
		    
		    response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment; filename=" +   URLEncoder.encode(fileName,"UTF-8"));
		    
		    OutputStream out =  response.getOutputStream();        
	        BufferedInputStream bin = null;
	        BufferedOutputStream bos = null; 
	        
	        try {
	            bin = new BufferedInputStream( inputStream );
	            bos = new BufferedOutputStream( out ); 
	        
	            byte[] buf = new byte[4096]; 
	            int read = 0;
	            while ((read = bin.read(buf)) != -1) {
	                bos.write(buf,0,read);
	            }
	        } finally {
	        	try {if(bos != null) bos.close();} catch(Exception e){e.printStackTrace();}
	        	try {if(bin != null) bin.close();} catch(Exception e){e.printStackTrace();}
	        } 
		    
			
		} catch (Exception e) { 
			throw new ErrorException("첨부파일 다운로드 실패",e);
		}finally{
			close(inbox);
		}
          
	} 
	
	/**
	 * 폴더 추가
	 * @param session
	 * @param folderName
	 * @throws ErrorException 
	 */
	public boolean createFolder(Session session,String folderName) throws ErrorException  {
		
		boolean success = false;
		Folder inbox = null;
		
		try{
			
			Store store = session.getStore(); 
	        store.connect(); 
			
			Folder root = store.getDefaultFolder(); 
	        inbox = root.getFolder("INBOX"); 
		
			Folder newFolder = inbox.getFolder(folderName);
			if( !newFolder.exists() ) {
				success = newFolder.create(Folder.HOLDS_MESSAGES);
			}
		}catch(Exception e){
			throw new ErrorException(e);
		}finally{
			close(inbox);
		}
		
		return success;
		
	}
	
	/**
	 * 폴더 삭제
	 * @param session
	 * @param folderName
	 * @throws ErrorException 
	 */
	public boolean deleteFolder(Session session,String folderName) throws ErrorException  {
		
		boolean success = false;
		Folder inbox = null;
		
		try{
			
			Store store = session.getStore(); 
	        store.connect(); 
			
			Folder root = store.getDefaultFolder(); 
	        inbox = root.getFolder("INBOX"); 
	        
			Folder oldFolder = inbox.getFolder(folderName);
			if( oldFolder.exists() ) {
				success = oldFolder.delete(true);
			}
			
		}catch(Exception e){
			throw new ErrorException(e);
		}finally{
			close(inbox);
		}
		
		return success;
		
	}
	
	/**
	 * 메세지 삭제
	 * @param session
	 * @param folderName
	 * @param uuids
	 * @return
	 * @throws ErrorException
	 */
	public boolean deleteMailMessages(Session session, String folderName, String[] uuids) throws ErrorException { 
		
        Folder inbox = null; 
        try { 
 
            inbox = getInbox(session, folderName);
 
            // Verify that we can even perform this operation 
            if (!(inbox instanceof UIDFolder)) { 
                String msg = "Folder feature is supported only for UIDFolder instances"; 
                throw new UnsupportedOperationException(msg); 
            } 
 
            inbox.open(Folder.READ_WRITE); 
 
            Message[] msgs = ((UIDFolder) inbox).getMessagesByUID(getMessageUidsAsLong(uuids)); 

//        	Store store = session.getStore(); 
//    		Folder toFolder = store.getDefaultFolder().getFolder("Trash"); 
//         inbox.copyMessages(msgs, toFolder);
            
            
            inbox.setFlags(msgs, new Flags(Flag.DELETED), true); 
//            inbox.setFlags(msgs, new Flags(Flag.SEEN), false); 
            inbox.close(true);
 
            return true;  // Indicate success 
 
        } catch (Exception e) { 
        	throw new ErrorException("메세지 삭제 실패",e);
        } finally { 
        	close(inbox);
        }
    } 	
	
	/**
	 * 메세지 이동
	 * @param session
	 * @param fromFolder
	 * @param toFolder
	 * @param uuids
	 * @return
	 * @throws ErrorException
	 */
	public boolean moveMailMessages(Session session, String from, String to, String[] uuids) throws ErrorException { 
		
        Folder frFolder = null; 
        Folder toFolder = null; 
        try { 
 
            Store store = session.getStore(); 
	        store.connect(); 
	        
	        frFolder = store.getDefaultFolder().getFolder(from); 
	        toFolder = store.getDefaultFolder().getFolder(to); 
	        frFolder.open(Folder.READ_WRITE); 
	        toFolder.open(Folder.READ_WRITE); 
	        
            Message[] msgs = ((UIDFolder) frFolder).getMessagesByUID(getMessageUidsAsLong(uuids)); 
            
            //대상폴더 메세디 복사
            frFolder.copyMessages(msgs, toFolder);
            
            //원본폴더 메세지 삭제
            frFolder.setFlags(msgs, new Flags(Flag.DELETED), true); 
            frFolder.close(true);
            
            return true;  // Indicate success 
 
        } catch (Exception e) { 
        	throw new ErrorException("메세지 이동 실패",e);
        } finally { 
        	close(frFolder);
        	close(toFolder);
        }
    } 	
	
	/**
	 * 상태변경
	 * @param session
	 * @param folderName
	 * @param uuids
	 * @param flag
	 * @return
	 * @throws ErrorException
	 */
	public boolean updateMailStatus(Session session, String folderName, String[] uuids, String flag) throws ErrorException { 
		
        Folder inbox = null; 
        try { 
 
            inbox = getInbox(session, folderName);
 
            // Verify that we can even perform this operation 
            if (!(inbox instanceof UIDFolder)) { 
                String msg = "Folder feature is supported only for UIDFolder instances"; 
                throw new UnsupportedOperationException(msg); 
            } 
 
            inbox.open(Folder.READ_WRITE); 
            Message[] msgs = ((UIDFolder) inbox).getMessagesByUID(getMessageUidsAsLong(uuids)); 
            
//            Flag.ANSWERED;
//            Flag.DELETED;
//            Flag.DRAFT;
//            Flag.SEEN
//            Flag.FLAGGED
            
            
            if("SEEN".equals(flag)){																	//읽음
            	inbox.setFlags(msgs, new Flags(Flag.SEEN), true); 
            }else if("UNSEEN".equals(flag)){														//읽지 않음
            	inbox.setFlags(msgs, new Flags(Flag.SEEN), false); 
            } else if("FLAGGED".equals(flag)){													//중요표시
            	inbox.setFlags(msgs, new Flags(Flag.FLAGGED), true); 
            } else if("UNFLAGGED".equals(flag)){												//중요표시 해제
            	inbox.setFlags(msgs, new Flags(Flag.FLAGGED), false); 			
            }
 
            return true;  // Indicate success 
 
        } catch (Exception e) { 
        	throw new ErrorException("상태 변경 실패",e);
        } finally { 
        	close(inbox);
        }
    } 	
	
	private Folder getInbox(Session session, String folderName) throws MessagingException { 
 
		Store store = session.getStore(); 
		store.connect(); 

		return store.getDefaultFolder().getFolder(folderName); 
 
    }
	 
	 private EmailMessageContent getMessageContent(Object content, String mimeType) throws IOException, MessagingException { 
		 
        // if this content item is a String, simply return it. 
        if (content instanceof String) { 
            return new EmailMessageContent((String) content, isHtml(mimeType)); 
        } 
 
        else if (content instanceof MimeMultipart) { 
            Multipart multipart = (Multipart) content; 
            int cnt = multipart.getCount(); 
 
            for (int i = cnt-1; i >= 0; i--) { 
                EmailMessageContent msgContent = null; 
 
                BodyPart part = multipart.getBodyPart(i); 
                Object partContent = part.getContent(); 
                String contentType = part.getContentType(); 
                boolean isHtml = isHtml(contentType); 
                logger.debug("Examining Multipart " + i + " with type " + contentType + " and class " + partContent.getClass()); 
                
                if (partContent instanceof String) { 
                	msgContent = new EmailMessageContent((String) partContent, isHtml); 
                } 
 
                else if (partContent instanceof InputStream && (contentType.startsWith("text/html"))) { 
                    StringWriter writer = new StringWriter(); 
                    IOUtils.copy((InputStream) partContent, writer); 
                    msgContent = new EmailMessageContent(writer.toString(), isHtml); 
                } 
 
                else if (partContent instanceof MimeMultipart) { 
                	msgContent = getMessageContent(partContent, contentType); 
                } 
                
                if (msgContent != null) { 
                    return msgContent; 
                } 
            } 
        } 
        return null; 
    } 
	 
	
	private EmailMessage wrapMessage(Message msg, boolean populateContent, Session session)  throws MessagingException, IOException { 
 
        String subject = msg.getSubject(); 
//        if (!StringUtils.isBlank(subject)) { 
//            AntiSamy as = new AntiSamy(); 
//            CleanResults cr = as.scan(subject, policy); 
//            subject = cr.getCleanHTML(); 
//        } 
 
        // Prepare content if requested 
        EmailMessageContent msgContent = null;  // default... 
        if (populateContent) { 
            // Defend against the dreaded: "Unable to load BODYSTRUCTURE" 
            try { 
                msgContent = getMessageContent(msg.getContent(), msg.getContentType()); 
            } catch (MessagingException me) { 
                // We are unable to read digitally-signed messages (perhaps 
                // others?) in the API-standard way;  we have to use a work around. 
                // See: http://www.oracle.com/technetwork/java/faq-135477.html#imapserverbug 
                // Logging as DEBUG because this behavior is known & expected. 
                logger.debug("Difficulty reading a message (digitally signed?). Attempting workaround..."); 
                try { 
                    MimeMessage mm = (MimeMessage) msg; 
                    ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
                    mm.writeTo(bos); 
                    bos.close(); 
                    SharedByteArrayInputStream bis = new SharedByteArrayInputStream(bos.toByteArray()); 
                    MimeMessage copy = new MimeMessage(session, bis); 
                    bis.close(); 
                    msgContent = getMessageContent(copy.getContent(), copy.getContentType()); 
                } catch (Throwable t) { 
                	logger.error("Failed to read message body", t); 
                    msgContent = new EmailMessageContent("UNABLE TO READ MESSAGE BODY: " + t.getMessage(), false); 
                } 
            } 
 
            // Sanitize with AntiSamy 
            String content = msgContent.getContentString(); 
//            if (!StringUtils.isBlank(content)) { 
//                AntiSamy as = new AntiSamy(); 
//                CleanResults cr = as.scan(content, policy); 
//                content = cr.getCleanHTML(); 
//            } 
            msgContent.setContentString(content); 
        } 
 
        int messageNumber = msg.getMessageNumber(); 
 
        // Prepare the UID if present 
        String uid = null;  // default 
        if (msg.getFolder() instanceof UIDFolder) { 
            uid = Long.toString(((UIDFolder) msg.getFolder()).getUID(msg)); 
        } 
 
        Address[] addr = msg.getFrom(); 
        String sender = getFormattedAddresses(addr, new ContentType( msg.getContentType()).getParameter("charset")); 
        Date sentDate = msg.getSentDate(); 
        
        boolean unread = !msg.isSet(Flag.SEEN); 
        boolean answered = msg.isSet(Flag.ANSWERED); 
        boolean deleted = msg.isSet(Flag.DELETED); 
        boolean flaged = msg.isSet(Flag.FLAGGED); 
        
        // Defend against the dreaded: "Unable to load BODYSTRUCTURE" 
        boolean multipart = false;  // sensible default; 
        String contentType = null;  // sensible default 
        try { 
            multipart = msg.getContentType().toLowerCase().startsWith(CONTENT_TYPE_ATTACHMENTS_PATTERN); 
            contentType = msg.getContentType(); 
        } catch (MessagingException me) { 
            // Message was digitally signed and we are unable to read it; 
            // logging as DEBUG because this issue is known/expected, and 
            // because the user's experience is in no way affected (at this point) 
            logger.debug("Message content unavailable (digitally signed?);  " + 
                        "message will appear in the preview table correctly, " + 
                        "but the body will not be viewable"); 
            logger.trace(me.getMessage(), me); 
        } 
        String to = getTo(msg); 
        String cc = getCc(msg); 
        String bcc = getBcc(msg); 
        return new EmailMessage(messageNumber, uid, sender, subject, sentDate, unread, answered, deleted, flaged, multipart, contentType, msgContent, to, cc, bcc); 
    } 
	
    private boolean isHtml(String mimeType) { 
 
        // if the mime-type is null, assume the content is not HTML 
        if (mimeType == null) { 
            return false; 
        } 
 
        // otherwise, check for the presence of the string "text/html" 
        mimeType = mimeType.trim().toLowerCase(); 
        if (mimeType.contains("text/html")) { 
            return true; 
        } 
 
        return false; 
    } 
	
	private String getTo(Message message) throws MessagingException, UnsupportedEncodingException { 
        Address[] toRecipients = message.getRecipients(RecipientType.TO); 
        return getFormattedAddresses(toRecipients,new ContentType( message.getContentType()).getParameter("charset")); 
    } 
     
    private String getCc(Message message) throws MessagingException, UnsupportedEncodingException { 
        Address[] ccRecipients = message.getRecipients(RecipientType.CC); 
        return getFormattedAddresses(ccRecipients, new ContentType( message.getContentType()).getParameter("charset")); 
    } 
     
    private String getBcc(Message message) throws MessagingException, UnsupportedEncodingException { 
        Address[] bccRecipients = message.getRecipients(RecipientType.BCC); 
        return getFormattedAddresses(bccRecipients, new ContentType( message.getContentType()).getParameter("charset")); 
    } 
    
    private String getFormattedAddresses(Address[] addresses, String charset) throws UnsupportedEncodingException { 
	  List <String> recipientsList = new ArrayList <String>(); 
	        String receiver = null; 
	        if (addresses != null && addresses.length != 0) { 
	            for (Address adress : addresses){ 
	                if (INTERNET_ADDRESS_TYPE.equals(adress.getType())) { 
	                	if(charset != null && "euc-kr".equals(charset.toLowerCase())){
	                		receiver =   new String(adress.toString().getBytes("iso-8859-1"), "euc-kr");
	                	}else{
		                    InternetAddress inet = (InternetAddress) adress; 
		                    receiver = inet.toUnicodeString(); 
	                	}
	                } else { 
	                    receiver = adress.toString(); 
	                }           
	                recipientsList.add(receiver); 
	            } 
	        } 
	        return StringUtils.join(recipientsList, "; ").replaceAll("<","<").replaceAll(">",">"); 
	 }
    
    private long[] getMessageUidsAsLong(String[] messageIds) { 
        long[] ids = new long[messageIds.length]; 
        int i = 0; 
        for (String id : messageIds) { 
            ids[i++] = Long.parseLong(id); 
        } 
        return ids; 
    } 

}
