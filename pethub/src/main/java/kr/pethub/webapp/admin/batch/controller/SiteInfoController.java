package kr.pethub.webapp.admin.batch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pethub.webapp.admin.batch.model.SiteInfo;
import kr.pethub.webapp.admin.batch.service.SiteInfoService;
import kr.pethub.webapp.admin.site.model.User;


@Controller
@RequestMapping("/adm")
public class SiteInfoController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SiteInfoService siteInfoService;
	
	@RequestMapping(value="/batch/siteList")
	public String siteList(@ModelAttribute User user, Model model) {
		
		 return "admin:batch/siteList";
	} 

	@ResponseBody
	@RequestMapping(value="/batch/siteListJson", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>  siteListJson(@ModelAttribute SiteInfo siteInfo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<SiteInfo> list =  siteInfoService.selectSiteInfoList(siteInfo);
		
		map.put("dataInfo", siteInfo);
		map.put("dataList", list);

		return map;
		
	} 
	
		
	
}