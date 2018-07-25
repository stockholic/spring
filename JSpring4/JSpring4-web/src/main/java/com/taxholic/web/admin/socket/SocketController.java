package com.taxholic.web.admin.socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/socket")
public class SocketController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value = "/chat")
	public String chat() {
		
		return "manager:admin/socket/chat";
	} 
	
	
	
}
