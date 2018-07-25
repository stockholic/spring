package com.taxholic.lamda;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *  요소가 없을 경우 예외를 피하는 방법이 세 가지 방법 3가지
 *  1. Optional 객체를 얻어 isPresent() 메소드로 평균값 여부를 체크, isPresent() 메소드가 true를 리턴할 때만 getAsDouble() 메소드로 평균값
 *  2. orElse() 메소드로 디폴트 값 셋팅
 *  3. fPresent() 메소드로 평균값이 있을 경우에만 값을 이용하는 람다식을 실행
 * @author jspark
 *
 */
public class Test_11_집계_기본값셋팅 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void doit() {
	
		 List<Integer> list = new ArrayList<>();
	        
	        OptionalDouble optional = list.stream()
	                .mapToInt(Integer :: intValue)
	                .average();
	        
	        if (optional.isPresent()) {
	            System.out.println("True 방법 1 평균: " + optional.getAsDouble());
	        } else {
	            System.out.println("False 방법 1 평균: " + 0.0);
	        }
	        
	        double avg = list.stream()
	                .mapToInt(Integer :: intValue)
	                .average()
	                .orElse(0.0);
	        
	        System.out.println("방법 2 평균: " + avg);
	        
	        list.stream()
	            .mapToInt(Integer :: intValue)
	            .average()
	            .ifPresent(a -> System.out.println("방법 3 평균: " + a));
        
        
	}

		
}
