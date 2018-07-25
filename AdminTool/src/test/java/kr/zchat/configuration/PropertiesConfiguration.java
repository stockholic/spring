package kr.zchat.configuration;


import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


@Configuration
public class PropertiesConfiguration {
	
	 @Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException   {
	 	PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		Resource[] locations = patternResolver.getResources("classpath:application.properties");
		configurer.setLocations(locations);
		configurer.setIgnoreUnresolvablePlaceholders(true);
		configurer.setIgnoreResourceNotFound(true);

		return configurer;
	}
    
    
    
}