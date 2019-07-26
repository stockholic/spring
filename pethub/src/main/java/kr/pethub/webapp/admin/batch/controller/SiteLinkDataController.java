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

import kr.pethub.core.authority.Auth;
import kr.pethub.webapp.admin.batch.model.SiteLinkData;
import kr.pethub.webapp.admin.batch.service.SiteLinkDataService;


@Controller
@RequestMapping("/adm")
public class SiteLinkDataController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SiteLinkDataService siteLinkDataService;
	
	/**
	 * 사이트 링크 데이타 화면
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/batch/siteLinkDataList")
	public String siteList(Auth user, Model model) {
		
		 return "admin:batch/siteLinkDataList";
	} 

	/**
	 * 사이트 링크  데이터
	 * @param siteLinkData
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/batch/siteLinkDataJson", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>  siteListJson(@ModelAttribute SiteLinkData siteLinkData) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<SiteLinkData> list =  siteLinkDataService.selectSiteLinkDataList(siteLinkData);
		
		map.put("dataInfo", siteLinkData);
		map.put("dataList", list);

		return map;
		
	} 
	
	/**
	 * 사이트 링크 데이타 삭제
	 * @param user
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/batch/deleteSiteLinkData", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> deleteSiteLinkData(SiteLinkData siteLinkData) {
		
		int result = siteLinkDataService.deleteSiteLinkData(siteLinkData);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		
		return map;
	} 
	
	
}