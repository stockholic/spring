package com.taxholic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.taxholic.core.authority.AuthDto;
import com.taxholic.web.test.dto.EncryptDto;
import com.taxholic.web.test.service.TestService;

public class DaoEncryptTest extends BaseTestCase{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	EncryptDto  dto = new EncryptDto();
	
	@Autowired
	private MessageSourceAccessor message;
	
	public void setUp() {
		dto.setUserId("stock");
		dto.setPasswd("1234");
		dto.setUserNm("메롱");
		
	}
	
	/**
	 * 스프링 암호화
	 */
	@Test
	public void bcrypttTest() {
		logger.debug("-------------------------------------------------------------------------------> start");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(dto.getPasswd());
		logger.debug(hashedPassword);
		//dto.setPasswd(hashedPassword);

		//testService.insertUserEncrypt(dto);
		
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
	
	
	

	
	
}
