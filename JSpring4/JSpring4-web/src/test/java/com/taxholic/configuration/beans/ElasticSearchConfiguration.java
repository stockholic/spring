package com.taxholic.configuration.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;


@Configuration
public class ElasticSearchConfiguration  {
	
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
}
