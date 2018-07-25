package com.taxholic.lamda;

import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  다음은 숫자 1부터 100까지의 합을 구하기 위해 IntStream의 rangeClosed() 메소드를 이용했습니다.
 *   rangeClosed()는 첫 번째 파라미터부터 두 번째 파라미터까지 순차적으로 제공하는 IntStream을 리턴합니다. 
 *  
 * @author jspark
 *
 */
public class Test_05_Int범위설정 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	public static int sum;

	@Test
	public void doit() {
		 
		IntStream stream = IntStream.rangeClosed(1, 100);
        stream.forEach(a -> sum += a);
 
        System.out.println("1 ~ 100 총합: " + sum);
	    }
		
}
