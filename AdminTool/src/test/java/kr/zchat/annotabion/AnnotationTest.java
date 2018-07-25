package kr.zchat.annotabion;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationTest {
	
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void annotation() throws IllegalAccessException, InstantiationException {
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		
		MyContextContainer demo = new MyContextContainer();

        // MyOjbect 객체를 하나 받아옵니다.
        MyObject obj = demo.get(MyObject.class);

        logger.debug("name : {}" ,obj.getName()); // print is "My name is JDM."
        logger.debug("name : defaultValue" ,obj.getDefaultValue()); // print is "This is StringInjector."
        logger.debug("invalidType : {}" ,obj.getInvalidType()); // print is "null".
		
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}
