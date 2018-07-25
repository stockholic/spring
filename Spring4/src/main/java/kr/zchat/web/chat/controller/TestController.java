package kr.zchat.web.chat.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController{
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/info")
	public String info() {
		 return "front:info";
	} 
	
	
}