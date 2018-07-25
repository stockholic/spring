package kr.zchat;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


public class DaoEncryptTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	private String password = "qkrtjqkddhkTsi";
	
	/**
	 * 스프링 암호화
	 */
	@Test
	public void bcrypttTest() {
		logger.debug("-------------------------------------------------------------------------------> start");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		logger.debug(hashedPassword);
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	
	

	
	
}
