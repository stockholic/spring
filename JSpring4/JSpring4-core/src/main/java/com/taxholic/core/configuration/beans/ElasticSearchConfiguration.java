package com.taxholic.core.configuration.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;


@Configuration
public class ElasticSearchConfiguration implements InitializingBean{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${deploy}")
	private String deploy;

	@Value("${elasticsearch.search.endpoint}")
	private String address;

	@Value("${elasticsearch.search.maxConnection}")
	private int maxTotalConnection;

	@Value("${elasticsearch.search.connTimeout}")
	private int connTimeout;

	@Value("${elasticsearch.search.readTimeout}")
	private int readTimeout;
	

	@Bean
	public JestClient jestClient(){
		HttpClientConfig clientConfig = new HttpClientConfig.Builder(address)
						.multiThreaded(true)
						.maxTotalConnection(maxTotalConnection)
						.connTimeout(connTimeout)
						.readTimeout(readTimeout)
						.build();
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(clientConfig);
		JestClient client = factory.getObject();
		return client;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("--------------------------- " + deploy +" : ElasticSearch");
		logger.info("Address : " + address);
	}
}
