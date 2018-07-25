package com.taxholic.web.ui.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/zTree")
public class ZtreeController{
	
	
	@RequestMapping(value = "/tree")
	public String tree() {
		return "manager:ui/zTree";
	} 
	
	@RequestMapping(value = "/saveTree", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String , String>  saveTree(@RequestBody List<Map<String, Object>> list, Model model) {
		
		Logger logger = LoggerFactory.getLogger(getClass());
		
		logger.debug(list.toString());
		
		Map<String, String> jsonObject = new HashMap<String, String>();
	    jsonObject.put("result","Success");
	    
	    return jsonObject; 
		
	}
	
}