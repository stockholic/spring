package com.taxholic.lamda.inface;

/**
 * 모든 인터페이스를 람다식의 타겟 타입으로 사용할 수 없습니다. 
 * 람다식이 하나의 메소드를 정의하기 때문에 두 개 이상의 추상 메소드가 선언된 인터페이스는 람다식을 이용해서 구현 객체를 생성할 수 없습니다.
 * @FunctionalInterface 어노테이션은 선택사항입니다. 이 어노테이션이 없더라도 하나의 추상 메소드만 있다면 모두 함수적 인터페이스입니다.
 *  
 * @author jspark
 *
 */
@FunctionalInterface
public interface  FncInterface3 {
	public int method(int x, int y);
}
