package kr.zchat.chat;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mysql.jdbc.StringUtils;

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
	
	
	private Integer initChannelId = ChatCmd.CHANNEL_ID;
	
	
	/**
	 * 채널등록 : 기본 채널 생성 
	 */
	public ChatManager(){
		channelMap.put(initChannelId, new Channel(initChannelId, "대기실",""));
		logger.debug("Create Channel : {}, {}",initChannelId, "대기실");
	}
	
	/**
	 * 대기실 유저 등록
	 * @param session
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public void addSessionByChannel(WebSocketSession session, Integer channelId) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException{
		
		logger.debug("isChannel : {}, channelId: {}", isCnannel(channelId),channelId);

		if(isCnannel(channelId)){
			
			//세션 목록에 채널 등록
			sessionMap.put(session.getId(), channelId );
			
			//채널 입장 - 채널에 유저 등록
			channelMap.get(channelId).addUser(session, channelId);
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
	 * 채널명 존재 확인
	 * @param channelId
	 * @return
	 */
	public boolean isCnannelNm(String channelNm){
		return  channelMap.entrySet().stream().anyMatch(ch -> ch.getValue().getChannel().equals(channelNm));
	}
	
	/**
	 * 세션목록 제거
	 * @param session
	 */
	public void deleteSession(WebSocketSession session){
		
		//유저가 존재하지 않는 채널을 제거한다(대기실 제외)
		Integer channelId = getChannelId(session);
		if(ChatCmd.CHANNEL_ID != channelId && getChannelUserCnt(channelId) == 0 ){
			channelMap.remove(channelId);
		}
		
		//sessionMap에서 삭제
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
	 * 채널 생성
	 * @param session
	 * @param msg
	 * @throws JsonProcessingException 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void createChannel(WebSocketSession session, Message msg) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		logger.debug("channelNm : {}", isCnannelNm(msg.getChannelNm()));
		
		Integer newChannelId = null;
		
		//채널이 없으면 생성
		if(!isCnannelNm(msg.getChannelNm())){
			initChannelId++;
			channelMap.put(initChannelId, new Channel(initChannelId, msg.getChannelNm(),""));
			logger.debug("Create Channel : {}, {}",initChannelId, msg.getChannelNm());
		}

		//채널명으로 채널아이디 찾기
		for (Entry<Integer, Channel> entry :channelMap.entrySet() ){
			Channel value = entry.getValue();
			if(value.getChannel().equals(msg.getChannelNm())){
				newChannelId = value.getChannelId();
				break;
			}
		}

		//비번방 유무
		if( !StringUtils.isNullOrEmpty(isPassword(newChannelId)) && msg.getPwd()==null ){
			
			msg.setCmd(ChatCmd.IS_PASSWORD);
	    	msg.setChannelId(newChannelId);
	    	msg.setChannelNm(msg.getChannelNm());
	    	msg.setUserNick(getUserNick(session));
	    	sendUserMsg(session, msg);
			
		}else {
			//--------------------------------------------------------------- 이전 채널 유저 제거
			//유저 제거
			deleteUser(session);		
			
			if(ChatCmd.CHANNEL_ID != getChannelId(session)){
				//채널 퇴장 전송
				msg.setCmd(ChatCmd.CHANNEL_OUT);
				sendChannelMsg(session, msg);
				
				//유저목록 전송
				sendUserList(session);	
			}
			
			//--------------------------------------------------------------- 대기실 -> 채널 세션목록 변경
			sessionMap.put(session.getId(), newChannelId);		
			
			//--------------------------------------------------------------- 변경 채널 유저 추가
			channelMap.get(getChannelId(session)).addUser(session, newChannelId);	
			
			//개인별 채널정보 메세지 전송
			msg.setCmd(ChatCmd.CHANNEL_INFO);
			msg.setChannelId(newChannelId);
			msg.setChannelNm(msg.getChannelNm());
			msg.setUserNick(getUserNick(session));
			sendUserMsg(session, msg);
			
			//채널별 입장 메세지 전송
			msg.setCmd(ChatCmd.CHANNEL_IN);
			sendChannelMsg(session, msg);
			
			//유저목록 전송
			sendUserList(session);	
		}
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
	 * 채널별 유저 
	 * @return
	 */
	public int getChannelUserCnt(Integer channelId){
		return channelMap.get(channelId).getUserCnt();
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

	/**
	 * 채널별 유저(닉) 명
	 * @param session
	 * @param msg 
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public void nickChange(WebSocketSession session, Message msg) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException{
		String beforeNick = channelMap.get(getChannelId(session)).getUserNick(session.getId());
		
		channelMap.get(getChannelId(session)).changeUserNick(session.getId(), msg.getChangeNick());
		
		//개인별 채널정보 메세지 전송
    	msg.setCmd(ChatCmd.NICK_CHANGE_FINISH);
    	msg.setOldNick(beforeNick);
    	
    	sendChannelMsg(session, msg);
		
    	sendUserList(session);	
	}

	/**
	 * 비번 생성
	 * @param session
	 * @param msg
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public void pwdCreate(WebSocketSession session, Message msg) throws JsonParseException, JsonMappingException, IOException {
		
		
		//channelMap.forEach((k,v)->System.out.println(k+" === "+v.toString() ));
		Integer goChannelId = null;
		//채널명으로 채널아이디 찾기
		for (Entry<Integer, Channel> entry :channelMap.entrySet() ){
			Channel value = entry.getValue();
			if(value.getChannel().equals(msg.getChannelNm())){
				goChannelId = value.getChannelId();
				break;
			}
		}
		
		if(!StringUtils.isNullOrEmpty( isPassword(goChannelId) ) && msg.getPwd().equals(channelMap.get(goChannelId).getChannelPwd()) ){		//비번방에 비번이 맞음
			createChannel(session, msg);
			msg.setCmd(ChatCmd.CREATE_PASSWORD_FINISH);
			sendUserMsg(session, msg);
		}else if( !StringUtils.isNullOrEmpty( isPassword(goChannelId) ) && !msg.getPwd().equals(channelMap.get( goChannelId ).getChannelPwd()) ){		//비번방에 비번이 틀림
			msg.setCmd(ChatCmd.FALSE_PASSWORD);
	    	msg.setChannelId(goChannelId);
	    	sendUserMsg(session, msg);
		}else{
			channelMap.get(goChannelId).setChannelPwd(msg.getPwd());
			msg.setCmd(ChatCmd.CREATE_PASSWORD_FINISH);
			msg.setPwd("true");
			sendChannelMsg(session, msg);
		}
	}
	public String isPassword(Integer channelId){
		return channelMap.get(channelId).getChannelPwd();
	}
}
