package com.taxholic.core.socket;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatUtil {
	
	/**
	 * String to Json
	 * @param message
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static Message getJson(TextMessage message) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return  mapper.readValue(message.getPayload(), Message.class);
	}

	/**
	 * Joson to String
	 * @param msg
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String getStringJson(Message msg) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
    	return mapper.writeValueAsString(msg);
    }
	
	public static TextMessage stringToText(String msg){
		return new TextMessage(msg);
	}
}