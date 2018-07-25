package com.taxholic.web.ui.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.taxholic.core.util.FileManager;

@Controller
@RequestMapping("/admin/ui/*")
public class UIController{
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String login() {
		 return  "manager:ui/form";
	} 
	
	@RequestMapping(value = "excel", method = RequestMethod.GET)
	public String excel(Map<String,Object> ModelMap) {
		
		
		List<Map<Integer, Object>> excelList =  new ArrayList<Map<Integer, Object>>();

		String[] header = {"번호","이름","제목"};
		
	
		int n = 0;
		for(int i = 1001; i <= 1100; i++){
			int k = 0;
			Map<Integer, Object> map = new HashMap<Integer,Object>();
	    	map.put(k++,i);
	    	map.put(k++,"이름" + n);
	    	map.put(k++,"제목 ");	    	
			
	    	if(i % 10 == 0) n++;
	    	
	    	excelList.add(map);
		}	
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		ModelMap.put("fileName", "샘플_"+sdf.format(date));
		ModelMap.put("header", header);
		ModelMap.put("excelList", excelList);

		return "excelView";
		
	} 
	
	
	@RequestMapping(value = "fileUpload")
	public String fileUpload() {
		 return  "manager:ui/fileUpload";
	} 
	
	
	@RequestMapping(value = "/saveFile", method = RequestMethod.POST)
	public String  saveFile(@RequestParam("files") MultipartFile files, Model model) throws Exception {
		
	    model.addAttribute(FileManager.upload(files, "D:/99.tmp/gg"));
	    
	    return "jsonView";
		
	}
	
	
	
}