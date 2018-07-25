package com.taxholic.lamda;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.lamda.vo.StudentCollect;


/**
 *  스트림은 요소들을 필터링 또는 매핑한 후 요소들을 수집하는 최종 처리 메소드인 collect()를 제공
 *  Stream의 collect(Collector<T, A, R> collector) 메소드는 필터링 또는 매핑된 요소들을 새로운 컬렉션에 수집하고, 이 컬렉션을 리턴
 *  Collector의 타입 파라미터 T는 요소이고, A는 누적기(Accumulator), R은 요소가 저장될 컬렉션
 *  풀어서 해석하면 T 요소를 A 누적기가 R에 저장한다는 의미입니다. 
 *  Collector의 구현 객체는 다음과 같이 Collectors 클래스의 다양한 정적 메소드를 이용해서 얻을 수 있음
 *  
 *  리턴 타입						Collectors의 정적 메소드					 설명 
 *  Collector<T, ?, List<T>> 	toList()											 T를 List에 저장 
 *  
 * @author jspark
 *
 */
public class Test_12_Collector extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void doit() {
	
		 List<StudentCollect> studentList = Arrays.asList(
	                new StudentCollect("Jolie", 19, StudentCollect.Sex.FEMALE),
	                new StudentCollect("Anne", 21, StudentCollect.Sex.FEMALE),
	                new StudentCollect("Martin", 17, StudentCollect.Sex.MALE),
	                new StudentCollect("Pierre", 15, StudentCollect.Sex.MALE),
	                new StudentCollect("Garcons", 19, StudentCollect.Sex.MALE)
	        );
	        
	     // 남학생들만 묶어서 List 생성
		 
		 //원칙
//		 Stream<StudentCollect> totalStream = studentList.stream();
//		 Stream<StudentCollect> maleStream = totalStream.filter( s -> s.getSex() == StudentCollect.Sex.MALE);
//		 Collector<StudentCollect, ?, List<StudentCollect>> collector = Collectors.toList();
//		 List<StudentCollect> maleList = maleStream.collect(collector);
		 
		 //압축
		 List<StudentCollect> maleList = studentList.stream()
                .filter( s -> s.getSex() == StudentCollect.Sex.MALE)
                .collect(Collectors.toList());
        
		 maleList.stream()
            .forEach(s -> System.out.println(s.getName()));
        
		 System.out.println();
        
        // 여학생들만 묶어서 HashSet 생성
		 Set<StudentCollect> femaleSet = studentList.stream()
                .filter( s -> s.getSex() == StudentCollect.Sex.FEMALE)
                .collect(Collectors.toCollection(HashSet :: new));
        
		 femaleSet.stream()
            .forEach(s -> System.out.println(s.getName()));
        
	}

		
}
