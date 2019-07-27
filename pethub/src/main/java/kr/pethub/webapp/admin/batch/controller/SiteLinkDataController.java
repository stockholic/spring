package kr.pethub.webapp.admin.batch.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;

import kr.pethub.site.AnimalOrKr;
import kr.pethub.site.DogZzangCoKr;
import kr.pethub.core.authority.Auth;
import kr.pethub.core.module.service.ConsoleLog;
import kr.pethub.webapp.admin.batch.model.SiteLinkData;
import kr.pethub.webapp.admin.batch.service.SiteLinkDataService;


@Controller
@RequestMapping("/adm")
public class SiteLinkDataController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SiteLinkDataService siteLinkDataService;

	@Autowired
	private ConsoleLog consoleLog;
	
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
	 * @throws InterruptedException
	 */
	@ResponseBody
	@RequestMapping(value="/batch/siteLinkDataJson", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>  siteListJson(@ModelAttribute SiteLinkData siteLinkData) {
		
		
		AnimalOrKr obj = new AnimalOrKr();
		String linkUrl = "http://www.animal.or.kr/bbs/board.php?bo_table=commu_08";
		try {
			if( consoleLog.getConsole() != null && consoleLog.getConsole().isOpen() == true) {
				obj.getDogList(linkUrl, consoleLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		DogZzangCoKr obj = new DogZzangCoKr();
		String linkUrl = "http://www.dog-zzang.co.kr/dog_sale/safe_list.php";
		try {
			if( consoleLog.getConsole() != null && consoleLog.getConsole().isOpen() == true) {
				obj.getDogList1(linkUrl, consoleLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 */
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//검색어 분리
		if( StringUtils.isNotEmpty( siteLinkData.getSearchString() ) ) {
			//여러공백 하나의 공잭처리
			String searchString = StringUtils.isNotEmpty( siteLinkData.getSearchString() ) ?  siteLinkData.getSearchString().trim().replaceAll(" +", " ") : "";
			siteLinkData.setSearchStringList( Arrays.asList(searchString.split(" ")) );
		}
		
		List<SiteLinkData> list =  siteLinkDataService.selectSiteLinkDataList(siteLinkData);
		
		map.put("page", siteLinkData.getPage());
		map.put("totalRow", siteLinkData.getTotalRow());
		map.put("totalPage", siteLinkData.getTotalPage());
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