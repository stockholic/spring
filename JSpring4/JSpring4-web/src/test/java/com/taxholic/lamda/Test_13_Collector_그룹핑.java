package com.taxholic.lamda;

import java.util.Arrays;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.lamda.vo.StudentCollect;


/**
 *  그룹핑 후, 매핑이나 집계(평균, 카운팅, 연결, 최대, 최소, 합계)를 할 수 있도록 두 번째 파라미터로 Collector를 가질 수 있다.
 *  
 *  
 * @author jspark
 *
 */
public class Test_13_Collector_그룹핑 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void doit() {
	
		List<StudentCollect> list = Arrays.asList(
				new StudentCollect("박종식", 22, StudentCollect.Sex.MALE, StudentCollect.City.Seoul),
				new StudentCollect("박종식", 10, StudentCollect.Sex.MALE, StudentCollect.City.Seoul),
                new StudentCollect("박재곤", 100, StudentCollect.Sex.GAY, StudentCollect.City.Pusan),
                new StudentCollect("김태희", 18, StudentCollect.Sex.FEMALE, StudentCollect.City.Seoul),
                new StudentCollect("아이유", 18, StudentCollect.Sex.FEMALE, StudentCollect.City.Pusan),
                new StudentCollect("수지", 22, StudentCollect.Sex.FEMALE, StudentCollect.City.Pusan));
		
		logger.debug("---------------------------------------------------------------------- 전체 / 쇼트");
        
		Comparator<StudentCollect> byScore = (e1, e2) -> Integer.compare(e1.getScore(), e2.getScore());
		Comparator<StudentCollect> byName = (e1, e2) -> e1.getName().compareTo(e2.getName()); 
		
        Map<String, List<StudentCollect>> resMap = list.stream()
//        																.sorted(byName)
        																.sorted(byName.thenComparing(byScore))
        																.collect(Collectors.groupingBy( s -> s.getSex().toString()));
        System.out.println("개수 : " + resMap.size());
        resMap.forEach( (k,v) ->  v.forEach(s-> System.out.println(s.getSex() +":"+ s.getName() + ":" + s.getScore())));
		
        
        logger.debug("---------------------------------------------------------------------- 성별평균");
        
        
        // 성별로 평균 점수를 저장하는 Map 얻기
        Map<StudentCollect.Sex, Double> mapBySex = list.stream()
                .collect(
                    Collectors.groupingBy(StudentCollect :: getSex,
                    Collectors.averagingDouble(StudentCollect :: getScore)
                )
            );
        
        System.out.println( mapBySex);
        System.out.println("남학생 평균 점수: " + mapBySex.get(StudentCollect.Sex.MALE));
        System.out.println("여학생 평균 점수: " + mapBySex.get(StudentCollect.Sex.FEMALE));
        
        
        logger.debug("----------------------------------------------------------------------");
        
        
        // 성별을 쉼표로 구분한 이름을 저장하는 Map 얻기
        Map<StudentCollect.Sex, String> mapByName = list.stream()
                .collect(
                    Collectors.groupingBy(StudentCollect :: getSex,
                    Collectors.mapping(StudentCollect :: getName, Collectors.joining(","))
                    )
                );
        
        System.out.println( mapByName);
        System.out.println("남학생 전체 이름: " + mapByName.get(StudentCollect.Sex.MALE));
        System.out.println("여학생 전체 이름: " + mapByName.get(StudentCollect.Sex.FEMALE));
    }
 
		
}
