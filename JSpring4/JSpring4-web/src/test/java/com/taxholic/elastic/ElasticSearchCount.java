package com.taxholic.elastic;

import java.util.List;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ElasticSearchCount {

	private String searchValue;
	private String startDt;
	private String endDt;
	private String defaultOperator;
	private List<String> fields;
	
	public String getSearchValue() {
		return searchValue;
	}
	
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	public String getDefaultOperator() {
		return  defaultOperator == null ? "AND" : defaultOperator;
	}
	
	public void setDefaultOperator(String defaultOperator) {
		this.defaultOperator = defaultOperator;
	}
	
	public List<String> getFields() {
		return fields;
	}
	
	public void setFields(List<String> fields) {
		this.fields = fields;
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
