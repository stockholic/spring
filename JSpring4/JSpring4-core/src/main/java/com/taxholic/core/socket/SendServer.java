package com.taxholic.core.socket;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class SendServer extends TextWebSocketHandler {
	
	Logger logger =  LoggerFactory.getLogger(this.getClass());

	private boolean isSeverMsg = false;
	
	
	 @Override
    public void afterConnectionEstablished(WebSocketSession session)   throws Exception {
    	logger.debug("connect sessionId : {}", session.getId());
        if(!isSeverMsg) sendServer(session);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session,  CloseStatus status) throws Exception {
        logger.debug("close sessionId : {}", session.getId());
    }
    
    
    public void sendServer(WebSocketSession session){
    	
    	isSeverMsg = true;

    	ScheduledExecutorService execService =	Executors.newScheduledThreadPool(5);
		execService.scheduleAtFixedRate(() -> {
			try {
				handleTextMessage(session, new TextMessage("------------ sever message : 차카게 살자 ------------"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 0, 5000L, TimeUnit.MILLISECONDS);
		
    }
    
}
