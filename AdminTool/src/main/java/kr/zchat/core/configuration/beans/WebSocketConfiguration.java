package kr.zchat.core.configuration.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import kr.zchat.core.configuration.handler.SystemInfoHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfiguration  implements WebSocketConfigurer {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	SystemInfoHandler systemInfoHandler;
	
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemInfoHandler(), "/system");
    }
    
    @Bean
    public WebSocketHandler systemInfoHandler() {
    	systemInfoHandler = new SystemInfoHandler();
        return systemInfoHandler;
    }
    
    @PostConstruct
    public void postConstruct() {
		logger.info("WebSocket Created");
    }
    
    @PreDestroy
    public void preDestroy() {
    	systemInfoHandler.shutdown();
		logger.info("WebSocket shutdown");
    }
    
}
