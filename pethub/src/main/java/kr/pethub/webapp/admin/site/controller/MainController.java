package kr.pethub.webapp.admin.site.controller;

import java.util.Locale;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pethub.webapp.admin.site.service.UserService;

//import kr.pethub.core.authority.Auth;


@Controller
@RequestMapping("/")
public class MainController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	/**
	 * 로그인 폼
	 * @return
	 */
	@RequestMapping(value="login")
	public String login() {
		
		 return "none:admin/site/user/login";
		 
	} 
	

	@RequestMapping(value="/main")
	@ResponseBody
	public void main(Locale locale, HttpServletRequest request) {
		
		logger.debug("Welcome home! The client locale is {}.", locale);
		
		System.out.println(userService.selectUserCount());
		
		
	} 
	

	
}