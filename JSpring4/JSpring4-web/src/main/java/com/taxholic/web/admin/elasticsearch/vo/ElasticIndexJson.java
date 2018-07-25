package com.taxholic.web.admin.elasticsearch.vo;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author jspark
 * 
POST news
{
    "settings": {
        "number_of_shards": 3,
        "number_of_replicas": 0,
        "analysis": {
            "analyzer": {
                "korean_analyzer": {
                    "type": "custom",
                    "tokenizer": "seunjeon_default_tokenizer",
                    "char_filter": [
                  		"html_strip"
               		]
                }
            },
            "tokenizer": {
                "seunjeon_default_tokenizer": {
                    "type": "seunjeon_tokenizer"
                }
            }
        }
    }
}
 *
 */
public class ElasticIndexJson {

	@JsonProperty("settings")
	private ObjectNode settings;
	
	
	public ElasticIndexJson(int shards, int replicas, boolean isAnalysis){
		
		 JsonNodeFactory factory = JsonNodeFactory.instance;
		
		 ObjectNode analysisNode = factory.objectNode();
		 if(isAnalysis){
			 //------------------------------ analysis S
				ObjectNode analyzer = factory.objectNode();
		 		ObjectNode korean_analyzer = factory.objectNode();
		 		analyzer.putPOJO("korean_analyzer",korean_analyzer);
		 			korean_analyzer.put("type", "custom");
		 			korean_analyzer.put("tokenizer", "seunjeon_default_tokenizer");
		 			korean_analyzer.putPOJO("char_filter", Arrays.asList("html_strip"));
		 			
		 		ObjectNode tokenizer = factory.objectNode();
		 		ObjectNode seunjeon_default_tokenizer = factory.objectNode();
		 		tokenizer.putPOJO("seunjeon_default_tokenizer",seunjeon_default_tokenizer);
		 			seunjeon_default_tokenizer.put("type", "seunjeon_tokenizer");
		 		
		 	analysisNode.putPOJO("analyzer",analyzer);
		 	analysisNode.putPOJO("tokenizer",tokenizer);
		 	//------------------------------ analysis E
		 }
		 	
		 ObjectNode notdes = factory.objectNode();
		 notdes.putPOJO("number_of_shards", shards);	
		 notdes.putPOJO("number_of_replicas", replicas);	
		 if(isAnalysis){
			 notdes.putPOJO("analysis", analysisNode);	
		 }
		
		this.settings = notdes;
		
	}

}
