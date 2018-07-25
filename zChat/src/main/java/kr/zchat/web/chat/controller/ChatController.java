package kr.zchat.web.chat.controller;


import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ChatController{
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/info")
	public String info() {
		 return "front:info";
	} 
	
	@RequestMapping(value="/chat/{uri}")
	public String chat(@PathVariable(value = "uri") String uri, Model model) {
		
		
		boolean isUri = Pattern.matches("^[a-zA-Zㄱ-ㅎ가-힣0-9\\-_]{4,30}$", uri);
		model.addAttribute("channelNm", uri);
		
		return isUri ? "zChat:zChat/chat" : "front:form/channelForm";
		
		 
	} 
	
}