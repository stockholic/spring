package com.taxholic.web.admin.elasticsearch.controller;


import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taxholic.web.admin.elasticsearch.service.ElasticService;

@Controller
@RequestMapping("/admin/elastic")
public class ElasticController{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private ElasticService elasticService;
	
	@RequestMapping(value = "/info")
	public String info() {
		
		return "manager:admin/elastic/info";
	} 
	
	/**
	 * Index 조회
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/indexList",produces = "application/json; charset=utf8")
	@ResponseBody
	public String indexList() throws IOException {
		
		return elasticService.indexList();
	} 
	
	/**
	 * Index / Type 등록/삭제
	 * @return
	 */
	@RequestMapping(value = "/createForm")
	public String createForm() {
		return "manager:admin/elastic/createForm";
	} 
	
	/**
	 * Index 생성
	 * @param indexNm
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/createIndex",produces = "application/text; charset=utf8")
	@ResponseBody
	public String createIndex(@RequestParam(required = true, value = "indexNm") String indexNm) throws IOException {
		return elasticService.createIndex(indexNm);
	} 
	
	/**
	 * Index 삭제
	 * @param indexNm
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteIndex",produces = "application/text; charset=utf8")
	@ResponseBody
	public String deleteIndex(@RequestParam(required = true, value = "indexNm") String indexNm) throws IOException {
		return elasticService.deleteIndex(indexNm);
	} 
	
	/**
	 * Mapping 조회
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/mappingList",produces = "application/json; charset=utf8")
	@ResponseBody
	public String mappingList() throws IOException {
		return elasticService.mappingList();
	} 
	
	/**
	 * Mapping 생성
	 * @param indexNm
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/createMapping", produces = "application/text; charset=utf8")
	@ResponseBody
	public String createMapping(
			@RequestParam(required = true, value = "indexNm") String indexNm,
			@RequestParam(required = true, value = "typeNm") String typeNm,
			@RequestParam(required = true, value = "propKey[]") List<String> propKey,
			@RequestParam(required = true, value = "propValue[]") List<String> propValue) throws IOException {
		
		return elasticService.createMapping(indexNm,typeNm,propKey,propValue);
	} 
	
	/**
	 * Mapping 삭제
	 * @param indexNm
	 * @param typeNm
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteMapping",produces = "application/text; charset=utf8")
	@ResponseBody
	public String deleteMapping(
			@RequestParam(required = true, value = "indexNm") String indexNm,
			@RequestParam(required = true, value = "typeNm") String typeNm) throws IOException {
		
		return elasticService.deleteMapping(indexNm, typeNm);
	} 
	
	
}
