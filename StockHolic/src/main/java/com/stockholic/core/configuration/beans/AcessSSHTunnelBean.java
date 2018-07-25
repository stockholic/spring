package com.stockholic.core.configuration.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.stockholic.core.utils.JSchUtil;

@Component
public class AcessSSHTunnelBean {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${deploy}")
	 private  String deploy;
	
	 @Value("#{systemProperties['stockHolic.key']}") 
	 private String key;
	 
	 private  Session session;
	 
	 private  String host = "218.38.12.215";
	 private  String user = "merong";
	 private  String password = "qkrtjqkddhkTsi";
	 
	
	@PostConstruct
	public void init() throws JSchException {
		
		if("local".equals(deploy)) {
			 JSchUtil js = new JSchUtil( host, 22,user ,password);
			 
			 Session session = js.getSession();
			 session.connect();
			 session.setPortForwardingL(3306, "localhost", 13271);
			 
			 logger.info("key : {}",key);
			 logger.info("deploy : {}, SSH forwarding {} To {}", deploy, "localhost",host);
		}
		
	}
	
	@PreDestroy
	public void close() {
		if("local".equals(deploy) && session != null) {
			session.disconnect();
			logger.info("deploy : {}, SSH forwarding disconnect", deploy);
		}
	}

}
