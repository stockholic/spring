package com.taxholic.web.test.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.web.test.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController{
	
	@Autowired
	private MessageSourceAccessor message;
	
	@Autowired
	private TestService testService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String login() {
		 return "";
	} 
	
	@RequestMapping(value = "/getMsg", method = RequestMethod.GET)
	@ResponseBody
	public String getMsg() {
		return  message.getMessage("stockChart.filePath");
		
	} 
	
	@RequestMapping(value = "/getId", method = RequestMethod.GET)
	@ResponseBody
	public String getId() {
		return  testService.getId();
	} 

	
	@RequestMapping(value = "/getJson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AuthDto getJson(AuthDto authDto, Model model) {
		
		return new AuthDto();
		
	}
	
//	@RequestMapping(value = "/getJson", method = RequestMethod.GET )
//	public String getJson(AuthDto authDto, Model model) {
//		model.addAttribute("auth",new AuthDto());
//	    return "jsonView";
//		
//	}
		
}