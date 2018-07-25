package com.taxholic.core.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ChatManager {
	
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 채널 목록
	 */
	private Map<Integer, Channel> channelMap = new HashMap<Integer, Channel>();

	/**
	 * 세선 목록 : 채널정보가 있는 세션목록
	 */
	private Map<String, Integer> sessionMap = new HashMap<String, Integer>();
	
	
	private Integer channelId = ChatCmd.CHANNEL_ID;
	
	private int tmpCnt = 1;
	
	/**
	 * 채널등록 : 기본 채널 생성 
	 */
	public ChatManager(){
		for(int i = 1; i <=3; i++){
			channelMap.put(channelId,new Channel(channelId, "Channel " + i));
			logger.debug("Create Channel : {}, {}",channelId, "Channel" + i);
			channelId++;
		}
	}
	
	/**
	 * 채널별 유저 등록
	 * @param session
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public void addSessionByChannel(WebSocketSession session, Integer channelId) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException{
		
		logger.debug("isChannel : {}", isCnannel(channelId));

		if(isCnannel(channelId)){
			
			//세션 목록에 채널 등록
			sessionMap.put(session.getId(), channelId );
			
			//채널 입장 - 채널에 유저 등록
			channelMap.get(channelId).addUser(session, "메렁" + tmpCnt);
			tmpCnt++;
		}
		
	}
	
	/**
	 * 채널존재 확인
	 * @param channelId
	 * @return
	 */
	public boolean isCnannel(Integer channelId){
		return  channelMap.entrySet().stream().anyMatch(ch -> ch.getKey().equals(channelId));
	}
	
	/**
	 * 채널 제거
	 * @param session
	 */
	public void deleteChannel(WebSocketSession session){
		
	}

	/**
	 * 세션목록 제거
	 * @param session
	 */
	public void deleteSession(WebSocketSession session){
		sessionMap.entrySet().removeIf(
			entry -> entry.getKey().equals(session.getId())
		);
	}
	
	/**
	 * 채널별 유저 제거
	 * @param session
	 */
	public void deleteUser(WebSocketSession session){
		channelMap.get(getChannelId(session)).deleteUser(session);
	}
	
	/**
	 * 유저목록 전송
	 * @param session
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public void sendUserList(WebSocketSession session) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException   {
		channelMap.get(getChannelId(session)).sendUserList(session);
	}
	
	/**
	 * 채널 변경
	 * @param session
	 * @param msg
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void changeChannel(WebSocketSession session, Message msg) throws JsonParseException, JsonMappingException, IOException  {
		
		logger.debug("channelId : {}, channelNm : {}", getChannelId(session), getChannelNm(session));
		
		//--------------------------------------------------------------- 이전 채널 유저 제거
		//유저 제거
		deleteUser(session);		
		
		//채널 퇴장 전송
		msg.setCmd(ChatCmd.CHANNEL_OUT);
		sendChannelMsg(session, msg);
		
		//유저목록 전송
    	sendUserList(session);	
		
		//--------------------------------------------------------------- 세션목록 변경
		sessionMap.put(session.getId(), msg.getChannelId());		
		
		//--------------------------------------------------------------- 변경 채널 유저 추가
		channelMap.get(getChannelId(session)).addUser(session, msg.getUserNick());	
		
		//개인별 채널정보 메세지 전송
    	msg.setCmd(ChatCmd.CHANNEL_INFO);
    	msg.setChannelId(getChannelId(session));
    	msg.setChannelNm(getChannelNm(session));
    	sendUserMsg(session, msg);
    	
    	//채널별 입장 메세지 전송
    	msg.setCmd(ChatCmd.CHANNEL_IN);
    	sendChannelMsg(session, msg);
    	
    	//유저목록 전송
    	sendUserList(session);		
		
		logger.debug("channelId : {}, channelNm : {}", getChannelId(session), getChannelNm(session));
	}
	
	/**
	 * 채널내 메세지 발송
	 * @param session
	 * @param message
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void sendChannelMsg(WebSocketSession session, Message msg) throws JsonParseException, JsonMappingException, IOException  {
		channelMap.get(getChannelId(session)).sendChannelMsg(msg);
	}
	
	/**
	 * 유저별 메세지 발송
	 * @param session
	 * @param msg
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void sendUserMsg(WebSocketSession session, Message msg) throws JsonParseException, JsonMappingException, IOException  {
		channelMap.get(getChannelId(session)).sendUserMsg(session, msg);
	}

	/**
	 * 채널 아이디
	 * @param session
	 * @return
	 */
	public Integer getChannelId(WebSocketSession session){
		return sessionMap.get(session.getId());
	}
	
	
	/**
	 * 채널 명
	 * @param session
	 * @return
	 */
	public String getChannelNm(WebSocketSession session){
		return channelMap.get(getChannelId(session)).getChannel();
	}
	
	/**
	 * 채널 수
	 * @return
	 */
	public int getChannelCnt(){
		return channelMap.size();
	}
	
	/**
	 * 세션 수
	 * @return
	 */
	public int getSesionCnt(){
		return sessionMap.size();
	}
	
	/**
	 * 채널별 유저(닉) 명
	 * @param session
	 */
	public String getUserNick(WebSocketSession session){
		return channelMap.get(getChannelId(session)).getUserNick(session.getId());
	}
	    
}
