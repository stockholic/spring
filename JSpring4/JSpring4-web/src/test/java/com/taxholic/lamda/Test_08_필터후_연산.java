package com.taxholic.lamda;

import java.util.Arrays;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.lamda.vo.Member;


/**
 *  filter( m -> m.getSex() == Member.MALE)는 남자 Member 객체를 요소로 하는 새로운 스트림을 생성합니다. 
 *  mapToInt(Member :: getAge())는 Member 객체를 age 값으로 매핑해서 age를 요소로 하는 새로운 스트림을 생성합니다.
 *  
 * @author jspark
 *
 */
public class Test_08_필터후_연산 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	public static int sum;

	@Test
	public void doit() {
		 
		List<Member> list = Arrays.asList(
                new Member("Kush", Member.MALE, 40),
                new Member("Pierre", Member.MALE, 22),
                new Member("Jolie", Member.FEMALE, 18),
                new Member("Sozi", Member.FEMALE, 22)
        );
        
        double ageAvg = list.stream()
                .filter(m -> m.getSex() == Member.MALE)
                .mapToInt(Member :: getAge)
                .average()
                .getAsDouble();
        
        System.out.println("남자 평균 나이: " + ageAvg);
	}
		
}
