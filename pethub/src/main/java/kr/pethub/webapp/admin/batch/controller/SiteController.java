package kr.pethub.webapp.admin.batch.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pethub.webapp.admin.batch.model.SiteInfo;
import kr.pethub.webapp.admin.site.model.User;


@Controller
@RequestMapping("/adm")
public class SiteController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/batch/siteList")
	public String siteList(@ModelAttribute User user, Model model) {
		
		 return "admin:batch/siteList";
	} 

	@ResponseBody
	@RequestMapping(value="/batch/siteListJson", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>  siteListJson() {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<SiteInfo> list = new ArrayList<SiteInfo>();
			SiteInfo siteInfo = new SiteInfo();
			
			for ( int  i =0; i < 10; i++ ) {
				siteInfo.setSiteNm("하하하하");
				list.add(siteInfo);
			}
			
			
			map.put("page",1);
			map.put("totalPage",10);
			map.put("totalRow",100);
			map.put("list", list);
			 
		 return map;
		
	} 
	
		
	
}