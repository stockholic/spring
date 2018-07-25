package com.taxholic.core.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class ChatHandler extends TextWebSocketHandler {
	
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	private ChatManager chatManager;
	
//	@Autowired
//	private CommonDao dao;

	public ChatHandler(){
		this.chatManager = new ChatManager();
	}
	
	/**
	 * 클라이언트 메세지 수신
	 * @param session
	 * @param message
	 * @throws Exception
	 */
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	
    	Message msg = ChatUtil.getJson(message);
    	msg.setUserNick(chatManager.getUserNick(session));
    	
    	//채널변경 요청
    	if(msg.getCmd().equals(ChatCmd.CHANNEL_CHANGE)){
    		chatManager.changeChannel(session, msg);
    	}else{
    		chatManager.sendChannelMsg(session, msg);
    	}
    }
    
	 /**
	  * 클라이언트 접속 후 처리
	  */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)   throws Exception {
    	
//    	logger.debug("Dao Test : {}", dao.getString("auth.selectId"));

    	//기본 채널 유저 등록
    	chatManager.addSessionByChannel(session, ChatCmd.CHANNEL_ID);
    	
    	Message msg = new Message();
    	msg.setChannelId(chatManager.getChannelId(session));
    	msg.setChannelNm(chatManager.getChannelNm(session));
    	msg.setUserNick(chatManager.getUserNick(session));

    	//개인별 채널정보 메세지 전송
    	msg.setCmd(ChatCmd.CHANNEL_INFO);
    	chatManager.sendUserMsg(session, msg);

    	//채널별 입장 메세지 전송
    	msg.setCmd(ChatCmd.CHANNEL_IN);
    	chatManager.sendChannelMsg(session, msg);
    	
    	//유저목록 전송
    	chatManager.sendUserList(session);			
    	
    	logger.debug("connect sessionId : {}, channel count : {}, session count : {}", session.getId(), chatManager.getChannelCnt(),chatManager.getSesionCnt());
    }
    
    /**
     * 클라이언트 접속 종료 후 처리
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session,  CloseStatus status) throws Exception {
    	
    	Message msg = new Message();
    	msg.setCmd(ChatCmd.CHANNEL_OUT);
    	msg.setUserNick(chatManager.getUserNick(session));
     	chatManager.sendChannelMsg(session, msg);
     	
     	chatManager.deleteUser(session);			//채널별 유저 제거
     	chatManager.sendUserList(session);			//유저목록 전송
     	chatManager.deleteSession(session);		//세션목록 제거
 
     	logger.debug("close sessionId : {},  session count : {}", session.getId(),  chatManager.getSesionCnt());
    }
    
    
    
}
