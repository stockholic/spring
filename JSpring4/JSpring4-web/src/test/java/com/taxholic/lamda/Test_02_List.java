package com.taxholic.lamda;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.lamda.vo.Student;


/**
 * Stream이 제공하는 대부분의 요소 처리 메소드는 함수적 인터페이스 매개 타입을 가지기 때문에 
 * 람다식 또는 메소드 참조를 이용해서 요소 처리 내용을 파라미터로 전달할 수 있습니다.
 * @author jspark
 *
 */
public class Test_02_List extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	

	@Test
	public void doit() {
		 
		List<Student> list = Arrays.asList(
				new Student("John Smith", 91), 
				new Student("Andy Knight", 93)
		);
		 
        Stream<Student> stream = list.stream();
//        stream.forEach(s -> {
//            String name = s.getName();
//            int score = s.getScore();
// 
//            System.out.println(name + " - " + score);
//        });
        
        stream.forEach(s -> System.out.println(s.getName()  +" : "+ s.getScore() ));
		
	}
	
}
