package com.taxholic.lamda;

import org.junit.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.lamda.inface.FncInterface;


/**
 *  (타입 매개변수, ...) -> { 실행문; ... }
 *  (타입 매개변수, ...)는 오른쪽 중괄호 { } 블록을 실행하기 위해 필요한 값을 제공하는 역할을 합니다.
 *  매개 변수의 이름은 개발자가 자유롭게 지정할 수 있습니다.
 * -> 기호는 매개 변수를 이용해서 중괄호 { }를 실행한다는 뜻으로 해석하면 됩니다.
 * (int a) -> { System.out.println(a); }
 * 	매개 변수 타입은 런타임 시에 대입되는 값에 따라 자동으로 인식될 수 있기 때문에 람다식에서는 매개 변수의 타입을 일반적으로 언급하지 않아도 됨
 * (a) -> { System.out.println(a); } 
 *  
 *  만약 매개 변수가 없다면 람다식에서 매개 변수 자리가 없어지므로 다음과 같이 빈 괄호 () 를 반드시 사용해야 합니다.
 *  () -> { statement; ... }
 *  
 * @author jspark
 *
 */
public class Test_14_interface_매개변수없을때 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void doit() {
	
		FncInterface fi;
		 
        fi = () -> {
            String str = "method call1";
            System.out.println(str);
        };
 
        fi.method();
 
        fi = () -> {
            System.out.println("method call2");
        };
        fi.method();
 
        fi = () -> System.out.println("method call3");
        fi.method();
    }
 
		
}
