package kr.zchat.chat;

import java.io.IOException;
import java.text.StringCharacterIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	/**
	 * String to Object
	 * @param msg
	 * @return
	 */
	public static TextMessage stringToText(String msg){
		return new TextMessage(msg);
	}
	
	/**
	 * messageConvert convert
	 * @param str
	 * @return
	 */
	public static String messageConvert(String str, int len){
		return changeLink(changeTag(stringLimit(str,len)));
	}
	
	/**
	 * <pre>
	 * String null or empty("") check
	 * </pre>
	 * @param str
	 * @return boolean 널이거나 공백일경우 true 아니면 false
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.equals("") ? true : false;
	}
	
	/**
	 * 태그 치환
	 * @param str
	 * @return
	 */
	public static String changeTag(String str){
		return (str == null) ? "" : str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	/**
	 * 링크 치환
	 * @param str
	 * @return
	 */
	public static String changeLink(String str){
		String regx = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?«»“”‘’]))";
		Pattern patt = Pattern.compile(regx);
		Matcher matcher = patt.matcher(str);
		return matcher.replaceAll("<a href=\"$1\" target=\"_blank\">$1</a>");
	}
	
	/**
	 * 문자열 제한
	 * @param str
	 * @param len
	 * @return
	 */
	public static String stringLimit(String str, int len){
		if( str.length() <= len){
			return str;
		}
		StringCharacterIterator sci = new StringCharacterIterator(str);
		StringBuffer buffer = new StringBuffer();
		buffer.append(sci.first());
		for(int i=1; i<len ; i++){
			if( i < len-1){
				buffer.append(sci.next());
			}else{
				char c=sci.next();
				if(c != 32){
					buffer.append(c);
				}
			}
		}	
		return buffer.toString();
	}
}