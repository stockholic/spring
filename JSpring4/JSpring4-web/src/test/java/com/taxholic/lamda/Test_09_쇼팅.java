package com.taxholic.lamda;

import java.util.Arrays;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.lamda.vo.StudentSort;


/**
 *  객체 (StudentSort)요소일 경우에는 클래스가 Comparable을 구현하지 않으면 sorted() 메소드를 호출했을 때 
 *  lassCastException이 발생하기 때문에 Comparable을 구현한 요소에서만 sorted() 메소드를 호출해야 합니다. 
 * @author jspark
 *
 */
public class Test_09_쇼팅 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void doit() {
	
		logger.debug("---------------------------------------------------------------------- Array sort");
		IntStream intStream = Arrays.stream(new int[] {9, 6, 11, 2, 3});
        intStream
            .sorted()
            .forEach(n -> System.out.print(n + ", "));
        
        System.out.println();
        
        
        List<StudentSort> studentList = Arrays.asList(
                new StudentSort("Jack", 80),
                new StudentSort("John", 100),
                new StudentSort("Andy", 99),
                new StudentSort("Jolie", 70)
        );
        
        logger.debug("---------------------------------------------------------------------- StudentSort");
       
        
        studentList.stream()
            .sorted()
            .forEach(s -> System.out.print(s.getScore() + ", "));
        
        System.out.println();
        
        
        logger.debug("---------------------------------------------------------------------- StudentSort reverseOrder");
        
        studentList.stream()
        .sorted( Comparator.reverseOrder())
        .forEach(s -> System.out.print(s.getScore() + ", "));
        
	}

		
}
