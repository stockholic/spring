package kr.zchat.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Channel {
	
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	private Integer channelId;
	private String channelNm;
	private String channelPwd;

	private Map<String, User> userMap = new HashMap<String, User>();
	
	private int userCnt = 1;
	
	
	public Channel(Integer channelId, String channelNm,String channelPwd) {
		this.channelId = channelId;
		this.channelNm = channelNm;
		this.channelPwd = channelPwd;
	}
	
	/**
	 * 유저 등록
	 * @param session
	 * @param channelId
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public void addUser(WebSocketSession session, Integer channelId){
		userMap.put(session.getId(), new User(this.channelId, this.channelNm, "손님" + this.userCnt ,session));
		if(ChatCmd.CHANNEL_ID != channelId) this.userCnt++;
//		logger.debug("user count : {}", userMap.size());
	}
	
	/**
	 * 유저 제거
	 * @param session
	 */
	public void deleteUser(WebSocketSession session){
		userMap.entrySet().removeIf(
			entry -> entry.getKey().equals(session.getId())
		);
	}
	
	/**
	 * 유저목록 전송
	 * @param session
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public void sendUserList(WebSocketSession session) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException  {
		
		Message msg = new Message();
     	List<String> user = new ArrayList<String>();
		userMap.forEach((k,v) -> user.add(v.getUserNick()));
		msg.setUserList(user);
    	msg.setCmd(ChatCmd.USER_LIST);

    	sendChannelMsg(msg);
	}
	
	/**
	 * 채널내 메세지 발송
	 * @param msg
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void sendChannelMsg(Message msg) throws JsonParseException, JsonMappingException, IOException {
		
		userMap.forEach((k,v) ->  {
			try {
				if(v.getSession().isOpen())
					sendUserMsg(v.getSession(), msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * 유저별 메세지 발송
	 * @param session
	 * @param msg
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public void sendUserMsg(WebSocketSession session, Message msg) throws JsonParseException, JsonMappingException, IOException {
		session.sendMessage(ChatUtil.stringToText( ChatUtil.getStringJson(msg) ));
	}	
	
	/**
	 * 채널 명
	 * @return
	 */
	public String getChannel(){
		return this.channelNm;
	}

	/**
	 * 채널 아이디
	 * @return
	 */
	public Integer getChannelId(){
		return this.channelId;
	}
	
	/**
	 * 유저(닉) 명
	 * @param sessionId
	 * @return
	 */
	public String getUserNick(String sessionId){
		return userMap.get(sessionId).getUserNick();
	}
	
	/**
	 * 유저(닉) 변경
	 * @param sessionId
	 * @param string 
	 * @return
	 */
	public void changeUserNick(String sessionId, String changeNick){
		userMap.get(sessionId).setUserNick(changeNick);
	}
	
	/**
	 * 유저수
	 * @return
	 */
	public int getUserCnt(){
		return userMap.size();
	}

	public String getChannelPwd() {
		return channelPwd;
	}

	public void setChannelPwd(String channelPwd) {
		this.channelPwd = channelPwd;
	}

	@Override
	public String toString() {
		return "Channel [channelId=" + channelId + ", channelNm=" + channelNm + ", channelPwd=" + channelPwd
				+ ", userMap=" + userMap + "]";
	}
	
}
