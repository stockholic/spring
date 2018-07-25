package com.taxholic.elastic;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxholic.BaseTestCase;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Count;
import io.searchbox.core.CountResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;

public class ElasticSearchTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JestClient client;
	
	private String index = "news";
	private String type = "stock";

	public void setUp() {
	}
	
	@Test
	public void createIndexTest() throws IOException {
		logger.debug("-------------------------------------------------------------------------------> start");
		
		boolean indexExists = client.execute(new IndicesExists.Builder(index).build()).isSucceeded();
		
		if (!indexExists) {
			ElasticIndexJson indexQuery = new ElasticIndexJson(3,0,true);
			ObjectMapper mapper = new ObjectMapper();
			logger.debug( mapper.writeValueAsString(indexQuery) );
			
			 JestResult result =  client.execute(new CreateIndex.Builder(index).settings(mapper.writeValueAsString(indexQuery)).build());
			 
			 if(!result.isSucceeded())
				logger.debug(result.getErrorMessage());
			 else
				logger.debug(result.getJsonString());
		}
		
		client.shutdownClient();
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void createMapping() throws IOException {
		logger.debug("-------------------------------------------------------------------------------> start");
		
		ElasticMapping elasticMapping = new ElasticMapping();
		List<String> propKey = Arrays.asList("seq","title","description","regdate");
		List<String> propValue = Arrays.asList(
				"{\"type\": \"integer\", \"index\": \"not_analyzed\"}",
				"{\"type\": \"string\", \"analyzer\": \"korean_analyzer\"}",
				"{\"type\": \"string\", \"analyzer\": \"korean_analyzer\"}",
//				"{\"type\": \"date\", \"format\" : \"yyyy/MM/dd HH:mm:ss\"}"
				"{\"type\": \"date\"}"
			);
		elasticMapping.setPropKey(propKey);
		elasticMapping.setPropValue(propValue);
		
		ElasticMappingJson mappingQuery = new ElasticMappingJson(elasticMapping);
		ObjectMapper mapper = new ObjectMapper();
		logger.debug( mapper.writeValueAsString(mappingQuery) );
		
		PutMapping putMapping = new PutMapping.Builder(
	        index,
	        type,
	        mapper.writeValueAsString(mappingQuery).toString()
		).build();
		
		JestResult result = client.execute(putMapping);
		
		 if(!result.isSucceeded())
			logger.debug(result.getErrorMessage());
		 else
			logger.debug(result.getJsonString());
		
		client.shutdownClient();
	
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void createDocument() throws IOException {
		logger.debug("-------------------------------------------------------------------------------> start");
		
		String[] title = {
			"엄마가 미장원에 들어가신다.",
			"아빠가 기원에 들어가신다.",
			"사과는 맛있다",
			"사과장사는 힘들다 어쩌라고",
			"딸기장사는 더 힘들다 어쩌라고",
		};
		
		for(int i = 0; i < title.length; i++){
			ElasticSearch es = new ElasticSearch();
			es.setSeq(i+1);
			es.setTitle(title[i]);
			Index idx = new Index.Builder(es).index(index).type(type).build();
			client.execute(idx);
			logger.debug("id : {}",idx.getId());
			
		}
		client.shutdownClient();
		
	
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void searchCountTest() throws IOException {
		logger.debug("-------------------------------------------------------------------------------> start");
		
		ElasticSearchCount elasticSearchCount = new ElasticSearchCount();
		elasticSearchCount.setSearchValue("롯데 신세계");
		
//		elasticSearchCount.setDefaultOperator("OR");
		
		//검색대상 필드
		List<String> fields = Arrays.asList("title","description");
		elasticSearchCount.setFields(fields);
		
		//날짜 범위
		elasticSearchCount.setStartDt("2012-04-01");
		elasticSearchCount.setEndDt("2016-01-01");
		
		ElasticSearchCountJson searchQuery = new ElasticSearchCountJson(elasticSearchCount);
		ObjectMapper mapper = new ObjectMapper();
		logger.debug( mapper.writeValueAsString(searchQuery) );
		
		 CountResult result = client.execute(new Count.Builder()
	                .query(mapper.writeValueAsString(searchQuery))
	                .addIndex(index)
	                .addType(type)
	                .build());
		 
		 logger.debug( "count : {}" ,result.getCount().intValue());
		
		client.shutdownClient();
	  
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	
	@Test
	public void searchTest() throws IOException {
		logger.debug("-------------------------------------------------------------------------------> start");
		
		ElasticSearch elasticSearch = new ElasticSearch();
		elasticSearch.setSearchValue("롯데 신세계");
		
//		elasticSearch.setDefaultOperator("OR");
		
		//검색대상 필드
		List<String> fields = Arrays.asList("title","description");
		elasticSearch.setFields(fields);
		
		//날짜 범위
		elasticSearch.setStartDt("2012-04-01");
		elasticSearch.setEndDt("2016-01-01");
		
		//결과 표시 필드
		List<String> includes = Arrays.asList("title","description","regdate");
		elasticSearch.setIncludes(includes);
		
		//Paging
		elasticSearch.setFrom(0);
		elasticSearch.setSize(10);
		
//		//sort
//		List<String> sortKey = Arrays.asList("_score");
//		elasticSearch.setSortKey(sortKey);
//		List<String> sortValue = Arrays.asList("desc");
//		elasticSearch.setSortValue(sortValue);
		
		//sort
		List<String> sortKey = Arrays.asList("regdate");
		elasticSearch.setSortKey(sortKey);
		List<String> sortValue = Arrays.asList("desc");
		elasticSearch.setSortValue(sortValue);
		
		
		ElasticSearchJson searchQuery = new ElasticSearchJson(elasticSearch);
		ObjectMapper mapper = new ObjectMapper();
		logger.debug( mapper.writeValueAsString(searchQuery) );
		
		SearchResult result = client.execute(new Search.Builder(
				mapper.writeValueAsString(searchQuery))
				.addIndex(index)
				.addType(type)
				.build()
		);
		
		client.shutdownClient();
	  
		List<SearchResult.Hit<ElasticSearch, Void>> hits = result.getHits(ElasticSearch.class);
		
		logger.debug( "total : {}", result.getTotal());
		
		for (SearchResult.Hit<ElasticSearch, Void> hit: hits) {
			ElasticSearch es = hit.source;
			logger.debug( es.getTitle() +" - "+ es.getRegdate());
		}

		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	
	@Test
	public void deleteIndexTest() throws IOException {
		logger.debug("-------------------------------------------------------------------------------> start");
		
		boolean indexExists = client.execute(new IndicesExists.Builder(index).build()).isSucceeded();
		
		if (indexExists) {
			JestResult result =  client.execute(new DeleteIndex.Builder(index).build());
			if(!result.isSucceeded())
				logger.debug(result.getErrorMessage());
			else
				logger.debug(result.getJsonString());
		}
		
		client.shutdownClient();
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
}
