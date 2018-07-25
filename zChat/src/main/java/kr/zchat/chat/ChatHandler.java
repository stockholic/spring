package kr.zchat.chat;

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
    	
    	if(msg.getCmd().equals(ChatCmd.CHANNEL_CREATE)){
			chatManager.createChannel(session, msg);
    	}else if(msg.getCmd().equals(ChatCmd.NICK_CHANGE)){
			chatManager.nickChange(session, msg);
    	}else if(msg.getCmd().equals(ChatCmd.PASSWORD_CREATE)){
    		chatManager.pwdCreate(session, msg);
		}else{
			msg.setMsg(ChatUtil.messageConvert(msg.getMsg(), 200));
			chatManager.sendChannelMsg(session, msg);
    	}
    }
    
	 /**
	  * 클라이언트 접속 후 처리
	  */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)   throws Exception {
    	
//    	logger.debug("Dao Test : {}", dao.getString("auth.selectId"));

    	//기본 채널(대기실) 유저 등록
    	chatManager.addSessionByChannel(session, ChatCmd.CHANNEL_ID);
    	
    	//채널연결(대기실) 완료 후 채널 변경을 요청한다.
    	Message msg = new Message();
    	msg.setCmd(ChatCmd.CHANNEL_CONNECT_FINISH);
    	chatManager.sendUserMsg(session, msg);
    	
    	logger.info("connect sessionId : {}, channel count : {}, session count : {}", session.getId(), chatManager.getChannelCnt(),chatManager.getSesionCnt());
    }
    
    /**
     * 클라이언트 접속 종료 후 처리
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session,  CloseStatus status) throws Exception {
    	
    	if(chatManager.getChannelId(session) != ChatCmd.CHANNEL_ID){
	    	Message msg = new Message();
	    	msg.setCmd(ChatCmd.CHANNEL_OUT);
	    	msg.setUserNick(chatManager.getUserNick(session));
	    	chatManager.sendChannelMsg(session, msg);
    	}
     	
     	chatManager.deleteUser(session);			//채널별 유저 제거
     	
     	if(chatManager.getChannelId(session) != ChatCmd.CHANNEL_ID){
     		chatManager.sendUserList(session);			//유저목록 전송
     	}
     	
     	chatManager.deleteSession(session);		//세션목록 제거
 
     	logger.info("close sessionId : {}, channel count : {},  session count : {}", session.getId(),  chatManager.getChannelCnt(), chatManager.getSesionCnt());
    }
    
    
    
}
