package com.taxholic.web.admin.elasticsearch.vo;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ElasticSearch {

	private int seq;
	private String title;
	private String description;
	private Date regdate;
	private String searchValue;
	private List<String> fields;
	private Integer from;
	private Integer size;
	private String sortKey;
	private String sortValue;
	private String startDt;
	private String endDt;
	private String defaultOperator;
	
	private Integer total;
	private String indexNm;
	private String typeNm;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public Integer getFrom() {
		return from;
	}
	public void setFrom(Integer from) {
		this.from = from;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public String getSortValue() {
		return sortValue;
	}
	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getDefaultOperator() {
		return  defaultOperator == null ? "AND" : defaultOperator;
	}
	public void setDefaultOperator(String defaultOperator) {
		this.defaultOperator = defaultOperator;
	}
	public String getIndexNm() {
		return indexNm;
	}
	public void setIndexNm(String indexNm) {
		this.indexNm = indexNm;
	}
	public String getTypeNm() {
		return typeNm;
	}
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	/**
	 * 결과표시 필드
	 */
	public List<String> getIncludes() {
		return Arrays.asList("title","description","regdate");
	}
	
	/**
	 * 결과표시 하이라이트 필드
	 */
	public List<ObjectNode> getHighlightFields() {
		
		JsonNodeFactory factory = JsonNodeFactory.instance;
		
		ObjectNode titleOpt = factory.objectNode();
		titleOpt.put("fragment_size", 100);
		titleOpt.put("number_of_fragments", 3);
		ObjectNode title = factory.objectNode();
		title.putPOJO("title", titleOpt);
		
		ObjectNode descriptionOpt = factory.objectNode();
		descriptionOpt.put("fragment_size", 200);
		descriptionOpt.put("number_of_fragments", 3);
		ObjectNode description = factory.objectNode();
		description.putPOJO("description", titleOpt);
		
		List<ObjectNode> fields = Arrays.asList(title, description);
		
		return fields;
	}
	
	
	public Map<String, String> getSort() {
		
		Map<String, String> map = new HashMap<String,String>();
		map.put(getSortKey(),getSortValue());
		
		return map;
	}
	
	/**
	 * 날짜 범위
	 * @return
	 */
	public ObjectNode getRange() {
		
		ObjectNode range = null;
		
		if( StringUtils.isNotEmpty(getStartDt()) || StringUtils.isNotEmpty(getEndDt())){
			JsonNodeFactory factory = JsonNodeFactory.instance;
			ObjectNode priod = factory.objectNode();
			
			if(StringUtils.isNotEmpty(getStartDt())) priod.put("gte", getStartDt() + "T00:00:00+0900");
			if(StringUtils.isNotEmpty(getEndDt()))priod.put("lte", getEndDt() + "T23:59:59+0900");
			
			range = factory.objectNode();
			range.putPOJO("regdate",priod);
		}
		
		return range;
	}
 
}
