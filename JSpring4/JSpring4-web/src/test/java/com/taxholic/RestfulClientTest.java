package com.taxholic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.taxholic.web.restful.vo.Address;
import com.taxholic.web.restful.vo.Person;

public class RestfulClientTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RestTemplate restTemplate;
	
	private @Value("${api.test.url}") String apiUrl;
	
	@Test
	public void getPersion1() {
		
		logger.debug("-------------------------------------------------------------------------------> start " + apiUrl);
        
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "샬랑");
        
//        Person person = restTemplate.getForObject(apiUrl + "/rest/json/{id}", Person.class,100);
        Person person = restTemplate.postForObject(apiUrl + "/rest/json/{id}",params, Person.class,100);
//        
        logger.debug("ID: " + person.getId());
        logger.debug("Name: " + person.getName());
        logger.debug("Village Name: " + person.getAddress().getVillage());
//		
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	@Test
	public void getPersion2() {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "9999");
        map.put("name", "샬랑");
        
        Address address = new Address("야동동", "파주", "경기도");
        Person person= restTemplate.postForObject(apiUrl + "/rest/json/{id}/{name}", address, Person.class,map);
        
        logger.debug("ID: " + person.getId());
        logger.debug("Name: " + person.getName());
        logger.debug("Village Name: " + person.getAddress().getVillage());
		
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}
