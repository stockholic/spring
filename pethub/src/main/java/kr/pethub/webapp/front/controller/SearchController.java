package kr.pethub.webapp.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.pethub.webapp.admin.site.model.User;


@Controller
@RequestMapping("/search")
public class SearchController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value="list")
	public String list(@ModelAttribute User user, Model model) {
		
		 return "front:search/searchList";
	} 

	
	
}