package kr.pethub.webapp.admin.batch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pethub.core.authority.Auth;
import kr.pethub.core.authority.AuthUtil;
import kr.pethub.webapp.admin.batch.model.SiteInfo;
import kr.pethub.webapp.admin.batch.service.SiteInfoService;
import kr.pethub.webapp.admin.site.model.User;


@Controller
@RequestMapping("/adm")
public class SiteInfoController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SiteInfoService siteInfoService;
	
	/**
	 * 사이트 정보 화면
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/batch/siteList")
	public String siteList(Auth user, Model model) {
		
		 return "admin:batch/siteList";
	} 

	/**
	 * 사이트 정보 데이터
	 * @param siteInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/batch/siteListJson", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>  siteListJson(@ModelAttribute SiteInfo siteInfo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<SiteInfo> list =  siteInfoService.selectSiteInfoList(siteInfo);
		
		map.put("dataInfo", siteInfo);
		map.put("dataList", list);

		return map;
		
	} 
	
	
	/**
	 * 사이트 정보 등록폼
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/batch/siteForm")
	public String siteForm(Auth user, @RequestParam(value="siteSrl", required=false) String siteSrl, Model model) {
		
		if( StringUtils.isNotEmpty(siteSrl) ) {
			model.addAttribute("siteInfo", siteInfoService.selectSiteInfo(siteSrl));
		}
		
		 return "ajax:admin/batch/siteForm";
	} 
	

	
		
	
}