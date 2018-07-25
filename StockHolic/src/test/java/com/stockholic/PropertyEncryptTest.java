package com.stockholic;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stockholic.core.constants.SystemConstants;

public class PropertyEncryptTest {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	 private String USER = "aaa";
	 private String PASSWORD = "aaa";
	
	@Test
	public void encryptTest() {
		
		
		logger.debug("-------------------------------------------------------------------------------> start");
		
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setAlgorithm("PBEWithMD5AndDES");
		standardPBEStringEncryptor.setPassword(SystemConstants.ENCRYPT_KEY);
		
		logger.debug("Encrypted user  : "+ standardPBEStringEncryptor.encrypt(USER));
		logger.debug("Encrypted password  : "+ standardPBEStringEncryptor.encrypt(PASSWORD));
		
		logger.debug("-------------------------------------------------------------------------------> end");
	}
}
