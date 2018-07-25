package kr.zchat.webapp.front;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import kr.zchat.core.authority.Auth;


@Controller
@RequestMapping("/")
public class MainController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	LocaleResolver localeResolver;

	@Autowired
	MessageSource msg;
	
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

//		logger.debug("Session locale is {}.", localeResolver.resolveLocale(request));
		logger.debug("main.title : {}", msg.getMessage("main.title", null, "default text", locale));
																										

		logger.debug("UserId : {}", auth.getUserId());
		
		return "admin:main";
	} 
	

	
}