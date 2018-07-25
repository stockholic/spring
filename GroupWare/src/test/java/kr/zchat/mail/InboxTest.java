package kr.zchat.mail;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.Session;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kr.zchat.BaseTestCase;
import kr.zchat.core.Exception.ErrorException;
import kr.zchat.web.admin.mail.service.EmailService;
import kr.zchat.web.admin.mail.vo.Email;
import kr.zchat.web.admin.mail.vo.EmailAttachment;
import kr.zchat.web.admin.mail.vo.EmailFolder;
import kr.zchat.web.admin.mail.vo.EmailMessage;

public class InboxTest extends BaseTestCase{

	//http://www.programcreek.com/java-api-examples/index.php?source_dir=email-preview-master/src/main/java/org/jasig/portlet/emailpreview/dao/javamail/JavamailAccountDaoImpl.java
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
    private String user = "jspark@zchat.kr";
    private String password = "qkrtjqkddhkTsi";
    
    @Autowired
	private EmailService emailService;
    
    @Test
	public void createFolder() throws ErrorException {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		String folderName = "아놔4";
		Session session = emailService.login(user, password); 
		logger.debug("success : " + emailService.createFolder(session,folderName));
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
    
    @Test
	public void deleteFolder() throws ErrorException {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		String folderName = "아놔4";
		Session session = emailService.login(user, password); 
		
		logger.debug("success : " + emailService.deleteFolder(session,folderName));
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void getFolder() throws ErrorException{
		
		logger.debug("-------------------------------------------------------------------------------> start");

			Session session = emailService.login(user, password);
			List<EmailFolder> list =  emailService.getFolderList(session, "INBOX"); 
			
			for(EmailFolder folder : list){
				logger.debug("folder : {} ({})" , folder.getName(), folder.getUnreadMessageCount() +"/"+folder.getMessageCount());
			}
			
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void getMailList() throws ErrorException{
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		String folderName = "INBOX";
//		String folderName = "INBOX.Trash";
	
		Session session = emailService.login(user, password);
		EmailFolder folder =  emailService.getFolderInfo(session,folderName); 
		logger.debug("name : " + folder.getName() + " MessageCount : " + folder.getMessageCount() + " UnreadMessageCount : " + folder.getUnreadMessageCount());
	
		Email  email = new Email();
		email.setBox(folderName);
		email.setTotal(folder.getMessageCount());
		email.setPage(1);
		
		
		List<EmailMessage> messages = emailService.getEmailMessageList(session,email); 
		
		for(EmailMessage list : messages){
			logger.debug("Uid : " + list.getUid() +
//								" [ContentType] : " + list.getContentType() +  
//								" [MessageId] : " + list.getMessageId() +  
								" [Sender] : " + list.getSender() + 
								" [SenderName] : " + list.getSenderName() + 
								" [Subject] : " + list.getSubject() + 
								" [SentDate] : " + list.getSentDateString() + 
								" [isUnread] : " + list.isUnread() + 
								" [isDeleted] : " + list.isDeleted() + 
								" [isFlaged] : " + list.isFlaged() + 
								" [isMultipart] : " + list.isMultipart() 
//								" [isAnswered] : " + list.isAnswered()
//								" [CcRecipients] : " + list.getCcRecipients() + 
//								" [BccRecipients] : " + list.getBccRecipients()
			);
		}
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void getMailMesage() throws ErrorException{ 
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		String folderName = "INBOX";
		String messageId = "22";
		
		Session session = emailService.login(user, password); 
	
    	EmailMessage message = emailService.getEmailMessage(session,folderName,messageId);
    	
    	logger.debug("SenderName : " + message.getSenderName());
    	logger.debug("Subject : " + message.getSubject());
    	logger.debug("Content : " + message.getContent().getContentString());
    	logger.debug("isMultipart : " + message.isMultipart());
    	logger.debug("ContentType : " + message.getContentType());
    	
    	for(EmailAttachment attach : message.getContent().getAttachment()){
    	//	logger.debug("fileName : " + attach.getFileName() + " size : " + attach.getFileSize());
    	}
    	
        logger.debug("-------------------------------------------------------------------------------> end");
 
    } 
	
	@Test
	public void deleteMailMessages() throws ErrorException{
		
		logger.debug("-------------------------------------------------------------------------------> start");

		Session session = emailService.login(user, password);
		logger.debug("success : " + emailService.deleteMailMessages(session, "INBOX", new String[] {"5"})); 
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void changeMailStatus() throws ErrorException{
		
		logger.debug("-------------------------------------------------------------------------------> start");

		Session session = emailService.login(user, password);
		logger.debug("success : " + emailService.updateMailStatus(session, "INBOX", new String[] {"33","31"}, "UNSEEN")); 
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void moveMailMessages() throws ErrorException{
		
		logger.debug("-------------------------------------------------------------------------------> start");

		Session session = emailService.login(user, password);
		logger.debug("success : " + emailService.moveMailMessages(session, "INBOX.Trash", "INBOX", new String[] {"12","11","10","9","8","7","6"} )); 
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	
	@Test
	public void encodingTest(){
		
		logger.debug("-------------------------------------------------------------------------------> start");

		String originalStr = "KEAÀü½Ã¸¶ÄÉÆÃÆÀ"; // 테스트 문장
		String [] charSet = {"utf-8","euc-kr","ksc5601","iso-8859-1","x-windows-949"};
		  
		for (int i=0; i<charSet.length; i++) {
			for (int j=0; j<charSet.length; j++) {
				try {
					System.out.println("[" + charSet[i] +"," + charSet[j] +"] = " + new String(originalStr.getBytes(charSet[i]), charSet[j]));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			 }
		}

		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	
}
