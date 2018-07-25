package com.taxholic.lamda;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 다음 예제는 String[]과 int[] 배열로부터 스트림을 얻어내고 콘솔에 출력합니다.
 * @author jspark
 *
 */
public class Test_03_Array extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	

	@Test
	public void doit() {
		 
		 String[] strArr = { "Kim", "Lee", "Park" };
	        Stream<String> strStream = Arrays.stream(strArr);
	        strStream.forEach(s -> System.out.print(s + ","));
	 
	        System.out.println();
	 
	        int[] intArr = { 10, 20, 30, 40, 50 };
	        IntStream intStream = Arrays.stream(intArr);
	        intStream.forEach(i -> System.out.print(i + ", "));
	    }
		
}
