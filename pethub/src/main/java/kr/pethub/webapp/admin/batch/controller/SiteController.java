package kr.pethub.webapp.admin.batch.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tiles.autotag.core.runtime.annotation.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pethub.webapp.admin.batch.model.SiteInfo;
import kr.pethub.webapp.admin.site.model.User;
import kr.pethub.webapp.api.model.SiteLinkData;


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
	public Map<String, Object>  siteListJson(@ModelAttribute SiteInfo siteInfo) {
		
		
		System.out.println(">>>" + siteInfo.getPage());
		System.out.println(">>>" + siteInfo.getRowSize());
		
			
		Map<String, Object> map = new HashMap<String, Object>();

		List<SiteInfo> list = new ArrayList<SiteInfo>();
		SiteInfo vo = new SiteInfo();

		for (int i = 0; i < 15; i++) {
			vo.setSiteNm("123456");
			list.add(vo);
		}
		
		siteInfo.setTotalRow(241);

		map.put("dataInfo", siteInfo);
		map.put("dataList", list);

		return map;
		
	} 
	
		
	
}