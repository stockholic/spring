package com.taxholic.lamda;

import java.util.Arrays;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.lamda.vo.Student;


/**
 * List에 저장되어 있는 Student객체를 중간처리해서 score 필드값에 매핑하고, 최종 처리에서 score의 평균 값을 산출하는 예제입니다.
 * @author jspark
 *
 */
public class Test_06_Map을Int변환후_합_평균연산 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	

	@Test
	public void doit() {
		 
		 List<Student> list = Arrays.asList(
	                new Student("John", 76), 
	                new Student("Jack", 88), 
	                new Student("Smith", 100),
	                new Student("hi", 20)
	        );
	 
		 	int sum = list.stream().mapToInt(Student::getScore).sum();
	        double avg = list.stream().mapToInt(Student::getScore).average().getAsDouble();
	        
	        System.out.println("합산: " + sum);
	        System.out.println("평균 점수: " + avg);
	    }
		
}
