package com.taxholic.elastic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author jspark
 * 
 POST news/stock
 {
     "properties": {
         "seq": { "type": "integer", "index": "not_analyzed" },
         "title": { "type": "string", "analyzer": "korean_analyzer"},
         "description": { "type": "string", "analyzer": "korean_analyzer"},
         "regdate": { "type": "date"}
     }
 }
 *
 */
public class ElasticMappingJson {

	@JsonProperty("properties")
	private Map<String, Object> properties;
	
	
	public ElasticMappingJson(ElasticMapping elasticMapping) throws JsonParseException, JsonMappingException, IOException{
		
		Map<String, Object> props = new HashMap<String,Object>();
		ObjectMapper mapper = new ObjectMapper();

		for(int i = 0; i < elasticMapping.getPropKey().size(); i++){
			props.put(elasticMapping.getPropKey().get(i), mapper.readValue(elasticMapping.getPropValue().get(i), Object.class) );
		}
		this.properties = props;
		
	}

}
