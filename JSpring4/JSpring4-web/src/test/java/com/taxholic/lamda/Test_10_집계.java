package com.taxholic.lamda;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  여러가지 집계 방식
 * @author jspark
 *
 */
public class Test_10_집계 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void doit() {
	
		logger.debug("---------------------------------------------------------------------- count");
		int[] intArr = {5, 8, 11, 13, 19, 20, 24};
        long count = Arrays.stream(intArr)
                .filter( n -> n % 2 == 0)
                .count();
        System.out.println("2의 배수 개수: " + count);
        
        logger.debug("---------------------------------------------------------------------- sum");
        long sum = Arrays.stream(intArr)
                .filter( n -> n % 2 == 0)
                .sum();
        System.out.println("2의 배수의 합: " + sum);
        
        logger.debug("---------------------------------------------------------------------- max");
        int max = Arrays.stream(intArr)
        		.max()
        		.getAsInt();
        System.out.println("max: " + max);
        
        logger.debug("---------------------------------------------------------------------- min");
        int min = Arrays.stream(intArr)
        		.min()
        		.getAsInt();
        System.out.println("min: " + min);
        
        logger.debug("---------------------------------------------------------------------- average");
        double avg = Arrays.stream(intArr)
                .average()
                .getAsDouble();
        System.out.println("배열의 평균; " + avg);
        
        logger.debug("---------------------------------------------------------------------- 첫번째요소");
        int third = Arrays.stream(intArr)
                .filter(n -> n % 3 == 0)
                .findFirst()
                .getAsInt();
        System.out.println("3의 배수: " + third);
        
        
	}

		
}
