package com.taxholic.core.configuration.web;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.taxholic.core.authority.UserHandlerMethodArgumentResolver;
import com.taxholic.core.util.ExcelView;

@Configuration
@EnableWebMvc
//@Import({TilesConfiguration.class, PropertiesConfiguration.class, MessageConfiguration.class, HttpClientConfiguration.class})
@ComponentScan(basePackages = {"com.taxholic.web"}, includeFilters = {
													@ComponentScan.Filter(Controller.class), @ComponentScan.Filter(ControllerAdvice.class)
												  }, useDefaultFilters = false)
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {
	
	/**
	 * User argumentResolvers
	 */
	@Override
	 public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userHandlerMethodArgumentResolver());
	 }
	 
   	/**
   	* static resources
   	*/
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       	registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
	
	/**
	 * argument를 이용한 로그인 사용자 정보
	 * @return
	 */
	 @Bean
	 public UserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver() {
		 return new UserHandlerMethodArgumentResolver();
	 }
	 
	/**
	 * Json message converter 
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
	    mappingJackson2HttpMessageConverter.setPrettyPrint(true);
	    return mappingJackson2HttpMessageConverter;
	}
	 
	/**
	 * XML message converter 
	 * @return
	 */
	@Bean
	public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter() {
		MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter = new MappingJackson2XmlHttpMessageConverter();
		mappingJackson2XmlHttpMessageConverter.setPrettyPrint(true);
	    return mappingJackson2XmlHttpMessageConverter;
	}    
	
	/**
	 * Http message configure
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);        
        converters.add(mappingJackson2HttpMessageConverter());
        converters.add(mappingJackson2XmlHttpMessageConverter());
    }
	
	/**
	 * messageSource 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() throws IOException {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:message/server");
		messageSource.setCacheSeconds(5);

		return messageSource;
	}

	/**
	 * MessageSourceAccessor 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public MessageSourceAccessor messageSourceAccessor() throws IOException {
		return new MessageSourceAccessor(messageSource());
	}
	
	/**
	 * Max FileSize
	 * @return
	 */
    @Bean
    public MultipartResolver multipartResolver() {
    	CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	    resolver.setMaxUploadSize(1024 * 1024 * 600);
	    
	    return resolver;
    }

    /**
     * Tiles View
     * @return
     */
    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        resolver.setOrder(1);
        return resolver;
    }
 
    /**
     * Tiles View
     * @return
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[] {"classpath:config/tiles/tiles-*.xml"});
        return tilesConfigurer;
    }
    
    /**
     * JSP View
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    /**
     * JSON View
     * @return
     */
    @Bean
    public MappingJackson2JsonView jsonView() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setContentType("application/json;charset=UTF-8");
        return jsonView;
    }
    
    @Bean
    public BeanNameViewResolver beanNameResolver() {
    	BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
    	beanNameViewResolver.setOrder(0);
        return beanNameViewResolver;
    }

    /**
     * Excel View
     * @return
     */
    @Bean
    public ExcelView excelView() {
    	return new ExcelView();
    }
    

}
