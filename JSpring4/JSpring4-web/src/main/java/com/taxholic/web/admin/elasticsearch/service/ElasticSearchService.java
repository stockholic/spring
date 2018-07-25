package com.taxholic.web.admin.elasticsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxholic.core.util.StringUtil;
import com.taxholic.web.admin.elasticsearch.vo.ElasticSearch;
import com.taxholic.web.admin.elasticsearch.vo.ElasticSearchJson;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


@Service
public class ElasticSearchService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JestClient client;
	
	public List<ElasticSearch> searchList(ElasticSearch elasticSearch) throws IOException{
		
		ElasticSearchJson searchQuery = new ElasticSearchJson(elasticSearch);
		ObjectMapper mapper = new ObjectMapper();
//		logger.debug( mapper.writeValueAsString(searchQuery) );
		
		SearchResult result = client.execute(new Search.Builder(
				mapper.writeValueAsString(searchQuery))
				.addIndex(elasticSearch.getIndexNm())
				.addType(elasticSearch.getTypeNm())
				.build()
		);
		
		List<SearchResult.Hit<ElasticSearch, Void>> hits = result.getHits(ElasticSearch.class);
		
//		logger.debug( "total : {}", result.getTotal());
		elasticSearch.setTotal(result.getTotal());
		
		List<ElasticSearch> list = new ArrayList<ElasticSearch>();
		for (SearchResult.Hit<ElasticSearch, Void> hit: hits) {
			ElasticSearch es = hit.source;

			if(hit.highlight.get("title") !=null ) {
				es.setTitle(hit.highlight.get("title").toString());
			}else{
				es.setTitle(StringUtil.strlenUTF(es.getTitle(), 100, "..."));
			}
			
			if(hit.highlight.get("description") !=null ) {
				es.setDescription(hit.highlight.get("description").toString());
			}else{
				es.setDescription(StringUtil.strlenUTF(StringUtil.removeTag(es.getDescription()), 200, "..."));
			}
			
			list.add(es);
//			logger.debug( es.getTitle() +" - "+ es.getRegdate());
		}
		
		return list;
	}
	
}
