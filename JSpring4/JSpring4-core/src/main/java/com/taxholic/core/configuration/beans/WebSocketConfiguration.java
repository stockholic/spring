package com.taxholic.core.configuration.beans;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.taxholic.core.socket.ChatHandler;


@Configuration
public class WebSocketConfiguration  implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(chatHandler(), "/chat.do");
    	
        registry.addHandler(chatHandler(), "/zzz").withSockJS()
//     	.setClientLibraryUrl("http://localhost:8080/static/js/sockjs-1.0.0.min.js")
        .setStreamBytesLimit(512 * 1024)	//512K default 128k
        .setHttpMessageCacheSize(1000)	//default 100
        .setDisconnectDelay(30 * 1000);		//30s default 5s
    }
    
    @Bean
    public WebSocketHandler chatHandler() {
        return new ChatHandler();
    }
    
}
