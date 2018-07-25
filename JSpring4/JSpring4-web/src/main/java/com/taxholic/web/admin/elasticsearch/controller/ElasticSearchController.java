package com.taxholic.web.admin.elasticsearch.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.taxholic.web.admin.elasticsearch.service.ElasticSearchService;
import com.taxholic.web.admin.elasticsearch.vo.ElasticSearch;

@Controller
@RequestMapping("/admin/elastic")
public class ElasticSearchController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private ElasticSearchService elasticSearchService;
	
	
	@RequestMapping(value = "/searchList")
	
	public String searchList(@ModelAttribute ElasticSearch elasticSearch, Model model) throws IOException {
		
		List<ElasticSearch> dataList = new ArrayList<ElasticSearch>();
		
		//검색대상 필드
		if(elasticSearch.getFields() == null){
			List<String> fields = Arrays.asList("title","description");
			elasticSearch.setFields(fields);
		}
		
		//sort
		if(elasticSearch.getSortKey() == null){
			elasticSearch.setSortKey("regdate");
			elasticSearch.setSortValue("desc");
		}
		
		if(StringUtils.isNotEmpty(elasticSearch.getSearchValue())) {
		
			//검색대상
			elasticSearch.setIndexNm("taxholic");
			elasticSearch.setTypeNm("news");
			
			//sort	
			elasticSearch.setSortValue("desc");
			
			//Paging
			elasticSearch.setFrom(0);
			elasticSearch.setSize(50);
			
			dataList = elasticSearchService.searchList(elasticSearch);
		}
		
		model.addAttribute("dataList",dataList);
		
		return "manager:admin/elastic/searchList";
	} 
	
	
	
	
}
