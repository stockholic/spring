package kr.zchat.web.admin.mail.controller;


import java.util.List;

import javax.mail.Session;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.zchat.core.Exception.ErrorException;
import kr.zchat.core.authority.Auth;
import kr.zchat.web.admin.mail.service.EmailService;
import kr.zchat.web.admin.mail.vo.Email;
import kr.zchat.web.admin.mail.vo.EmailFolder;
import kr.zchat.web.admin.mail.vo.EmailMessage;

@Controller
@RequestMapping("/mail")
public class MailController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EmailService emailService;
	
    /**
     * 메세지 목록
     * @param email
     * @param model
     * @return
     * @throws ErrorException
     */
	@RequestMapping(value="/inbox")
	public String inbox(@ModelAttribute Email email,	Model model) throws ErrorException {
		
		
		model.addAttribute("menu0200", "in");
		
		if("INBOX".equals(email.getBox())){
			model.addAttribute("menu0201", "select-menu");
			model.addAttribute("boxName", "받은편지함");
		}else if("INBOX.Sent".equals(email.getBox())){
			model.addAttribute("menu0202", "select-menu");
			model.addAttribute("boxName", "보낸편지함");
		}else if("INBOX.Drafts".equals(email.getBox())){
			model.addAttribute("menu0203", "select-menu");
			model.addAttribute("boxName", "임시저장함");
		}else if("INBOX.Trash".equals(email.getBox())){
			model.addAttribute("menu0204", "select-menu");
			model.addAttribute("boxName", "지운편지함");
		}else if("INBOX.Spam".equals(email.getBox())){
			model.addAttribute("menu0205", "select-menu");
			model.addAttribute("boxName", "스팸편지함");
		}
		
		return "manager:admin/mail/inbox";
	} 
	
	/**
	 * 메세지 목록 데이터
	 * @param auth
	 * @param inbox
	 * @param page
	 * @return
	 * @throws ErrorException
	 */
	@ResponseBody
	@RequestMapping(value= "/{inbox}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Email getInboxList(Auth auth, 
			@PathVariable(value = "inbox") String inbox, 
			@PathVariable(value = "page") Integer page
	) throws ErrorException {
		
		Email email = new Email();
		
		Session session = emailService.login(auth.getEmail(), auth.getPassword());
		EmailFolder folderInfo =  emailService.getFolderInfo(session,inbox); 
		
		email.setBox(inbox);
		email.setPage(page);
		email.setTotal(folderInfo.getMessageCount());

		List<EmailMessage> messages = emailService.getEmailMessageList(session,email); 
		email.setInboxList(messages);
		
		return email;
	} 
	
	/**
	 * 메세지 조회
	 * @param auth
	 * @param inbox
	 * @param messageId
	 * @return
	 * @throws ErrorException
	 */
	@ResponseBody
	@RequestMapping(value="/{inbox}/view/{messageId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public EmailMessage message(Auth auth, 
			@PathVariable(value = "inbox") String inbox, 
			@PathVariable(value = "messageId") String messageId
	) throws ErrorException {
		
		Session session = emailService.login(auth.getEmail(), auth.getPassword()); 
    	EmailMessage message = emailService.getEmailMessage(session,inbox,messageId);
    	return message;
	} 

	@RequestMapping(value="/compose")
	public String compose(Model model ) {
		
		model.addAttribute("menu0200", "opened select-menu");
		model.addAttribute("menu0202", "select-menu");
		
		return "manager:admin/mail/compose";
	} 

	/**
	 * 첨부파일 다운로드
	 * @param response
	 * @param email
	 * @throws ErrorException
	 */
	@RequestMapping(value="/attachment/{inbox}/{messageId}/{index}")
	public void attachment(HttpServletResponse response,Auth auth,
			@PathVariable(value = "inbox") String inbox, 
			@PathVariable(value = "messageId") String messageId,
			@PathVariable(value = "index") Integer index
			) throws ErrorException  {
		Session session = emailService.login(auth.getEmail(), auth.getPassword()); 
		emailService.attachmentDownLoad(session, response, inbox, messageId, index);
	}
	
	/**
	 * 메일이동
	 * @param auth
	 * @param fromBox
	 * @param toBox
	 * @param uuids
	 * @throws ErrorException
	 */
	@ResponseBody
	@RequestMapping(value="/moveMailMessages", method = RequestMethod.POST)
	public void moveMailMessages(Auth auth, 
			@RequestParam(required = true, value = "fromBox") String fromBox,
			@RequestParam(required = true, value = "toBox") String toBox,
			@RequestParam(required = true, value = "uuids") String[] uuids)
		throws ErrorException {
		
		Session session = emailService.login(auth.getEmail(), auth.getPassword()); 
		emailService.moveMailMessages(session, fromBox, toBox, uuids);
		
	} 
	
	/**
	 * 상태변경
	 * @param auth
	 * @param fromBox
	 * @param uuids
	 * @throws ErrorException
	 */
	@ResponseBody
	@RequestMapping(value="/updateMailStatus", method = RequestMethod.POST)
	public void updateMailStatus(Auth auth, 
			@RequestParam(required = true, value = "inbox") String inbox,
			@RequestParam(required = true, value = "uuids") String[] uuids,
			@RequestParam(required = true, value = "flag") String flag)
		throws ErrorException {
		
		Session session = emailService.login(auth.getEmail(), auth.getPassword()); 
		emailService.updateMailStatus(session, inbox, uuids, flag);
		
	} 
	/**
	 * 
	 * @param auth
	 * @param fromBox
	 * @param toBox
	 * @param uuids
	 * @throws ErrorException
	 */
	@ResponseBody
	@RequestMapping(value="/deleteMailMessages", method = RequestMethod.POST)
	public void deleteMailMessages(Auth auth, 
			@RequestParam(required = true, value = "fromBox") String fromBox,
			@RequestParam(required = true, value = "toBox") String toBox,
			@RequestParam(required = true, value = "uuids") String[] uuids)
		throws ErrorException {
		
		Session session = emailService.login(auth.getEmail(), auth.getPassword()); 
		emailService.deleteMailMessages(session, fromBox, uuids);
	}
	
}