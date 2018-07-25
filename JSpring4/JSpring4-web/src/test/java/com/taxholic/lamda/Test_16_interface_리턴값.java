package com.taxholic.lamda;

import org.junit.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taxholic.lamda.inface.FncInterface3;


/**
 *  method() 가 리턴 타입이 있기 때문에 중괄호 {}에는 return 문이 있어야 합니다.
 *  
 *  만약 중괄호 {}에 return 문만 있고, return 문 뒤에 연산식이나 메소드 호출이 오는 경우라면 다음과 같이 작성할 수 있습니다.
 *  
 * @author jspark
 *
 */
public class Test_16_interface_리턴값 extends Assert {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void doit() {
	
		FncInterface3 fi;
		 
		fi = (x, y) -> {
            int result = x + y;
 
            return result;
        };
        System.out.println(fi.method(2, 5));
 
        fi = (x, y) -> {
            return x + y;
        };
        System.out.println(fi.method(2, 5));
 
        fi = (x, y) -> x + y;
        System.out.println(fi.method(2, 5));
 
        fi = (x, y) -> sum(x, y);
        System.out.println(fi.method(2, 5));
    }
 
    public static int sum(int x, int y) {
        return x + y;
    }
 
		
}
