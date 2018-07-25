package com.taxholic.lamda;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 스트림(Stream)은 자바 8부터 추가된 컬렉션(배열 포함)의 저장 요소를 하나씩 참조해서
 *  람다식(functional-style)으로 처리할 수 있도록 해주는 반복자입니다.
 * @author jspark
 *
 */
public class Test_04_이터레이터VS스트림 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	

	@Test
	public void doit() {
		 
		List<String> list = Arrays.asList("John", "Simons", "Andy");
		 
        // Iterator 이용
        Iterator<String> iterator = list.iterator();
 
        while (iterator.hasNext()) {
            String name = iterator.next();
            System.out.println(name);
        }
 
        
        logger.debug("----------------------------------------------------------------------");
        
        // Stream 이용
        Stream<String> stream = list.stream();
        stream.forEach(name -> System.out.println(name));
		
	}
	
}
