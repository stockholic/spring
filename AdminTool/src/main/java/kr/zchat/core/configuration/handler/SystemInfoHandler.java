package kr.zchat.core.configuration.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;


public class SystemInfoHandler extends TextWebSocketHandler {
	
	Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	
	ScheduledThreadPoolExecutor exec ;
	
	public SystemInfoHandler() {
		pushSystemInfo();
	}
	
	 @Override
    public void afterConnectionEstablished(WebSocketSession session)   throws Exception {
		//the messages will be broadcasted to all users.
	    sessions.add(session);
    	logger.info("connect sessionId : {} , count :  {}", session.getId(), sessions.size());
    	
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session,  CloseStatus status) throws Exception {
    	sessions.remove(session);
        logger.info("close sessionId : {}, count :  {}", session.getId(), sessions.size());
    }
    
    public void pushSystemInfo() {
    	
    	SystemInfo systemInfo = new SystemInfo();
		exec = new ScheduledThreadPoolExecutor(1);
		
		exec.scheduleAtFixedRate(new Runnable() {

			public void run() {
				
				Map<String,Object> system = new HashMap<String,Object>();
				HardwareAbstractionLayer hal = systemInfo.getHardware();
				SystemInfo si = new SystemInfo();
			    OperatingSystem os = si.getOperatingSystem();
				
				//---------- CPU 정보
				Map<String,Object> cpu = new HashMap<String,Object>();
				cpu.put("name", hal.getProcessor().getName());
				cpu.put("load", hal.getProcessor().getSystemCpuLoadBetweenTicks());
				
				//---------- Memory 정보
				Map<String,Object> memory = new HashMap<String,Object>();
				memory.put("total", hal.getMemory().getTotal());
				memory.put("available", hal.getMemory().getAvailable());
				
				//---------- Traffic 정보
				List<Map<String,Object>> nets = new ArrayList<Map<String,Object>>();
				for (NetworkIF net : hal.getNetworkIFs()) {
					if( net.getIPv4addr().length > 0 ) {
//						 boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0 || net.getPacketsSent() > 0;
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("name", net.getName());
						map.put("displayName", net.getDisplayName());
						map.put("speed", net.getSpeed());
						map.put("recvPacket", net.getPacketsRecv());
						map.put("sentPacket", net.getPacketsSent());
						nets.add(map);
					}
				}
				
				//---------- Disk 정보
				List<Map<String,Object>> disks = new ArrayList<Map<String,Object>>();
				OSFileStore[] fsArray = os.getFileSystem().getFileStores();
				for (OSFileStore fs : fsArray) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("name", fs.getName());
					map.put("mount", fs.getMount());
					map.put("usable", fs.getUsableSpace());
					map.put("total", fs.getTotalSpace());
					disks.add(map);
				}
				
				system.put("cpu",cpu);
				system.put("memory",memory);
				system.put("net",nets);
				system.put("disk",disks);
				
				//---------- 데이터 송신
				ObjectMapper mapper = new ObjectMapper();  
				for (WebSocketSession webSocketSession : sessions) {
					try {
						webSocketSession.sendMessage(new TextMessage(mapper.writeValueAsString(system)));
					} catch (IOException e) {
						shutdown();
						e.printStackTrace();
					}
				}
			}
		}, 5, 1, TimeUnit.SECONDS);
    }
    
    public void shutdown() {
    	exec.shutdown();
    }
    
    
    
}
