package kr.zchat.web.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController{
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/index")
	public String info() {
		 return "manager:admin/index";
	} 
	
	
	@RequestMapping(value="/login")
	public String login() {
		 return "none:admin/login";
	} 
	
	
}