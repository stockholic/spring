package kr.pethub.core.module.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;

public class ConsoleLog {

	
	@Value("${websocket.console}") 
	private String websocketUrl;
	
	private  WebSocketClient webSocket = null;
	
	/**
	 *  WebSocket 초기화
	 * @param wsUrl
	 * @throws URISyntaxException
	 */
	public void connect(String wsUrl) throws URISyntaxException{
		
		WebSocketClient ws = new WebSocketClient(new URI(wsUrl)) {
	
			@Override
			public void onOpen(ServerHandshake serverHandshake) {
				// WebSocket 서버 연결 후 동작 정의,
			}

			@Override
			public void onMessage(String message) {
				// WebSocket 서버에서 메시지 수신시 동작 정의
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
				// 서버 연결 종료 후 동작 정의
			}

			@Override
			public void onError(Exception ex) {
				// 예외 발생시 동작 정의
			}

		};
		
		webSocket = ws;
		webSocket.connect();
	}
	
	
	/**
	 * WebSocket 연결
	 * @return
	 * @throws URISyntaxException
	 */
	public WebSocketClient getConsole() throws URISyntaxException {
		
		if(webSocket == null) connect(websocketUrl);
		 
		return webSocket;
	}

}
