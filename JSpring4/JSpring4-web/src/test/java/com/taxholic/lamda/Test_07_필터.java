package com.taxholic.lamda;

import java.util.Arrays;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  filter() 메소드는 파라미터로 주어진 Predicate가 true를 리턴하는 요소만 필터링합니다.
 *  
 * @author jspark
 *
 */
public class Test_07_필터 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void doit() {
	
		 List<String> names = Arrays.asList(
	                "Jack Daniel",
	                "Andy Smith",
	                "Demian Rice",
	                "Mike Tomson",
	                "Jack Daniel",
	                "Jolie Martonne"
	                
	        );
	        
		 logger.debug("---------------------------------------------------------------------- distinct()");

		 names.stream()
	            .distinct()
	            .forEach(n -> System.out.println(n));
	        
		 logger.debug("---------------------------------------------------------------------- filter()");   
		 
		 names.stream()
	            .filter(n -> n.startsWith("J"))
	            .forEach(n -> System.out.println(n));
	     
	        
		 logger.debug("---------------------------------------------------------------------- distinct() filter()");   
		 
	        names.stream()
	            .distinct()
	            .filter(n -> n.startsWith("J"))
	            .forEach(n -> System.out.println(n));
	    }
		
}
