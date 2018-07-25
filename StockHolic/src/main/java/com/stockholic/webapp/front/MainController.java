package com.stockholic.webapp.front;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stockholic.core.authority.Auth;


@Controller
@RequestMapping("/")
public class MainController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 로그인 폼
	 * @return
	 */
	@RequestMapping(value="login")
	public String login() {
		
		 return "none:admin/site/user/login";
		 
	} 
	

	@RequestMapping(value="/main")
	public String main(Locale locale, HttpServletRequest request, Auth auth) {
		
		logger.debug("Welcome home! The client locale is {}.", locale);
		
		return "admin:main";
	} 
	

	
}