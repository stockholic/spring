package com.taxholic.web.admin.elasticsearch.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxholic.web.admin.elasticsearch.vo.ElasticIndexJson;
import com.taxholic.web.admin.elasticsearch.vo.ElasticMapping;
import com.taxholic.web.admin.elasticsearch.vo.ElasticMappingJson;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.DeleteMapping;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.type.TypeExist;
import io.searchbox.indices.aliases.GetAliases;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;


@Service
public class ElasticService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JestClient client;
	
	/**
	 * Index 조회 - GET _aliases
	 * @return
	 * @throws IOException
	 */
	public String indexList() throws IOException{
		
		JestResult result = client.execute(new GetAliases.Builder().build());
		
		return result.getJsonString();
	}
	
	/**
	 * Index 생성
	 * @param indexNm
	 * @return
	 * @throws IOException
	 */
	public String createIndex(String indexNm) throws IOException{
		boolean indexExists = client.execute(new IndicesExists.Builder(indexNm).build()).isSucceeded();
		
		String resultMsg = "";
	
		if (!indexExists) {
			ElasticIndexJson indexQuery = new ElasticIndexJson(3,0,true);
			ObjectMapper mapper = new ObjectMapper();
			logger.debug( mapper.writeValueAsString(indexQuery) );
			
			JestResult result =  client.execute(new CreateIndex.Builder(indexNm).settings(mapper.writeValueAsString(indexQuery)).build());
			 
			 if(!result.isSucceeded())
				 resultMsg = result.getErrorMessage();
			 else
				 resultMsg = result.getJsonString();
		}else{
			resultMsg ="존재하는 Index";
		}
		
		return resultMsg;
		
	}
	
	/**
	 * Index 삭제
	 * @param indexNm
	 * @return
	 * @throws IOException
	 */
	public String deleteIndex(String indexNm) throws IOException{
		
		String resultMsg = "";
		
		boolean indexExists = client.execute(new IndicesExists.Builder(indexNm).build()).isSucceeded();
		
		if (indexExists) {
			JestResult result =  client.execute(new DeleteIndex.Builder(indexNm).build());
			if(!result.isSucceeded())
				resultMsg = result.getErrorMessage();
			else
				resultMsg = result.getJsonString();
		}else{
			resultMsg ="존재하는 않는 Index";
		}
		return resultMsg;
	}
	
	
	/**
	 * Mapping 생성
	 * @param indexNm
	 * @param typeNm
	 * @param propKey
	 * @param propValue
	 * @return
	 * @throws IOException
	 */
	public String createMapping(String indexNm, String typeNm, List<String> propKey, List<String> propValue) throws IOException{
		
		boolean indexExists = client.execute(new IndicesExists.Builder(indexNm).build()).isSucceeded();
		boolean typeExists = client.execute(new TypeExist.Builder(indexNm).addType(typeNm).build()).isSucceeded();
		
		String resultMsg = "N/A";
		
		if(!indexExists) {
			resultMsg ="존재하지 않는 Index";
		}else	if(typeExists){
			resultMsg ="존재하는 Type";
		}
	
		if (indexExists && !typeExists) {
			
			ElasticMapping elasticMapping = new ElasticMapping();
			elasticMapping.setPropKey(propKey);
			elasticMapping.setPropValue(propValue);
			
			ElasticMappingJson mappingQuery = new ElasticMappingJson(elasticMapping);
			ObjectMapper mapper = new ObjectMapper();
			logger.debug( mapper.writeValueAsString(mappingQuery) );
			
			PutMapping putMapping = new PutMapping.Builder(
				indexNm,
				typeNm,
		        mapper.writeValueAsString(mappingQuery).toString()
			).build();
			
			JestResult result = client.execute(putMapping);
			
			 if(!result.isSucceeded())
				 resultMsg = result.getErrorMessage();
			 else
				 resultMsg = result.getJsonString();
		}
		
		return resultMsg;
		
	}
	
	
	/**
	 * Mapping 삭제
	 * @param indexNm
	 * @param typeNm
	 * @return
	 * @throws IOException
	 */
	public String deleteMapping(String indexNm, String typeNm) throws IOException{
		
		boolean indexExists = client.execute(new IndicesExists.Builder(indexNm).build()).isSucceeded();
		boolean typeExists = client.execute(new TypeExist.Builder(indexNm).addType(typeNm).build()).isSucceeded();
		
		String resultMsg = "";
		
		if(!indexExists) {
			resultMsg ="존재하지 않는 Index";
		}else	if(!typeExists){
			resultMsg ="존재하지 않는 Type";
		}
		
		if (indexExists && typeExists) {
			JestResult result =  client.execute(new DeleteMapping.Builder(indexNm, typeNm).build());
			if(!result.isSucceeded())
				resultMsg = result.getErrorMessage();
			else
				resultMsg = result.getJsonString();
		}
		return resultMsg;
	}
	
	
	/**
	 * Mapping 조회 - GET <index>/_mapping
	 * @return
	 * @throws IOException
	 */
	public String  mappingList() throws IOException{
		
		JestResult result = client.execute(new GetMapping.Builder().build());
		return result.getJsonString();
		
	}
}
