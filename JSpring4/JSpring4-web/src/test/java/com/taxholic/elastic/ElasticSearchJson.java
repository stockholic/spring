package com.taxholic.elastic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author jspark
 *
 GET news/stock/_search
 {
    "query": {
        "query_string": {
            "fields": ["title","description"],
            "query": "롯데 신세계",
            "default_operator": "AND"
        }
    },
    
     "filter" : {
     	"range": {"regdate": {"gte": "2012-04-01","lte": "2016-01-01"}}
    },
    
    "_source": {
        "include": ["title", "description","reg_date"]
    },

    "highlight": {
        "pre_tags": ["<span class='sht'>"],
        "post_tags": ["</span>"],
        "fields": [
        	{"title": {}},
        	{"description": {}}
        ]
    },

    "sort": [
    	{"_score": "asc"},
    	{"regdate": "desc"}
    ],
    "from": 0,
    "size": 10
}
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElasticSearchJson {

	@JsonProperty("query")
	private Map<String, Object> query;
	
	@JsonProperty("_source")
	private Map<String, Object> source;
	
	@JsonProperty("from")
	private Integer from;

	@JsonProperty("size")
	private Integer size;

	@JsonProperty("sort")
	private List<Map<String,String>> sort;
	
	@JsonProperty("filter")
	private Map<String, Object> filter;

	ElasticSearchJson(ElasticSearch elasticSearch){
		
		//query > query_string
		Map<String,Object> queryStringMap = new HashMap<String,Object>();
		JsonNodeFactory factory = JsonNodeFactory.instance;
 		ObjectNode fieldQueryMap = factory.objectNode();
 		fieldQueryMap.putPOJO("query", elasticSearch.getSearchValue());
 		fieldQueryMap.putPOJO("default_operator", elasticSearch.getDefaultOperator());
 		fieldQueryMap.putPOJO("fields", elasticSearch.getFields());
		queryStringMap.put("query_string",fieldQueryMap);
		this.query = queryStringMap;
		
		
		// filter
		if( elasticSearch.getRange() !=null){
			Map<String,Object> filterMap = new HashMap<String,Object>();
			filterMap.put("range",elasticSearch.getRange());
			this.filter = filterMap;
		}
		
		// _source
		Map<String,Object> incudeMap = new HashMap<String,Object>();
		incudeMap.put("include",elasticSearch.getIncludes());
		this.source = incudeMap;

		//sort
		this.sort = elasticSearch.getSort();
		
		//paging
		this.from = elasticSearch.getFrom();
		this.size = elasticSearch.getSize();
		
	}


}
