package com.taxholic.mail;


import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.BaseTestCase;

public class MailTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private Store store;
	private Folder folder;
	
	
	public void setUp() throws MessagingException {
		
		String target = "INBOX";
		String user = "";
		String passwd = "";
		Properties  props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		store = session.getStore("imaps");
		store.connect("imap.gmail.com", 993, user, passwd);
		folder = store.getFolder(target);
		folder.open(Folder.READ_ONLY);
	}
	
	@Test
	public void getFolder(){
		
		logger.debug("-------------------------------------------------------------------------------> start");
        
			try{ 
				
				for(Folder folder : store.getDefaultFolder().list())
					logger.debug("folder : {}" , folder.getName());
				
			}catch(Exception e){
				
			}finally{
				try {folder.close(false);} catch (MessagingException e) {}
				try {store.close();} catch (MessagingException e) {}
			}
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void getTitle(){
		
		logger.debug("-------------------------------------------------------------------------------> start");
        
			try{ 
				logger.debug("MessageCount : " + getMessageCount());
				
				Message[] messages = getMessages();
				for (int i = getMessageCount() -1; i < getMessageCount(); i--) {
					Address fromAddress = messages[i].getFrom()[0];
					logger.debug(messages[i].getMessageNumber() + ". Date : " + messages[i].getReceivedDate() + "\t" + fromAddress + "\tSubject : " + messages[i].getSubject());
				}
				
			}catch(Exception e){
				
			}finally{
				try {folder.close(false);} catch (MessagingException e) {}
				try {store.close();} catch (MessagingException e) {}
			}
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void getContent()  {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		   
				try{ 
					logger.debug("MessageCount : " + getMessageCount());
					logger.debug( folder.getMessages()[0].getContent().toString() );
				}catch(Exception e){
					
				}finally{
					try {folder.close(false);} catch (MessagingException e) {}
					try {store.close();} catch (MessagingException e) {}
				}
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	
	public int getMessageCount() {
		int messageCount = 0;
		try {
			messageCount = folder.getMessageCount();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		return messageCount;
	}

	public Message[] getMessages() throws MessagingException {
		return folder.getMessages();
	}
	
}
