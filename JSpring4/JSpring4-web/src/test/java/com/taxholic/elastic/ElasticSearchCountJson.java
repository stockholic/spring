package com.taxholic.elastic;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author jspark
 *
 GET news/stock/_count
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
    }
}
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ElasticSearchCountJson {

	@JsonProperty("query")
	private Map<String, Object> query;

	@JsonProperty("filter")
	private Map<String, Object> filter;
	

	ElasticSearchCountJson(ElasticSearchCount elasticSearchCount){
		
		//query > query_string
		Map<String,Object> queryStringMap = new HashMap<String,Object>();
		JsonNodeFactory factory = JsonNodeFactory.instance;
 		ObjectNode fieldQueryMap = factory.objectNode();
 		fieldQueryMap.putPOJO("query", elasticSearchCount.getSearchValue());
 		fieldQueryMap.putPOJO("default_operator",  elasticSearchCount.getDefaultOperator());
 		fieldQueryMap.putPOJO("fields", elasticSearchCount.getFields());
		queryStringMap.put("query_string",fieldQueryMap);
		this.query = queryStringMap;
		
		// filter
		if( elasticSearchCount.getRange() !=null){
			Map<String,Object> filterMap = new HashMap<String,Object>();
			filterMap.put("range",elasticSearchCount.getRange());
			this.filter = filterMap;
		}
		
	}


}
