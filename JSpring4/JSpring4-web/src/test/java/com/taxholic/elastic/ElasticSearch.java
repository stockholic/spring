package com.taxholic.elastic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ElasticSearch {

	private int seq;
	private String title;
	private String description;
	private Date regdate;
	private String searchValue;
	private List<String> fields;
	private List<String> includes;
	private Integer from;
	private Integer size;
	private List<String> sortKey;
	private List<String> sortValue;
	private String startDt;
	private String endDt;
	private String defaultOperator;
	
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
	public List<String> getIncludes() {
		return includes;
	}
	public void setIncludes(List<String> includes) {
		this.includes = includes;
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
	public List<String> getSortKey() {
		return sortKey;
	}
	public void setSortKey(List<String> sortKey) {
		this.sortKey = sortKey;
	}
	public List<String> getSortValue() {
		return sortValue;
	}
	public void setSortValue(List<String> sortValue) {
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
	
	public List<Map<String, String>> getSort() {
		
		List<Map<String,String>> sort = new ArrayList<Map<String,String>>();
		for(int i = 0; i < getSortKey().size(); i++){
			Map<String, String> map = new HashMap<String,String>();
			map.put(getSortKey().get(i),getSortValue().get(i));
			sort.add(map);
		}
		
		return sort;
	}
	
	public ObjectNode getRange() {
		
		ObjectNode range = null;
		
		if( getStartDt() !=null || getEndDt() !=null){
			JsonNodeFactory factory = JsonNodeFactory.instance;
			ObjectNode priod = factory.objectNode();
			
			if(getStartDt() != null) priod.put("gte", getStartDt());
			if(getEndDt() != null )priod.put("lte", getEndDt());
			
			range = factory.objectNode();
			range.putPOJO("regdate",priod);
		}
		
		return range;
	}
 
}
