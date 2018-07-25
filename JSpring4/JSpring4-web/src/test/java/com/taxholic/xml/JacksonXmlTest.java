package com.taxholic.xml;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.taxholic.BaseTestCase;

public class JacksonXmlTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void makeXml() throws JsonProcessingException {
		
		logger.debug("-------------------------------------------------------------------------------> start");
        
		
		ObjectMapper xmlMapper = new XmlMapper();
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		List<Employee> list = new ArrayList<Employee>();
		list.add(new Employee("", "", "", 1));
		list.add(new Employee("", "", "", 1));
		list.add(new Employee("", "", "", 1));
		list.add(new Employee("", "", "", 1));
		
		String xml = xmlMapper.writeValueAsString( new Employees(list) );
		
		
		logger.debug(xml);
		
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	

}
