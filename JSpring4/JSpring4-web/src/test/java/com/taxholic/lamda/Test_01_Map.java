package com.taxholic.lamda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Test_01_Map extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	

	@Test
	public void doit() {
		 
		List<String> lst = new ArrayList<String>(){{
			add("강아지");
			add("고양이");
			add("비둘기");
		}};
		
//	    lst.forEach(System.out::println);
	    lst.forEach(name-> System.out.println(name));  
	    
	    logger.debug("----------------------------------------------------------------------");
	    
	    Map<String, String> map = new HashMap<String,String>(){{
	    	put("이름1","얄랑");	    	
	    	put("이름2","샬랑");	    	
	    }};
	    
	    map.forEach( (k,v) -> System.out.println("key : " + k + " / " + "value : " + v) );
		
	}
	
}
